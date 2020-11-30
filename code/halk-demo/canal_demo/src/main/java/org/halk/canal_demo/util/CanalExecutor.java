package org.halk.canal_demo.util;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.otter.canal.client.CanalConnector;
import com.alibaba.otter.canal.client.CanalConnectors;
import com.alibaba.otter.canal.protocol.CanalEntry.*;
import com.alibaba.otter.canal.protocol.Message;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.io.IOException;
import java.io.InputStream;
import java.net.InetSocketAddress;
import java.util.List;
import java.util.Properties;

/**
 * Created by zhangpan on 2019/1/4.
 */
public class CanalExecutor {

    private static final Log logger = LogFactory.getLog(CanalExecutor.class);

    /**
     * canal服务器IP
     */
    private static String IP;

    /**
     * canal的端口号
     */
    private static int PORT;

    /**
     * canal扫描数据库的规则
     */
    private static String MYSQL_REGEX;

    private final static String KEY_PREFIX_MYSQL = "ddpt:plan:mysql:";

    public CanalExecutor() {
        InputStream inputStream = CanalExecutor.class.getClassLoader().getResourceAsStream("application.properties");
        Properties properties = new Properties();
        try {
            properties.load(inputStream);
        } catch (IOException e) {
            logger.error("canal服务获取配置参数异常", e);
        }
        IP = properties.getProperty("canal.instance.master.ip");
        PORT = Integer.parseInt(properties.getProperty("canal.instance.master.port"));
        MYSQL_REGEX = properties.getProperty("canal.instance.filter.regex");

        try {
            inputStream.close();
        } catch (IOException e) {
            logger.error("canal读取配置流关闭异常", e);
        }

        CanalConnector connector = CanalConnectors.newSingleConnector(new InetSocketAddress(IP,
                PORT), "example", "", "");
        //获取服务端消息
        int batchSize = 100;
        try {
            connector.connect();
            connector.subscribe(MYSQL_REGEX);
            connector.rollback();
            while (true) {
                // 获取指定数量的数据
                Message message = connector.getWithoutAck(batchSize);
                long batchId = message.getId();
                int size = message.getEntries().size();
                logger.info("batchId = " + batchId  + ";" + "size = " + size);
                if (batchId == -1 || size == 0) {
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                } else {
                    printEntry(message.getEntries());
                }
                // 提交确认
                connector.ack(batchId);
                // connector.rollback(batchId); // 处理失败, 回滚数据
            }
        } finally {
            connector.disconnect();
        }
    }

    private static void printEntry(List<Entry> entrys) {
        for (Entry entry : entrys) {
            if (entry.getEntryType() == EntryType.TRANSACTIONBEGIN || entry.getEntryType() == EntryType.TRANSACTIONEND) {
                continue;
            }
            RowChange rowChage = null;
            try {
                rowChage = RowChange.parseFrom(entry.getStoreValue());
            } catch (Exception e) {
                throw new RuntimeException("ERROR ## parser of eromanga-event has an error , data:" + entry.toString(),
                        e);
            }
            EventType eventType = rowChage.getEventType();
            System.out.println(String.format("================> binlog[%s:%s] , name[%s,%s] , eventType : %s",
                    entry.getHeader().getLogfileName(), entry.getHeader().getLogfileOffset(),
                    entry.getHeader().getSchemaName(), entry.getHeader().getTableName(),
                    eventType));

            for (RowData rowData : rowChage.getRowDatasList()) {

                if (eventType == EventType.DELETE) {
                    redisDelete(rowData.getBeforeColumnsList(), entry);
                } else if (eventType == EventType.INSERT) {
                    redisInsert(rowData.getAfterColumnsList(), entry);
                } else {
                    System.out.println("-------> before");
                    printColumn(rowData.getBeforeColumnsList());
                    System.out.println("-------> after");
                    redisUpdate(rowData.getAfterColumnsList(), entry);
                }
            }
        }
    }

    private static void printColumn(List<Column> columns) {
        for (Column column : columns) {
            System.out.println(column.getName() + " : " + column.getValue() + "    update=" + column.getUpdated());
        }
    }

    private static void redisInsert(List<Column> columns, Entry entry) {
        JSONObject json = new JSONObject();
        for (Column column : columns) {
            json.put(column.getName(), column.getValue());
        }
        if (columns.size() > 0) {
            StringBuffer keyPrefixMysql = new StringBuffer();
            keyPrefixMysql.append(KEY_PREFIX_MYSQL)
                    .append(entry.getHeader().getSchemaName()).append(":")
                    .append(entry.getHeader().getTableName()).append(":")
                    .append(columns.get(0).getValue());
            JedisUtil.getInstance().set(keyPrefixMysql.toString(), json.toJSONString(), null);
        }
    }

    private static void redisUpdate(List<Column> columns, Entry entry) {
        JSONObject json = new JSONObject();
        for (Column column : columns) {
            json.put(column.getName(), column.getValue());
        }
        if (columns.size() > 0) {
            StringBuffer keyPrefixMysql = new StringBuffer();
            keyPrefixMysql.append(KEY_PREFIX_MYSQL)
                    .append(entry.getHeader().getSchemaName()).append(":")
                    .append(entry.getHeader().getTableName()).append(":")
                    .append(columns.get(0).getValue());
            JedisUtil.getInstance().set(keyPrefixMysql.toString(), json.toJSONString(), null);
        }
    }

    private static void redisDelete(List<Column> columns, Entry entry) {
        JSONObject json = new JSONObject();
        for (Column column : columns) {
            json.put(column.getName(), column.getValue());
        }
        if (columns.size() > 0) {
            StringBuffer keyPrefixMysql = new StringBuffer();
            keyPrefixMysql.append(KEY_PREFIX_MYSQL)
                    .append(entry.getHeader().getSchemaName()).append(":")
                    .append(entry.getHeader().getTableName()).append(":")
                    .append(columns.get(0).getValue());
            JedisUtil.getInstance().delete(keyPrefixMysql.toString());
        }
    }
}

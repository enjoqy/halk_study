package org.halk.seckill.zookeeper;

import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooKeeper;
import org.apache.zookeeper.data.Stat;
import org.halk.seckill.controller.ProductController;
import org.halk.seckill.controller.SeckillController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import sun.rmi.runtime.Log;

/**
 * @Author halk
 * @Date 2020/8/6 0006 17:25
 */
public class ZooKeeperWatcher implements Watcher {

    @Autowired
    private ZooKeeper zooKeeper;

    private static final Logger log = LoggerFactory.getLogger(ZooKeeperWatcher.class);

    @Override
    public void process(WatchedEvent event) {

        if (event.getType() == Event.EventType.None && event.getPath() == null){
            log.info("==============zookeeper连接成功==========");
        }

        // zk连接成功通知事件
        if (Event.KeeperState.SyncConnected == event.getState()){
            if (Event.EventType.None == event.getType() && null == event.getPath()){
            }else if (event.getType() == Event.EventType.NodeDataChanged){  // zk目录节点数据变化通知事件
                try {
                    String path = event.getPath();
                    String s = new String(zooKeeper.getData(path, new ZooKeeperWatcher(), new Stat()));
                    SeckillController.getProductSoldOutMap().remove("");
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }
}

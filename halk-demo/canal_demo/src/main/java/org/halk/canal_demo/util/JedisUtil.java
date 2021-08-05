package org.halk.canal_demo.util;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

import java.io.IOException;
import java.io.InputStream;
import java.util.Map;
import java.util.Properties;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Jedis工具类
 */
public final class JedisUtil {

    private static final Log logger = LogFactory.getLog(JedisUtil.class);

    //Redis服务器IP
    private static String IP;

    //Redis的端口号
    private static int PORT;

    //Redis服务密码
    private static String PASSWORD;

    //可用连接实例的最大数目，默认值为8；
    //如果赋值为-1，则表示不限制；如果pool已经分配了maxActive个jedis实例，则此时pool的状态为exhausted(耗尽)。
    private static int MAX_ACTIVE;

    //控制一个pool最多有多少个状态为idle(空闲的)的jedis实例，默认值也是8。
    private static int MAX_IDLE;

    //等待可用连接的最大时间，单位毫秒，默认值为-1，表示永不超时。如果超过等待时间，则直接抛出JedisConnectionException；
    private static int MAX_WAIT;

    private static int TIMEOUT;

    //在borrow一个jedis实例时，是否提前进行validate操作；如果为true，则得到的jedis实例均是可用的；
    private static boolean TEST_ON_BORROW;

    //在return给pool时，是否提前进行validate操作；
    private static boolean TEST_ON_RETURN;

    /**
     * redis中的key值的默认过期时间
     */
    private static Integer EXPIRATION_TIME;

    private static Map<String, JedisPool> maps = new ConcurrentHashMap<>();

    private JedisUtil() {
        InputStream inputStream = JedisUtil.class.getClassLoader().getResourceAsStream("application.properties");
        Properties properties = new Properties();
        try {
            properties.load(inputStream);
        } catch (IOException e) {
            logger.error("redis连接池获取配置参数异常", e);
        }
        IP = properties.getProperty("redis.jedis.IP");
        PORT = Integer.parseInt(properties.getProperty("redis.jedis.PORT"));
        EXPIRATION_TIME = Integer.parseInt(properties.getProperty("redis.jedis.EXPIRATION_TIME"));
        MAX_ACTIVE = Integer.parseInt(properties.getProperty("redis.jedis.MAX_ACTIVE"));
        MAX_IDLE = Integer.parseInt(properties.getProperty("redis.jedis.MAX_IDLE"));
        MAX_WAIT = Integer.parseInt(properties.getProperty("redis.jedis.MAX_WAIT"));
        TIMEOUT = Integer.parseInt(properties.getProperty("redis.jedis.TIMEOUT"));
        TEST_ON_BORROW = Boolean.parseBoolean(properties.getProperty("redis.jedis.TEST_ON_BORROW"));
        TEST_ON_RETURN = Boolean.parseBoolean(properties.getProperty("redis.jedis.TEST_ON_RETURN"));
        EXPIRATION_TIME = Integer.parseInt(properties.getProperty("redis.jedis.EXPIRATION_TIME"));

        try {
            inputStream.close();
        } catch (IOException e) {
            logger.error("redis读取配置流关闭异常", e);
        }
    }

    /**
     * 类级的内部类，也就是静态的成员式内部类，该内部类的实例与外部类的实例 没有绑定关系，而且只有被调用到时才会装载，从而实现了延迟加载。
     */
    private static class RedisUtilHolder {
        private static JedisUtil instance = new JedisUtil();
    }

    /**
     * 当getInstance方法第一次被调用的时候，它第一次读取 RedisUtilHolder.instance，导致RedisUtilHolder类得到初始化；
     * 而这个类在装载并被初始化的时候，会初始化它的静态域，从而创建RedisUtil的实例，由于是静态的域，因此只会在虚拟机装载类的时候初始化一次，
     * 并由虚拟机来保证它的线程安全性。 这个模式的优势在于，getInstance方法并没有被同步，
     * 并且只是执行一个域的访问，因此延迟初始化并没有增加任何访问成本。
     */
    public static JedisUtil getInstance() {
        return RedisUtilHolder.instance;
    }

    /**
     * 获取连接池.
     */
    private JedisPool getPool(String ip, int port) {
        String key = ip + ":" + port;
        JedisPool pool = null;
        if (!maps.containsKey(key)) {//根据ip和端口判断连接池是否存在.
            JedisPoolConfig config = new JedisPoolConfig();
            config.setMaxTotal(MAX_ACTIVE);
            config.setMaxIdle(MAX_IDLE);
            config.setMaxWaitMillis(MAX_WAIT);
            config.setTestOnBorrow(TEST_ON_BORROW);
            config.setTestOnReturn(TEST_ON_RETURN);

            //false:如果连接池没有可用Jedis连接，立即抛出异常;默认true时：如果连接池没有可用Jedis连接，
            //会等待maxWaitMillis(毫秒)，依然没有获取到可用Jedis连接
            //config.setBlockWhenExhausted(false);

            try {
                pool = new JedisPool(config, ip, port, TIMEOUT, PASSWORD);
                maps.put(key, pool);
            } catch (Exception e) {
                logger.error("初始化Redis连接池异常:", e);
            }
        } else {
            pool = maps.get(key);
        }
        return pool;
    }

    /**
     * 获取Jedis实例
     */
    public Jedis getJedis() {
        Jedis jedis = null;
        try {
            jedis = getPool(IP, PORT).getResource();
        } catch (Exception e) {
            logger.error("获取Jedis实例异常:", e);
            // 销毁对象
            getPool(IP, PORT).returnBrokenResource(jedis);
        }
        return jedis;
    }

    /**
     * 释放jedis资源到连接池
     */
    public void returnResource(final Jedis jedis) {
        if (jedis != null) {
            getPool(IP, PORT).returnResource(jedis);
        }
    }

    /**
     * 获取数据
     */
    public Object get(String key) {
        Object value = null;
        Jedis jedis = null;
        try {
            jedis = getJedis();
            value = jedis.get(key);
        } catch (Exception e) {
            logger.warn("获取数据异常:", e);
        } finally {
            //返还到连接池
            returnResource(jedis);
        }
        return value;
    }

    /**
     * Jedis 删除指定前缀的key对应的key，使用keys
     * @param givenKey
     * @return
     */
    public Boolean delValuesByKeys(String givenKey) {
        logger.info("开始模糊删除set中的数据，givenKey = " + givenKey);
        Jedis jedis = this.getJedis();
        Set<String> keys = jedis.keys(givenKey);
        for (String key : keys) {
            logger.info("当前 key 是 ：" + key);
            jedis.del(key);
        }
        return true;
    }

    //设置数据
    public boolean set(String key, String value, Integer expire) {
        Jedis jedis = null;
        try {
            jedis = getJedis();
            jedis.set(key, value);
            //设置过期时间
            if (expire != null){
                jedis.expire(key, expire);
            }
            jedis.expire(key, EXPIRATION_TIME);
            return true;
        } catch (Exception e) {
            logger.warn("设置数据异常", e);
            return false;
        } finally {
            //返还到连接池
            returnResource(jedis);
        }
    }

    //删除数据
    public void delete(String key) {
        Jedis jedis = null;
        try {
            jedis = getJedis();
            jedis.del(key);
        } catch (Exception e) {
            logger.warn("删除数据异常", e);
        } finally {
            //返还到连接池
            returnResource(jedis);
        }
    }
}


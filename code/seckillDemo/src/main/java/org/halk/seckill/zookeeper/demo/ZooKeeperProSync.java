package org.halk.seckill.zookeeper.demo;

import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooKeeper;
import org.apache.zookeeper.data.Stat;

import java.io.IOException;
import java.util.concurrent.CountDownLatch;

/**
 * @Author halk
 * @Date 2020/8/7 0007 15:06
 */
public class ZooKeeperProSync{

    private static CountDownLatch connectedSemaphore = new CountDownLatch(1);
    private static ZooKeeper zk = null;
    private static Stat stat = new Stat();

    public static void main(String[] args) throws IOException, InterruptedException, KeeperException {

        Watcher watcher = event -> {
            // zk连接成功通知事件
            if (Watcher.Event.KeeperState.SyncConnected == event.getState()){
                if (Watcher.Event.EventType.None == event.getType() && null == event.getPath()){
                    connectedSemaphore.countDown();
                }else if (event.getType() == Watcher.Event.EventType.NodeDataChanged){  // zk目录节点数据变化通知事件
                    try {
                        System.out.println("配置已修改，新值为：" + new String(zk.getData(event.getPath(), true, stat)));
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        };

        //zookeeper配置数据库存放路径
        String path = "/username";
        //连接zookeeper并且注册一个默认的监听器
        zk = new ZooKeeper("192.168.25.154:2181", 5000, watcher);
        connectedSemaphore.await();
        //获取path目录节点的配置数据，并注册默认的监听器
        System.out.println(new String(zk.getData(path, true, stat)));

        Thread.sleep(Integer.MAX_VALUE);
    }
}

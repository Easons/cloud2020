package com.pangpan.springcloud;

import org.apache.zookeeper.*;
import org.apache.zookeeper.data.Stat;

import java.io.IOException;

public class WatchMore {

    private final static String CONNECT_STRING = "192.168.1.103:2181";

    private final static String PATH = "/pangpan";

    private final static int SESSION_TIMEOUT = 50 * 1000;

    private ZooKeeper zk = null;//实例变量

    private  String oldValue = null;//老值


    public void setZk(ZooKeeper zk) {
        this.zk = zk;
    }

    public ZooKeeper getZk() {
        return zk;
    }

    public ZooKeeper startZK() throws IOException {

        return new ZooKeeper(CONNECT_STRING, SESSION_TIMEOUT, WatchedEvent -> {

        });
    }

    public  void createZnode(String nodePath,String nodeValue) throws KeeperException, InterruptedException {
        //OPEN_ACL_UNSAFE：去掉权限的限制，linux能进去，肯定可以操作zookeeper，不需要进行权限限制
        String s = zk.create(nodePath, nodeValue.getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
    }

    public  String getZnode(String nodePath) throws KeeperException, InterruptedException {
        String result = "";

        //Stat 结构体，用zookeeper自己的结构体，自己不定义
//        byte[] data = zk.getData(PATH, new Watcher() {
//            @Override
//            public void process(WatchedEvent event)
//            {
//                try {
//                    trigerValue(PATH);
//                } catch (KeeperException | InterruptedException e) {
//                    e.printStackTrace();
//                }
//            }
//        }, new Stat());



        byte[] data = zk.getData(PATH, watchedEvent ->{
                    try {
                    trigerValue(PATH);
                } catch (KeeperException | InterruptedException e) {
                    e.printStackTrace();
                }
        }, new Stat());


        result = new String(data);

        oldValue = result;//赋值

        return result;
    }

    /**
     *
     * @param path
     */
    private boolean trigerValue(String nodePath) throws KeeperException, InterruptedException {

        String result = null;

        byte[] data = zk.getData(PATH, new Watcher() {
            @Override
            public void process(WatchedEvent event) {
                try {
                    trigerValue(nodePath);
                } catch (KeeperException e) {
                    e.printStackTrace();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }, new Stat());

        result = new String(data);
        String newValue = result;

        if(!newValue.equals(oldValue))
        {
            System.out.println("*****老值：" +oldValue);
            System.out.println("*****新值：" +result);
            oldValue = newValue;
            return true;
        }else{
            System.out.println("没变化");
            return false;
        }

    }

    /**
     * 监控我们的/pangpan 节点，获得初次值后设置watch，只要发生新的变化，打印出最新的值，一次性watch
     * @param args
     * @throws IOException
     * @throws InterruptedException
     * @throws KeeperException
     */
    public static void main(String[] args) throws IOException, InterruptedException, KeeperException {
        WatchMore watchmore = new WatchMore();

        watchmore.setZk(watchmore.startZK());

        if(watchmore.getZk().exists(PATH,false) == null)
        {
            watchmore.createZnode(PATH,"AAA");

            String retValue = watchmore.getZnode(PATH);
            System.out.println("*****************first  retValue："+retValue);

            Thread.sleep(Long.MAX_VALUE);
        }else {
            System.out.println("**************i have this node");
        }


    }


}

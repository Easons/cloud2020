package com.pangpan.springcloud;

import org.apache.zookeeper.*;
import org.apache.zookeeper.data.Stat;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

import java.io.IOException;

@SpringBootApplication
@EnableDiscoveryClient
public class PaymentMain8005 {
//    public static void main(String[] args) {
//        SpringApplication.run(PaymentMain8005.class,args);
//    }

    private final static String CONNECT_STRING = "192.168.56.10:2181";

    private final static String PATH = "/pangpan";

    private final static int SESSION_TIMEOUT = 50 * 1000;


    public ZooKeeper startZK() throws IOException {
        return new ZooKeeper(CONNECT_STRING, SESSION_TIMEOUT, new Watcher() {
            @Override
            public void process(WatchedEvent event) {


            }
        });
    }

    public void stopZK(ZooKeeper zk) throws InterruptedException {
        if (zk != null) {
            zk.close();
        }
    }

    public  void createZnode(ZooKeeper zk,String nodePath,String nodeValue) throws KeeperException, InterruptedException {
        //OPEN_ACL_UNSAFE：去掉权限的限制，linux能进去，肯定可以操作zookeeper，不需要进行权限限制
        String s = zk.create(nodePath, nodeValue.getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
    }

    public  String getZnode(ZooKeeper zk,String nodePath) throws KeeperException, InterruptedException {
        String result = "";

        //Stat 结构体，用zookeeper自己的结构体，自己不定义
        byte[] data = zk.getData(nodePath, false, new Stat());
        result = new String(data);
        return result;
    }

    public static void main(String[] args) throws IOException, InterruptedException, KeeperException {
        PaymentMain8005 pay = new PaymentMain8005();

        ZooKeeper zk = pay.startZK();

        if(zk.exists(PATH,false) == null)
        {
            pay.createZnode(zk,PATH,"hello1234");
            String znode = pay.getZnode(zk, PATH);
            System.out.println("**************"+znode);
        }else {
            System.out.println("**************i have this node");
        }

        pay.stopZK(zk);




    }


}

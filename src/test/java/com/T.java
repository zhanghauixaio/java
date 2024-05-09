package com;

// import com.auth0.jwt.JWT;
// import com.auth0.jwt.algorithms.Algorithm;
import io.netty.util.concurrent.DefaultThreadFactory;
import jodd.util.concurrent.ThreadFactoryBuilder;
import net.sf.ehcache.Cache;
import net.sf.ehcache.CacheManager;
import net.sf.ehcache.Element;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;
import java.util.concurrent.locks.AbstractQueuedSynchronizer;
import java.util.concurrent.locks.ReentrantLock;

public class T {
//    public static void main(String args[]) {
////        ReentrantLock lock = new ReentrantLock();
//        System.out.println(Runtime.getRuntime().availableProcessors());
//        try {
//            Process process = Runtime.getRuntime().exec("ipconfig");
//            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream(),"gbk"));
//            String str = "";
//            while ((str = reader.readLine()) != null){
//                System.out.println(str);
//            }
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }
    ExecutorService taskExe = new ThreadPoolExecutor(3,4,800L,TimeUnit.SECONDS,new LinkedBlockingQueue<Runnable>());
    int count = 0;
    @Test
    public void threadPoolTest(){
        List<FutureTask<Integer>> taskList = new ArrayList<FutureTask<Integer>>();
        for (int i = 0; i < 100; i++) {
            FutureTask<Integer> futureTask = new FutureTask<Integer>(new Callable<Integer>() {
                public Integer call() throws Exception {
                    return 1;
                }
            });
            taskList.add(futureTask);
            taskExe.submit(futureTask);
        }
        for (FutureTask<Integer> futureTask:taskList) {
            try {
                count+=futureTask.get();
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            }
        }
        taskExe.shutdown();
        System.out.println(count);
    }
    // @Test
    // public void jwtTest(){
    //     String token = "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJ0ZXN0Ijoi56ys5LiA5qyh5bCd6K-VIn0.hodMEu13NNwKqwDsmhuQYBfE23NGj5U3ga9SMaemRLE";
    //     System.out.println(JWT.create()
    //             .withClaim("test", "第一次尝试")
    //             .sign(Algorithm.HMAC256("123456")));
    //     System.out.println(JWT.require(Algorithm.HMAC256("123456")).withClaim("test", "第一次尝试").build().verify(token).getHeader());
    //     System.out.println(JWT.decode(token).getClaim("test").asString());
    // }
    Logger logger = LoggerFactory.getLogger(this.getClass());

    @Test
    public void cacheTest(){
        CacheManager manager = CacheManager.create("./src/main/resources/ehcache.xml");
        Cache local = manager.getCache("local");
        Element element = new Element("2","qwe");
        local.put(element);
        System.out.println(local.get("2").getObjectValue());
        logger.info("第hi一");
        manager.shutdown();
    }
}



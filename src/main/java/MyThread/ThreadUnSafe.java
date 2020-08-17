package MyThread;

import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;

@Slf4j
public class ThreadUnSafe {
    static int THREAD_NUM = 2;
    static int LOOP_COUNT = 200;

    public static void main(String[] args) {
        String s = "aaa";
        ThreadUnSafe threadUnSafe = new ThreadUnSafe();
        for (int i = 0; i < THREAD_NUM; i++) {
            new Thread(()->{
                threadUnSafe.test();
            },"t"+i).start();
        }
    }

    public void test(){
        List<String> list = new ArrayList();
        for (int i = 0; i < LOOP_COUNT; i++) {
            list.add("aa");
            list.remove(0);
        }
    }

    public void method2(){
        //log.info("size:{}",list.size());

    }
    public void method3(){
        //log.info("size after:{}",list.size());

    }
}

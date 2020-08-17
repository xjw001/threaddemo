package Volatile;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class Demo {

    static volatile boolean isStop = false;

    public static void main(String[] args) throws InterruptedException {
        Thread t = new Thread(()->{
            while(true){
                if(isStop){
                    break;
                }
            }
            log.info("t线程结束");
        });
        t.start();
        Thread.sleep(100);
        isStop = true;
        log.info("主线程结束");
    }
}

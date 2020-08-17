package MyThread;

import lombok.extern.slf4j.Slf4j;

/**
 * 保护性暂停模式
 */
@Slf4j(topic = "tm")
public class ThreadMethod {
    static final Object lock = new Object();

    public static void main(String[] args) {
        Thread t1 = new Thread(()->{
            synchronized (lock){
                log.debug("开始");
                try {
                    lock.wait(5000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                log.debug("结束");
            }
        },"t1");
        t1.start();

        Sleeper.sleep(1);
        synchronized (lock){
            log.debug("主线程获得锁");
        }
    }
}

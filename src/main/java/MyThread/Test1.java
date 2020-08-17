package MyThread;

import lombok.extern.slf4j.Slf4j;

/**
 * @author xiongjw
 * @version 1.0
 * @date 2020/6/28 23:53
 */

@Slf4j(topic = "c.Test1")
public class Test1 {
    public static void main(String[] args) {
        Runnable r = new Runnable() {
            @Override
            public void run() {
                log.info("r1");
            }
        };
        Thread t = new Thread(r);
        t.start();
        log.info("r2");
    }
}

package MyThread;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.locks.LockSupport;

@Slf4j
public class LockParkTest {
    public static void main(String[] args) {

        Thread t1 = new Thread(()->{
            log.debug("start..");
            Sleeper.sleep(1);
            log.debug("park");
            LockSupport.park();
            log.debug("resume");
        },"t1");
        t1.start();

        t1.interrupt();
        Sleeper.sleep(2);
        log.debug("unpark...");
        LockSupport.unpark(t1);
    }
}

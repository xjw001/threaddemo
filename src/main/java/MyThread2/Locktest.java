package MyThread2;

import MyThread.Sleeper;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

@Slf4j
public class Locktest {
    static ReentrantLock lock = new ReentrantLock();
    static Condition c1 = lock.newCondition();
    static Condition c2 = lock.newCondition();
    static boolean hasC1 = false;
    static boolean hashC2 = false;
    public static void main(String[] args) {
        new Thread(()->{
         lock.lock();
         try{
             log.debug("有烟没? {}",hasC1);
            while (!hasC1){
                log.debug("没有烟,休息");
                c1.await();
            }
            log.debug("有烟了，干活");
            } catch (InterruptedException e) {
             e.printStackTrace();
         } finally {
             lock.unlock();
         }
        },"t1").start();

        new Thread(()->{
            lock.lock();
            try{
                log.debug("有外卖没? {}",hasC1);
                while (!hashC2){
                    log.debug("没有外卖,休息");
                    c2.await();
                }
                log.debug("有外卖，干活");
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                lock.unlock();
            }
        },"t2").start();

        Sleeper.sleep(1);
        new Thread(()->{
            lock.lock();
            try{
                hashC2 = true;
                c2.signal();
            }finally {
                lock.unlock();
            }
        },"送外卖").start();

        Sleeper.sleep(1);
        new Thread(()->{
            lock.lock();
            try{
                hasC1 = true;
                c1.signal();
            }finally {
                lock.unlock();
            }
        },"送烟").start();
    }
}

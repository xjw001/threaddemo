package MyThread;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author xiongjw
 * @version 1.0
 * @date 2020/7/3 10:04
 */
public class ThreadConditionDemo {

    private static Lock lock = new ReentrantLock();

    private static Condition condition1 = lock.newCondition();

    private static Condition condition2 = lock.newCondition();

    private static Boolean t1Flag = false;

    public static void main(String[] args) {
        Thread t1 = new Thread(()->{
            lock.lock();
            try {
                System.out.println("t1");
                t1Flag = true;
                condition1.signal();
            } catch (Exception e) {
                e.printStackTrace();
            }finally {
                lock.unlock();
            }
        });
        Thread t2 = new Thread(()->{
            lock.lock();
            try {
                if(!t1Flag){
                    condition1.await();
                }
                System.out.println("t2");
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                lock.unlock();
            }
        });
        t2.start();
        t1.start();

    }
}

package MyThread;

/**
 * @author xiongjw
 * @version 1.0
 * @date 2020/7/2 16:35
 */
public class WaitTest {
    private static Object myLock1 = new Object();

    private static Object MyLock2 = new Object();

    private static Boolean t1Run = false;
    private static Boolean t2Run = false;

    public static void main(String[] args) {
        Thread t1 = new Thread(()->{
            System.out.println("t1");
            t1Run = true;
            synchronized (myLock1){
                myLock1.notify();
            }
        });
        Thread t2 = new Thread(()->{
            try {
                synchronized (myLock1) {
                    if(!t1Run) {
                        myLock1.wait();
                    }
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("t2");
        });
        t1.start();
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        t2.start();
        System.exit(0);
    }
}

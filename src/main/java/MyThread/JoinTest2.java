package MyThread;

/**
 * @author xiongjw
 * @version 1.0
 * @date 2020/7/2 16:24
 */
public class JoinTest2 {
    public static void main(String[] args) {
        final Thread t1 = new Thread(()->{
            System.out.println("t1");
        });
        final Thread t2 = new Thread(()->{
            try {
                t1.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("t2");
        });
        Thread t3 = new Thread(()->{
            try {
                t2.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("t3");
        });
        t3.start();
        t1.start();
        t2.start();

    }
}

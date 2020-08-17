package combine;

import java.util.concurrent.Semaphore;

/**
 * @author xiongjw
 * @version 1.0
 * @date 2020/6/29 0:10
 */
public class SemaphoreTest {
    public static void main(String[] args) {
        Semaphore semaphore = new Semaphore(3);
        for(int i=0; i<10 ; i++){
            new Thread(()->{
                try {
                    semaphore.acquire();
                    System.out.println("当前线程:"+Thread.currentThread().getName());
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }finally {
                    semaphore.release();
                }
            }).start();
        }
    }
}

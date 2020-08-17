package combine;

import java.util.Arrays;
import java.util.Random;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 多人游戏同时加载完才开始
 */
public class CountDownLatchTest {
    public static void main(String[] args) throws InterruptedException {
        ExecutorService executorService = Executors.newFixedThreadPool(10);
        String all[] = new String[10];
        Random r = new Random();
        CountDownLatch countDownLatch = new CountDownLatch(10);
        for (int j = 0; j < 10; j++) {
            final int x = j;
            executorService.submit(()->{
                for (int i = 0; i <= 100; i++) {
                    all[x] = i+"%";
                    System.out.print("\r"+Arrays.toString(all));
                    try {
                        Thread.sleep(r.nextInt(100));
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                countDownLatch.countDown();
            });
        }
        countDownLatch.await();
        System.out.println("\n加载完毕!");
        executorService.shutdown();

    }
}

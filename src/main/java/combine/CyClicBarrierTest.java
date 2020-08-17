package combine;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 所有乘客都坐满才发车
 */
@Slf4j
public class CyClicBarrierTest {
    public static void main(String[] args) {
        ExecutorService executorService = Executors.newFixedThreadPool(2);
        CyclicBarrier c = new CyclicBarrier(2,()->{
            log.info("1,2都结束");
            executorService.shutdown();
        });
        executorService.submit(()->{
            log.info("任务1开始");
            try {
                Thread.sleep(1000);
                c.await();
                log.info("任务1结束");
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
        executorService.submit(()->{
            log.info("任务2开始");
            try {
                Thread.sleep(2000);
                c.await();
                log.info("任务2结束");
            } catch (Exception e) {
                e.printStackTrace();
            }
        });

    }
}

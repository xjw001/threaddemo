package Volatile;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class TwoPhaseTermination {
    private Thread monitorThread;

    public static void main(String[] args) throws InterruptedException {
        TwoPhaseTermination twoPhaseTermination = new TwoPhaseTermination();
        twoPhaseTermination.start();

        Thread.sleep(3500);
        log.debug("停止监控");
        twoPhaseTermination.stop();
    }

    public void start(){
        monitorThread = new Thread(()->{
            while (true){
                Thread current = Thread.currentThread();
                if(current.isInterrupted()){
                    log.debug("处理后事");
                    break;
                }
                try {
                    Thread.sleep(1000);
                    log.debug("执行监控");
                } catch (InterruptedException e) {
                    current.interrupt();
                }
            }
        },"monitor");
        monitorThread.start();
    }

    public void stop(){
        monitorThread.interrupt();
    }
}

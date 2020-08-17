package MyThread;

import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Vector;

@Slf4j(topic = "tkWindow")
public class TicketWindow {
    public static void main(String[] args) throws InterruptedException {
        TicketWindow ticketWindow = new TicketWindow(30000);
        List<Integer> list = new Vector<>();
        List<Thread> threadList = new ArrayList<>();
        for (int i = 0; i < 10000; i++) {
            Thread t = new Thread(()->{
                int count = ticketWindow.sell(TicketWindow.randomCount());
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                list.add(count);
            });
            threadList.add(t);
            t.start();
        }

        for (Thread t : threadList){
            t.join();
        }
        log.debug("卖出的票数:{}",list.stream().mapToInt(i->i).sum());
        log.debug("剩余票数：{}",ticketWindow.getCount());
    }
    static Random random = new Random();
    public static int randomCount(){
        return random.nextInt(5)+1;
    }
    private int count;
    public TicketWindow(int count){
        this.count = count;
    }

    public int getCount() {
        return count;
    }

    public int sell(int amount){
        if(this.count > amount){
            this.count -= amount;
            return amount;
        }else{
            return 0;
        }
    }
}

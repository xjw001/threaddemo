package MyThread;

import java.util.concurrent.TimeUnit;

public class Sleeper {

    public static void sleep(int i){
        try {
            TimeUnit.SECONDS.sleep(i);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}

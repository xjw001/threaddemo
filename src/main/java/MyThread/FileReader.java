package MyThread;

import lombok.extern.slf4j.Slf4j;

import java.io.File;
import java.io.FileInputStream;

@Slf4j(topic="c.fileReader")
public class FileReader {
    public static void main(String[] args) {
        new Thread(()->{
            FileReader.read("D:\\guoshou\\gsCarInsurance\\insurance-web\\target\\ROOT.war");
        },"t1").start();
        System.out.println("ok");
    }

    public static void read(String filename){
        int idx = filename.lastIndexOf(File.pathSeparator);
        String shortName = filename.substring(idx + 1);
        try{
            FileInputStream in = new FileInputStream(filename);
            long start = System.currentTimeMillis();
            log.debug("read {} start...",shortName);
            byte[] buf = new byte[1024];
            int n = -1;
            do {
                n = in.read(buf);
            }while (n != -1);
            Thread.sleep(3000);
            long end = System.currentTimeMillis();
            log.debug("read{} end,cost:{} ms",shortName,end - start);
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}

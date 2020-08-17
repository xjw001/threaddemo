package MyThread;

import lombok.extern.slf4j.Slf4j;

import java.util.Hashtable;
import java.util.Map;
import java.util.Set;

public class ThreadMethod2 {
    public static void main(String[] args) {
        for (int i = 0; i < 3; i++) {
            new Person().start();
        }
        Sleeper.sleep(1);
        for(Integer id: MailBox.getIds()){
            new Postman(id,"内容:"+id).start();
        }
    }
}

@Slf4j(topic = "person")
class Person extends Thread{
    @Override
    public void run() {
        GuardObject guardObject = MailBox.createGuardObject();
        log.debug("等待收邮件,id:{}",guardObject.getId());
        Object mail = guardObject.get(5000);
        log.debug("收到邮件,id:{},内容{}",guardObject.getId(),mail);
    }
}

@Slf4j(topic = "postman")
class Postman extends Thread{
    private int id;
    private String mail;
    public Postman(int id,String mail){
        this.id = id;
        this.mail = mail;
    }
    @Override
    public void run() {
        GuardObject guardObject = MailBox.getGuardObject(id);
        log.debug("开始发邮件,id:{},内容{}",id,mail);
        guardObject.complete(mail);
    }
}

class MailBox{
    private static Map<Integer, GuardObject> boxes = new Hashtable<>();
    private static int id = 1;
    public static synchronized int generateId(){
        return id++;
    }

    public static GuardObject getGuardObject(int id){
        return boxes.remove(id);
    }
    public static GuardObject createGuardObject(){
        GuardObject go = new GuardObject(generateId());
        boxes.put(go.getId(),go);
        return go;
    }
    public static Set<Integer> getIds(){
        return boxes.keySet();
    }
}

class GuardObject{
    private int id;

    public int getId() {
        return id;
    }

    public GuardObject(int id){
        this.id = id;
    }
    //结果
    private Object response;

    public Object get(long timeout){
        synchronized (this){
            long begin = System.currentTimeMillis();
            //已经经过的时间
            long passedTime = 0;
            while (response == null){
                long waitTime = timeout - passedTime;
                if(waitTime <= 0 ){
                    break;
                }
                try {
                    this.wait(waitTime);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                passedTime = System.currentTimeMillis() - begin;
            }
            return response;
        }
    }

    public void complete(Object response){
        synchronized (this){
            this.response = response;
            this.notifyAll();
        }
    }
}

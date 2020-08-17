package MyThread;

import lombok.extern.slf4j.Slf4j;

import java.util.LinkedList;

@Slf4j
public class MessageQueue {

    public static void main(String[] args) {
        MessageQueue messageQueue = new MessageQueue(2);
        for (int i = 0; i < 3; i++) {
            int id = i;
            new Thread(()->{
                messageQueue.put(new Message(id,"值:"+id));
            },"生产者"+i).start();
        }
        new Thread(()->{
            while(true){
                Sleeper.sleep(1);
                Message message = messageQueue.take();
            }
        },"消费者").start();
    }
    private LinkedList<Message> list = new LinkedList<>();
    private int capcity;

    public MessageQueue(int capcity) {
        this.capcity = capcity;
    }

    public Message take(){
        synchronized (list) {
            while (list.isEmpty()) {
                try {
                    log.debug("队列为空");
                    list.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            Message message = list.removeFirst();
            log.debug("已消费消息,{}",message);
            list.notifyAll();
            return message;
        }
    }

    public void put(Message message){
        synchronized (list){
            while (list.size() == capcity){
                try {
                    log.debug("队列已满");
                    list.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            list.addLast(message);
            log.debug("已生产消息,{}",message);
            list.notify();
        }
    }
}

final class Message{
    private int id;
    private Object value;

    public Message(int id, Object value) {
        this.id = id;
        this.value = value;
    }

    public int getId() {
        return id;
    }

    public Object getValue() {
        return value;
    }

    @Override
    public String toString() {
        return "Message{" +
                "id=" + id +
                ", value=" + value +
                '}';
    }
}

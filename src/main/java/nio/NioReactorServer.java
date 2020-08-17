package nio;

import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.*;
import java.util.Iterator;
import java.util.Random;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.FutureTask;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.atomic.AtomicInteger;

public class NioReactorServer {
    private static ExecutorService workPool = Executors.newCachedThreadPool();

    abstract class ReactorThread extends Thread{
        Selector selector;
        LinkedBlockingQueue<Runnable> taskQueue = new LinkedBlockingQueue<>();

        public abstract void handler(SelectableChannel channel) throws Exception;

        private ReactorThread() throws Exception{
            selector = Selector.open();
        }
        volatile boolean running = false;

        @Override
        public void run() {
            while (running){
                try {
                    Runnable task;
                    while ((task = taskQueue.poll()) != null){
                        task.run();
                    }
                    selector.select(1000);

                    Set<SelectionKey> selected = selector.selectedKeys();
                    Iterator<SelectionKey> it = selected.iterator();
                    while (it.hasNext()){
                        SelectionKey key = it.next();
                        it.remove();
                        int readyOps = key.readyOps();
                        if( (readyOps & (SelectionKey.OP_READ | SelectionKey.OP_ACCEPT))!=0 || readyOps == 0){
                            try {
                                SelectableChannel channel = (SelectableChannel)key.attachment();
                                channel.configureBlocking(false);
                                handler(channel);
                                if(!channel.isOpen()){
                                    key.cancel();
                                }
                            } catch (Exception e) {
                                key.cancel(); //取消对key的订阅
                            }
                        }
                    }
                }catch (Exception e){

                }
            }
        }

        private SelectionKey register(SelectableChannel channel) throws Exception{
            // 为什么register要以任务提交的形式，让reactor线程去处理？
            // 因为线程在执行channel注册到selector的过程中，会和调用selector.select()方法的线程争用同一把锁
            // 而select()方法实在eventLoop中通过while循环调用的，争抢的可能性很高，为了让register能更快的执行，就放到同一个线程来处理
            FutureTask<SelectionKey> futureTask = new FutureTask<>(()->
                channel.register(selector,0,channel)
            );
            taskQueue.add(futureTask);
            return  futureTask.get();
        }

        private void doStart(){
            if(!running){
                running = true;
                start();
            }
        }
    }
    private ServerSocketChannel serverSocketChannel;

    //accept接收处理的线程
    private ReactorThread[] mainReactorThreads = new ReactorThread[1];

    //IO处理的线程
    private ReactorThread[] subReactorThreads = new ReactorThread[8];

    private  void newGroup() throws Exception{
        for (int i = 0; i < subReactorThreads.length; i++) {
            subReactorThreads[i] = new ReactorThread() {
                @Override
                public void handler(SelectableChannel channel) throws Exception {
                    SocketChannel ch = (SocketChannel)channel;
                    ByteBuffer requestByteBuffer = ByteBuffer.allocate(1024);
                    while (ch.isOpen() && ch.read(requestByteBuffer)!= -1){
                        if(requestByteBuffer.position() > 0){
                            break;
                        }
                    }
                    if(requestByteBuffer.position() == 0)
                        return;
                    requestByteBuffer.flip();
                    byte[] content = new byte[requestByteBuffer.limit()];
                    requestByteBuffer.get(content);
                    //得到数据
                    System.out.println(new String(content));

                    //处理业务 数据库、接口
                    workPool.submit(()->{

                    });

                    // 响应结果 200
                    String response = "HTTP/1.1 200 OK\r\n" +
                            "Content-Length: 11\r\n\r\n" +
                            "Hello World";
                    ByteBuffer buffer = ByteBuffer.wrap(response.getBytes());
                    while (buffer.hasRemaining()) {
                        ch.write(buffer);
                    }
                }
            };
        }

        for (int i = 0; i < mainReactorThreads.length; i++) {
            mainReactorThreads[i] = new ReactorThread() {
                AtomicInteger incr = new AtomicInteger(0);

                @Override
                public void handler(SelectableChannel channel) throws Exception {
                    //只处理请求分发，不处理数据读取
                    ServerSocketChannel ch = (ServerSocketChannel) channel;
                    SocketChannel socketChannel = ch.accept();
                    socketChannel.configureBlocking(false);
                    int index = incr.getAndIncrement()%subReactorThreads.length;
                    ReactorThread workEventLoop = subReactorThreads[index];
                    workEventLoop.doStart();
                    SelectionKey selectionKey = workEventLoop.register(socketChannel);
                    selectionKey.interestOps(SelectionKey.OP_READ);
                    System.out.println(Thread.currentThread().getName() + "收到新连接 : " + socketChannel.getRemoteAddress());
                }
            };
        }
    }
    private void initAndRegister() throws Exception{
        //创建ServerSocketChannel
        serverSocketChannel = ServerSocketChannel.open();
        serverSocketChannel.configureBlocking(false);
        int index = new Random().nextInt(mainReactorThreads.length);
        mainReactorThreads[index].doStart();
        SelectionKey selectionKey = mainReactorThreads[index].register(serverSocketChannel);
        selectionKey.interestOps(SelectionKey.OP_ACCEPT);
    }

    private void bind() throws Exception{
        serverSocketChannel.bind(new InetSocketAddress(8080));
        System.out.println("启动服务，端口：8080");
    }

    public static void main(String[] args) throws Exception{
        NioReactorServer nioReactorServer = new NioReactorServer();
        nioReactorServer.newGroup(); //创建main和 sub两组线程
        nioReactorServer.initAndRegister(); //创建serverSocketChannel 注册到mianReactor的Selector上
        nioReactorServer.bind(); //为创建serverSocketChannel指定端口
    }
}

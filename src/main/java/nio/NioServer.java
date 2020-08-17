package nio;

import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Set;

public class NioServer {
    public static void main(String[] args) throws Exception {
        ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
        serverSocketChannel.configureBlocking(false);

        //创建一个selector
        Selector selector = Selector.open();
        SelectionKey selectionKey = serverSocketChannel.register(selector,0,serverSocketChannel);
        selectionKey.interestOps(SelectionKey.OP_ACCEPT);

        serverSocketChannel.socket().bind(new InetSocketAddress(8080));
        System.out.println("###start###########");
        while (true){
            selector.select();
            Set<SelectionKey> selectionKeys =  selector.selectedKeys();
            Iterator<SelectionKey> iterator = selectionKeys.iterator();
            while(iterator.hasNext()){
                SelectionKey key = iterator.next();
                iterator.remove();//处理完后移除
                if(key.isAcceptable()){
                    ServerSocketChannel server = (ServerSocketChannel)key.attachment();
                    SocketChannel clientSocketChannel = server.accept();
                    clientSocketChannel.configureBlocking(false);
                    clientSocketChannel.register(selector,SelectionKey.OP_READ,clientSocketChannel);
                    System.out.println("收到新连接:" + clientSocketChannel.getRemoteAddress());
                }

                if(key.isReadable()){
                    SocketChannel socketChannel = (SocketChannel)key.attachment();
                    ByteBuffer requestBuffer = ByteBuffer.allocate(4);
                    while (socketChannel.isOpen() && socketChannel.read(requestBuffer) != -1){
                        if(requestBuffer.position() > 0){
                            break;
                        }
                    }
                    if(requestBuffer.position() == 0){
                        continue;
                    }
                    requestBuffer.flip();
                    byte[] content = new byte[requestBuffer.limit()];
                    requestBuffer.get(content);
                    String message = new String(content);
                    System.out.println("收到数据:"+message+",来自:"+socketChannel.getRemoteAddress());

                    String response = "HTTP/1.1 200 OK\r\n"+
                            "Content-Length: 11\r\n\r\n"+
                            "Hello world";
                    ByteBuffer buffer = ByteBuffer.wrap(response.getBytes());
                    while(buffer.hasRemaining()){
                        socketChannel.write(buffer);
                    }
                }
            }
            selector.selectNow();
        }

    }
}

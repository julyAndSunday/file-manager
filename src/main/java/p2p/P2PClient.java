package p2p;


import java.io.IOException;
import java.io.InputStream;
import java.net.*;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;
import java.util.concurrent.FutureTask;

/**
 * @Description:
 * @Author: July
 * @Date: 2021-11-06 17:55
 **/
public class P2PClient {

    private SocketChannel socket;

    public FutureTask<SocketChannel> connect(int homeId) {
        FutureTask<SocketChannel> futureTask = null;
        try {
            SocketAddress localAddress;
            socket = SocketChannel.open();
            socket.setOption(StandardSocketOptions.SO_REUSEADDR, true);
            socket.connect(new InetSocketAddress("120.79.220.182", 9090));
            localAddress = socket.getLocalAddress();
            InetSocketAddress address = (InetSocketAddress) localAddress;
            byte[] bytes = String.valueOf((homeId) + "&" + address.toString()).getBytes();
            ByteBuffer byteBuffer = ByteBuffer.wrap(bytes);
            socket.write(byteBuffer);
            //等待连接对端
           futureTask = new FutureTask<>(() -> connectPeer(localAddress));
           new Thread(futureTask).start();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return futureTask;
    }

    private SocketChannel connectPeer(SocketAddress localAddress) {
        try {
            ByteBuffer allocate = ByteBuffer.allocate(128);
            //阻塞接收服务器发送对端的地址
            socket.read(allocate);
            String address = new String(allocate.array());
            String[] split = address.trim().substring(1).split(":");
//            System.out.println(address);
            String ip = split[0];
            int port = Integer.parseInt(split[1]);
            socket = SocketChannel.open();
            socket.setOption(StandardSocketOptions.SO_REUSEADDR, true);
            socket.bind(localAddress);
            socket.connect(new InetSocketAddress(ip, port));
            return socket;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }


    private static void handle(Socket socket) {
        //通过socket 获取输入流
        try {
            byte[] bytes = new byte[32];
            InputStream inputStream = socket.getInputStream();
            while (true) {
                int read = inputStream.read(bytes);
                if (read != -1) {
                    System.out.println(read);
                } else
                    break;
            }

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                socket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}

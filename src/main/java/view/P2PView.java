package view;

import entity.CreateHomeResponse;
import org.apache.commons.io.IOUtils;
import service.HomeService;
import view.component.TipView;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;

/**
 * @Description:
 * @Author: July
 * @Date: 2021-11-08 21:52
 **/
public class P2PView extends JFrame {
    private HomeService homeService;
    private JPanel jPanel;
    private SocketChannel socketChannel;

    public P2PView() {
        homeService = new HomeService("120.79.220.182", 8080);
        setTitle("文件传输");
        setBounds(400, 200, 600, 500);
        setLocationRelativeTo(null);
        setDefaultLookAndFeelDecorated(true);
        jPanel = new JPanel();
        JTextField tip = new JTextField(30);
        tip.setText("创建通道输入 通道密码 & 加入则输入 通道id:通道密码");
        tip.setDisabledTextColor(Color.BLACK);
        tip.setHorizontalAlignment(JTextField.CENTER);
        tip.setEnabled(false);
        JTextArea passwordArea = new JTextArea(1, 30);
        JButton createButton = new JButton("创建通道");
        JButton joinButton = new JButton("加入通道");

        createButton.addActionListener(e -> {
            CreateHomeResponse response = homeService.createHome("abc", passwordArea.getText());
            new TipView("创建成功 通道为：" + response.getHomeId());
            new Thread(()->{
                dealWithFuture(response.getFutureTask());
            }).start();
        });
        joinButton.addActionListener(e -> {
            FutureTask<SocketChannel> futureTask = homeService.joinHome(passwordArea.getText());
            dealWithFuture(futureTask);
        });
        jPanel.add(tip);
        jPanel.add(passwordArea);
        jPanel.add(passwordArea);
        jPanel.add(createButton);
        jPanel.add(joinButton);
        add(jPanel);
        setVisible(true);
    }

    private void dealWithFuture(FutureTask<SocketChannel> futureTask) {
        try {
            socketChannel = futureTask.get();
        } catch (InterruptedException | ExecutionException e1) {
            e1.printStackTrace();
        }
        //开新线程等待接收文件
        new Thread(() -> {
            receiveFile(socketChannel);
        }).start();
        JTextField jTextField = new JTextField(30);
        jTextField.setHorizontalAlignment(SwingConstants.CENTER);
        jTextField.setDisabledTextColor(Color.BLACK);
        jTextField.setText("选择传输的文件");
        JFileChooser jFileChooser = new JFileChooser();
        JButton sendButton = new JButton("发送");
        sendButton.addActionListener(e -> {
            try {
                File file = jFileChooser.getSelectedFile();
                String fileName = file.getName();
                socketChannel.write(ByteBuffer.wrap(fileName.getBytes()));
                ByteBuffer byteBuffer = ByteBuffer.wrap(IOUtils.toByteArray(new FileInputStream(file)));
                socketChannel.write(byteBuffer);
                new TipView("发送成功："+fileName);
            } catch (IOException e1) {
                e1.printStackTrace();
            }

        });
        jPanel.removeAll();
        jPanel.add(jTextField);
        jPanel.add(jFileChooser);
        jPanel.add(sendButton);
        jPanel.revalidate();
        jPanel.repaint();
    }

    private void receiveFile(SocketChannel socketChannel) {
        ByteBuffer buffer = ByteBuffer.allocate(1024);
        File file = null;
        FileOutputStream fileOutputStream = null;
        try {
            int size;
            int offset = 0;
            while ((size = socketChannel.read(buffer)) > 0) {
                if (file == null) { //初始化文件
                    String fileName = new String(buffer.array()).trim();
                    file = new File(fileName);
                    fileOutputStream = new FileOutputStream(file);
                    buffer.clear();
                } else {
                    fileOutputStream.write(buffer.array(), offset, size);
                    offset += size;
                }
            }
            new TipView("接收一个文件："+file.getName());
            fileOutputStream.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            try {
                fileOutputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}

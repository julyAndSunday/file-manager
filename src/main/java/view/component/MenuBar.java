package view.component;

import view.P2PView;

import javax.swing.*;

/**
 * @Description:
 * @Author: July
 * @Date: 2021-10-30 19:56
 **/
public class MenuBar extends JMenuBar{
    public MenuBar(){
        /*
         * 创建一级菜单
         */
        JButton p2p = new JButton("文件点对点传输");
        JMenu feedbackMenu = new JMenu("反馈");
        JMenu aboutMenu = new JMenu("关于");
        p2p.addActionListener(e -> {
            P2PView p2PView = new P2PView();
        });
        // 一级菜单添加到菜单栏
        add(p2p);
        add(feedbackMenu);
        add(aboutMenu);
    }

}

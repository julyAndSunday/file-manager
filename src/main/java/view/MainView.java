package view;

import view.component.MenuBar;
import view.component.ScrollPanel;
import view.component.ToolBar;

import javax.swing.*;
import java.awt.*;

public class MainView extends JFrame {

    /**
     * Launch the application.
     */
    public static void main(String[] args) {
        EventQueue.invokeLater(() -> {
            try {
                MainView frame = new MainView(300,100,650,700);
                frame.setVisible(true);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    /**
     * Create the frame.
     */
    public MainView(int x,int y,int width,int height) {
        setTitle("file-manager");
        setSize(width,height);
        setLocation(x, y);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setDefaultLookAndFeelDecorated(true);

        //添加菜单
        MenuBar menuBar = new MenuBar();
        setJMenuBar(menuBar);

        ScrollPanel pcp = new ScrollPanel(width,height);
        //添加功能条
        ToolBar toolBar = new ToolBar(pcp);
        getContentPane().add(toolBar);
        //添加滚动面板
        getContentPane().add(pcp);

    }

}
package view.component;

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
        JMenu fileMenu = new JMenu("文件");
        JMenu editMenu = new JMenu("编辑");
        JMenu viewMenu = new JMenu("视图");
        JMenu aboutMenu = new JMenu("关于");
        // 一级菜单添加到菜单栏
        add(fileMenu);
        add(editMenu);
        add(viewMenu);
        add(aboutMenu);
    }

}

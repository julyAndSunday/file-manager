package view.component;

import service.FileService;
import view.FileSelectView;
import view.SelectView;

import javax.swing.*;

/**
 * @Description:
 * @Author: July
 * @Date: 2021-10-31 10:50
 **/
public class ToolBar extends JToolBar {
    private ScrollPanel scrollPanel;
    private SelectView selectView;
    private FileSelectView fileSelectView;
    private FileService fileService;

    public ToolBar(ScrollPanel scrollPanel) {
        this.scrollPanel = scrollPanel;
        this.selectView = new SelectView(scrollPanel);
        this.fileSelectView = new FileSelectView(scrollPanel);
        JButton ret = new JButton("返回");
        ret.addActionListener(e -> {
            scrollPanel.back_path();
        });

        JButton select = new JButton("全选");
        select.addActionListener(e -> {
            scrollPanel.selectAll();
        });

        JButton classifySelect = new JButton("条件选择");
        classifySelect.addActionListener(e -> {
            selectView.setVisible(true);
        });

        JButton copy = new JButton("拷贝到");
        copy.addActionListener(e -> {
            fileSelectView.setOperate("拷贝");
            fileSelectView.setVisible(true);
        });

        JButton move = new JButton("移动到");
        move.addActionListener(e->{
                fileSelectView.setOperate("移动");
                fileSelectView.setVisible(true);
        });

        JButton delete = new JButton("删除");
        delete.addActionListener(e1 -> {
            JButton jButton = new JButton("确定");
            ConfirmView confirmView = new ConfirmView("确定删除吗", jButton);
            jButton.addActionListener(e2 -> {
                fileService.delete(scrollPanel.getSelectFiles());
                confirmView.setVisible(false);
            });
        });

        add(ret);
        add(select);
        add(classifySelect);
        add(copy);
        add(move);
        add(delete);
        setBounds(5, 10, 600, 50);
//
    }

}

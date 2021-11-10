package view;

import service.FileService;
import view.component.ScrollPanel;
import view.component.ConfirmView;

import javax.swing.*;
import java.awt.event.ActionEvent;

/**
 * @Description:
 * @Author: July
 * @Date: 2021-10-31 20:49
 **/
public class FileSelectView extends JFrame {
    private String toPath = "";
    private ScrollPanel scrollPanel;
    private JButton operateButton;
    private JPanel jPanel;
    private FileService fileService;
    public FileSelectView(ScrollPanel scrollPanel){
        fileService  = FileService.getInstance();
        this.scrollPanel = scrollPanel;
        setTitle("路径选择");
        setBounds(400, 200, 600, 500);
        setLocationRelativeTo(null);
        setDefaultLookAndFeelDecorated(true);
        jPanel = new JPanel();
        JTextArea jTextArea = new JTextArea(1,30);
        jTextArea.setText("选择的路径为：");
        jTextArea.setEditable(false);

        JFileChooser jFileChooser  = new JFileChooser();
        JButton jButton = new JButton("选中该目录");
        operateButton  =  new JButton();
        jButton.addActionListener(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                toPath = jFileChooser.getCurrentDirectory().getAbsolutePath();
                jTextArea.setText("选择路径为："+toPath);
                jTextArea.repaint();
                jTextArea.revalidate();
                jPanel.repaint();
            }
        });
        operateButton.addActionListener(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String operate = operateButton.getText();
                String tip = operate + "到：" + toPath ;
                JButton confirm = new JButton("确定");
                ConfirmView confirmView = new ConfirmView(tip, confirm);
                confirm.addActionListener(new AbstractAction() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        if (operate.equals("移动")){
                            fileService.transfer(scrollPanel.getSelectFiles(),toPath);
                        }else{
                            fileService.copy(scrollPanel.getSelectFiles(),toPath);
                        }
                        scrollPanel.reFresh();
                        confirmView.setVisible(false);
                        setVisible(false);
                    }
                });
            }
        });

        jPanel.add(jFileChooser);
        jPanel.add(jButton);
        jPanel.add(operateButton);
        jPanel.add(jTextArea);
        add(jPanel);
    }

    public void setOperate(String operateName) {
        operateButton.setText(operateName);
        operateButton.repaint();
        operateButton.revalidate();
        jPanel.repaint();
    }
}

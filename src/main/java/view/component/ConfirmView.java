package view.component;

import javax.swing.*;
import java.awt.event.ActionEvent;

/**
 * @Description:
 * @Author: July
 * @Date: 2021-10-31 21:06
 **/
public class ConfirmView extends JFrame {
    public ConfirmView(String tip, JButton button){
        setBounds(400, 200, 400, 200);
        setLocationRelativeTo(null);
        setDefaultLookAndFeelDecorated(true);
        JPanel jPanel = new JPanel();
        JTextArea jTextArea = new JTextArea(1,30);
        jTextArea.setText(tip);
        jTextArea.setEditable(false);
        JButton cancelButton = new JButton("取消");
        cancelButton.addActionListener(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                setVisible(false);
            }
        });
        jPanel.add(jTextArea);
        jPanel.add(button);
        jPanel.add(cancelButton);
        add(jPanel);
        setVisible(true);
    }

}

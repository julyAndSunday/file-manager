package view.component;

import javax.swing.*;
import java.awt.event.ActionEvent;

/**
 * @Description:
 * @Author: July
 * @Date: 2021-11-10 19:58
 **/
public class TipView extends JFrame {
    public TipView(String tip) {
        setTitle("tip");
        setBounds(400, 200, 400, 200);
        setLocationRelativeTo(null);
        setDefaultLookAndFeelDecorated(true);
        JPanel jPanel = new JPanel();
        JTextArea jTextArea = new JTextArea(1, 30);
        jTextArea.setText(tip);
        jTextArea.setEditable(false);
        JButton Button = new JButton("确定");
        Button.addActionListener(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                setVisible(false);
            }
        });
        jPanel.add(jTextArea);
        jPanel.add(Button);
        add(jPanel);
        setVisible(true);
    }
}

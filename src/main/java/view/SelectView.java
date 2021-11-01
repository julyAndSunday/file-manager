package view;

import common.Option;
import view.component.ScrollPanel;

import javax.swing.*;
import java.awt.event.ActionEvent;

/**
 * @Description:
 * @Author: July
 * @Date: 2021-10-31 17:11
 **/
public class SelectView extends JFrame {
    private ScrollPanel scrollPanel;

    public SelectView(ScrollPanel scrollPanel) {
        this.scrollPanel = scrollPanel;
        setTitle("条件选择");
        setBounds(400, 200, 400, 200);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setDefaultLookAndFeelDecorated(true);
        JPanel jPanel = new JPanel();
        jPanel.setBounds(500, 300, 200, 100);
        JComboBox<String> classify = new JComboBox<>();

        classify.setSize(5, 20);
        classify.addItem(Option.PRE);
        classify.addItem(Option.TAIL);
        classify.addItem(Option.TYPE);
        classify.addItem(Option.REG);

        JTextField jTextField = new JTextField();
        jTextField.setColumns(30);
        jPanel.add(classify);
        jPanel.add(jTextField);

        JButton jButton = new JButton("确定");
        jButton.addActionListener(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                scrollPanel.selectByOption((String) classify.getSelectedItem(), jTextField.getText());
                setVisible(false);
            }
        });
        jPanel.add(jButton);
        add(jPanel);

    }
}
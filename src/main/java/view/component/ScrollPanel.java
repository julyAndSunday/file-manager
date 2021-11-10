package view.component;

import common.Option;
import entity.FileVo;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import service.FileService;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class ScrollPanel extends JPanel {

    private static final long serialVersionUID = 786968642266699123L;
    private JPanel filePane;
    private JScrollPane scrollPane;
    private FileService fileService;
    private static StringBuilder currPath = new StringBuilder();
    private boolean selectAll = false;
    private static String DIR_PATH = "src/main/resources/directory.jpg";
    private static String FILE_PATH = "src/main/resources/file.jpg";

    /**
     * Create the panel.
     */

    public ScrollPanel(int width, int height) {
        fileService = FileService.getInstance();
        setLayout(null);

        setSize(new Dimension(width, height));

        scrollPane = new JScrollPane();
        scrollPane.getVerticalScrollBar().setUnitIncrement(40);
        scrollPane.setBounds(5, 60, width - 20, height - 120);
        filePane = new JPanel();
        filePane.setLayout(new GridLayout(0, 2));

        scrollPane.setViewportView(filePane);
        add(scrollPane);
        init();
    }

    private void init() {
        File[] files = File.listRoots();
        for (File file : files) {
            addCheckBox(file.getPath(), file.isDirectory());
        }
    }

    private void addCheckBox(String name, boolean isDir) {
        JCheckBox checkBox;
        if (isDir) {
            checkBox = new JCheckBox(name, new ImageIcon(DIR_PATH));
        } else {
            checkBox = new JCheckBox(name, new ImageIcon(FILE_PATH));
        }
        checkBox.setSelectedIcon(new ImageIcon("src/main/resources/select.jpg"));
        checkBox.setSize(10, 20);
        addDoubleClickEvent(checkBox);
        filePane.add(checkBox);
    }


    public void reFresh() {
        System.out.println(currPath.toString());
        filePane.removeAll();
        if (currPath.length() == 0) {
            init();
        } else {
            List<FileVo> fileVos = fileService.listFile(currPath.toString());
            for (FileVo fileVo : fileVos) {
                addCheckBox(fileVo.getName(), fileVo.isDirectory());
            }
        }
        filePane.repaint();
        filePane.revalidate();
        scrollPane.repaint();
    }

    private void addDoubleClickEvent(JCheckBox checkBox) {
        checkBox.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 2) {
                    JCheckBox checkBox1 = (JCheckBox) e.getSource();
                    File file = new File(currPath.toString() + checkBox1.getText());
                    if (file.isDirectory()) {
                        if (currPath.length() == 0) {
                            currPath.append(checkBox1.getText()); //磁盘
                        } else {
                            currPath.append(checkBox1.getText() + "\\");
                        }
                        reFresh();
                    }
                }
            }
            @Override
            public void mousePressed(MouseEvent e) {

            }

            @Override
            public void mouseReleased(MouseEvent e) {

            }

            @Override
            public void mouseEntered(MouseEvent e) {
            }

            @Override
            public void mouseExited(MouseEvent e) {
            }

        });
    }

    void back_path() {
        int i = currPath.lastIndexOf("\\", currPath.length() - 2);
        currPath.delete(i + 1, currPath.length());
        reFresh();
    }

    void selectAll() {
        Component[] components = filePane.getComponents();
        selectAll = !selectAll;
        for (Component component : components) {
            if (component instanceof JCheckBox) {
                JCheckBox checkBox = (JCheckBox) component;
                checkBox.setSelected(selectAll);
            }
        }
    }

    public void selectByOption(String selectedItem, String text) {
        Component[] components = filePane.getComponents();
        switch (selectedItem) {
            case Option.PRE:
                for (Component component : components) {
                    if (component instanceof JCheckBox) {
                        JCheckBox checkBox = (JCheckBox) component;
                        String filename = checkBox.getText();
                        if (filename.startsWith(text)) {
                            checkBox.setSelected(true);
                        }
                    }
                }
                break;
            case Option.TAIL:
                for (Component component : components) {
                    if (component instanceof JCheckBox) {
                        JCheckBox checkBox = (JCheckBox) component;
                        String filename = checkBox.getText();
                        if (filename.contains(".")) {
                            filename = filename.split("\\.")[0];
                        }
                        if (filename.endsWith(text)) {
                            checkBox.setSelected(true);
                        }
                    }
                }
                break;
            case Option.TYPE:
                for (Component component : components) {
                    if (component instanceof JCheckBox) {
                        JCheckBox checkBox = (JCheckBox) component;
                        String filename = checkBox.getText();
                        String[] content = filename.split("\\.");
                        if (content.length > 1 && text.equals(content[1])) {
                            checkBox.setSelected(true);
                        }
                    }
                }
                break;
            case Option.REG:
                for (Component component : components) {
                    if (component instanceof JCheckBox) {
                        JCheckBox checkBox = (JCheckBox) component;
                        String filename = checkBox.getText();
                        if (filename.matches(text)) {
                            checkBox.setSelected(true);
                        }
                    }
                }
                break;
        }
    }


    public List<File> getSelectFiles() {
        List<File>  files =new ArrayList<>();
        Component[] components = filePane.getComponents();
        String basePath = currPath.toString();
        for (Component component : components) {
            if (component instanceof JCheckBox) {
                JCheckBox checkBox = (JCheckBox) component;
                if (checkBox.isSelected()) {
                    files.add(new File(basePath + checkBox.getText()));
                }
            }
        }
        return files;
    }

}
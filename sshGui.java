import java.awt.*;
import java.awt.event.*;
import java.io.IOException;

import static javax.swing.JOptionPane.*;

public class sshGui {
    Label l1;

    public void sshGui() {
        Frame f = new Frame("SSH Server Selection");

        l1 = new Label("Select option");
        l1.setBounds(300, 50, 380, 80);
        f.add(l1);

        List list1 = new List(5);
        list1.setBounds(250, 150, 200, 90);
        f.add(list1);

        list1.add("Item 1");
        list1.add("Item 2");
        list1.add("Item 3");
        list1.add("Item 4");
        list1.add("Item 5");

        list1.addItemListener(new ItemListener() {
            public void itemStateChanged(ItemEvent e) {
                list1.addKeyListener(new KeyAdapter() {
                    public void keyPressed(KeyEvent e) {
                        if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                            l1.setText("You have selected " + list1.getSelectedItem());
                            try {
                                Process p = Runtime.getRuntime().exec(new String[]{"ghostty"});
                            } catch (IOException exception) {
                                exception.printStackTrace();
                            }
                        }
                    }
                });

            }
        });


        f.setSize(800, 800);
        f.setLayout(null);
        f.setVisible(true);

        f.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent event) {
                if (showConfirmDialog(f, "Are you sure you want to exit?", "Exiting Application!", YES_NO_OPTION, WARNING_MESSAGE) == YES_OPTION) {
                    System.exit(0);
                }

            }
        });

    }

    public static void main(String args[]) {
        sshGui g = new sshGui();
        g.sshGui();

    }

}

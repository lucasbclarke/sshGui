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

        list1.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    l1.setText("You have selected " + list1.getSelectedItem());
                    new sshGui().sshConneciton(list1.getSelectedIndex());
                }
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

    public void sshConneciton(int connectionNum) {
        System.out.println(connectionNum);
        try {
            switch (connectionNum) {
                case 0:
                    Process p00 = Runtime.getRuntime().exec(new String[]{"ghostty", "-e", "ssh", "lucas@192.168.0.6"});
                    break;
                case 1:
                    Process p1 = Runtime.getRuntime().exec(new String[]{"echo", "2"});
                    break;                                                    
                case 2:                                                      
                    Process p2 = Runtime.getRuntime().exec(new String[]{"echo", "3"});
                    break;                                                    
                case 3:                                                       
                    Process p3 = Runtime.getRuntime().exec(new String[]{"echo", "4"});
                    break;                                                    
                case 4:                                                       
                    Process p4 = Runtime.getRuntime().exec(new String[]{"echo", "5"});
                    break;
            }
        } catch (IOException exception) {
            exception.printStackTrace();
        }
    }

    public static void main(String args[]) {
        new sshGui().sshGui();
    }
}

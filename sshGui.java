import java.awt.*;
import java.awt.event.*;
import java.io.IOException;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class sshGui {
    Label l1;

    public void sshGui() {
        Frame f = new Frame("SSH Server Selection");

        l1 = new Label("Select option");
        l1.setBounds(55, 25, 290, 40);
        f.add(l1);

        List list1 = new List(5);
        list1.setBounds(65, 65, 120, 50);
        f.add(list1);

        list1.add("Pi-hole server");
        list1.add("Minecraft server");

        list1.addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    l1.setText("You have selected " + list1.getSelectedItem());
                    l1.setLocation(20, 25);
                    new sshGui().sshConneciton(list1.getSelectedIndex());
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException ie) {
                        System.err.println("Thread sleep was interrupted: " + ie.getMessage());
                        Thread.currentThread().interrupt(); 
                    }
                    f.dispose();
                }
                if (e.isControlDown() && e.getKeyCode() == KeyEvent.VK_P) {
                    list1.select(list1.getSelectedIndex() - 1);
                }
                if (e.isControlDown() && e.getKeyCode() == KeyEvent.VK_N) {
                    list1.select(list1.getSelectedIndex() + 1);
                }
            }
        });

        f.setSize(285, 150);
        f.setLayout(null);
        f.setVisible(true);

        f.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent event) {
                System.exit(0);
            }
        });
    }

    public void sshConneciton(int connectionNum) {
        try {
            switch (connectionNum) {
                case 0:
                    Process p0 = Runtime.getRuntime().exec(new String[]{getTerminal(), "-e", "ssh", getServer(1)});
                    break;
                case 1:
                    Process p1 = Runtime.getRuntime().exec(new String[]{getTerminal(), "-e", "ssh", getServer(2)});
                    break;                                                    
            }
        } catch (IOException exception) {
            exception.printStackTrace();
        }
    }

    public String getTerminal() {
        File config = new File("config");
        try (Scanner sc = new Scanner(config)) {
            while (sc.hasNextLine()) {
                String line = sc.nextLine().trim();
                if (line.startsWith("terminal=")) {
                    String com = line.substring("terminal=".length()).trim();
                    if (!com.isEmpty()) {
                        return com;
                    }
                }
            }
        } catch (FileNotFoundException e) {
            System.err.println("Config file not found: " + e.getMessage());
            e.printStackTrace();
        }
        System.out.println("No terminal found in config. Using default terminal.");
        return "ghostty";
    }

    public String getServer(int serverNum) {
        File config = new File("config");
        try (Scanner sc = new Scanner(config)) {
            while (sc.hasNextLine()) {
                String line = sc.nextLine().trim();
                String server = "server" + Integer.toString(serverNum) + "=";
                if (line.startsWith(server)) {
                    String com = line.substring(server.length()).trim();
                    if (!com.isEmpty()) {
                        return com;
                    }
                }
            }
        } catch (FileNotFoundException e) {
            System.err.println("Config file not found: " + e.getMessage());
            e.printStackTrace();
        }
        System.out.println("No terminal found in config. Using default terminal.");
        return "ghostty";

    }

    public static void main(String args[]) {
        new sshGui().sshGui();
    }
}

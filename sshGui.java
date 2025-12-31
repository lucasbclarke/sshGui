import java.awt.*;
import java.awt.event.*;
import java.io.IOException;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.regex.Pattern;

public class sshGui {
    Label l1;

    public String getTerminal() {
        File config = new File("config");
        try (Scanner sc = new Scanner(config)) {
            while (sc.hasNextLine()) {
                String line = sc.nextLine().trim();
                if (line.startsWith("terminal = ")) {
                    String com = line.substring("terminal = ".length()).trim();
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

    public String getServerAddress(int serverNum) {
        File config = new File("config");
        try (Scanner sc = new Scanner(config)) {
            while (sc.hasNextLine()) {
                String line = sc.nextLine().trim();
                String server = "server " + Integer.toString(serverNum) + " = ";
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
        System.out.println("No server found in config");
        return "error";
    }

    public String getServerName(int serverNum) {
        File config = new File("config");
        try (Scanner sc = new Scanner(config)) {
            while (sc.hasNextLine()) {
                String line = sc.nextLine().trim();
                String server = "server " + Integer.toString(serverNum) + " name = ";
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
        System.out.println("No server name found in config, using server number provided");
        return Integer.toString(serverNum);
    }

    public void sshConneciton(int connectionNum, Label label) {
        try {
            if (connectionNum >= 0 && connectionNum < totalServers()) {
                Process p = Runtime.getRuntime().exec(new String[]{getTerminal(), "-e", "ssh", getServerAddress(connectionNum + 1)});
            } else {
                label.setText("Invalid server selection. Please select a server from the list.");
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException ie) {
                    System.err.println("Thread sleep was interrupted: " + ie.getMessage());
                    Thread.currentThread().interrupt();
                }
            }
        } catch (IOException exception) {
            exception.printStackTrace();
        }
    }

    public int totalServers() {
        int totalServers = 0;
        File config = new File("config");
        try (Scanner sc = new Scanner(config)) {
            while (sc.hasNextLine()) {
                String line = sc.nextLine().trim();
                Pattern p = Pattern.compile("server \\d name = ");
                if (p.matcher(line).find()) {
                    totalServers++;
                }
            }
            return totalServers;
        } catch (FileNotFoundException e) {
            System.err.println("Config file not found: " + e.getMessage());
            e.printStackTrace();
            return 0;
        }
    }

    public void setupGui() {
        Frame f = new Frame("SSH Server Selection");

        l1 = new Label("Select option");
        l1.setBounds(55*2, 25, 290*2, 40*2);
        f.add(l1);

        List list1 = new List(5);
        list1.setBounds(65*2, 65*2, 120*2, 50*2);
        f.add(list1);

        for (int i = 1; i <= totalServers(); i++) {
            list1.add(getServerName(i));
        }

        list1.addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    l1.setText("You have selected " + list1.getSelectedItem());
                    l1.setLocation(20, 25);
                    new sshGui().sshConneciton(list1.getSelectedIndex(), l1);
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

        f.setSize(285*2, 150*2);
        f.setLayout(null);
        f.setVisible(true);

        f.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent event) {
                System.exit(0);
            }
        });
    }

    public static void main(String args[]) {
        new sshGui().setupGui();
    }
}

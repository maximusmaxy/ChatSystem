
import java.awt.Color;
import java.awt.Desktop;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.dnd.DropTarget;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.ScrollPaneConstants;
import javax.swing.SwingConstants;
import javax.swing.filechooser.FileFilter;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author Max
 */
public class ChatClientFrame extends javax.swing.JFrame {
    
    //private fields
    private ChatClient ChatClient;
    private String username = "Anonymous";
    private DropTarget listTarget;
    private DropTarget labelTarget;
    private BufferedImage storedImage;

    /**
     * Constructor
     */
    public ChatClientFrame() {
        initComponents();
        btnSend.setEnabled(false);
        mnuDisconnect.setEnabled(false);
        ListDropListener listDropListener = new ListDropListener(this);
        listTarget = new DropTarget(lstOnlineUsers, listDropListener);
        LabelDropListener labelDropListener = new LabelDropListener(this);
        labelTarget = new DropTarget(lblImage, labelDropListener);
    }

    //getters and setters
    public JTextArea getConversation() {
        return txtConversation;
    }

    public JList getOnlineUsers() {
        return lstOnlineUsers;
    }

    public JTextField getMessage() {
        return txtMessage;
    }

    public String getUsername() {
        return username;
    }

    /**
     * appends the message to the conversation text area.
     * @param message 
     */
    public void appendMessage(String message) {
        txtConversation.append(message + "\n");
    }
    
    /**
     * Connects to the specified host and port.
     * @param host
     * @param port
     * @return 
     */
    public boolean Connect(String host, int port) {
        try {

            Socket SOCK = new Socket(host, port);
            System.out.println("You connected to: " + host);
            ChatClient = new ChatClient(this, SOCK);
            System.out.println("Connecting");
            ChatClient.CONNECT(username);
            System.out.println("Connected");
            Thread X = new Thread(ChatClient);
            X.start();
            System.out.println("Client started");
            return true;
        } catch (Exception X) {
            System.out.print(X);
            JOptionPane.showMessageDialog(null, "Could not connect to " + host);
//            System.exit(0);
        }
        return false;
    }

    /**
     * Sends the image file to the specified user index.
     * @param file
     * @param index 
     */
    public void sendImage(File file, int index) {
        BufferedImage image = createImage(file);
        ChatClient.SENDIMAGE(image, username, index);
        txtConversation.append("Sent " + file.getName() + " to " + lstOnlineUsers.getSelectedValue() + "\n");
    }
    
    /**
     * Creates the buffered image and writes a watermark based on your username.
     * @param file 
     * @return 
     */
    public BufferedImage createImage(File file) {
        BufferedImage img = null;
        try {
            img = ImageIO.read(file);
        } catch (Exception ex) {
            System.out.println("Error reading file " + file.getName());
        }
        Graphics2D g = (Graphics2D) img.getGraphics();
        Font font = new Font("Helvetica", Font.BOLD | Font.ITALIC, 64);
        g.setFont(font);
        g.setColor(Color.BLACK);
        g.drawString(username, 2, 66);
        g.setColor(Color.WHITE);
        g.drawString(username, 0, 64);
        return img;
    }
    /**
     * Displays the buffered image to the screen and stores it to be saved later
     * @param img 
     */
    public void displayImage(BufferedImage img) {
        storedImage = img;
        int width = img.getWidth();
        int height = img.getHeight();
        if (img.getWidth() > lblImage.getWidth()) {
            width = lblImage.getWidth();
            height = (width * img.getHeight()) / img.getWidth();
        }
        if (height > lblImage.getHeight()) {
            height = lblImage.getHeight();
            width = (height * img.getWidth()) / img.getHeight();
        }
        Image scaledImg = img.getScaledInstance(width, height, Image.SCALE_SMOOTH);
        ImageIcon icon = new ImageIcon(scaledImg);
        lblImage.setIcon(icon);
    }
    
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jSeparator1 = new javax.swing.JSeparator();
        jLabel1 = new javax.swing.JLabel();
        txtMessage = new javax.swing.JTextField();
        btnSend = new javax.swing.JButton();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        txtConversation = new javax.swing.JTextArea();
        jLabel4 = new javax.swing.JLabel();
        lblUsername = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        lblImage = new javax.swing.JLabel();
        jScrollPane3 = new javax.swing.JScrollPane();
        lstOnlineUsers = new javax.swing.JList<>();
        jMenuBar1 = new javax.swing.JMenuBar();
        jMenu1 = new javax.swing.JMenu();
        mnuConnect = new javax.swing.JMenuItem();
        mnuDisconnect = new javax.swing.JMenuItem();
        jMenu2 = new javax.swing.JMenu();
        mnuAbout = new javax.swing.JMenuItem();
        mnuHelp = new javax.swing.JMenuItem();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Chat System");
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosed(java.awt.event.WindowEvent evt) {
                formWindowClosed(evt);
            }
        });

        jLabel1.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel1.setText("Message");

        btnSend.setText("Send");
        btnSend.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSendActionPerformed(evt);
            }
        });

        jLabel2.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel2.setText("Conversation");

        jLabel3.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel3.setText("Currently Online");

        txtConversation.setColumns(20);
        txtConversation.setRows(5);
        jScrollPane2.setViewportView(txtConversation);

        jLabel4.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel4.setText("Currently Logged In As");

        lblUsername.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblUsername.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        jLabel6.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel6.setText("Image");

        lblImage.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        lblImage.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lblImageMouseClicked(evt);
            }
        });

        jScrollPane3.setViewportView(lstOnlineUsers);

        jMenu1.setText("Connection");

        mnuConnect.setText("Connect");
        mnuConnect.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mnuConnectActionPerformed(evt);
            }
        });
        jMenu1.add(mnuConnect);

        mnuDisconnect.setText("Disconnect");
        mnuDisconnect.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mnuDisconnectActionPerformed(evt);
            }
        });
        jMenu1.add(mnuDisconnect);

        jMenuBar1.add(jMenu1);

        jMenu2.setText("Help");

        mnuAbout.setText("About");
        mnuAbout.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mnuAboutActionPerformed(evt);
            }
        });
        jMenu2.add(mnuAbout);

        mnuHelp.setText("Help");
        mnuHelp.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mnuHelpActionPerformed(evt);
            }
        });
        jMenu2.add(mnuHelp);

        jMenuBar1.add(jMenu2);

        setJMenuBar(jMenuBar1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel1)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(txtMessage, javax.swing.GroupLayout.PREFERRED_SIZE, 259, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(btnSend))
                            .addComponent(jScrollPane2))
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(40, 40, 40)
                                .addComponent(jLabel4))
                            .addGroup(layout.createSequentialGroup()
                                .addGap(18, 18, 18)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 166, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(lblUsername, javax.swing.GroupLayout.PREFERRED_SIZE, 166, javax.swing.GroupLayout.PREFERRED_SIZE))))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 18, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(170, 170, 170)
                        .addComponent(jLabel2)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jLabel3)
                        .addGap(53, 53, 53)))
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblImage, javax.swing.GroupLayout.PREFERRED_SIZE, 270, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(112, 112, 112)
                        .addComponent(jLabel6)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel4)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lblUsername, javax.swing.GroupLayout.PREFERRED_SIZE, 19, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel6)))
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel1)
                            .addComponent(txtMessage, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btnSend))))
                .addGap(6, 6, 6)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel2)
                            .addComponent(jLabel3))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 212, Short.MAX_VALUE)
                            .addComponent(jScrollPane2)))
                    .addComponent(lblImage, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(22, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents
    /**
     * sends the message to the other clients.
     * @param evt 
     */
    private void btnSendActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSendActionPerformed
        if (txtMessage.getText().length() != 0) {
            ChatClient.SEND(txtMessage.getText());
            txtMessage.requestFocus();
        }
    }//GEN-LAST:event_btnSendActionPerformed
    /**
     * opens up the connection dialog and connects to the server
     * @param evt 
     */
    private void mnuConnectActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mnuConnectActionPerformed
        LoginFrame loginFrame = new LoginFrame(this);
        loginFrame.setVisible(true);
        username = loginFrame.getUsername();
        if (loginFrame.isLogin() &&
                Connect(loginFrame.getHostName(), loginFrame.getHostPort())) {
            lblUsername.setText(username);
            this.setTitle(username + "'s Chat Box");
            btnSend.setEnabled(true);
            mnuDisconnect.setEnabled(true);
            mnuConnect.setEnabled(false);
        }
    }//GEN-LAST:event_mnuConnectActionPerformed
    /**
     * disconnects from the server.
     * @param evt 
     */
    private void mnuDisconnectActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mnuDisconnectActionPerformed
        try {
            ChatClient.DISCONNECT();
            mnuConnect.setEnabled(true);
            mnuDisconnect.setEnabled(false);
        } catch (Exception Y) {
            Y.printStackTrace();
        }
    }//GEN-LAST:event_mnuDisconnectActionPerformed
    /**
     * opens the about dialog
     * @param evt 
     */
    private void mnuAboutActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mnuAboutActionPerformed
        JOptionPane.showMessageDialog(this,
                "Multi-Client CHAT Program\nTAFE CULT 2017");
    }//GEN-LAST:event_mnuAboutActionPerformed
    /**
     * opens the help.html file in the default browser
     * @param evt 
     */
    private void mnuHelpActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mnuHelpActionPerformed
        File file = new File("help.html");
        try {
            Desktop.getDesktop().browse(file.toURI());
        } catch (Exception ex) {
            System.out.println("Error reading html file.");
            ex.printStackTrace();
        }
    }//GEN-LAST:event_mnuHelpActionPerformed
    /**
     * prompts the user to download the image in the label.
     * @param evt 
     */
    private void lblImageMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblImageMouseClicked
        if (storedImage != null) {
            int result = JOptionPane.showConfirmDialog(this, "Download this image?");
            if (result == JOptionPane.OK_OPTION) {
               JFileChooser fileChooser = new JFileChooser();
               fileChooser.setFileFilter(new FileFilter() {
                   @Override
                   public boolean accept(File file) {
                       return file.getName().toLowerCase().endsWith(".jpg")
                                 || file.isDirectory();
                   }

                   @Override
                   public String getDescription() {
                       return "jpg files";
                   }
               });
               result = fileChooser.showSaveDialog(this);
               if (result == JFileChooser.APPROVE_OPTION) {
                   File file = fileChooser.getSelectedFile();
                   if (!file.getName().toLowerCase().endsWith(".jpg"))
                       file = new File(file.getPath() + ".jpg");
                   try {
                       ImageIO.write(storedImage, "jpg", file);
                       JOptionPane.showMessageDialog(this,
                               "Saved " + file.getName());
                       lblImage.setIcon(null);
                       storedImage.flush();
                       storedImage = null;
                   }
                   catch (IOException ex) {
                       System.out.println("Error writing to file " + file.getName());
                       ex.printStackTrace();
                   }
               }
            }
        }
    }//GEN-LAST:event_lblImageMouseClicked
    /**
     * Disconnects from the client when closing the window.
     * @param evt 
     */
    private void formWindowClosed(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowClosed
        try {
            ChatClient.DISCONNECT();
        }
        catch (Exception ex) {
            System.out.println("Error closing client socket");
            ex.printStackTrace();
        } 
    }//GEN-LAST:event_formWindowClosed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Windows".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(ChatClientFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(ChatClientFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(ChatClientFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(ChatClientFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new ChatClientFrame().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnSend;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JMenu jMenu1;
    private javax.swing.JMenu jMenu2;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JLabel lblImage;
    private javax.swing.JLabel lblUsername;
    private javax.swing.JList<String> lstOnlineUsers;
    private javax.swing.JMenuItem mnuAbout;
    private javax.swing.JMenuItem mnuConnect;
    private javax.swing.JMenuItem mnuDisconnect;
    private javax.swing.JMenuItem mnuHelp;
    private javax.swing.JTextArea txtConversation;
    private javax.swing.JTextField txtMessage;
    // End of variables declaration//GEN-END:variables
}

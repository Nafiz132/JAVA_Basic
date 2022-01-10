import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.Socket;

public class Client {

    public static void main(String[] args) {
        final File[] file= new File[1];
        JFrame jf = new JFrame( "Sizan's Client");
        jf.setSize(450,450);
        jf.setLayout(new BoxLayout(jf.getContentPane(), BoxLayout.Y_AXIS));
        jf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JLabel jlt = new JLabel("Nafiz Sizan's file sender");
        jlt.setFont(new Font("Arial", Font.BOLD, 25));
        jlt.setBorder(new EmptyBorder(20,0,10,0));
        jlt.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel jlFileName = new JLabel("Choose a file to send");
        jlFileName.setFont(new Font("Arial", Font.BOLD, 20));
        jlFileName.setBorder(new EmptyBorder(50,0,0,0));
        jlFileName.setAlignmentX(Component.CENTER_ALIGNMENT);

        JPanel jpButton= new JPanel();
        jpButton.setBorder(new EmptyBorder(75,0,10,0));

        JButton jbSendFile= new JButton("Send file");
        jbSendFile.setPreferredSize(new Dimension(150,75));
        jbSendFile.setFont(new Font("Arial",Font.BOLD,20));

        JButton jbChooseFile = new JButton("choose File");
        jbChooseFile.setPreferredSize(new Dimension(150,75));
        jbChooseFile.setFont(new Font("Arial",Font.BOLD,20));

        jpButton.add(jbSendFile);
        jpButton.add(jbChooseFile);

        jbChooseFile.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFileChooser jfchooser = new JFileChooser();
                jfchooser.setDialogTitle("Choose a file to send");

                if(jfchooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION){
                    file[0]= jfchooser.getSelectedFile();
                    jlFileName.setText("The file you want to send: "+ file[0].getName());
                }
            }
        });

        jbSendFile.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                if (file[0] == null) {
                    jlFileName.setText("Please choose a file first!");
                } else {
                    try {
                        FileInputStream fileInputStream = new FileInputStream(file[0].getAbsolutePath());
                        Socket socket = new Socket("localhost", 1234);

                        DataOutputStream dataOutputStream = new DataOutputStream(socket.getOutputStream());

                        String fileName = file[0].getName();
                        byte[] fileNameBytes = fileName.getBytes();

                        byte[] fileContentBytes = new byte[(int) file[0].length()];
                        fileInputStream.read(fileContentBytes);

                        dataOutputStream.writeInt(fileNameBytes.length);
                        dataOutputStream.write(fileNameBytes);

                        dataOutputStream.writeInt(fileContentBytes.length);
                        dataOutputStream.write(fileContentBytes);
                    } catch (IOException error) {
                        error.printStackTrace();
                    }
                }
            }
        });
            jf.add(jlt);
            jf.add(jlFileName);
            jf.add(jpButton);
            jf.setVisible(true);
    }
}
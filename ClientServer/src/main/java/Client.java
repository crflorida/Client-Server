// Christina Grant
//  Application that uses a socket connection to allow a client
//  -to specify a file name of a text file and have the server send
//  -the contents of the file or indicate the file does not exist.
//  The server contains a text based password file username & password.
//  The client must pass a valid username and password to establish a
//  -connection with the Server.
// ***Important run Server class first and then run Client class***
// ***Username=admin, Password=password***

import java.awt.*;
import java.net.*;
import java.io.*;
import java.awt.event.*;
import javax.swing.*;

public class Client extends JFrame implements ActionListener {

    private static final long serialVersionUID = 1L;
    private JTextField fileField;
    private JTextArea contents;
    private BufferedReader bufferInput;
    private BufferedWriter bufferOutput;
    private Socket connection;
    private JPanel panel;
    private JLabel label;
    private JScrollPane scroller;

    // set up GUI, connect to server, get streams
    @SuppressWarnings("deprecation")
    public Client() {
        // set up GUI
        label = new JLabel("Enter file name to retrieve:");

        panel = new JPanel();
        panel.setLayout(new GridLayout(1, 2, 0, 0));
        panel.add(label);

        fileField = new JTextField();
        fileField.addActionListener(this);
        panel.add(fileField);

        contents = new JTextArea();
        scroller = new JScrollPane(contents);

        Container container = getContentPane();
        container.setLayout(new BorderLayout());
        container.add(panel, BorderLayout.NORTH);
        container.add(scroller, BorderLayout.CENTER);

        // connect to server, get streams
        try {

            // create Socket to make connection to server
            connection = new Socket(InetAddress.getLocalHost(), 5000);

            // set up output stream
            bufferOutput = new BufferedWriter(new OutputStreamWriter(
                    connection.getOutputStream()));

            // flush output buffer to send header information
            bufferOutput.flush();

            // set up input stream
            bufferInput = new BufferedReader(new InputStreamReader(
                    connection.getInputStream()));
        } // process problems communicating with server
        catch (IOException ioException) {
        }

        setSize(500, 500);
        show();
    }

    // process file name entered by user
    @Override
    public void actionPerformed(ActionEvent event) {
        // display contents of file
        try {
            String fileName = event.getActionCommand() + "\n";
            bufferOutput.write(fileName, 0, fileName.length());
            bufferOutput.flush();
            String output = bufferInput.readLine();

            contents.setText(output);

            // if file exists, dislay file contents
            if (output.equals("The file is:")) {
                output = bufferInput.readLine();

                while (output != null) {
                    contents.append(output + "\n");
                    output = bufferInput.readLine();
                }
            }

            fileField.setEditable(false);
            fileField.setBackground(Color.lightGray);
            fileField.removeActionListener(this);

            // close streams and socket
            bufferOutput.close();
            bufferInput.close();
            connection.close();
        } // end of file
        catch (EOFException eofException) {
            System.out.println("End of file");
        } // process problems communicating with server
        catch (IOException ioException) {
        }
    }

    // execute application
    public static void main(String args[]) throws FileNotFoundException {

        JFrame frame = new LoginClient();
        frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
    }

}  // end class Client

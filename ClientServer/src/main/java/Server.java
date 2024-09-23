// Project by Christina Grant
//  Application that uses a socket connection to allow a client
//  -to specify a file name of a text file and have the server send
//  -the contents of the file or indicate the file does not exist.
//  The server contains a text based password file username & password.
//  The client must pass a valid username and password to establish a
//  -connection with the Server.
// ***Important run Server class first and then run Client class***
// ***Username=admin, Password=password***

import java.net.*;
import java.io.*;
import javax.swing.JFrame;

public class Server {

    private ServerSocket server;
    private Socket connection;
    private BufferedReader input;
    private BufferedWriter output;

    // constructor
    public Server() {
        // create ServerSocket
        try {
            server = new ServerSocket(5000, 10);
        } // process problems communicating with server
        catch (IOException exception) {
            System.exit(0);
        }
    }
    // run server
    public void runServer() {
        // wait for connection, get streams, read file
        try {

            // allow server to accept connection
            connection = server.accept();

            // set up output stream
            output = new BufferedWriter(new OutputStreamWriter(
                    connection.getOutputStream()));

            // flush output buffer to send header information
            output.flush();

            // set up input stream
            input = new BufferedReader(new InputStreamReader(
                    connection.getInputStream()));

            // receive file name from client
            File file = new File(input.readLine());

            String result;

            // send file to client
            if (file.exists()) {
                BufferedReader fileInput = new BufferedReader(
                        new InputStreamReader(new FileInputStream(file)));

                // write first 13 characters
                output.write("The file is:\n", 0, 13);
                output.flush();

                // read first line of file
                result = fileInput.readLine();

                while (result != null) {
                    output.write(result + '\n', 0, result.length() + 1);
                    output.flush();
                    result = fileInput.readLine();
                }
            } // file does not exist
            else {
                result = file.getName() + " does not exist\n";
                output.write(result, 0, result.length());
                output.flush();
            }

            // close streams and socket
            output.close();
            input.close();
            connection.close();
            
        } // process problems communicating with server
        catch (IOException ioException) {
            System.err.println("IOException has occurred!");

            System.exit(0);
        }

    }  // end method runServer

    // execute application
    public static void main(String args[]) throws FileNotFoundException {

        Server application = new Server();
        application.runServer();

    }
} //end class Server

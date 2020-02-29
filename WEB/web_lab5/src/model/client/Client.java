package model.client;

import model.exception.ClientConnectionException;
import model.server.Server;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;
import java.util.Scanner;

/**
 * Class of client that connect to server and receive the string from it
 *
 * @author Vera Shavela
 * @version 1.0
 */
public class Client {

    private static final Logger logger = LogManager.getLogger();

    private BufferedReader in;

    private PrintWriter out;

    private Socket socket;

    private String name;

    /**
     * Client name getter
     * @return client name
     */
    private String getName() {
        return name;
    }

    /**
     * default constructor of Client
     */
    private Client() {
        System.out.println("Enter client name: ");
        Scanner sc = new Scanner(System.in);
        name = "Client_" + sc.nextLine();
    }

    /**
     * constructor of Client with specified name
     */
    private Client(String name) {
        this.name = name;
    }

    /**
     * Runs the client as an application
     * @param args args of command line
     */
    public static void main(String[] args) {
        Client client = new Client();
        try {
            client.run();
        } catch(IOException | ClientConnectionException e) {
            logger.error(e);
        }
    }

    /**
     * Connects to the server then receives the string.
     *
     * @throws ClientConnectionException if can't start server
     * @throws IOException in case of problems with IO
     */
    private void run() throws ClientConnectionException, IOException {
        try {
            socket = new Socket(InetAddress.getLocalHost(), 9001);
        } catch (IOException e) {
            throw new ClientConnectionException("Server isn't started!", e);
        }
        try {
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        } catch (IOException e) {
            throw new ClientConnectionException("Connection with server-output error!", e);
        }
        try {
            out = new PrintWriter(socket.getOutputStream(), true);
        } catch (IOException e) {
            throw new ClientConnectionException("Connection with server-input error!", e);
        }

        logger.info(name + " connected to server.");

        String test = null;
        try {
            test = in.readLine();
        } catch (IOException e) {
            throw e;
        }
        if (test.equals("CLIENT_NAME_REQUEST")) {
            out.println(name);
            logger.info("Send self name to server.");
        }

        String message = null;
        while(true) {
            try {
                message = in.readLine();
            } catch (IOException e) {
                throw e;
            }
            if(message != null) {
                logger.info(name + " accepted string: \"" + message + "\"");
                break;
            }
        }
    }
}

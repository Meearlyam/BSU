package model.client;

import model.exceptions.ClientConnectionException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.Scanner;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;

/**
 * Class of client that connect to server and receive the string from it
 *
 * @author Vera Shavela
 * @version 1.0.0
 */
public class Client {

    private static Logger logger;

    private SocketChannel socket;

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
        name = "client_" + sc.nextLine();
        logger = LogManager.getLogger(name);
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
            socket = SocketChannel.open(new InetSocketAddress(InetAddress.getLocalHost(), 9001));
            socket.configureBlocking(false);
        } catch (IOException e) {
            throw new ClientConnectionException("Server isn't started!", e);
        }

        logger.info(name + " connected to server.");


        while(true) {
            ByteBuffer buffer = ByteBuffer.allocate(1000);
            socket.read(buffer);
            String input = new String(buffer.array()).trim();

            buffer.clear();

            if(input.length() > 0) {
                if (input.equals("CLIENT_NAME_REQUEST")) {
                    buffer.put(name.getBytes());
                    buffer.flip();
                    socket.write(buffer);
                    logger.info("Send self name to server.");
                } else {
                    logger.info(name + " accepted string: \"" + input + "\"");
                    break;
                }
            }
        }
    }
}

package model.server;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.*;

/**
 * Class of server that connect specified clients and send them random strings from file
 *
 * @author Vera Shavela
 * @version 1.0
 */
public class Server {

    private static final Logger logger = LogManager.getLogger();

    private static final int PORT = 9001;

    private static List<String> fileStrings;

    /**
     * Runs the server as an application
     * @param args args of command line
     */
    public static void main(String[] args) {
        fileStrings = new ArrayList<>();
        logger.info("The server is running on port: " + PORT) ;
        try {
            BufferedReader bf = new BufferedReader(new FileReader(new File("D:\\Web_lab2\\web_lab5\\src\\model\\file")));
            String readString;
            while((readString = bf.readLine()) != null) {
                fileStrings.add(readString);
            }
            ServerSocket listener = new ServerSocket(PORT);
            while (true) {
                new ServerHandler(listener.accept()).start();
            }
        }
        catch (IOException e) {
            logger.error(e);
        }
    }

    /**
     * class of server handler that handling connections
     *
     * @author Vera Shavela
     * @version 1.0
     */
    private static class ServerHandler extends Thread {

        private String name;

        private Socket socket;

        private BufferedReader in;

        private PrintWriter out;

        ServerHandler(Socket socket) {
            this.socket = socket;
        }

        public void run() {
            try {
                in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                out = new PrintWriter(socket.getOutputStream(), true);

                while(true) {
                    out.println("CLIENT_NAME_REQUEST");
                    name = in.readLine();
                    if(name == null) {
                        continue;
                    }
                    logger.info("Server got client name: " + name);
                    break;
                }

                Random rnd = new Random();
                int index = Math.abs(rnd.nextInt() % fileStrings.size());
                String message = fileStrings.get(index);
                out.println(message);
                logger.info("Send string: \"" + message + "\" to: " + name);

            } catch (IOException e) {
                System.out.println(e);
            } finally {
                if(name != null) {
                    logger.info("Client " + name + " disconnected from server.");
                    name = null;
                }
                try {
                    socket.close();
                } catch (IOException e) {}
            }
        }
    }
}

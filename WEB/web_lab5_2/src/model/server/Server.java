package model.server;

import com.sun.xml.internal.ws.policy.privateutil.PolicyUtils;
import model.exceptions.ServerException;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.*;

import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;

/**
 * Class of server that connect specified clients and send them random strings from file
 *
 * @author Vera Shavela
 * @version 1.0
 */
public class Server {

    private static final Logger logger = LogManager.getLogger("server");

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
            ServerHandler serverHandler = new ServerHandler();
            serverHandler.work();
        }
        catch (ServerException | IOException e) {
            logger.error(e);
        }
    }

    /**
     * class of server handler that handling connections
     *
     * @author Vera Shavela
     * @version 1.0.0
     */
    private static class ServerHandler {

        private ServerSocketChannel serverSocketChannel;

        private Selector selector;

        ServerHandler() throws ServerException {
            try {
                serverSocketChannel = ServerSocketChannel.open();
                serverSocketChannel.configureBlocking(false);
                serverSocketChannel.socket().bind(new InetSocketAddress(InetAddress.getLocalHost(), 9001));
            } catch(IOException e) {
                throw new ServerException("Server socket channel problem was registered", e);
            }

            try {
                selector = Selector.open();
                int ops = serverSocketChannel.validOps();
                serverSocketChannel.register(selector, ops, null);
            } catch(IOException e) {
                throw new ServerException("Selector problem was registered", e);
            }
        }

        private void acceptClient() throws IOException {
            logger.info("Client try to connect");
            SocketChannel client = serverSocketChannel.accept();
            client.configureBlocking(false);

            client.register(selector, SelectionKey.OP_READ);

            ByteBuffer buffer = ByteBuffer.allocate(1000);
            buffer.put("CLIENT_NAME_REQUEST".getBytes());
            buffer.flip();

            client.write(buffer);
        }

        private void sendRandomString(SelectionKey key) throws IOException {
            SocketChannel client = (SocketChannel) key.channel();
            ByteBuffer buffer = ByteBuffer.allocate(1000);
            client.read(buffer);
            String clientName = "";

            String rez = new String(buffer.array()).trim();
            buffer.clear();

            if(rez.length() > 0) {
                clientName = rez;

                logger.info("Server got client name: " + clientName);

                Random rnd = new Random();
                int index = Math.abs(rnd.nextInt() % fileStrings.size());
                String message = fileStrings.get(index);
                buffer.put(message.getBytes());
                buffer.flip();
                client.write(buffer);

                logger.info("Send string: \"" + message + "\" to: " + clientName);

                key.channel().close();
                logger.info("Client " + clientName + " disconnected from server");
            }
        }

        public void work() throws ServerException{
            while(true) {
                try {
                    selector.select();
                } catch(IOException e) {
                    throw new ServerException("Selector select problem caused problem", e);
                }

                Set<SelectionKey> selectedKeys = selector.selectedKeys();
                Iterator<SelectionKey> iterator = selectedKeys.iterator();
                while(iterator.hasNext()) {
                    SelectionKey key = iterator.next();
                    if(key.isAcceptable()) {
                        try {
                            acceptClient();
                        } catch(IOException e) {
                            throw new ServerException("Client accepting exception", e);
                        }
                    } else if(key.isReadable()) {
                        try {
                            sendRandomString(key);
                        } catch(IOException e) {
                            throw new ServerException("Client dialog exception", e);
                        }
                    }
                    iterator.remove();
                }
            }

//            try {
//                in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
//                out = new PrintWriter(socket.getOutputStream(), true);
//
//                while(true) {
//                    out.println("CLIENT_NAME_REQUEST");
//                    name = in.readLine();
//                    if(name == null) {
//                        continue;
//                    }
//                    logger.info("Server got client name: " + name);
//                    break;
//                }
//
//                Random rnd = new Random();
//                int index = Math.abs(rnd.nextInt() % fileStrings.size());
//                String message = fileStrings.get(index);
//                out.println(message);
//                logger.info("Send string: \"" + message + "\" to: " + name);
//
//            } catch (IOException e) {
//                System.out.println(e);
//            } finally {
//                if(name != null) {
//                    logger.info("Client " + name + " disconnected from server.");
//                    name = null;
//                }
//                try {
//                    socket.close();
//                } catch (IOException e) {}
//            }
        }
    }
}

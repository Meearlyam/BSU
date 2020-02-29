package model.client;

import model.exceptions.ClientConnectionException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;

import java.io.IOException;
import java.net.InetAddress;
import java.util.Scanner;

/**
 * Class of client that connect to server and receive the string from it
 *
 * @author Vera Shavela
 * @version 1.0.0
 */
public class Client {

    private static Logger logger = LogManager.getLogger();

    private String name;

    /**
     * Client name getter
     * @return client name
     */
    public String getName() {
        return name;
    }

    /**
     * default constructor of Client
     */
    private Client() {
        System.out.println("Enter client name: ");
        Scanner sc = new Scanner(System.in);
        name = "client_" + sc.nextLine();
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
        } catch(ClientConnectionException e) {
            logger.error(e);
        }
    }

    /**
     * Connects to the server then receives the string.
     *
     * @throws ClientConnectionException if can't start server
     */
    private void run() throws ClientConnectionException {
        EventLoopGroup group = new NioEventLoopGroup();
        try {
            Bootstrap clientBootstrap = new Bootstrap();
            clientBootstrap.group(group)
                    .channel(NioSocketChannel.class)
                    .remoteAddress(InetAddress.getLocalHost(), 9001)
                    .option(ChannelOption.TCP_NODELAY, true)
                    .handler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        public void initChannel(SocketChannel socketChannel) {
                            socketChannel.pipeline().addLast(new ClientHandler(Client.this));
                        }
                    });

            ChannelFuture channelFuture = clientBootstrap.connect().sync();
            channelFuture.channel().closeFuture().sync();
        } catch (Exception e) {
            throw new ClientConnectionException(name + " cannot connect to server!", e);
        }
        finally {
            group.shutdownGracefully();
        }

        logger.info(name + " connected to server.");

    }
}

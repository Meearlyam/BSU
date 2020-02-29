package model.server;

import model.exceptions.ServerException;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import java.io.*;
import java.util.*;
import java.net.InetAddress;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;

/**
 * Class of server that connect specified clients and send them random strings from file
 *
 * @author Vera Shavela
 * @version 1.0
 */
public class Server {

    private static final Logger logger = LogManager.getLogger();

    private static final int PORT = 9001;

    public static List<String> fileStrings;

    /**
     * Runs the server as an application
     * @param args args of command line
     */
    public static void main(String[] args) {
        fileStrings = new ArrayList<>();
        logger.info("The server is running on port: " + PORT);
        EventLoopGroup group = new NioEventLoopGroup();

        try {
            BufferedReader bf = new BufferedReader(new FileReader(new File("D:\\Web_lab2\\web_lab5\\src\\model\\file")));
            String readString;
            while((readString = bf.readLine()) != null) {
                fileStrings.add(readString);
            }

            ServerBootstrap serverBootstrap = new ServerBootstrap();
            serverBootstrap
                    .group(group)
                    .channel(NioServerSocketChannel.class)
                    .localAddress(InetAddress.getLocalHost(), PORT);

            serverBootstrap.childHandler(new ChannelInitializer<SocketChannel>() {
                protected void initChannel(SocketChannel socketChannel) throws Exception {
                    socketChannel.pipeline().addLast(new ServerHandler());
                }
            });
            ChannelFuture channelFuture = serverBootstrap.bind().sync();
            channelFuture.channel().closeFuture().sync();
        }
        catch (Exception e) {
            logger.warn(e);
        }
        finally {
            group.shutdownGracefully();
        }
    }
}

package model.server;


import model.exceptions.ServerException;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.util.CharsetUtil;
import io.netty.util.ReferenceCountUtil;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import java.util.Random;

/**
 * class of server handler that handling connections
 *
 * @author Vera Shavela
 * @version 1.0.0
 */

@ChannelHandler.Sharable
public class ServerHandler extends ChannelInboundHandlerAdapter {

    private static final Logger logger = LogManager.getLogger();

//    ServerHandler() throws ServerException {
//        try {
//            serverSocketChannel = ServerSocketChannel.open();
//            serverSocketChannel.configureBlocking(false);
//            serverSocketChannel.socket().bind(new InetSocketAddress(InetAddress.getLocalHost(), 9001));
//        } catch(IOException e) {
//            throw new ServerException("Server socket channel problem was registered", e);
//        }
//
//        try {
//            selector = Selector.open();
//            int ops = serverSocketChannel.validOps();
//            serverSocketChannel.register(selector, ops, null);
//        } catch(IOException e) {
//            throw new ServerException("Selector problem was registered", e);
//        }
//    }

    @Override
    public void channelRegistered(ChannelHandlerContext ctx) throws ServerException {
        try {
            super.channelRegistered(ctx);
        } catch (Exception e) {
            throw new ServerException("channelRegistered", e);
        }
        ctx.writeAndFlush(Unpooled.copiedBuffer("CLIENT_NAME_REQUEST", CharsetUtil.UTF_8));
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) {
        String clientName = ((ByteBuf) msg).toString(CharsetUtil.UTF_8).trim();

        if(clientName.length() > 0) {
            logger.info("Client with name ( " + clientName + " ) is connected!");
            Random rnd = new Random();
            int index = Math.abs(rnd.nextInt() % Server.fileStrings.size());
            String message = Server.fileStrings.get(index);
            ctx.writeAndFlush(Unpooled.copiedBuffer(message, CharsetUtil.UTF_8));
            logger.info("Send string: \"" + message + "\" to: " + clientName);
        }


        ReferenceCountUtil.release(msg);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws ServerException {
        ctx.close();
        throw new ServerException("exceptionCaught", cause);
    }

//    private void acceptClient() throws IOException {
//        logger.info("Client try to connect");
//        SocketChannel client = serverSocketChannel.accept();
//        client.configureBlocking(false);
//
//        client.register(selector, SelectionKey.OP_READ);
//
//        ByteBuffer buffer = ByteBuffer.allocate(1000);
//        buffer.put("CLIENT_NAME_REQUEST".getBytes());
//        buffer.flip();
//
//        client.write(buffer);
//    }



//    private void sendRandomString(SelectionKey key) throws IOException {
//        SocketChannel client = (SocketChannel) key.channel();
//        ByteBuffer buffer = ByteBuffer.allocate(1000);
//        client.read(buffer);
//        String clientName = "";
//
//        String rez = new String(buffer.array()).trim();
//        buffer.clear();
//
//        if(rez.length() > 0) {
//            clientName = rez;
//
//            logger.info("Server got client name: " + clientName);
//
//            Random rnd = new Random();
//            int index = Math.abs(rnd.nextInt() % fileStrings.size());
//            String message = fileStrings.get(index);
//            buffer.put(message.getBytes());
//            buffer.flip();
//            client.write(buffer);
//
//            logger.info("Send string: \"" + message + "\" to: " + clientName);
//
//            key.channel().close();
//            logger.info("Client " + clientName + " disconnected from server");
//        }
//    }

//    public void startWork() throws ServerException{
//        while(true) {
//            try {
//                selector.select();
//            } catch(IOException e) {
//                throw new ServerException("Selector select caused problem", e);
//            }
//
//            Set<SelectionKey> selectedKeys = selector.selectedKeys();
//            Iterator<SelectionKey> iterator = selectedKeys.iterator();
//            while(iterator.hasNext()) {
//                SelectionKey key = iterator.next();
//                if(key.isAcceptable()) {
//                    try {
//                        acceptClient();
//                    } catch(IOException e) {
//                        throw new ServerException("Client accepting exception", e);
//                    }
//                } else if(key.isReadable()) {
//                    try {
//                        sendRandomString(key);
//                    } catch(IOException e) {
//                        throw new ServerException("Client dialog exception", e);
//                    }
//                }
//                iterator.remove();
//            }
//        }
//    }
}

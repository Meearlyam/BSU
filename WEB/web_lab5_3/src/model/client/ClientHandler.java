package model.client;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.util.CharsetUtil;
import io.netty.util.ReferenceCountUtil;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


@ChannelHandler.Sharable
public class ClientHandler extends ChannelInboundHandlerAdapter {
    private static final Logger logger = LogManager.getLogger();

    private String name;
    private Client client;

    ClientHandler(Client client) {
        this.client = client;
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        super.channelActive(ctx);
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) {
        String input = ((ByteBuf) msg).toString(CharsetUtil.UTF_8).trim();

        if (input.equals("CLIENT_NAME_REQUEST")) {
            name = client.getName();
            ctx.writeAndFlush(Unpooled.copiedBuffer(name, CharsetUtil.UTF_8));
            logger.info("Send self name to server.");
        }
        else {
            logger.info(name + " got message \"" + input + "\" from server.");
        }
        ReferenceCountUtil.release(msg);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext channelHandlerContext, Throwable cause) {
        cause.printStackTrace();
        channelHandlerContext.close();
    }
}

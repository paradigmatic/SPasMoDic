package spasmodic;

import java.util.Collection;
import java.util.Iterator;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;
import org.jgroups.Address;
import org.jgroups.Receiver;
import org.jgroups.View;

import static spasmodic.Message.Kind.*;

/**
 *
 * @author falcone
 */
public class CommunicatorReceiver implements Receiver {

    private final MessageBucket bucket = new MessageBucket();
    private final BlockingQueue<Message<?>> shutdownQueue = new LinkedBlockingQueue<Message<?>>();

    public void receive(org.jgroups.Message msg) {
        Object data = msg.getObject();
        Message<?> m = (Message<?>) data;
        if ( m.kind == SHUTDOWN ) {
            shutdownQueue.add(m);
        } else {
            bucket.add( m );
        }
    }

    public Message<?> getData( Message<?> template ) throws InterruptedException {
        return bucket.take( template );
    }

    public Message<?> getShutdownMessage() throws InterruptedException {
        return shutdownQueue.take();
    }

    public byte[] getState() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public void setState(byte[] state) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public void viewAccepted(View new_view) {
        //TODO: Check for early disconnections
    }

    public void suspect(Address suspected_mbr) {
        //TODO: Check for early disconnections
    }

    public void block() {
        //TODO: See if useful
    }
}

package spasmodic;

import org.jgroups.Address;
import org.jgroups.Receiver;
import org.jgroups.View;

/**
 *
 * @author falcone
 */
public class CommunicatorReceiver implements Receiver {

    private final MessageBucket bucket = new MessageBucket();

    public void receive(org.jgroups.Message msg) {
        Object data = msg.getObject();
        Message<?> m = (Message<?>) data;
        bucket.add( m );
    }

    public Message<?> getData( Message<?> template ) throws InterruptedException {
        return bucket.take( template );
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

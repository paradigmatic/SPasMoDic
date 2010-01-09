package jgroupsmpi;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingDeque;
import org.jgroups.Address;
import org.jgroups.Message;
import org.jgroups.Receiver;
import org.jgroups.View;

/**
 *
 * @author falcone
 */
public class CommunicatorReceiver implements Receiver {

    private final BlockingQueue buffer = new LinkedBlockingDeque();

    public void receive(Message msg) {
        Object data = msg.getObject();
        buffer.offer(data);
    }

    public Object getData() throws InterruptedException {
        return buffer.take();
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

package jgroupsmpi;

import org.jgroups.Address;
import org.jgroups.Message;
import org.jgroups.Receiver;
import org.jgroups.View;

/**
 *
 * @author falcone
 */
public class CommunicatorReceiver implements Receiver {

    public void receive(Message msg) {
        throw new UnsupportedOperationException("Not supported yet.");
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

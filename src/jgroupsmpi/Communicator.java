package jgroupsmpi;

import java.io.Serializable;
import java.util.Vector;
import org.jgroups.Address;
import org.jgroups.Channel;
import org.jgroups.ChannelClosedException;
import org.jgroups.ChannelException;
import org.jgroups.ChannelNotConnectedException;
import org.jgroups.JChannel;



/**
 *
 * @author falcone
 */
public class Communicator {

    private final static int UNDEFINED = -1;

    private final Channel channel;
    private final CommunicatorReceiver receiver;
    private final int nProc;
    private final String clusterName;
    private int nRank = -UNDEFINED;
    private Vector<Address> procs;

    public Communicator(int nProc, String clusterName) throws ChannelException {
        this.nProc = nProc;
        this.clusterName = clusterName;
        channel = new JChannel();
        receiver = new CommunicatorReceiver();
    }

    public void start() throws ChannelException, InterruptedException {
        channel.connect(clusterName);
        channel.setReceiver( receiver );
        while( channel.getView().getMembers().size() < nProc ) {
            Thread.sleep(1000);
            System.out.println("Waiting for everybody to connect...");

        }
        procs = channel.getView().getMembers();
        for( int i=0; i<nProc; i++ ) {
            if( channel.getAddress().equals( procs.get(i) ) ) {
                nRank = i;
                break;
            }
        }
    }

    public int nRank() {
        return nRank;
    }

    public int nProc() {
        return nProc;
    }

    public void stop() {
        channel.disconnect();
        channel.close();
    }

    public void send( Serializable s, int dest, int tag ) throws ChannelNotConnectedException, ChannelClosedException {
        Message msg = new Message(nRank, tag, s);
        channel.send( new org.jgroups.Message(procs.get( dest ), null, msg) );
    }

    public Object receive( int source, int tag ) throws InterruptedException {
        Message template = new Message( source, tag, null );
        return receiver.getData(template);
    }

    
}

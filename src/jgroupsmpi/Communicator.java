package jgroupsmpi;

import org.jgroups.Address;
import org.jgroups.Channel;
import org.jgroups.ChannelException;
import org.jgroups.JChannel;



/**
 *
 * @author falcone
 */
public class Communicator {

    private final static int UNDEFINED = -1;

    private final Channel channel;
    private final int nProc;
    private final String clusterName;
    private int nRank = -UNDEFINED;

    public Communicator(int nProc, String clusterName) throws ChannelException {
        this.nProc = nProc;
        this.clusterName = clusterName;
        channel = new JChannel();
    }

    public void start() throws ChannelException, InterruptedException {
        channel.connect(clusterName);
        channel.setReceiver( new CommunicatorReceiver() );
        while( channel.getView().getMembers().size() < nProc ) {
            Thread.sleep(1000);
            System.out.println("Waiting for everybody to connect...");

        }
        for( int i=0; i<nProc; i++ ) {
            Address addr = channel.getView().getMembers().get(i);
            if( channel.getAddress().equals( addr ) ) {
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


    
}

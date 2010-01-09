package jgroupsmpi;

import org.jgroups.Channel;
import org.jgroups.ChannelException;
import org.jgroups.JChannel;



/**
 *
 * @author falcone
 */
public class Communicator {
    private final Channel channel;
    private final int nProc;
    private final String clusterName;

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
        System.out.println("Everybody is here.");
    }

    public void stop() {
        channel.disconnect();
        channel.close();
    }


    
}

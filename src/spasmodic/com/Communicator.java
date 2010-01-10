/*
 * This file is part of SPasMoDic
 *
 *  SPasMoDic is free software: you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation, either version 3 of the License, or
 *  (at your option) any later version.
 *
 *  SPasMoDic is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU General Public License for more details.
 *
 *  You should have received a copy of the GNU General Public License
 *  along with Foobar.  If not, see <http://www.gnu.org/licenses/>.
 *
 *  (c) 2009, paradigmatic, paradigmatic@streum.org
 *
 */

package spasmodic.com;

import java.io.Serializable;
import java.util.Vector;
import org.jgroups.Address;
import org.jgroups.Channel;
import org.jgroups.ChannelClosedException;
import org.jgroups.ChannelException;
import org.jgroups.ChannelNotConnectedException;
import org.jgroups.JChannel;

import spasmodic.Status;
import spasmodic.msg.Message;
import static spasmodic.msg.Message.Kind.*;



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
    private int myRank = -UNDEFINED;
    private Vector<Address> procs;

    public static Communicator init( int nProc, String clusterName ) throws ChannelException, InterruptedException {
        Communicator comm = new Communicator(nProc, clusterName);
        comm.start();
        return comm;
    }

    private Communicator(int nProc, String clusterName) throws ChannelException {
        this.nProc = nProc;
        this.clusterName = clusterName;
        channel = new JChannel();
        receiver = new CommunicatorReceiver();
    }

    private void start() throws ChannelException, InterruptedException {
        channel.connect(clusterName);
        channel.setReceiver( receiver );
        while( channel.getView().getMembers().size() < nProc ) {
            Thread.sleep(1000);
            System.out.println("Waiting for everybody to connect...");

        }
        procs = channel.getView().getMembers();
        for( int i=0; i<nProc; i++ ) {
            if( channel.getAddress().equals( procs.get(i) ) ) {
                myRank = i;
                break;
            }
        }
    }

    public int myRank() {
        return myRank;
    }

    public int nProc() {
        return nProc;
    }

    public void shutdown() throws ChannelNotConnectedException, ChannelClosedException, InterruptedException {
        channel.send( new org.jgroups.Message( null, null, Message.shutdown(myRank) ) );
        for( int i=0; i<nProc; i++ ) {
            receiver.getShutdownMessage();
        }
        channel.disconnect();
        channel.close();
    }

    public void send( Serializable s, int dest, int tag ) throws ChannelNotConnectedException, ChannelClosedException {
        Message msg = new Message( POINT2POINT, s.getClass(), myRank, tag, s);
        channel.send( new org.jgroups.Message(procs.get( dest ), null, msg) );
    }

    public <T> T receive( Class<T> type, int source, int tag ) throws InterruptedException {
        Message template = new Message( POINT2POINT, type, source, tag, null );
        return type.cast( receiver.getData(template).content );
    }

    public <T> T receive( Class<T> type, int source, int tag, Status status ) throws InterruptedException {
        Message template = new Message( POINT2POINT, type, source, tag, null );
        Message received = receiver.getData(template);
        status.source = received.source;
        status.tag = received.tag;
        return type.cast( received.content );
    }

    public <T extends Serializable> T broadcast( T s,  Class<T> type, int root, int tag ) throws ChannelNotConnectedException, ChannelClosedException, InterruptedException {
        if (myRank == root) {
            Message msg = new Message( BROADCAST, type, myRank, tag, s);
            channel.send( new org.jgroups.Message( null, null, msg) );
            return s;
        } else {
            Message template = new Message( BROADCAST, type, root, tag, null );
            return (T) receiver.getData(template).content;
        }

    }

    
}

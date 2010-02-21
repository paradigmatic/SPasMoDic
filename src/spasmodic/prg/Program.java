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
 *  (c) 2009-2010, paradigmatic, paradigmatic@streum.org
 *
 */

package spasmodic.prg;

import java.io.Serializable;
import org.jgroups.ChannelClosedException;
import org.jgroups.ChannelNotConnectedException;
import spasmodic.Status;
import spasmodic.com.Communicator;
import spasmodic.op.Reductor;

public abstract class Program {

    private Communicator comm;

    public final void defineCommunicator( Communicator comm ) {
        if ( this.comm == null ) {
            this.comm = comm;
        } else {
            throw new IllegalStateException("Communicator was already defined");
        }
    }

    public abstract void execute() throws Exception;

    public void shutdown() throws ChannelNotConnectedException, ChannelClosedException, InterruptedException {
        comm.shutdown();
    }

    public void send(Serializable s, int dest, int tag) throws ChannelNotConnectedException, ChannelClosedException {
        comm.send(s, dest, tag);
    }

    public <T extends Serializable> T reduce(T s, Class<T> type, Reductor<T> reductor, int tag) throws InterruptedException, ChannelNotConnectedException, ChannelClosedException {
        return comm.reduce(s, type, reductor, tag);
    }

    public <T> T receive(Class<T> type, int source, int tag, Status status) throws InterruptedException {
        return comm.receive(type, source, tag, status);
    }

    public <T> T receive(Class<T> type, int source, int tag) throws InterruptedException {
        return comm.receive(type, source, tag);
    }

    public int nProc() {
        return comm.nProc();
    }

    public int myRank() {
        return comm.myRank();
    }

    public <T extends Serializable> T broadcast(T s, Class<T> type, int root, int tag) throws ChannelNotConnectedException, ChannelClosedException, InterruptedException {
        return comm.broadcast(s, type, root, tag);
    }

    

}

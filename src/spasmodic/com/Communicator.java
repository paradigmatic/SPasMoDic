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

package spasmodic.com;

import java.io.Serializable;
import org.jgroups.ChannelClosedException;
import org.jgroups.ChannelNotConnectedException;
import spasmodic.Status;
import spasmodic.op.Reductor;
import spasmodic.prg.Program;

/**
 *
 * @author falcone
 */
public interface Communicator {

    <T extends Serializable> T broadcast(T s, Class<T> type, int root, int tag) throws ChannelNotConnectedException, ChannelClosedException, InterruptedException;

    int myRank();

    int nProc();

    void execute( Program prg ) throws Exception;

    <T> T receive(Class<T> type, int source, int tag) throws InterruptedException;

    <T> T receive(Class<T> type, int source, int tag, Status status) throws InterruptedException;

    <T extends Serializable> T reduce(T s, Class<T> type, Reductor<T> reductor, int tag) throws InterruptedException, ChannelNotConnectedException, ChannelClosedException;

    void send(Serializable s, int dest, int tag) throws ChannelNotConnectedException, ChannelClosedException;

    void shutdown() throws ChannelNotConnectedException, ChannelClosedException, InterruptedException;

}

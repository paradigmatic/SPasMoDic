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

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import org.jgroups.Address;
import org.jgroups.Receiver;
import org.jgroups.View;

import spasmodic.msg.Message;
import static spasmodic.msg.Message.Kind.*;


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

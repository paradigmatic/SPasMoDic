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

import spasmodic.msg.Message;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import static spasmodic.Constants.*;

public class MessageBucket {

    private final List<Message<?>> bucket;
    private final Lock lock;
    private final Condition newMessage;

    public MessageBucket() {
        bucket = new LinkedList<Message<?>>();
        lock = new ReentrantLock();
        newMessage = lock.newCondition();
    }

    public void add(Message<?> msg) {
        try {
            lock.lock();
            bucket.add( msg );
            newMessage.signalAll();
        
        } finally {
            lock.unlock();
        }
    }

    public Message<?> take( Message<?> template ) throws InterruptedException {
        try{
            lock.lock();
            while( true ) {
                for( Message<?> msg: bucket ) {
                    boolean typeOK = template.type.equals( msg.type );
                    boolean sourceOK = template.source == ANY_SOURCE || msg.source == template.source;
                    boolean tagOK = template.tag == ANY_SOURCE || msg.tag == template.tag;
                    if( typeOK && sourceOK && tagOK ) {
                        bucket.remove(msg);
                        return msg;
                    }
                }
                newMessage.await();
            }
        } finally {
            lock.unlock();
        }
    }

    public int size() {
        return bucket.size();
    }
}

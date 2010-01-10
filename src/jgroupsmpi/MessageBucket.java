package jgroupsmpi;

import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import static jgroupsmpi.Constants.*;

/**
 *
 * @author falcone
 */
//TODO: Concurrency
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
                    boolean sourceOK = template.source == ANY_SOURCE || msg.source == template.source;
                    boolean tagOK = template.tag == ANY_SOURCE || msg.tag == template.tag;
                    if( sourceOK && tagOK ) {
                        return msg;
                    }
                }
                newMessage.await();
            }
        } finally {
            lock.unlock();
        }
    }
}

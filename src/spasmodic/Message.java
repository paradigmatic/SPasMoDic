package spasmodic;

import java.io.Serializable;

/**
 *
 * @author falcone
 */
public class Message<T extends Serializable> implements Serializable {

    enum Kind {
        POINT2POINT,
        SHUTDOWN,
        BROADCAST;
    }



    public final Kind kind;
    public final int source;
    public final int tag;
    public final T content;
    public final Class<T> type;

    public Message(Kind kind,  Class<T> type, int source, int tag, T content) {
        this.kind = kind;
        this.source = source;
        this.tag = tag;
        this.content = content;
        this.type = type;
    }

    public static Message<?> shutdown( int source ) {
        return new Message<Serializable>( Kind.SHUTDOWN, Serializable.class, source, Constants.ANY_TAG, null );
    }



}

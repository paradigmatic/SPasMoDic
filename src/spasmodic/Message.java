package spasmodic;

import java.io.Serializable;

/**
 *
 * @author falcone
 */
public class Message<T extends Serializable> implements Serializable {

    public final int source;
    public final int tag;
    public final T content;
    public final Class<T> type;

    public Message(Class<T> type, int source, int tag, T content) {
        this.source = source;
        this.tag = tag;
        this.content = content;
        this.type = type;
    }

}

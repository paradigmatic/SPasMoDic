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

package spasmodic.msg;

import java.io.Serializable;
import spasmodic.Constants;

public class Message<T extends Serializable> implements Serializable {

    public enum Kind {
        POINT2POINT,
        SHUTDOWN,
        BROADCAST,
        REDUCTION;
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

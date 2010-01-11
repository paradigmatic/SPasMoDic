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

/*
 *                            0
 *                      1             2
 *                   3     4       5       6
 *                 7   8  9  10  11  12  13  14
 */


public class CommunicationTree {

    public static final int EMPTY = -1;

    private final int nProc;

    public CommunicationTree(int nProc) {
        this.nProc = nProc;
    }

    public int parentOf( int i ) {
        if( i == 0 ) {
            return EMPTY;
        }
        return (i-1)/2;
    }

    public int leftChildOf( int i ) {
        final int child = i*2 + 1;
        if( child >= nProc ) {
            return EMPTY;
        } else {
            return child;
        }
    }

    public int rightChildOf( int i ) {
        final int child = i*2 + 2;
        if( child >= nProc ) {
            return EMPTY;
        } else {
            return child;
        }
    }

}

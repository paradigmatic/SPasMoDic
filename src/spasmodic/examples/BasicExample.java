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

package spasmodic.examples;

import spasmodic.com.Communicator;
import spasmodic.*;
import static spasmodic.Constants.*;

public class BasicExample {

    public static void main(String[] args) throws Exception {
        Communicator com = Communicator.init(3, "MPIJgroupsTest");

        System.out.println("My rank: " + com.myRank());

        if (com.myRank() == 0) {
            for (int i = 0; i < 1; i++) {
                com.send("Machin" + i, 2, 0);
                Thread.sleep(500);
            }
        } else if (com.myRank() == 1) {
            for (int i = 0; i < 1; i++) {
                com.send(i, 2, 0);
                Thread.sleep(500);
            }
        } else {
            Status status = new Status();
            for (int i = 0; i < 1; i++) {
                String msg = com.receive(String.class, ANY_SOURCE, 0, status);
                System.out.println("Received: " + msg + " from node: " + status.source);
                int msg2 = com.receive( Integer.class, ANY_SOURCE, 0, status);
                System.out.println("Received: " + msg2 + " from node: " + status.source);
            }
        }

        String machin = null;
        if ( com.myRank() == 2 ) {
            machin = "truc";
        }
        machin = com.broadcast(machin, String.class, 2, 0);
        System.out.println("Machin: " + machin);
        com.shutdown();
    }
}

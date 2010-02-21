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

package spasmodic.examples;

import spasmodic.com.Communicator;
import spasmodic.com.JGroupsCommunicator;
import spasmodic.prg.Program;

public class HelloWorld extends Program {

    public void execute() throws Exception {
        if ( nProc() != 2 ) {
            throw new RuntimeException("Two nodes must connect");
        }
        if( myRank() == 0 ) {
            send("Hello World !", 1, 0);
        } else {
            String greetings = receive(String.class, 0, 0);
            System.out.println("Received: " + greetings);
        }
    }

    public static void main(String[] args) throws Exception {
        Communicator com = JGroupsCommunicator.init(2, "HelloWorldTest");
        com.execute( new HelloWorld() );
        com.shutdown();
    }



}

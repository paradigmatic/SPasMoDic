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

import spasmodic.com.JGroupsCommunicator;
import spasmodic.op.Reductor;
import spasmodic.prg.Program;


public class RankReduction extends Program {
    
    private final Reductor<Integer> plus = new Reductor<Integer>() {
        public Integer reduce(Integer left, Integer right) {
            return left + right;
        }
    };

    public void execute() throws Exception {
        int sum = reduce( myRank(), Integer.class, plus, 0);
        System.out.println("The sum is: " + sum);
    }

    public static void main(String[] args) throws Exception {
        JGroupsCommunicator com = JGroupsCommunicator.init(4, "ReductionTest");
        com.execute( new RankReduction() );
        com.shutdown();
    }

}

package jgroupsmpi;

import static jgroupsmpi.Constants.*;



public class Main {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws Exception {
        Communicator com = new Communicator(3, "MPIJgroupsTest");
        com.start();
        System.out.println("My rank: " + com.nRank() );
        if( com.nRank() == 0 )  {
            for( int i=0; i<10; i++ ) {
              com.send( "Machin"+i, 2, 0 );
              Thread.sleep(500);
            }
        } else if( com.nRank() == 1 ) {
            for( int i=0; i<10; i++ ) {
              com.send( "Truc"+i, 2, 0 );
              Thread.sleep(500);
            }
        } else {
            for( int i=0; i<10; i++ ) {
                String msg = (String) com.receive( 0, 0 );
                System.out.println("Received: " + msg);
            }
            for( int i=0; i<10; i++ ) {
                String msg = (String) com.receive( 1, 0 );
                System.out.println("Received: " + msg);
            }
        }
        Thread.sleep(1000);
        com.stop();
    }

}

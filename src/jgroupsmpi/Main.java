package jgroupsmpi;


import org.jgroups.ChannelException;


public class Main {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws Exception {
        Communicator com = new Communicator(3, "MPIJgroupsTest");
        com.start();
        System.out.println("My rank: " + com.nRank() );
        if( com.nRank() == 0 )  {
            com.send( "Machin", 1 );
        }
        if( com.nRank() == 1 ) {
            String msg = (String) com.receive();
            System.out.println("Received: " + msg);
        }
        Thread.sleep(1000);
        com.stop();
    }

}

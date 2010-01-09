package jgroupsmpi;


import org.jgroups.ChannelException;


public class Main {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws ChannelException, InterruptedException {
        Communicator com = new Communicator(3, "MPIJgroupsTest");
        com.start();
        System.out.println("My rank: " + com.nRank() );
        Thread.sleep(2000);
        com.stop();
    }

}

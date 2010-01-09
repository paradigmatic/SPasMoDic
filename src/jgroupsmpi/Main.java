package jgroupsmpi;


import jgroupsmpi.Communicator;
import org.jgroups.ChannelException;


public class Main {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws ChannelException, InterruptedException {
        Communicator com = new Communicator(3, "MPIJgroupsTest");
        com.start();
    }

}

package jgroupsmpi;



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
        /*String msg2 = null;
        if( com.nRank() == 2 ) {
            msg2 = "truc chose";
        }
        msg2 = (String) com.broadcast(msg2, 2);
        System.out.println("Received by broadcast: " + msg2);*/
        Thread.sleep(1000);
        com.stop();
    }

}

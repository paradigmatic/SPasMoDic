package spasmodic;

import static spasmodic.Constants.*;

public class Main {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws Exception {
        Communicator com = new Communicator(3, "MPIJgroupsTest");
        com.start();
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
        com.stop();
    }
}

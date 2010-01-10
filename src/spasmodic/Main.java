package spasmodic;

import static spasmodic.Constants.*;

public class Main {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws Exception {
        Communicator com = new Communicator(3, "MPIJgroupsTest");
        com.start();
        System.out.println("My rank: " + com.nRank());
        if (com.nRank() == 0) {
            for (int i = 0; i < 10; i++) {
                com.send("Machin" + i, 2, 0);
                Thread.sleep(500);
            }
        } else if (com.nRank() == 1) {
            for (int i = 0; i < 10; i++) {
                com.send(i, 2, 0);
                Thread.sleep(500);
            }
        } else {
            Status status = new Status();
            for (int i = 0; i < 10; i++) {
                String msg = com.receive(String.class, ANY_SOURCE, 0, status);
                System.out.println("Received: " + msg + " from node: " + status.source);
                int msg2 = com.receive( Integer.class, ANY_SOURCE, 0, status);
                System.out.println("Received: " + msg2 + " from node: " + status.source);
            }
        }
        com.stop();
    }
}

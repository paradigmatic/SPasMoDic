package jgroupsmpi;

import static jgroupsmpi.Constants.*;

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
                com.send(String.class, "Machin" + i, 2, 0);
                Thread.sleep(500);
            }
        } else if (com.nRank() == 1) {
            for (int i = 0; i < 10; i++) {
                com.send(int[].class, new int[]{i}, 2, 0);//FIXME: only 0 are received...
                Thread.sleep(500);
            }
        } else {
            for (int i = 0; i < 10; i++) {
                String msg = (String) com.receive(String.class, 0, 0);
                System.out.println("Received: " + msg);
            }
            for (int i = 0; i < 10; i++) {
                int[] msg = (int[]) com.receive(int[].class, 1, 0);
                System.out.println("Received: " + msg[0]);
            }
        }
        Thread.sleep(1000);
        com.stop();
    }
}

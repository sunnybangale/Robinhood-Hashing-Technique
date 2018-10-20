package shb170230;

import java.util.Random;

public class DriverProgram {


    public static void main(String[] args) {

        RobinhoodHashing<Integer> map = new RobinhoodHashing();
        /*System.out.println("Index for find " + map.find(87));
        for (int i = 0; i <= 1000; i++) {
            map.add(i);
        }
        map.printTable();
        for (int i = 50; i <= 1000; i++) {
            map.remove(i);
        }*/
        map.printTable();
        Random r = new Random();
        Integer[] integers = new Integer[1000000000];
        for (int i = 0; i < integers.length; i++) {
            int newNumber = r.nextInt();
            integers[i] = newNumber;
            //integers[i] = i;
        }
        //System.out.println(Arrays.toString(integers));
        Timer timer = new Timer();
        timer.start();
        System.out.println(RobinhoodHashing.distinctElements(integers));
        timer.end();
        System.out.println(timer);
    }


}

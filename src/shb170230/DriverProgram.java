package shb170230;

import java.util.HashSet;
import java.util.Random;
import java.util.Set;

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
        Random r = new Random();
        Integer[] integers = new Integer[1000000];
        for (int i = 0; i < integers.length; i++) {
            int newNumber = r.nextInt();
            integers[i] = newNumber;
            //integers[i] = i;
        }
        //System.out.println(Arrays.toString(integers));
        Timer timer = new Timer();
        timer.start();
        System.out.println("Distinct elements in Robinhood hasing " + RobinhoodHashing.distinctElements(integers));
        timer.end();
        System.out.println(timer);

        timer.start();
        Set<Integer> set = new HashSet<>();
        for (int i = 0; i < integers.length; i++) {
            int newNumber = r.nextInt();
            set.add(newNumber);
            //integers[i] = i;
        }
        System.out.println("Distinct elements in HashSet " + set.size());
        timer.end();
        System.out.println(timer);


    }


}

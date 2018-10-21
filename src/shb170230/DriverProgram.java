package shb170230;

import java.util.Random;

public class DriverProgram {


    public static void main(String[] args) {

        RobinhoodHashing<Integer> map = new RobinhoodHashing();

/*
        Random random = new Random();
        for (int i = 0; i <= 1000; i++) {
            //map.add(random.nextInt());
            map.add(i);
        }
        //map.printTable();
        System.out.println(map.getSize());

        for (int i = 0; i <= 1000; i++) {
            //map.remove(random.nextInt());
            map.remove(i);
        }
        //map.printTable();
        System.out.println(map.getSize());
*/

        Random r = new Random();
        Integer[] integers = new Integer[1000000];
        for (int i = 0; i < integers.length; i++) {
            int newNumber = r.nextInt();
            integers[i] = newNumber;
            //integers[i] = -49585003;
        }
        //System.out.println(Arrays.toString(integers));
        Timer timer = new Timer();
        timer.start();
        System.out.println("Distinct elements in Robinhood hasing " + RobinhoodHashing.distinctElements(integers));
        timer.end();
        System.out.println(timer);

        /*timer.start();
        Set<Integer> set = new HashSet<>();
        //set.addAll(Arrays.asList(integers));
        for (int i = 0; i < integers.length; i++) {
            set.add(integers[i]);
        }
        System.out.println("Distinct elements in HashSet " + set.size());
        timer.end();
        System.out.println(timer);*/

    }


}

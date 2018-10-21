package shb170230;

mport java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class DriverProgram {
    public static void main(String[] args) throws FileNotFoundException {
        Scanner sc;
        if (args.length > 0) {
            File file = new File(args[0]);
            sc = new Scanner(file);
        } else {
            sc = new Scanner(System.in);
        }
        String operation = "";
        int operand = 0;
        long modValue = 1000000009;
        long result = 0;

        System.out.println("After performing large number of Add, Remove and Contains operations\n");
        Timer timer = new Timer();
        Set<Integer> set = new HashSet<>();
        while (!((operation = sc.next()).equals("End"))) {
            switch (operation) {
                case "Add": {
                    operand = sc.nextInt();
                    if(set.add(operand) == true ) {
                        result = (result + 1) % modValue;
                    }
                    break;
                }
                case "Remove": {
                    operand = sc.nextInt();
                    if (set.remove(operand) != false) {
                        result = (result + 1) % modValue;
                    }
                    break;
                }
                case "Contains":{
                    operand = sc.nextInt();
                    if (set.contains(operand)) {
                        result = (result + 1) % modValue;
                    }
                    break;
                }
                default:
                    break;
            }
        }
        System.out.println("Java Hashset result : "+ result);
        System.out.println("Java Hashset size : "+ set.size());
        System.out.println(timer.end());

        File file = new File(args[0]);
        sc = new Scanner(file);
        result = 0;
        RobinhoodHashing<Integer> dm = new RobinhoodHashing();
        while (!((operation = sc.next()).equals("End"))) {
            switch (operation) {
                case "Add": {
                    operand = sc.nextInt();
                    if(dm.add(new Integer(operand))) {
                        result = (result + 1) % modValue;
                    }
                    break;
                }
                case "Remove": {
                    operand = sc.nextInt();
                    if (dm.remove(new Integer(operand)) != null) {
                        result = (result + 1) % modValue;
                    }
                    break;
                }
                case "Contains":{
                    operand = sc.nextInt();
                    if (dm.contains(new Integer(operand))) {
                        result = (result + 1) % modValue;
                    }
                    break;
                }
                default:
                    break;
            }
        }
        System.out.println("\nRobinhood Hashing result : "+ result);
        System.out.println("Robinhood Hashing size : "+ dm.getSize());
        System.out.println(timer.end());

        /*Generate an array of random integers, and calculate how many distinct
        * numbers it has.
        * Compare running times of HashSet and your hashing implementation, for large n.*/

        Random random = new Random();
        Integer[] integers = new Integer[1000000];
        for (int i = 0; i < integers.length; i++) {
            int newNumber = random.nextInt();
            integers[i] = newNumber;
        }
        System.out.println("-------------------------------------------------");
        System.out.println("Adding random integers\n");
        //Timer timer = new Timer();
        timer.start();
        System.out.println("Distinct elements in Robinhood hasing : " + RobinhoodHashing.distinctElements(integers));
        timer.end();
        System.out.println(timer);

        timer.start();
        Set<Integer> jset = new HashSet<>();
        jset.addAll(Arrays.asList(integers));
        System.out.println("\nDistinct elements in Java's HashSet : " + jset.size());
        timer.end();
        System.out.println(timer);
    }
}

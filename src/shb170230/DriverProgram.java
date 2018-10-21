package shb170230;

import java.io.File;
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
        int val = 0; // change to 1, for testing java hashset
        if(val == 1) {
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
            System.out.println("java hashset result : "+result);
            System.out.println("java hashset size : "+set.size());
            System.out.println(timer.end());
        }
        else {
            Timer timer = new Timer();
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
            System.out.println("robinhood result : "+result);
            System.out.println("robinhood size : "+dm.getSize());
            System.out.println(timer.end());
        }

        /*RobinhoodHashing<Integer> map = new RobinhoodHashing();
        Random r = new Random();
        Integer[] integers = new Integer[1000000];
        for (int i = 0; i < integers.length; i++) {
            int newNumber = r.nextInt();
            integers[i] = newNumber;
        }
        Timer timer = new Timer();
        timer.start();
        System.out.println("Distinct elements in Robinhood hasing " + RobinhoodHashing.distinctElements(integers));
        timer.end();
        System.out.println(timer);

        timer.start();
        Set<Integer> set = new HashSet<>();
        set.addAll(Arrays.asList(integers));
        System.out.println("Distinct elements in HashSet " + set.size());
        timer.end();
        System.out.println(timer);*/
    }
}

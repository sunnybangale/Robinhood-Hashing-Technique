package shb170230;

public class DriverProgram {


    public static void main(String[] args) {

        RobinhoodHashing<Integer> map = new RobinhoodHashing();

        System.out.println("Index for find " + map.find(87));

        for (int i = 0; i <= 1000; i++) {
            map.add(i);
        }
        map.printTable();
        for (int i = 50; i <= 1000; i++) {
            map.remove(i);
        }
        map.printTable();
    }


}

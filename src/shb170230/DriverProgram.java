package shb170230;

public class DriverProgram {


    public static void main(String[] args) {

        RobinhoodHashing<Integer> map = new RobinhoodHashing();

        for (int i = 0; i <= 2000; i++) {
            map.add(i);
        }
        map.printTable();
        for (int i = 50; i <= 100; i++) {
            map.remove(i);
        }
        map.printTable();
    }


}

package shb170230;

import java.util.HashSet;

public class RobinhoodHashing<T> {

    static final double LOADFACTOR = 0.5;
    private int size;
    private Entry[] robinhoodHashingHashTable;
    private int capacity;
    //private int maxDisplacement = 0;

    static class Entry<T>
    {
        T data;
        boolean isDeleted;

        Entry(T data)
        {
            this.data = data;
            this.isDeleted = false;
        }
    }

    RobinhoodHashing()
    {
        this.size = 0;
        this.capacity = 1024;
        this.robinhoodHashingHashTable = new Entry[capacity];
    }

    static int hash(int h)
    {
        h ^= (h >>> 20) ^ (h >>> 12);
        return h ^ (h >>> 7) ^ (h >>> 4);
    }

    private int hashHelper(T x)
    {
        return indexFor(hash(x.hashCode()), robinhoodHashingHashTable.length);
    }

    private static int indexFor(int h, int length)
    {
        return (h & (length - 1));
    }

    static <T> int distinctElements(T[] arr) {
        RobinhoodHashing<T> rh = new RobinhoodHashing<>();
        HashSet<T> set = new HashSet<>();

        for (T ele : arr) {
            rh.add(ele);
            //set.add(ele);
        }
        //rh.printTable();
        //System.out.println("Robinhood size " + rh.getSize());
        //System.out.println("Hashset size " + set.size());

        return rh.size;
    }

    public int getSize() {
        return this.size;
    }

    public void printTable() {
        System.out.print("Table: ");
        for (int j = 0; j < robinhoodHashingHashTable.length; j++) {
            if (robinhoodHashingHashTable[j] != null && robinhoodHashingHashTable[j].isDeleted == false)
                System.out.print(robinhoodHashingHashTable[j].data + " ");
            else
                System.out.print("* ");
        }
        System.out.println();
    }

    public boolean contains(T x) {
        int loc = find(x);
        return ((robinhoodHashingHashTable[loc] != null) && robinhoodHashingHashTable[loc].data.equals(x)
                && (robinhoodHashingHashTable[loc].isDeleted == false));
    }

    private int displacement(T x, int loc) {
        return loc >= hashHelper(x) ? loc - hashHelper(x) : robinhoodHashingHashTable.length + loc - hashHelper(x);
    }

    public int find(T x)
    {
        int k = 0;
        int ik = 0;

        while (true)
        //while( k <= maxDisplacement)
        {
            //ik = hashHelper(x) + (k * hashHelper2(x)) % size;
            //ik = (ik + k) % robinhoodHashingHashTable.length;
            ik = (hashHelper(x) + k) % robinhoodHashingHashTable.length;

            if (robinhoodHashingHashTable[ik] == null ||
                    (robinhoodHashingHashTable[ik].data.equals(x) && !robinhoodHashingHashTable[ik].isDeleted))
            {
                return ik;
            } else if (robinhoodHashingHashTable[ik].isDeleted) {
                break;
            } else {
                k++;
            }
        }

        int xspot = ik;

        while(true)
        //while( k <= maxDisplacement)
        {
            k++;
            ik = (hashHelper(x) + k) % robinhoodHashingHashTable.length;

            if (robinhoodHashingHashTable[ik] != null && robinhoodHashingHashTable[ik].data.equals(x))
            {
                return ik;
            }
            if (robinhoodHashingHashTable[ik] == null)
            {
                return xspot;
            }
        }
        //return ik;
    }

    public boolean add(T x)
    {
        if (contains(x)) {
            return false;
        }

        int loc = hashHelper(x);
        int d = 0;

        while(true)
        {
            if (robinhoodHashingHashTable[loc] == null || robinhoodHashingHashTable[loc].isDeleted)
            {
                robinhoodHashingHashTable[loc] = new Entry(x);
                this.size++;
                double currentLoad = (this.size * 1.0) / (robinhoodHashingHashTable.length * 1.0);
                if (currentLoad > LOADFACTOR) {
                    rehashTable();
                }
                return true;
            } else if (displacement((T) robinhoodHashingHashTable[loc], loc) >= d) {
                d = d + 1;
                loc = (loc + 1) % robinhoodHashingHashTable.length;

                /*if (d > maxDisplacement) {
                    maxDisplacement = d;
                }*/

            } else {
                Entry<T> temp = robinhoodHashingHashTable[loc];
                temp.data = (T) robinhoodHashingHashTable[loc].data;

                robinhoodHashingHashTable[loc] = new Entry(x);
                x = temp.data;

                loc = (loc + 1) % robinhoodHashingHashTable.length;
                d = displacement(x, loc);

                /*if (d > maxDisplacement) {
                    maxDisplacement = d;
                }*/

            }
        }
    }

    public Entry remove(T x) {
        int loc = find(x);
        if (robinhoodHashingHashTable[loc] != null && robinhoodHashingHashTable[loc].data.equals(x) && !robinhoodHashingHashTable[loc].isDeleted) {
            Entry result = robinhoodHashingHashTable[loc];
            robinhoodHashingHashTable[loc].isDeleted = true;
            this.size--;
            return result;
        } else {
            return null;
        }
    }

    private void rehashTable() {
        Entry<T> temporaryHashingTable[] = this.robinhoodHashingHashTable;
        this.capacity = capacity * 2;
        this.robinhoodHashingHashTable = new Entry[capacity];
        this.size = 0;

        for (int i = 0; i < temporaryHashingTable.length; i++) {
            if (temporaryHashingTable[i] != null && !temporaryHashingTable[i].isDeleted) {
                temporaryHashingTable[i].isDeleted = false;
                add(temporaryHashingTable[i].data);
            }
        }
    }

}
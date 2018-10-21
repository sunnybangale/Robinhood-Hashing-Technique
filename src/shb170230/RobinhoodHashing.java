package shb170230;

import java.util.HashSet;

public class RobinhoodHashing<T> {

    static final double LOADFACTOR = 0.5; // load factor represents at what level the Rohinhood hashtable capacity should be doubled
    private int size; // size of the rohinhood hashtable
    private Entry[] robinhoodHashingHashTable; // stores elements in the array
    private int capacity; // maximum capacity to store elements in the table
    //private int maxDisplacement = 0; // maximum displacement

    static class Entry<T>
    {
        T data; // store the data of the element
        boolean isDeleted; // to check whether the element is deleted or not

        Entry(T data)
        {
            this.data = data;
            this.isDeleted = false;
        }
    }

    RobinhoodHashing()
    {
        this.size = 0;
        this.capacity = 1024; // initial capacity of the table
        this.robinhoodHashingHashTable = new Entry[capacity];
    }

    /* hash method
     * Purpose:
     * Parameters:
     * Return values: */
    static int hash(int h)
    {
        h ^= (h >>> 20) ^ (h >>> 12);
        return h ^ (h >>> 7) ^ (h >>> 4);
    }
    
    /* hashHelper method
     * Purpose:
     * Parameters:
     * Return values: */
    private int hashHelper(T x)
    {
        return indexFor(hash(x.hashCode()), robinhoodHashingHashTable.length);
    }

    /* indexFor method
     * Purpose:
     * Parameters:
     * Return values: */
    private static int indexFor(int h, int length)
    {
        return (h & (length - 1));
    }
    
    /* distinctElements method
     * Purpose:
     * Parameters:
     * Return values: */
    static <T> int distinctElements(T[] arr) {
        RobinhoodHashing<T> rh = new RobinhoodHashing<>();
        HashSet<T> set = new HashSet<>();

        for (T ele : arr) {
            rh.add(ele);
        }
        return rh.size;
    }

    /* getSize method
     * Purpose:
     * Parameters:
     * Return values: */
    public int getSize() {
        return this.size;
    }

    /* printTable method
     * Purpose: */
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

    /* contains method
     * Purpose:
     * Parameters:
     * Return values: */
    public boolean contains(T x) {
        int loc = find(x);
        return ((robinhoodHashingHashTable[loc] != null) && robinhoodHashingHashTable[loc].data.equals(x)
                && (robinhoodHashingHashTable[loc].isDeleted == false));
    }

    /* displacement method
     * Purpose:
     * Parameters:
     * Return values: */
    private int displacement(T x, int loc) {
        return loc >= hashHelper(x) ? loc - hashHelper(x) : robinhoodHashingHashTable.length + loc - hashHelper(x);
    }

    /* find method
     * Purpose:
     * Parameters:
     * Return values: */
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

/**
 * @author shb170230
 * Robinhood Hashing Class
 * <p>
 * SP7 for CS 5V81 (F18)
 * <p>
 * Team members:
 * 1. Sunny Bangale
 * 2. Utkarsh Gandhi
 */

package shb170230;

public class RobinhoodHashing<T> {

    static final double LOADFACTOR = 0.75; // load factor represents at what level the Rohinhood hashtable capacity should be doubled
    private int size; // size of the Robinhood hashtable
    private Entry[] robinhoodHashTable; // stores elements in the array
    private int capacity; // maximum capacity to store elements in the table
    //private int maxDisplacement = 0; // maximum displacement

    static class Entry<T> {
        T data; // store the data of the element
        boolean isDeleted; // to check whether the element is deleted or not

        Entry(T data) {
            this.data = data;
            this.isDeleted = false;
        }
    }

    //constructor for hashing
    RobinhoodHashing() {
        this.size = 0;
        this.capacity = 1024; // initial capacity of the table
        this.robinhoodHashTable = new Entry[capacity];
    }

    /* hash method
     * Purpose: hashing function for Robin Hood
     * Parameters: h
     * Return values: returns hashing index
     */
    static private int hash(int h) {
        h ^= (h >>> 20) ^ (h >>> 12);
        return h ^ (h >>> 7) ^ (h >>> 4);
    }

    /* indexFor method
     * Purpose: Function to return index for the key
     * Parameters: h and length
     * Return values: Returns index value
     **/
    private static int indexFor(int h, int length) {
        return (h & (length - 1));
    }

    /* distinctElements method
     * Purpose: Finds distinct elements in robinhood hash table
     * Parameters: Input arr by user
     * Return values: Count of distinct elements
     * */
    static <T> int distinctElements(T[] arr) {
        RobinhoodHashing<T> rh = new RobinhoodHashing<>();

        if (arr.length != 0 || arr == null) {
            for (T ele : arr) {
                rh.add(ele);
            }
            return rh.size;
        }

        return -1;
    }

    /* hashHelper method
     * Purpose: Helper method for hash
     * Parameters: x
     * Return values: returns indes for a key
     * */
    private int hashHelper(T x) {
        return indexFor(hash(x.hashCode()), robinhoodHashTable.length);
    }

    /* getSize method
     * Purpose: Function to find size of the hash table
     * Parameters: no param
     * Return values: Returns size
     * */
    public int getSize() {
        return this.size;
    }

    /* printTable method
     * Purpose: Prints the hash table
     * Parameters: no param
     * Return values: Returns nothing
     * */
    private void printTable() {
        System.out.print("Table: ");
        for (int j = 0; j < robinhoodHashTable.length; j++) {
            if (robinhoodHashTable[j] != null && robinhoodHashTable[j].isDeleted == false)
                System.out.print(robinhoodHashTable[j].data + " ");
            else
                System.out.print("* ");
        }
        System.out.println();
    }

    /* contains method
     * Purpose: Function to check if hash table contains x?
     * Parameters: x
     * Return values: Returns true or false (if element present or not)
     * */
    public boolean contains(T x) {
        int loc = find(x);
        return ((robinhoodHashTable[loc] != null) && robinhoodHashTable[loc].data.equals(x)
                && (robinhoodHashTable[loc].isDeleted == false));
    }

    /* displacement method
     * Purpose: Function to check displacement of element in hash table from original designated spot
     * Parameters: x and location
     * Return values: Returns displacement value
     * */
    private int displacement(T x, int loc) {
        return loc >= hashHelper(x) ? loc - hashHelper(x) : robinhoodHashTable.length + loc - hashHelper(x);
    }

    /* find method
     * Purpose: Function to find index of x
     * Parameters: x
     * Return values: Returns index
     * */
    public int find(T x) {
        int k = 0;
        int ik = 0;

        while (true)
        //while( k <= maxDisplacement)
        {
            //ik = (ik + k) % robinhoodHashTable.length;
            ik = (hashHelper(x) + k) % robinhoodHashTable.length;

            if (robinhoodHashTable[ik] == null ||
                    (robinhoodHashTable[ik].data.equals(x) && !robinhoodHashTable[ik].isDeleted)) {
                return ik;
            } else if (robinhoodHashTable[ik].isDeleted) {
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
            ik = (hashHelper(x) + k) % robinhoodHashTable.length;

            if (robinhoodHashTable[ik] != null && robinhoodHashTable[ik].data.equals(x)) {
                return ik;
            }
            if (robinhoodHashTable[ik] == null) {
                return xspot;
            }
        }
        //return ik;
    }

    /* add method
     * Purpose: Function to add x to hash table
     * Parameters: x
     * Return values: Returns true or false (if element was added or not)
     * */
    public boolean add(T x) {
        if (contains(x)) {
            return false;
        }

        int loc = hashHelper(x);
        int d = 0;

        while (true) {
            if (robinhoodHashTable[loc] == null || robinhoodHashTable[loc].isDeleted) {
                robinhoodHashTable[loc] = new Entry(x);
                this.size++;
                double currentLoad = (this.size * 1.0) / (robinhoodHashTable.length * 1.0);
                if (currentLoad > LOADFACTOR) {
                    rehashTable();
                }
                return true;
            } else if (displacement((T) robinhoodHashTable[loc], loc) >= d) {
                d = d + 1;
                loc = (loc + 1) % robinhoodHashTable.length;

                /*if (d > maxDisplacement) {
                    maxDisplacement = d;
                }*/

            } else {
                Entry<T> temp = robinhoodHashTable[loc];
                temp.data = (T) robinhoodHashTable[loc].data;

                robinhoodHashTable[loc] = new Entry(x);
                x = temp.data;

                loc = (loc + 1) % robinhoodHashTable.length;
                d = displacement(x, loc);

                /*if (d > maxDisplacement) {
                    maxDisplacement = d;
                }*/
            }
        }
    }

    /* remove method
     * Purpose: Function to remove x
     * Parameters: x
     * Return values: Returns removed element
     * */
    public T remove(T x) {
        int loc = find(x);
        if (robinhoodHashTable[loc] != null && robinhoodHashTable[loc].data.equals(x) && !robinhoodHashTable[loc].isDeleted) {
            T result = (T) robinhoodHashTable[loc].data;
            robinhoodHashTable[loc].isDeleted = true;
            this.size--;
            return result;
        } else {
            return null;
        }
    }

    /* rehash method
     * Purpose: Function to rehash and resize the table
     * Parameters: no param
     * Return values: Returns nothing
     * */

    private void rehashTable() {
        Entry<T> temporaryHashingTable[] = this.robinhoodHashTable;
        this.capacity = capacity * 2;
        this.robinhoodHashTable = new Entry[capacity];
        this.size = 0;

        for (int i = 0; i < temporaryHashingTable.length; i++) {
            if (temporaryHashingTable[i] != null && !temporaryHashingTable[i].isDeleted) {
                temporaryHashingTable[i].isDeleted = false;
                add(temporaryHashingTable[i].data);
            }
        }
    }

}

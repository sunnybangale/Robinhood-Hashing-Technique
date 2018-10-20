package usg170030;

public class DoubleHashing<T>{

    int size;
    Entry[] doubleHashingHashTable;

    /*   static class Entry<T> {
           T value;
           int probe;

           public Entry(T value) {
               this.value = value;
               this.probe = 0;
           }
       } */
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

    DoubleHashing(int size)
    {
        this.size = size;
        this.doubleHashingHashTable = new Entry[this.size];
    }

    static int hashFunction1(int h)
    {
        h ^= (h >>> 20) ^ (h >>> 12);
        return h ^ (h >>> 7) ^ (h >>> 4);
    }

    static int hashFunction2(int h)
    {
        return (1 + h % 9);
    }

    private int hashHelper1(T x)
    {
        return indexFor(hashFunction1(x.hashCode()),size);
    }

    private int hashHelper2(T x)
    {
        return indexFor(hashFunction2(x.hashCode()),size);
    }

    private static int indexFor(int h, int length)
    {
        return (h & length-1);
    }

/*    public int find(T x)
    {
        int h1 = hashHelper1(x);
        int h2 =  hashHelper2(x);

        while(doubleHashingHashTable[h1]== null)
        {
            if(doubleHashingHashTable[h1].data == x)
            {
                return h1;
            }
            h1 += h2;
            h1 %= h2;
        }

        return -1;
    }*/

    public int find(T x) {
        int k = 0;
        int ik = 0;
        while (true) {
            ik = hashHelper1(x) + (k * hashHelper2(x)) % size;
            if(doubleHashingHashTable[ik] == x || doubleHashingHashTable[ik].isDeleted == false) {
                return ik;
            }
            else if(doubleHashingHashTable[ik].isDeleted == true) {
                break;
            }
            else {
                k++;
            }
        }

        int xspot = ik;
        while(true) {
            k++;
            if (doubleHashingHashTable[ik] == x) {
                return ik;
            }
            if(doubleHashingHashTable[ik].isDeleted == false) {
                return xspot;
            }
        }
    }

    public void printTable()
    {
        System.out.print("Table: ");
        for(int j=0; j<size; j++)
        {
            if(doubleHashingHashTable[j] != null)
                System.out.print(doubleHashingHashTable[j].data+ " ");
            else
                System.out.print("*--* ");
        }
        System.out.println("");
    }

    public boolean contains(T x) {
        int loc = find(x);
        if(doubleHashingHashTable[loc]==x) {
            return true;
        }
        else {
            return false;
        }
    }

    public boolean add(T x) {
        int loc = find(x);
        if (doubleHashingHashTable[loc] == x) {
            return false;
        }
        else {
            doubleHashingHashTable[loc] = (Entry) x;
            return true;
        }
    }

    public Entry remove(T x) {
        int loc = find(x);
        if (doubleHashingHashTable[loc] == x) {
            Entry result = doubleHashingHashTable[loc];
            doubleHashingHashTable[loc].isDeleted = true;
            return result;
        }
        else {
            return null;
        }
    }

    public static void main(String[] args) {
        int tableSize = 1024;
        Entry ent;
        DoubleHashing dh = new DoubleHashing(tableSize);
        for (int i=750; i<800; i++) {
            dh.add(i);
        }

        dh.printTable();
    }
}

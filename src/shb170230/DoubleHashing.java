package shb170230;

public class DoubleHashing<T>{

    int size;
    Entry[] doubleHashingHashTable;

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

    DoubleHashing()
    {
        this.size = 0;
        this.doubleHashingHashTable = new Entry[1024];
    }

    static int hash(int h)
    {
        h ^= (h >>> 20) ^ (h >>> 12);
        return h ^ (h >>> 7) ^ (h >>> 4);
    }

    private int hashHelper(T x)
    {
        return indexFor( hash(x.hashCode()), doubleHashingHashTable.length);
    }

    private static int indexFor(int h, int length)
    {
        return (h & length-1);
    }

    public int find(T x)
    {
        int k = 0;
        int ik = hashHelper(x);

        while (true)
        {
            //ik = hashHelper(x) + (k * hashHelper2(x)) % size;
            ik += k % doubleHashingHashTable.length;
            //System.out.println(doubleHashingHashTable[ik]);
            if( doubleHashingHashTable[ik]== null || doubleHashingHashTable[ik].data.equals(x))
            {
                return ik;
            }
            else
                if(doubleHashingHashTable[ik].isDeleted)
                {
                    break;
                }
                else
                {
                    k++;
                }
        }

        int xspot = ik;

        while(true)
        {
            k++;
            if (doubleHashingHashTable[ik].data.equals(x))
            {
                return ik;
            }
            if(doubleHashingHashTable[ik].equals(null))
            {
                return xspot;
            }
        }
    }

    public void printTable()
    {
        System.out.print("Table: ");
        for(int j = 0; j < doubleHashingHashTable.length; j++)
        {
            if(doubleHashingHashTable[j] != null && doubleHashingHashTable[j].isDeleted == false)
                System.out.print(doubleHashingHashTable[j].data+ " ");
            else
                System.out.print("* ");
        }
        System.out.println("");
    }

    public boolean contains(T x)
    {
        int loc = find(x);
        if(doubleHashingHashTable[loc]!= null && doubleHashingHashTable[loc].data.equals(x))
        {
            return true;
        }
        else
        {
            return false;
        }
    }

    private int displacement(T x, int loc)
    {
        return loc >= hashHelper(x) ? loc - hashHelper(x): doubleHashingHashTable.length + loc - hashHelper(x);
    }

    public boolean add(T x)
    {
        if(contains(x))
        {
            return false;
        }

        int loc = hashHelper(x);
        int d = 0;

        while(true)
        {
            if(doubleHashingHashTable[loc]== null || doubleHashingHashTable[loc].isDeleted == true)
            {
                doubleHashingHashTable[loc] = new Entry(x);
                size++;
                return true;
            }
            else
                if(displacement((T)doubleHashingHashTable[loc], loc) >= d)
                {
                    d = d + 1;
                    loc = (loc + 1) % doubleHashingHashTable.length;
                }
                else
                {
                    Entry<T> temp = doubleHashingHashTable[loc];
                    doubleHashingHashTable[loc] = new Entry(x);
                    x = temp.data;

                    loc = (loc + 1) % doubleHashingHashTable.length;
                    d = displacement(x, loc);
                }
        }
    }

    public Entry remove(T x)
    {
        int loc = find(x);
        //System.out.println("location to remove "+loc);
        if (doubleHashingHashTable[loc]!= null && doubleHashingHashTable[loc].data.equals(x))
        {
            //System.out.println("in");
            Entry result = doubleHashingHashTable[loc];
            doubleHashingHashTable[loc].isDeleted = true;
            return result;
        }
        else
            {
            return null;
        }
    }


    public static void main(String[] args) {

        DoubleHashing<Integer> map = new DoubleHashing();

        System.out.println("Index for find " + map.find(87));

        for(int i = 0; i<= 1000; i++ ) {
            map.add(i);
        }
        map.printTable();
        for(int i = 50; i<= 1000; i++ ){
        map.remove(i);
        }
        map.printTable();
    }

}

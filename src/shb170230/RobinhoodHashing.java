package shb170230;

public class RobinhoodHashing<T> {

    static final double LOADFACTOR = 0.5;
    private int size;
    private Entry[] robinhoodHashingHashTable;
    private int capacity = 1024;

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
        return (h & length-1);
    }

    public int find(T x)
    {
        int k = 0;
        int ik = hashHelper(x);

        while (true)
        {
            //ik = hashHelper(x) + (k * hashHelper2(x)) % size;
            ik += k % robinhoodHashingHashTable.length;

            if (robinhoodHashingHashTable[ik] == null || robinhoodHashingHashTable[ik].data.equals(x))
            {
                return ik;
            }
            else if (robinhoodHashingHashTable[ik].isDeleted)
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
            if (robinhoodHashingHashTable[ik].data.equals(x))
            {
                return ik;
            }
            if (robinhoodHashingHashTable[ik] == null)
            {
                return xspot;
            }
        }
    }

    public void printTable()
    {
        System.out.print("Table: ");
        for (int j = 0; j < robinhoodHashingHashTable.length; j++)
        {
            if (robinhoodHashingHashTable[j] != null && robinhoodHashingHashTable[j].isDeleted == false)
                System.out.print(robinhoodHashingHashTable[j].data + " ");
            else
                System.out.print("* ");
        }
        System.out.println();
    }

    public boolean contains(T x)
    {
        int loc = find(x);
        return robinhoodHashingHashTable[loc] != null && robinhoodHashingHashTable[loc].data.equals(x);
    }

    private int displacement(T x, int loc)
    {
        return loc >= hashHelper(x) ? loc - hashHelper(x) : robinhoodHashingHashTable.length + loc - hashHelper(x);
    }

    public boolean add(T x)
    {

        if(contains(x))
        {
            return false;
        }

        if ((size + 1) / robinhoodHashingHashTable.length > LOADFACTOR) {
            rehashTable();
        }

        int loc = hashHelper(x);
        int d = 0;

        while(true)
        {
            if (robinhoodHashingHashTable[loc] == null || robinhoodHashingHashTable[loc].isDeleted == true)
            {
                robinhoodHashingHashTable[loc] = new Entry(x);
                size++;
                return true;
            }
            else if (displacement((T) robinhoodHashingHashTable[loc], loc) >= d)
                {
                    d = d + 1;
                    loc = (loc + 1) % robinhoodHashingHashTable.length;
                }
                else
                {
                    Entry<T> temp = robinhoodHashingHashTable[loc];
                    robinhoodHashingHashTable[loc] = new Entry(x);
                    x = temp.data;

                    loc = (loc + 1) % robinhoodHashingHashTable.length;
                    d = displacement(x, loc);
                }
        }
    }

    private void rehashTable() {

        Entry<T> temporaryHashingTable[] = this.robinhoodHashingHashTable;
        this.capacity = capacity * 2;
        this.robinhoodHashingHashTable = new Entry[capacity];

        for (int i = 0; i < temporaryHashingTable.length; i++) {
            if (temporaryHashingTable[i] != null) {
                add(temporaryHashingTable[i].data);
            }
        }

    }

    public Entry remove(T x)
    {
        int loc = find(x);
        if (robinhoodHashingHashTable[loc] != null && robinhoodHashingHashTable[loc].data.equals(x))
        {
            Entry result = robinhoodHashingHashTable[loc];
            robinhoodHashingHashTable[loc].isDeleted = true;
            return result;
        }
        else
            {
            return null;
        }
    }
    
    static<T> int distinctElements(T[ ] arr) {
        //Set<T> set = new HashSet<>();
        //set.addAll(Arrays.asList(arr));
        RobinhoodHashing<T> rh = new RobinhoodHashing<>();
        for (T ele: arr){
            rh.add(ele);
        }
        return rh.size;
        //return set.size();
    }

}
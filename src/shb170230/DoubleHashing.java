package shb170230;

public class DoubleHashing<T>{

    int size;
    Data[] doubleHashingHashTable;

 /*   static class Entry<T> {
        T value;
        int probe;

        public Entry(T value) {
            this.value = value;
            this.probe = 0;
        }

    }
*/
    static class Data<T>
    {
        T data;

        Data(T data)
        {
            this.data = data;
        }
    }

    DoubleHashing()
    {
        this.size = 1024;
        this.doubleHashingHashTable = new Data[this.size];
    }

    static int hashFunction1(int h)
    {
        h ^= (h >>> 20) ^ (h >>> 12);
        return h ^ (h >>> 7) ^ (h >>> 4);
    }

    static int hashFunction2(int h)
    {
        h ^= (h >>> 20) ^ (h >>> 12);
        return h ^ (h >>> 7) ^ (h >>> 4);
    }

    private int hashHelper1(T x)
    {
        return indexFor(hashFunction1(x.hashCode()),size);
    }

    private int hashHelper2(T x)
    {
        return indexFor(hashFunction1(x.hashCode()),size);
    }

    private static int indexFor(int h, int length)
    {
        return (h & length-1);
    }

    public int find(T key)
    {
        int h1 = hashHelper1(key);
        int h2 =  hashHelper2(key);

        while(doubleHashingHashTable[h1]== null)
        {
            if(doubleHashingHashTable[h1].data == key)
            {
                return h1;
            }
            h1 += h2;
            h1 %= h2;
        }

        return -1;
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





    public static void main(String[] args) {

    }

}

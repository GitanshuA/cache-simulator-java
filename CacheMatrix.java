import java.lang.*;
import java.util.ArrayList;

interface CacheOps {
    public void Search(CacheBlock c);
    public void PrintHitsMisses();
}
public class CacheMatrix implements CacheOps{
    CacheSet[] set;
    int hits = 0, misses = 0;
    public void Search(CacheBlock c){
        this.set[(int)c.setIndex].Search(c);
    }
    public void PrintHitsMisses(){
        for(CacheSet i: this.set){
            System.out.println("Set "+i.index+":");
            i.PrintHitsMisses();
            this.hits += i.hits;
            this.misses += i.misses;
        }
        System.out.println("Total Hits: "+this.hits);
        System.out.println("Total Misses: "+this.misses);
    }
    public CacheMatrix(int setCount, int W)
    {
        set = new CacheSet[setCount];
        for(int i = 0; i<setCount; i++)
            set[i] = new CacheSet(W,i);
    }
}

class CacheSet implements CacheOps{
    CacheBlock[] ways;
    int index, filled = 0, misses = 0, hits = 0;
    ArrayList<Integer> priority;
    public void Search(CacheBlock c){
        boolean present = false;
        for(int i = 0; i<filled; i++){
            if(ways[i].tag==c.tag){
                hits++;
                present = true;
                priority.remove((Integer)i);
                priority.add(i);
                break;
            }
        }
        if(!present){
            misses++;
            if(filled<Main.w)
                filled++;
            int temp = priority.get(0);
            ways[temp] = c;
            priority.remove(0);
            priority.add(temp);
        }
    }
    public void PrintHitsMisses(){
        System.out.println("Hits: "+hits);
        System.out.println("Misses: "+misses);
        System.out.println();
    }
    public CacheSet(int W, int index){
        this.index = index;
        ways = new CacheBlock[W];
        priority = new ArrayList<>();
        for(int i = 0; i<W; i++)
            priority.add(i);
    }
}

class CacheBlock{
    long tag, setIndex;
    public CacheBlock(long address, int setBits){
        long bitmask = (long)Math.pow(2,setBits)-1;
        address = address>>6;
        setIndex = address&bitmask;
        tag = address>>setBits;
    }
}

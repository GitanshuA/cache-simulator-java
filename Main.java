import java.io.File;
import java.io.FileNotFoundException;
import java.lang.*;
import java.util.Scanner;

public class Main {
    public static int setCount, w;
    public static void main(String[] args){
        if(args.length!=4)
        {
            System.out.println("Invalid Input Format\nUsage: java Main CacheSize(P) Associativity(W) BlockSize TraceFilePath");
            return;
        }
        double p;
        try {
            p = Double.parseDouble(args[0]);
        }
        catch (NumberFormatException e)
        {
            System.out.println("Invalid Cache Size(P) argument");
            return;
        }
        try {
            w = Integer.parseInt(args[1]);
        }
        catch (NumberFormatException e)
        {
            System.out.println("Invalid Associativity(W) argument");
            return;
        }
        int blockSize;
        try {
            blockSize = Integer.parseInt(args[2]);
        }
        catch(NumberFormatException e)
        {
            System.out.println("Invalid Block Size argument");
            return;
        }
        String tracePath = args[3];
        setCount = (int)Math.ceil((p * 1024) / (w * 64));
        CacheMatrix myCache = new CacheMatrix(setCount,w);
        int setBits = MyFunctions.log2(setCount);
        CacheBlock request;
        Scanner traceReader;
        try {
            traceReader = new Scanner(new File(tracePath));
        }
        catch (FileNotFoundException e) {
            System.out.println("Unable to access the file.");
            return;
        } ;
        while(traceReader.hasNext())
        {
            String input = traceReader.next();
            if(input.charAt(1)=='x')
                input = input.substring(2);
            request = new CacheBlock(Long.parseLong(input, 16), setBits);
            myCache.Search(request);
        }
        myCache.PrintHitsMisses();
    }
}
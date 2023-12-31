public class MyFunctions {
    public static int log2(int a)
    {
        int count = 0;
        while(a>1)
        {
            count++;
            a/=2;
        }
        return count;
    }
}

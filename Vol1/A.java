import java.util.Scanner;
import java.util.LinkedList;

public class A {









    public static void main (String[] strg){

    Scanner in = new Scanner(System.in);

    LinkedList<Integer> a = new LinkedList<Integer>();

    a.addFirst(10);
    a.addLast(20);

    int cur = a.getFirst();
    int cur2 = a.get(1);
    
    System.out.println(a.size()+ " "+ cur+ " "+ cur2);
    //input 

    /*
    int n = in.nextInt();
    int[] input_array = new int [n+1];
    for (int i = 1; i < n+1; i++) {
        input_array[i]= in.nextInt();
    }

    */

    }
}
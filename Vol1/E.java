import java.util.Scanner;


public class E {
    
    public static void solve(int[] x, int n){

        int point_i=0;

        while (point_i<n) {
            int point_f = point_i + 1;
            int tmp_pi=point_i;

            while (point_f<n) {
                if(x[tmp_pi]==x[point_f]){
                    delete(x,point_i,point_f);
                    point_i=point_f;
                    continue;
                }

                point_f++;
            }
            if (tmp_pi == point_i) point_i++;
        }
        print_seq(x,n);
    }

    public static void print_seq(int[] x, int n){

        int i=0;
        do{
            if(x[i]!=-1)
                System.out.println(x[i]);
        }while (x[++i]!=0);


    }

    public static void delete(int[] x,int pi, int pf){
        while (pi<pf) {
            x[pi++] = -1;
        }
    }



    public static void main(String[] args){

        Scanner in = new Scanner(System.in);
        int[] seq = new int[30];
        int i=0;

        do{
            seq[i]=in.nextInt();
        }while (seq[i++]!=0);

        solve(seq, i);
        
        in.close();
    }
}

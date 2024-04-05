import java.util.Scanner;
import java.util.LinkedList;

//import java.util.*;
//============================== Graph.java 

class Edge {
    private int enode;
    private int value;
    
    Edge(int endv, int v){
	enode = endv;
	value = v;
    }

    public int endnode() {
	return enode;
    }

    public int value() {
	return value;
    }

    public void newvalue(int v) {
	value = v;
    }
}


class Node {
    //private int label;
    private LinkedList<Edge> neighbours;

    Node() {
	neighbours  = new LinkedList<Edge>();
    }

    public LinkedList<Edge> adjs() {
	return neighbours;
    }
   
}

/*
 * Defines a Weighted Directed Graph
 * Represented by Adjacency list (i.e., an array of lists of edges).
 * Assumes that vertex labels are integers from 1 to n (position 0 not used)
 */

class Graph {
    private Node verts[];
    private int nverts, nedges;
			
    public Graph(int n) {
	nverts = n;
	nedges = 0;
	verts  = new Node[n+1];
	for (int i = 0 ; i <= n ; i++)
	    verts[i] = new Node();
    }
    
    public int num_vertices(){
	return nverts;
    }

    public int num_edges(){
	return nedges;
    }

    public LinkedList<Edge> adjs_no(int i) {
	return verts[i].adjs();
    }
    
    public void insert_new_edge(int i, int j, int value_ij){
	verts[i].adjs().addFirst(new Edge(j,value_ij));
        nedges++;
    }

    public Edge find_edge(int i, int j){
	for (Edge adj: adjs_no(i))
    if (adj.endnode() == j) return adj;
	return null;
    }
}



public class Halloween {
    

    public static void analizar(int start, int[] stock, Graph g){
        int max_stock = stock[start];
        int max_Super_market = start;
        int k=1;
        int[] visited = new int[g.num_vertices()+1], verify = new int[g.num_vertices()+1];

        for (int i=1;i<g.num_vertices()+1;i++) {visited[i]=0; verify[i] = 0;} //inicializar o array visited with 0s
        verify[k] = start; visited[start] = 1;
        
        int h = 1;
        while (verify[h]!=0) {
            LinkedList<Edge> tmp = g.adjs_no(verify[h]); //nos  adjacentes
            
            for(int i=0;i<tmp.size();i++){
                int sup_mark = tmp.get(i).endnode(); //indice do no
                if (visited[sup_mark] == 1) continue;
                
                visited[sup_mark] = 1;
                verify[++k] = sup_mark;

            }
            h++;
        }
        
        while(k>0){
            if((stock[verify[k]] == max_stock) && (verify[k] < max_Super_market)){
                max_Super_market = verify[k];
                max_stock = stock[verify[k]];
            }
            else if (stock[verify[k]] > max_stock){
                max_stock = stock[verify[k]]; 
                max_Super_market = verify[k];
            }
            k--;
        }

        //respostas
        if (max_stock == 0){
            System.out.println("Impossivel");
        }
        else{
            System.out.println(max_Super_market);
        }

    }


    public static void main (String[] args){

        Scanner in = new Scanner(System.in);
        
        int n_super_mercados = in.nextInt();
        Graph g = new Graph(n_super_mercados);
        int[] quantidade_aboboras = new int[n_super_mercados+1];

        for (int i=1;i<n_super_mercados+1;i++)
            quantidade_aboboras[i] = in.nextInt();

         
        //receber as ligacoes entre sup_merc
        int ligacoes = in.nextInt();
        for(int i = 0; i<ligacoes;i++){
            int k = in.nextInt(), h=in.nextInt();
            g.insert_new_edge(k,h,0);
            g.insert_new_edge(h,k,0);
        }

        int n_visitas = in.nextInt();
        for(int n = 0;n<n_visitas;n++){
            int inicio = in.nextInt();
            if (quantidade_aboboras[inicio]>0){
                System.out.println(inicio);
            }
            else{
                analizar(inicio,quantidade_aboboras,g);
            }
        }
    
        in.close();
    }
}

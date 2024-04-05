import java.util.Scanner;
import java.util.LinkedList;


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
    

    static void analizar(int start, int[] stock, Graph g){
        int num_stock = stock[start], max_stock = stock[start];
        int Super_market = start, max_Super_market = start;
        int[] visited = new int[g.num_vertices()];

        for (int i=0;i<g.num_vertices();i++) visited[i]=0;



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
        
        for (int j=1;j<n_super_mercados+1;j++){
            LinkedList<Edge> tmp = g.adjs_no(j);
            System.out.println(tmp);
            for(int i=0;i<tmp.size();i++){
                System.out.println(tmp.get(i).endnode());
            } 

        }


        /*
        int n_visitas = in.nextInt();
        for(int n = 0;n<n_visitas;n++)  
            analizar(in.nextInt(),quantidade_aboboras,g);
        */
        in.close();
    }
}

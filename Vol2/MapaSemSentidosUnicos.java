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






public class MapaSemSentidosUnicos {

    public static int inputGraph_and_lenght(Graph g, Scanner stdin,int[] appear){
        int dist, i, j, comprimento=0;
        int caminho = stdin.nextInt();
        i = stdin.nextInt();
        appear[i]++;
        for (int h=1;h<caminho;h++){
            dist = stdin.nextInt();j = stdin.nextInt();
            appear[j]++;
            comprimento += dist;
            g.insert_new_edge(i,j,dist);
            i = j;
        }        
        return comprimento;
    }
    

        public static void main(String[] args){
    
        Scanner stdin = new Scanner(System.in);	

        int trajetos = stdin.nextInt();
        int nos = stdin.nextInt(); //num de inputs
        Graph g = new Graph(nos);
        int[] appear = new int[nos+1];
        for(int i=0;i<nos+1;i++) appear[i]=0;

        for (int k = 1; k<=trajetos;k++){
            int lenght_path = inputGraph_and_lenght(g,stdin,appear);
            System.out.println("Trajeto "+k+": "+lenght_path);
        }

        for (int k = 1; k<=nos; k++){
            System.out.println("No "+k+": "+appear[k]);
        }
    }
}
    

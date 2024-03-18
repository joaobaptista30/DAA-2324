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

//============================== Bacalhaus Congelados

class BacalhausCongelados {

   
    public static Graph inputGraph(Scanner stdin){
	int nverts = stdin.nextInt(); //dim da matrix
 	int nedges = stdin.nextInt(); //num de inputs
    int temp, aux, i, j;

	Graph g = new Graph(nverts);

    for (int h=0;h<nedges;h++){
        i = stdin.nextInt(); j = stdin.nextInt();
        temp = stdin.nextInt(); aux = stdin.nextInt();
        g.insert_new_edge(i,j,temp);
    }

	return g;
    }

    public static void checkRoute(Scanner stdin,int nvs, Graph g){
	int prev = stdin.nextInt();
 	int curr = stdin.nextInt();
	int mintemp, maxtemp;
	int temp;
    if (g.find_edge(prev,curr) == null)    
        temp=g.find_edge(curr,prev).value();
    else temp = g.find_edge(prev,curr).value(); 

	mintemp = maxtemp = temp;

	nvs = nvs-2;
    
    while (nvs>0) {
        prev = curr;
        curr = stdin.nextInt();
        if (g.find_edge(prev,curr) == null)    
            temp=g.find_edge(curr,prev).value();
        else temp = g.find_edge(prev,curr).value();

        if (temp > maxtemp)    maxtemp = temp;
        else if (temp < mintemp)    mintemp = temp;


        nvs--;
    }

	System.out.println(mintemp+" "+maxtemp);

    }	

    public static void main(String[] args){

	Scanner stdin = new Scanner(System.in);	

	Graph g = inputGraph(stdin);

	int numbnodes;
	while ((numbnodes = stdin.nextInt()) != 0)
	    checkRoute(stdin,numbnodes,g);
	
    }
}


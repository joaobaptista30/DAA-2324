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


public class Wally {
    

    static public void main(String[] args){

        Scanner in = new Scanner(System.in);

        int n_nos = in.nextInt(), n_ramos = in.nextInt(), n_obj_proc = in.nextInt(), origem = in.nextInt();

        Graph g = new Graph(n_nos);
        int[] objetos = new int[n_nos+1];

        for(int i=1; i<=n_nos;i++) objetos[i] = in.nextInt();

        for(int i=1;i<=n_ramos;i++){
            int m = in.nextInt(), n = in.nextInt();
            g.insert_new_edge(m, n, objetos[n]);
            g.insert_new_edge(n, m, objetos[m]);
        }

        int count = 0 ,flag=-1,i = 1;
        int[] fila = new int[n_nos+2], n_fig = new int[n_obj_proc+1]; 
        int[] visited = new int[n_nos+1];
        int k = i+1;

        fila[i] = origem;
        while( fila[i] != 0 ){
            int no = fila[i];
            visited[no] = 1;
            for (Edge adj: g.adjs_no(fila[i])){
                if(visited[adj.endnode()] == 1) continue;
                fila[k++] = adj.endnode(); visited[adj.endnode()] = 1;
            }

            if(objetos[no] == 0) flag = no;
            else if(objetos[no]>0 && objetos[no] <= n_obj_proc && n_fig[objetos[no]]!=1){
                n_fig[objetos[no]] = 1;
                count++;
            }
            i++;
        }


        if(flag == -1) System.out.println("Wally not found");
        else System.out.println("Wally: "+ flag);
        
        System.out.print(count);
        if(count > 0){
            for(int j=1;j<n_obj_proc+1;j++)
                if(n_fig[j]==1) System.out.print(" "+j);
            }
            
        System.out.print("\n");

        in.close();
    }


}

/*-------------------------------------------------------------------*\
|  Classe para grafos dirigidos (sem pesos)                           |
|     * vertices numerados de 1 to |V|.                               |
|                                                                     |
|  Class for directed graphs  (no weghts)                             |
|     * vertices are labeled from 1 to |V|.                           |
|                                                                     |
|   A.P.Tomas, CC2001 (material for the test), DCC-FCUP, 2024         |
|   Last modified: 2024.04.04                                         |
\--------------------------------------------------------------------*/


import java.util.LinkedList;

//============================== Graph0.java 

class Edge {
    private int enode;
    
    Edge(int endv){
	enode = endv;
    }

    public int endnode() {
	return enode;
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



/**
 * Defines a Directed Graph
 * Represented by Adjacency list (i.e., an array of lists of edges).
 * Assumes that vertex labels are integers from 1 to n (position 0 not used)
 */


class Graph0 {
    private Node verts[];
    private int nverts, nedges;
			
    public Graph0(int n) {
	nverts = n;
	nedges = 0;
	verts  = new Node[n+1];
	for (int i = 0 ; i <= n ; i++)
	    verts[i] = new Node();
        // vertex labels are integers from 1 to n (position 0 is not used)
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
    
    public void insert_new_edge(int i, int j){
	verts[i].adjs().addFirst(new Edge(j));
        nedges++;
    }

    public Edge find_edge(int i, int j){
	for (Edge adj: adjs_no(i))
	    if (adj.endnode() == j) return adj;
	return null;
    }
}

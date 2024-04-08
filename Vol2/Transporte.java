import java.util.Scanner;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Arrays;
//import java.util.*;


//============================== Graph.java 

class Edge {
    private int enode;
    private int comp,larg,alt;
    
    Edge(int endv, int c,int l, int a){
	enode = endv;
	comp = c;
    larg = l;
    alt = a;
    }

    public int endnode() {
	return enode;
    }

    public int comp() {
	return comp;
    }

    public int larg() {
    return larg;
    }

    public int alt() {
    return alt;
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
    
    public void insert_new_edge(int i, int j, int c, int l, int a){
	verts[i].adjs().addFirst(new Edge(j,c,l,a));
        nedges++;
    }

    public Edge find_edge(int i, int j){
	for (Edge adj: adjs_no(i))
	    if (adj.endnode() == j) return adj;
	return null;
    }
}


public class Transporte {
    


    public static void main(String[] args){

        Scanner in = new Scanner(System.in);
        int n_locais = in.nextInt();
        int larg_min = in.nextInt(), larg_max = in.nextInt();
        int comp_min = in.nextInt(), comp_max = in.nextInt();
        int altura = in.nextInt();
        int start = in.nextInt(), end = in.nextInt();
        
        Graph g = new Graph(n_locais);
        
        //input
        int i = in.nextInt();
        while (i != -1) {
            int j = in.nextInt();
            int larg = in.nextInt(), comp = in.nextInt(), alt = in.nextInt();
            if(comp_min <= comp && comp <= comp_max && larg_min <= larg && larg <= larg_max && altura <= alt){
                g.insert_new_edge(i, j, comp, larg, alt);
                g.insert_new_edge(j,i, comp, larg, alt);
            }

            i=in.nextInt();
        }

        //Queue<Integer> caminho = new LinkedList<>();
        //caminho.add(start);
        int[] visited = new int[n_locais+1];
        int[] caminho = new int[n_locais+1];
        caminho[0]=start; i=1; int atual = 0;

        while (caminho[atual] != 0) {
            LinkedList<Edge> nos = g.adjs_no(start);
            for(Edge no : nos){
                int point = no.endnode();
                if(visited[point] == 0){
                    caminho[i++] = point;
                    visited[point] = 1;
                }
                if(point == end) break;
            }
            if (caminho[i]==end) break;
            atual++;
        }
        System.out.println(Arrays.toString(caminho));
        
        if(caminho[--i] == 0) System.out.println("Impossivel");

        else{
            int num_trocos = 0;
            i = atual -1;
            while (atual != 0) {
                if(g.find_edge(atual, i) != null){
                    num_trocos++;
                    atual = i;
                    i--;
                }
                else{
                    
                    i--;
                } 
                
            }
            System.out.println(num_trocos);

        }
        
        in.close();
    }
}

import java.util.Scanner;
import java.util.LinkedList;

//import java.util.*;
//============================== Graph.java 

class Edge {
    private int enode;
    private int preco;
    private int lugares;

    Edge(int endv, int l, int p){
	enode = endv;
    lugares = l;
	preco = p;
    }

    public int endnode() {
	return enode;
    }

    public int preco() {
	return preco;
    }

    public int lugares() {
    return lugares;
    }

    public void updateLugares(int n){
        lugares -= n;
    }

    public void newvalue(int v) {
	preco = v;
    }

    @Override
    public String toString() {
        return String.valueOf(enode);
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
    
    public void insert_new_edge(int i, int j, int l, int p){
	verts[i].adjs().addFirst(new Edge(j,l,p));
        nedges++;
    }

    public Edge find_edge(int i, int j){
	for (Edge adj: adjs_no(i))
	    if (adj.endnode() == j) return adj;
	return null;
    }
}

public class Reservas {
    
    public static void analizar(Graph g, Scanner in){
        int lugares_reservar = in.nextInt(), num_locais = in.nextInt();
        int start = in.nextInt(), end;
        int to_pay = 0, contador = 1;

        for(;contador<num_locais;contador++){
            end = in.nextInt();
            Edge no = g.find_edge(start, end);
            
            if (g.find_edge(start, end) == null){
                System.out.printf("(%d,%d) inexistente\n",start, end);
                for(int j=contador;j<num_locais-1;j++) end = in.nextInt();
                return;
            }
            if (no.lugares() < lugares_reservar){
                System.out.printf("Sem lugares suficientes em (%d,%d)\n",start, end);
                for(int j=contador;j<num_locais-1;j++) end = in.nextInt();
                return;
            }

            to_pay += (lugares_reservar * no.preco());
            no.updateLugares(lugares_reservar);
            start = end;
        }

        System.out.printf("Total a pagar: %d\n", to_pay);
        return;
    }


    public static void main(String[] args){

        Scanner in = new Scanner(System.in);

        int num_paragens = in.nextInt(), trajetos = in.nextInt();

        Graph g = new Graph(num_paragens);

        for (int h=0; h<trajetos;h++){
            int origem = in.nextInt(), dest = in.nextInt();
            int lugares = in.nextInt(), preco = in.nextInt();
            g.insert_new_edge(origem,dest,lugares,preco);
        }


        int reservas = in.nextInt();
        for(int i=0; i<reservas; i++){
            analizar(g,in);
        }

        in.close();
    }
}

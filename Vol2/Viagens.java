import java.util.Scanner;
import java.util.LinkedList;

//import java.util.*;


//============================== Graph.java 

class Edge {
    private int enode;
    private int problemas;
    private int lugares;
    private int duracao;

    
    Edge(int endv, int prob, int lug, int dur){
	enode = endv;
	problemas = prob;
    lugares = lug;
    duracao = dur;
    }

    public int endnode() {
	return enode;
    }

    public int problemas() {
	return problemas;
    }

    public int lugares() {
    return lugares;
    }

    public int duracao() {
    return duracao;
    }

    public void set_lugares(int lug) {
	lugares = lug;
    }

    public void set_duracao(int dur) {
    duracao = dur;
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
    
    public void insert_new_edge(int i, int j, int prob, int lug, int dur){
	verts[i].adjs().addFirst(new Edge(j,prob,lug,dur));
        nedges++;
    }

    public Edge find_edge(int i, int j){
	for (Edge adj: adjs_no(i))
	    if (adj.endnode() == j) return adj;
	return null;
    }
}



public class Viagens {

    static public void resol(Scanner in){
        int n_elementos_grupo = in.nextInt(), inicio = in.nextInt(), destino = in.nextInt();
        int h_partida = in.nextInt(), h_chegada = in.nextInt(), n_locais = in.nextInt();
        Graph g = new Graph(n_locais);
        int tempo_limite_minutos = (h_chegada-h_partida)*60, caminhos_possiveis = 0;

        //input
        for(int i = 1;i<=n_locais;i++){
            for(int j = 1;j<=n_locais;j++){
                int problema = in.nextInt();
                //Edge(int endv, int prob, int lug, int dur){
                if (problema != 2) g.insert_new_edge(i, j, problema,0,0);
            }
        }

        while(true){
            int n = in.nextInt(), h_inicio = in.nextInt();
            if(n == 0) break;
            int caminho_inicio = in.nextInt(), t_decorrido = 0;

            for(int i=1;i<=n;i++){
                int n_lugares = in.nextInt(), t_duracao = in.nextInt(), caminho_final = in.nextInt();
            }


        }




    }


    static public void main (String[] args){

        Scanner in = new Scanner(System.in);

        resol(in);


    }
}

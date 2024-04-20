import java.util.LinkedList;
import java.util.Scanner;

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


/**
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




public class A {

    static public void analizar(Graph g, Graph g_trans, int[] res){

        int[] visited_final = new int[g.num_vertices()];

        for(int i = 1; i< g.num_vertices();i++){
            if(visited_final[i]==1) continue;

            int[] visited_normal = new int[g.num_vertices()];
            int[] visited_trans = new int[g.num_vertices()];

            int[] fila = new int[g.num_vertices()];
            fila[0] = i;
            int k = 0;
            while ( fila[k]!=0){






            }



            
        }



    }

    static public void main(String[] args){
        Scanner in = new Scanner(System.in);

        int n_cenarios = in.nextInt();

        for(int i = 1; i<=n_cenarios;i++){
            int n_alunos = in.nextInt();
            Graph g = new Graph(n_alunos);
            Graph g_trans = new Graph(n_alunos);

            for(int j=0;j<n_alunos;j++){
                int aluno = in.nextInt(), amigos = in.nextInt();
                for(int k = 0; k<amigos;k++){
                    int par = in.nextInt();
                    g.insert_new_edge(aluno, par, 0);
                    g_trans.insert_new_edge(par, aluno, 0);                    
                }
            }

            int[] res = new int[2];  // res[n_grupos >= 4, n_alunos em grupos < 4]
            analizar(g,g_trans,res);

            System.out.printf("Caso #%d\n%d %d", i,res[1],res[2]);

        }
        in.close();
    }
}

import java.util.*;

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



public class I {

    public static int procurar(Graph g, int s, int limite ,int[] stock){
        int validas = 0;
        boolean[] visited = new boolean[g.num_vertices()+1];
        int[] dist = new int[g.num_vertices()+1];
        visited[s] = true;
        Queue<Integer> q = new LinkedList<>();
        q.add(s);

        while ( !q.isEmpty() ) {
            int v = q.poll();

            for (Edge edj : g.adjs_no(v)){
                if (visited[edj.endnode()] == false) {
                    visited[edj.endnode()] = true;
                    dist[edj.endnode()] = dist[v] + 1; //adicionar a distancia percorrida
                    if (dist[edj.endnode()] <= limite && stock[edj.endnode()] > 0){
                        validas++;
                    }
                    q.add(edj.endnode());
                }
            }
        }
        return validas;
    }

    public static void main(String[] Args){
    
        Scanner in = new Scanner(System.in);

        int n_nos = in.nextInt();
        Graph g = new Graph(n_nos);
        int[] stock = new int[n_nos+1];
        for(int i=1;i<=n_nos;i++) stock[i] = in.nextInt();
        int ligacoes = in.nextInt();
        for(int j=1;j<=ligacoes;j++){
            int a = in.nextInt(), b = in.nextInt();
            g.insert_new_edge(a, b, 0);
            g.insert_new_edge(b, a, 0);
        }
        int loja = in.nextInt(), dist_lim = in.nextInt();


        if (stock[loja] > 0) System.out.println("Que sorte");
        else{
            int possiveis = procurar(g,loja,dist_lim,stock);
            System.out.println(possiveis);
        }

        in.close();
    }
}
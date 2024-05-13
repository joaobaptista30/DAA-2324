#include <stdio.h>
#include <stdlib.h>
#include <limits.h>

// #include "../DAA2324_DataStructures/Mooshak_Clang/graph.h"

// #include "../DAA2324_DataStructures/Mooshak_Clang/heapmin.h"



/*-------------------------------------------------------------------*\
|  TAD para grafos dirigidos pesados                                  |
|     * um unico peso (um qualquer valor do tipo int em cada aresta)  |
|     * vertices numerados de 1 to |V|.                               |
|     * |V| <= MAXVERTS                                               |
|                                                                     |
|  ADT for weighted directed graphs dd                                |
|     * single weight (any value of type int on each edge)            |
|     * vertices are labeled from 1 to |V|.                           |
|     * |V| <= MAXVERTS                                               |
|                                                                     |
|   A.P.Tomas, CC2001 (material for the test), DCC-FCUP, 2024         |
|   Last modified: 2024.04.04                                         |
\--------------------------------------------------------------------*/

#include <stdio.h>
#include <stdlib.h>

#define MAXVERTS 1000
// numero maximo de vertices (alterar se necessario)
// maximum number of vertices (update if necessary)


typedef struct edge {
  int enode, value;
  struct edge *next;
} EDGE;

typedef struct node {
  //int label;
  EDGE *adjs;
} NODE;

typedef struct graph {
  NODE verts[MAXVERTS+1];  // vertex labels will be numbers from 1 to nvs
  int nvs, nedges;
} GRAPH;

/* ------------------------------------------------------------------*\
|     Prototipos das funcoes disponiveis (publicas)                   |
|     (Prototypes of the available public functions                   |
|     ATENCAO AOS TIPOS DE ARGUMENTOS E DE RESULTADO                  |
|     CHECK CAREFULLY the arguments and result types                  |
\* ------------------------------------------------------------------*/

GRAPH *new_graph(int nverts);
/* cria um grafo com nverts vertices e sem arcos */
/* creates a graph with nverts vertices and without edges */

void destroy_graph(GRAPH *g);
/* liberta memoria reservada na criacao do grafo */
/* to free the memory allocated for a graph */

void insert_new_edge(int i, int j, int value_ij, GRAPH *g);
/* insere arco (i,j) no grafo, bem como o seu peso; nao evita repeticoes */
/* adds a new edge (i,j) to the graph, as well as its weight; does not prevent repetitions */

void remove_edge(EDGE *edge, int i, GRAPH *g);
/* retira arco edge da lista de adjacentes de i */
/* deletes the edge given by edge from the list of neighbours of i */

EDGE *find_edge(int i, int j, GRAPH *g);
/* retorna um apontador para o arco (i,j) ou NULL se nao existir */
/* returns a pointer to the edge (i,j) or NULL if that does does not exist */

//--- macros uteis de acesso aos campos da estrutura ----------------
//--- useful macros to get or check fields (members of the structure) -------

#define NUM_VERTICES(g) ( (g) -> nvs )
// numero de vertices (number of vertices)

#define NUM_EDGES(g) ( (g) -> nedges )
// numero de arcos   (number of edges)

#define ADJS_NO(i,g) ( (g) -> verts[i].adjs )
// primeiro arco da lista de adjacentes do nó i (inicio da lista)
// first edge in the list of adjacent nodes of node i (head of the list)

#define NXT_ADJ(edge) ((edge) -> next)
// proximo adjacente (next adjacent)

#define VALID_ADJ(edge) (((edge) != NULL))
// se arco é válido  (checks the end of the list)

#define EDGE_ENODE(edge) ((edge) -> enode)
// qual o extremo final de arco  (the end node of an edge)

#define EDGE_VALUE(edge) ((edge) -> value)
// qual o valor do arco (the value on the edge)


// ======  prototipos de funções auxiliares (privadas) ======
// ======  prototypes of private functions ===
static EDGE* new_edge(int j, int valor_ij);
static void free_edges(EDGE *list);


//======  Implementacao/Implementation (definicao das funcoes / function definitions) 

// para criar um grafo com nverts vertices e sem ramos
// to create a graph with nverts vertices without edges
GRAPH *new_graph(int nverts)
{
  if (nverts > MAXVERTS) {
    fprintf(stderr,"Error: %d > MAXVERTS\n",nverts);
    exit(EXIT_FAILURE);
  }
  GRAPH *g = (GRAPH *) malloc(sizeof(GRAPH));
  if (g == NULL) { 
    fprintf(stderr,"Error: lack of memory\n");
    exit(EXIT_FAILURE);
  }

  NUM_VERTICES(g) = nverts;  
  NUM_EDGES(g) = 0;
  while (nverts >= 0) {
    ADJS_NO(nverts,g) = NULL;
    nverts--;
  }
  return g;
}


// para destruir um grafo criado  (to destroy a graph)
void destroy_graph(GRAPH *g)
{ int i;
  if (g != NULL) {
    for (i=1; i<= NUM_VERTICES(g); i++) 
      free_edges(ADJS_NO(i,g));
    free(g);
  }
}

// para inserir um novo arco num grafo (to insert a new edge in a graph)
void insert_new_edge(int i, int j, int value_ij, GRAPH *g)
{ 
  EDGE *edge = new_edge(j,value_ij);
  NXT_ADJ(edge) = ADJS_NO(i,g);
  ADJS_NO(i,g) = edge; 
     // novo adjacente fica como cabeca da lista
     // new adjacent becomes the head of the list 
  NUM_EDGES(g)++;
}

// para remover um arco de um grafo (se existir na lista de adjacentes do nó i)
// to remove an edge from the graph (if it occurs in the list of neighbours of node i)
void remove_edge(EDGE *edge, int i, GRAPH *g)
{ 
  if (edge != NULL) {
    EDGE *aux = ADJS_NO(i,g), *prev = NULL;
    while (aux != edge && VALID_ADJ(aux)) {
      prev = aux;
      aux = NXT_ADJ(aux);
    }
    if (aux == edge) {
      if (prev == NULL) {
	ADJS_NO(i,g)  = NXT_ADJ(edge);
      } else NXT_ADJ(prev) = NXT_ADJ(edge);
      free(edge);
      NUM_EDGES(g)--;
    }
  }
}

// retorna um apontador para o arco (i,j) ou NULL se não existir 
// returns a pointer to the edge (i,j) or NULL if it does not exist
EDGE  *find_edge(int i, int j, GRAPH *g){
  EDGE *adj = ADJS_NO(i,g);

  while(adj != NULL && EDGE_ENODE(adj) != j)
    adj = NXT_ADJ(adj);

  return adj;
}
    

// ----  as duas funcoes abaixo sao auxiliares nao publicas ----
// ---- the two functions given below are not public -----

// reservar memoria para um novo arco e inicializa-lo
// allocates memory for a new edge and inicializes it
static EDGE *new_edge(int j, int value)
{ 
  EDGE *edge = (EDGE *) malloc(sizeof(EDGE));
  if (edge == NULL) {
    fprintf(stderr,"ERROR: cannot create edge\n");
    exit(EXIT_FAILURE);
  }
  EDGE_ENODE(edge) = j;
  EDGE_VALUE(edge) = value;
  NXT_ADJ(edge) = NULL;
  return edge;
}

// libertar a memória alocada para uma lista de arcos 
// to free the memory allocated for a list of edges
static void free_edges(EDGE *edge)
{ 
  if (edge == NULL) return;
  free_edges(NXT_ADJ(edge));
  free(edge);
}







/*-----------------------------------------------------------------------*\
|  Exemplo de implementacao de fila de prioridade (por heap de mínimo)    |
|                                                                         |
|   A.P.Tomás, CC2001 (material para prova pratica), DCC-FCUP, 2017       |
|   Last modified: 2017.12.18                                             |
\*-----------------------------------------------------------------------*/

#include<stdio.h>
#include<stdlib.h>

typedef struct qnode {
  int vert, vertkey;
} QNODE;

typedef struct heapMin {
  int sizeMax, size;
  QNODE *a;
  int *pos_a;
} HEAPMIN;

//---------  protótipos das funções disponíveis --------------------

HEAPMIN *build_heap_min(int v[], int n);
void insert(int v, int key, HEAPMIN *q);
int extractMin(HEAPMIN *q);   // retorna v 
void decreaseKey(int v, int newkey, HEAPMIN *q);
int heap_isEmpty(HEAPMIN *q);   // retorna 1 ou 0

void write_heap(HEAPMIN *q);
void destroy_heap(HEAPMIN *q);


//----------------- definição das funções e macros ---------------------

#define POSINVALIDA 0

#define LEFT(i) (2*(i))
#define RIGHT(i) (2*(i)+1)
#define PARENT(i) ((i)/2)

static void heapify(int i,HEAPMIN *q);
static void swap(int i,int j,HEAPMIN *q);
static int compare(int i, int j, HEAPMIN *q);
static int pos_valida(int i,HEAPMIN *q);

static int compare(int i, int j, HEAPMIN *q){
  if (q -> a[i].vertkey < q -> a[j].vertkey)
    return -1;
  if (q -> a[i].vertkey == q -> a[j].vertkey)
    return 0;
  return 1;
}


static int pos_valida(int i, HEAPMIN *q) {
  return (i >= 1 && i <= q -> size);
}

int extractMin(HEAPMIN *q) {
  int vertv = q -> a[1].vert;
  swap(1,q->size,q);
  q -> pos_a[vertv] = POSINVALIDA;  // assinala vertv como removido
  q -> size--;
  heapify(1,q);
  return vertv;
}

void decreaseKey(int vertv, int newkey, HEAPMIN *q){
  int i = q -> pos_a[vertv];
  q -> a[i].vertkey = newkey;

  while(i > 1 && compare(i,PARENT(i),q) < 0){
    swap(i,PARENT(i),q);
    i = PARENT(i);
  }
}


static void heapify(int i,HEAPMIN *q) {
  // para heap de minimo
  int l, r, smallest;
  l = LEFT(i);
  if (l > q -> size) l = i;
  r = RIGHT(i);
  if (r > q -> size) r = i;
  
  smallest = i;
  if (compare(l,smallest,q) < 0) 
    smallest = l;
  if (compare(r,smallest,q) < 0) 
    smallest = r;
  
  if (i != smallest) {
    swap(i,smallest,q);
    heapify(smallest,q);
  }
}

static void swap(int i,int j,HEAPMIN *q){
  QNODE aux;
  q -> pos_a[q -> a[i].vert] = j;
  q -> pos_a[q -> a[j].vert] = i;
  aux = q -> a[i];
  q -> a[i] = q -> a[j];
  q -> a[j] = aux;
}



HEAPMIN *build_heap_min(int vec[], int n){
  // supor que vetor vec[.] guarda elementos nas posições 1 a n
  // cria heapMin correspondente em tempo O(n)
  HEAPMIN *q = (HEAPMIN *)malloc(sizeof(HEAPMIN));
  int i;
  q -> a = (QNODE *) malloc(sizeof(QNODE)*(n+1));
  q -> pos_a = (int *) malloc(sizeof(int)*(n+1));
  q -> sizeMax = n; // posicao 0 nao vai ser ocupada
  q -> size = n;   
  for (i=1; i<= n; i++) {
    q -> a[i].vert = i;
    q -> a[i].vertkey = vec[i];
    q -> pos_a[i] = i;  // posicao inicial do elemento i na heap
  }

  for (i=n/2; i>=1; i--) 
    heapify(i,q);
  return q;
}


void insert(int vertv, int key, HEAPMIN *q)
{ 
  if (q -> sizeMax == q -> size) {
    fprintf(stderr,"Heapmin is full\n");
    exit(EXIT_FAILURE);
  }
  q -> size++;
  q -> a[q->size].vert = vertv;
  q -> pos_a[vertv] = q -> size;   // supondo 1 <= vertv <= n
  decreaseKey(vertv,key,q);   // diminui a chave e corrige posicao se necessario
}


int heap_isEmpty(HEAPMIN *q){
  if (q -> size == 0) return 1;
  return 0;
}


// --------- auxiliar para ver conteudo  ---------------------
void write_heap(HEAPMIN *q){
  int i;

  printf("Max size: %d\n", q -> sizeMax);
  printf("Current size: %d\n", q -> size);
  
  printf("(Vert,Key)\n---------\n");
  for(i=1; i <= q -> size; i++)
    printf("(%d,%d)\n",q->a[i].vert,q->a[i].vertkey);

  printf("-------\n(Vert,PosVert)\n---------\n");

  for(i=1; i <= q -> sizeMax; i++)
    if (pos_valida(q -> pos_a[i],q))
      printf("(%d,%d)\n",i,q->pos_a[i]);
}


void destroy_heap(HEAPMIN *q){
  if (q != NULL) {
    free(q -> a);
    free(q -> pos_a);
    free(q);
  }
}
    



// ============ Exemplo Dijkstra 

typedef struct pair{
  int mindist;
  int *pai;
} PAIR;



GRAPH *inputGraph(){
  int nverts, nedges, val, i, j;
  
  scanf("%d%d",&nverts,&nedges);

  GRAPH *g = new_graph(nverts);

  for(int k=0; k<nedges;k++){
    scanf("%d%d%d",&i,&j,&val);
    insert_new_edge(i, j, val,g);
    // insert_new_edge(j, i, val,g);  // not symmetrical                                                                  
    }
  return g;
}

PAIR shortestPath(int s,int t,GRAPH *g){
  int nverts = NUM_VERTICES(g);
  int *pai = (int *) malloc(sizeof(int)*(nverts+1)); 
  int dist[nverts+1];   // local à função

  for (int v = 1; v <= nverts; v++) {
    pai[v] = 0;
    dist[v] = INT_MAX;
  }
  dist[s] = 0;


  HEAPMIN *q = build_heap_min(dist,nverts);
  while (!heap_isEmpty(q)) {
    int v = extractMin(q);
    if (v == t)
      break; 
    EDGE *adjs = ADJS_NO(v,g);
    while (adjs != NULL) {
      int w = EDGE_ENODE(adjs);
      if (dist[v]+EDGE_VALUE(adjs) < dist[w]) {
	  pai[w] = v;
	  dist[w] = dist[v]+EDGE_VALUE(adjs);
	  decreaseKey(w,dist[w],q);
	}
      adjs = NXT_ADJ(adjs);
    }
  }

  destroy_heap(q);    // libertar o espaço

  PAIR res;
  res.mindist = dist[t];
  res.pai = pai;
  return res;
}

void writePath(int pai[],int t){
  if (pai[t] == 0)
    printf("%d",t);
  else {
    writePath(pai,pai[t]);
    printf(" %d",t);
  }
}

int main(){
  int s, t;
  PAIR res;
  GRAPH *g = inputGraph();
  scanf("%d%d",&s,&t);

  res = shortestPath(s,t,g);

  if (res.mindist == INT_MAX)
    printf("Impossible\n");
  else {
    printf("Min distance from %d to %d: %d\n", s, t,res.mindist);
    printf("Shortest path: ");
    writePath(res.pai,t);
    putchar('\n');
  }

  destroy_graph(g);   // libertar o espaço
  free(res.pai);    // libertar o espaço    

  return 0;
}


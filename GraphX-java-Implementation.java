import java.util.*;

class Hash {
    private Map<Tuple, Integer> hashTable;

    public Hash() {
        hashTable = new HashMap<>();
    }

    public int hash(int x) {
        return hash(new Tuple(x, 0, 0));
    }

    public int hash(Tuple x) {
        return hash(new Tuple(x.getFirst(), x.getSecond(), 0));
    }

    public int hash(Tuple x) {
        if (hashTable.containsKey(x)) {
            return hashTable.get(x);
        }
        int newHash = hashTable.size();
        hashTable.put(x, newHash);
        return newHash;
    }
}

class Graph {
    private boolean isDirected;
    private List<List<Pair<Integer, Integer>>> adj;
    private int n;
    private static final int N = 5000000;
    private Hash h;

    public Graph(int n_, boolean isDirected_) {
        n = n_;
        isDirected = isDirected_;
        adj = new ArrayList<>(N);
        for (int i = 0; i < N; i++) {
            adj.add(new ArrayList<>());
        }
        h = new Hash();
    }

    public int hash(int u, int v) {
        return h.hash(new Tuple(u, v));
    }

    public int hash(int u, int v, int k) {
        return h.hash(new Tuple(u, v, k));
    }

    public void addEdge(int uR, int vR, int c) {
        int u = h.hash(uR), v = h.hash(vR);
        addEdgeInternal(u, v, c);
    }

    public void addEdge(Tuple<Integer, Integer> uR, Tuple<Integer, Integer> vR, int c) {
        int u = h.hash(uR), v = h.hash(vR);
        addEdgeInternal(u, v, c);
    }

    public void addEdge(Tuple<Integer, Integer, Integer> uR, Tuple<Integer, Integer, Integer> vR, int c) {
        int u = h.hash(uR), v = h.hash(vR);
        addEdgeInternal(u, v, c);
    }

    private void addEdgeInternal(int u, int v, int c) {
        addEdgeWeightedUndirected(u, v, c);
        if (!isDirected) {
            addEdgeWeightedUndirected(v, u, c);
        }
    }

    private void addEdgeWeightedUndirected(int u, int v, int c) {
        Pair<Integer, Integer> p = new Pair<>(v, c);
        adj.get(u).add(p);
    }
}

class BFS {
    private List<Integer> minDistFromSource;
    private List<Boolean> visited;
    private Graph g;

    public BFS(Graph g_) {
        g = g_;
        clear();
    }

    public void clear() {
        minDistFromSource = new ArrayList<>(Collections.nCopies(g.N, -1));
        visited = new ArrayList<>(Collections.nCopies(g.N, false));
    }

    public void run(int sourceR) {
        int source = g.h.hash(sourceR);
        runInternal(source);
    }

    public void run(Tuple<Integer, Integer> sourceR) {
        int source = g.h.hash(sourceR);
        runInternal(source);
    }

    public void run(Tuple<Integer, Integer, Integer> sourceR) {
        int source = g.h.hash(sourceR);
        runInternal(source);
    }

    public int minDist(int targetR) {
        int target = g.h.hash(targetR);
        return minDistInternal(target);
    }

    public int minDist(Tuple<Integer, Integer> targetR) {
        int target = g.h.hash(targetR);
        return minDistInternal(target);
    }

    public int minDist(Tuple<Integer, Integer, Integer> targetR) {
        int target = g.h.hash(targetR);
        return minDistInternal(target);
    }

    public boolean isVisited(int targetR) {
        int target = g.h.hash(targetR);
        return isVisitedInternal(target);
    }

    public boolean isVisited(Tuple<Integer, Integer> targetR) {
        int target = g.h.hash(targetR);
        return isVisitedInternal(target);
    }

    public boolean isVisited(Tuple<Integer, Integer, Integer> targetR) {
        int target = g.h.hash(targetR);
        return isVisitedInternal(target);
    }

    private void runInternal(int source) {
        Queue<Integer> q = new LinkedList<>();
        q.add(source);

        visited.set(source, true);
        minDistFromSource.set(source, 0);

        while (!q.isEmpty()) {
            int curNode = q.poll();
            for (Pair<Integer, Integer> p : g.adj.get(curNode)) {
                int adjNode = p.getFirst();
                if (!visited.get(adjNode)) {
                    visited.set(adjNode, true);
                    minDistFromSource.set(adjNode, minDistFromSource.get(curNode) + 1);
                    q.add(adjNode);
                }
            }
        }
    }

    private int minDistInternal(int target) {
        return minDistFromSource.get(target);
    }

    private boolean isVisitedInternal(int target) {
        return visited.get(target);
    }
}

class Tuple {
    private final int first;
    private final int second;
    private final int third;

    public Tuple(int first, int second, int third) {
        this.first = first;
        this.second = second;
        this.third = third;
    }

    public int getFirst() {
        return first;
    }

    public int getSecond() {
        return second;
    }

    public int getThird() {
        return third;
    }
}

class Pair<K, V> {
    private final K first;
    private final V second;

    public Pair(K first, V second) {
        this.first = first;
        this.second = second;
    }

    public K getFirst() {
        return first;
    }

    public V getSecond() {
        return second;
    }
}

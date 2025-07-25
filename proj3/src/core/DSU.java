package core;

// Disjoint Set Union
public class DSU {
    private int[] parent;
    private int[] size;
    private int numSets;

    public DSU(int n){
        parent = new int[n];
        size = new int[n];
        numSets = n;
        for (int i = 0; i < n; i++) {
            parent[i] = i;
            size[i] = 1;
        }
    }

    public int find(int x){
        if (parent[x] == x){
            return x;
        }
        return parent[x] = find(parent[x]);
    }

    public void union(int i, int j){
        int rootI = find(i);
        int rootJ = find(j);
        if (rootI != rootJ){
            if (size[rootI] < size[rootJ]){
                parent[rootI] = rootJ;
                size[rootJ] += size[rootI];
            }else {
                parent[rootJ] = rootI;
                size[rootI] += size[rootJ];
            }
            numSets--;
        }
    }

    public boolean isConnected(int i, int j){
        return find(i) == find(j);
    }

    public int getNumSets(){
        return numSets;
    }
}

import edu.princeton.cs.algs4.WeightedQuickUnionUF;
public class Percolation {

    private final int gridSize;
    private final int total;
    private boolean[][] trackSite;
    private WeightedQuickUnionUF wquf;
    private int openSites;


    // creates n-by-n grid, with all sites initially blocked
    public Percolation(int n) {

        if (n <= 0) {
            throw new IllegalArgumentException();
        }
        else {
            trackSite = new boolean[n][n];
            gridSize = n;
            openSites = 0;

            for(int i=0;i<n;i++) {
                for(int j=0;j<n;j++) {
                    trackSite[i][j] = false;
                }
            }
            total = n*n;
            wquf = new WeightedQuickUnionUF(total+2);

        }
    }


    // opens the site (row, col) if it is not open already
    public void open(int row, int col) {

        if (row > gridSize || row < 1) {
            throw new IllegalArgumentException();
        }
        else if (col > gridSize || col < 1) {
            throw new IllegalArgumentException();
        }
        else {
            if(!trackSite[row-1][col-1] && !isFull(row,col)) {
                trackSite[row-1][col-1] = true;
                openSites += 1;
                check(row,col);
            }
        }
    }

    private void check(int row,int col) {

        int r_n = row-1;
        int c_n = col-1;

        int temp = (r_n) * gridSize + c_n + 1;

        if (r_n == 0) {
            wquf.union(0, temp);
        }
        if (r_n == gridSize-1) {
            wquf.union(temp,total+1);
        }

        if (r_n + 1 <= gridSize-1 && trackSite[r_n+1][c_n]) {
            int temp1 = (r_n + 1) * gridSize + c_n +1;
            wquf.union(temp1,temp);
        }
        if (c_n+1 <= gridSize-1 && trackSite[r_n][c_n+1]) {
            int temp2 = (r_n) * gridSize + c_n + 2;
            wquf.union(temp2,temp);
        }
        if (r_n - 1 >= 0 && trackSite[r_n-1][c_n]) {
            int temp3 = (r_n - 1) * gridSize + c_n + 1;
            wquf.union(temp3,temp);
        }
        if (c_n-1 >= 0 && trackSite[r_n][c_n-1]) {
            int temp4 = (r_n) * gridSize + c_n;
            wquf.union(temp4,temp);
        }
    }


    // is the site (row, col) open?
    public boolean isOpen(int row, int col) {
        if (row > gridSize || row < 1) {
            throw new IllegalArgumentException();
        }
        else if (col > gridSize || col < 1) {
            throw new IllegalArgumentException();
        }
        else {
            return trackSite[row-1][col-1];
        }
    }


    // is the site (row, col) full?
    public boolean isFull(int row, int col) {
        if (row > gridSize || row < 1) {
            throw new IllegalArgumentException();
        }
        else if (col > gridSize || col < 1) {
            throw new IllegalArgumentException();
        }
        else {
            if(trackSite[row-1][col-1]){
                return wquf.find(0) == wquf.find((row-1) * gridSize + col);
            }
            else {
                return false;
            }
        }
    }


    // returns the number of open sites
    public int numberOfOpenSites() {
        return openSites;
    }

    // does the system percolate?
    public boolean percolates() {
        return wquf.find(0) == wquf.find(total+1);
    }

}

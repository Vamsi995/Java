import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;
import edu.princeton.cs.algs4.StdOut;

public class PercolationStats {

    private static final double CONFIDENCE_95 = 1.96;
    private double[] percolationThreshold;
    private double mean_val;
    private double stdDev;
    // perform independent trials on an n-by-n grid
    public PercolationStats(int n, int trials) {
        if (trials <= 0 || n <= 0) {
            throw new IllegalArgumentException();
        }
        percolationThreshold = new double[trials];
        Percolation model;

        double total_grid = n*n;
        for (int i=0;i<trials;i++) {
             model = new Percolation(n);

            while (!model.percolates()) {
                int row = StdRandom.uniform(1,n+1);
                int col = StdRandom.uniform(1,n+1);
                    model.open(row, col);
            }

            percolationThreshold[i] = model.numberOfOpenSites()/total_grid;
        }

    }

    // sample mean of percolation threshold
    public double mean() {
        mean_val = StdStats.mean(percolationThreshold);
        return mean_val;
    }

    // sample standard deviation of percolation threshold
    public double stddev() {
        stdDev = StdStats.stddev(percolationThreshold);
        return stdDev;
    }

    // low endpoint of 95% confidence interval
    public double confidenceLo() {
        double len = percolationThreshold.length;
        return mean_val - ((CONFIDENCE_95*stdDev)/Math.sqrt(len));
    }

    // high endpoint of 95% confidence interval
    public double confidenceHi() {
        double len = percolationThreshold.length;
        return mean_val + ((CONFIDENCE_95*stdDev)/Math.sqrt(len));
    }

    // test client (see below)
    public static void main(String[] args) {

        int n = Integer.parseInt(args[0]);
        int trials = Integer.parseInt(args[1]);

        if (trials <= 0 || n <= 0) {
            throw new IllegalArgumentException();
        }

        PercolationStats per = new PercolationStats(n, trials);


        StdOut.println( "mean = " + per.mean());
        StdOut.println( "stddev = " + per.stddev());
        StdOut.println("95% confidence interval = " + "[" + per.confidenceLo() + ", " + per.confidenceHi() + "]");
    }
}
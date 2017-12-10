import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;

public class PercolationStats {

    private final double[] results;
    private final int trials;
    private final int n;
    private double meand;
    private double stddevd;

    public PercolationStats(int n, int trials) {    // perform trials independent experiments on an n-by-n grid
        if (n <= 0 || trials <= 0)
            throw new java.lang.IllegalArgumentException();
        this.trials = trials;
        this.n = n;
        this.results = runTrials();
    }

    public double mean() {                          // sample mean of percolation threshold
        meand = meand > 0 ? meand : StdStats.mean(results);
        return meand;
    }

    public double stddev() {                        // sample standard deviation of percolation threshold
        stddevd = stddevd > 0 ? stddevd : StdStats.stddev(results);
        return stddevd;
    }

    public double confidenceLo() {                  // low  endpoint of 95% confidence interval
        return mean() - confidenceInterval(stddev());
    }

    public double confidenceHi() {                  // high endpoint of 95% confidence interval
        return mean() + confidenceInterval(stddev());
    }

    private double confidenceInterval(double stddev) {
        return 1.96 * stddev / Math.sqrt(trials);
    }

    private double[] runTrials() {
        double[] trialResults = new double[trials];
        for (int i = 0; i < trials; i++) {
            trialResults[i] = runTrial();
        }
        return trialResults;
    }

    private void openRandomSite(Percolation percolation) {
        int row, col;
        do {
            row = StdRandom.uniform(1, n + 1);
            col = StdRandom.uniform(1, n + 1);
        } while (percolation.isOpen(row, col));
        percolation.open(row, col);
    }


    private double runTrial() {
        Percolation percolation =  new Percolation(n);
        while (!percolation.percolates())
            openRandomSite(percolation);
        return (double) percolation.numberOfOpenSites()/(n * n);
    }

    public static void main(String[] args) {        // test client (described below)
        int n = Integer.parseInt(args[0]);
        int trials = Integer.parseInt(args[1]);
        PercolationStats percolationStats = new PercolationStats(n, trials);
        double mean = percolationStats.mean();
        double stddev = percolationStats.stddev();

        System.out.printf("mean                    = %f", mean).println();
        System.out.printf("stddev                  = %f", stddev).println();
        System.out.printf("95 pct confidence interval = [%f, %f]", percolationStats.confidenceLo(), percolationStats.confidenceHi()).println();
    }
}
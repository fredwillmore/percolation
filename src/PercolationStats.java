public class PercolationStats {

    public PercolationStats(int n, int trials) {    // perform trials independent experiments on an n-by-n grid
        if(n < 0 || trials < 0) {
            throw new java.lang.IllegalArgumentException();
        }
        double[] results = double[trials];
        for(int i = 0; i < trials; i++){
            Percolation percolation = new Percolation();
            while !percolation.percolates() {

            }
            results[i] = percolation.nu
        }
    }

    public double mean() {                          // sample mean of percolation threshold
        return 0;
    }

    public double stddev() {                        // sample standard deviation of percolation threshold
        return 0;
    }

    public double confidenceLo() {                  // low  endpoint of 95% confidence interval
        return 0;
    }

    public double confidenceHi() {                  // high endpoint of 95% confidence interval
        return 0;
    }

    public static void main(String[] args) {        // test client (described below)
        int n = Integer.valueOf(args[0]));
        int trials = Integer.valueOf(args[1]));

        PercolationStats percolationStats = PercolationStats.new(n, trials);
    }
}
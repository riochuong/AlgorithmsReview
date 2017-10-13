import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;

public class PercolationStats {
    private static final double CONFIDENCE_95 = 1.96;
    private final double mean;
    private final double stdDev;
    private final double confLo;
    private final double confHigh;

    public PercolationStats(int n, int trials) {
        // initialized all data
        if (n <= 0) throw new IllegalArgumentException("n cannot be 0 or negative");
        if (trials <= 0) throw new IllegalArgumentException("trials cannot be 0 or negative");
        Percolation perc;
        double[]  x = new double[trials];
        // random row and col
        int randRow;
        int randCol;

        // repeate the experiments
        for (int i = 0; i < trials; i++) {
            perc = new Percolation(n);
            while (true) {
                // obtain a blocked site
                do {
                    randRow = StdRandom.uniform(n) + 1;
                    randCol = StdRandom.uniform(n) + 1;
                } while (perc.isOpen(randRow, randCol));
                // open it
                perc.open(randRow, randCol);
                // check if the system is now percolate
                if (perc.percolates()) {
                    // calculate the ratios
                    x[i] = perc.numberOfOpenSites() / ((double) (n * n));
                    break;
                }
            }

        }
        // collect data
        mean = StdStats.mean(x);
        stdDev = StdStats.stddev(x);
        confLo = mean() - (CONFIDENCE_95 * stddev() / Math.sqrt(trials));
        confHigh = mean() + (CONFIDENCE_95 * stddev() / Math.sqrt(trials));


    }    // perform trials independent experiments on an n-by-n grid

    public double mean() {
        return mean;
    }                          // sample mean of percolation threshold

    public double stddev() {
        return stdDev;
    }                     // sample standard deviation of percolation threshold

    public double confidenceLo() {
        return confLo;
    }                 // low  endpoint of 95% confidence interval

    public double confidenceHi() {
        return confHigh;
    }               // high endpoint of 95% confidence interval

    public static void main(String[] args) {
        if (args.length < 2) throw new IllegalArgumentException("Must give 2 args for size and trials number");
        int n = Integer.parseInt(args[0]);
        int trials = Integer.parseInt(args[1]);
        PercolationStats stats = new PercolationStats(n, trials);
        System.out.println("mean = " + stats.mean());
        System.out.println("stddev = " + stats.mean());
        System.out.println("95% confidence interval = [" + stats.confidenceLo() + ", " + stats.confidenceHi() + "]");
    }
}
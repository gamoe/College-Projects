import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;

public class PercolationStats {
	private int trials;
	private int n;
	private double[] savervec;

	 // perform independent trials on an n-by-n grid
    public PercolationStats(int n, int trials){
    	this.trials=trials;
    	this.n=n;
    	if(n<=0||trials<=0) {
    		throw new IllegalArgumentException();
    	}
    	//criar vetor com mesmo numero de tentativas
    	this.savervec=new double[trials];
    	Percolation per;
    	for (int i = 0; i < trials; i++) {
    		per = new Percolation(this.n);
            while (!per.percolates()) {
             int a = StdRandom.uniform(1,n+1);
             int b = StdRandom.uniform(1,n+1);
                    per.open(a, b);
                    }
            double fraction = (double) per.numberOfOpenSites() /( n* n);
            savervec[i] = fraction;
    	}
        }
     // sample mean of percolation threshold   
    public double mean() {
    	 return StdStats.mean(savervec);
    }
   // sample standard deviation of percolation threshold  
    public double stddev() {
    	return StdStats.stddev(savervec);
    }

    // low endpoint of 95% confidence interval 
    public double confidenceLo() {
    	return mean() - ((1.96 * stddev()) / Math.sqrt(trials));
    }

    // high endpoint of 95% confidence interval
    public double confidenceHi() {
    	return mean() + ((1.96 * stddev()) / Math.sqrt(trials));
    }

   // test client (see below)
   public static void main(String[] args) {
	   int n = Integer.parseInt(args[0]);
       int trials = Integer.parseInt(args[1]);
       PercolationStats per = new PercolationStats(n, trials);
       String confidence = "("+per.confidenceLo() + ", " + per.confidenceHi()+")";
       
       
       StdOut.println("mean----->" + per.mean());
       StdOut.println("stddev------->1" + per.stddev());
       StdOut.println("95% confidence interval------->" +confidence);
   }
   }


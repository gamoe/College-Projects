import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;
import edu.princeton.cs.algs4.WeightedQuickUnionUF;

	public class Percolation {
	private final int n;
	private  final WeightedQuickUnionUF vec;
	private final WeightedQuickUnionUF vec2;
	private final boolean[] color;
	
	// creates n-by-n grid, with all sites initially blocked
	
	public Percolation(int n) {
		if(n<=0) {
			throw new IllegalArgumentException();
		}
		this.n=n;
		this.color=new boolean[n*n+2];
		this.vec= new WeightedQuickUnionUF (n*n+2);
		this.vec2=new WeightedQuickUnionUF(n*n+1);
		color[0]=true;
		color[n*n+1]=true;
		
	
	}
	//auxiliar get Index on the vec
	private int getVecIndex(int i, int j) {
	    return n * (i - 1) + j;
	}
	public boolean isOpen(int row, int col) {
		 return color[getVecIndex(row,col)];
	}
		
	// opens the site (row, col) if it is not open already
	public void open(int row, int col) {
		if(!isOpen(row,col)) {
		 color[getVecIndex(row,col)] = true;
	     if (row == 1) {
	         vec.union(getVecIndex(row, col),vec.find(0));
	         vec2.union(getVecIndex(row, col),vec2.find(0));
	         
	     }
	     if (row == n) {
	       vec.union(getVecIndex(row, col), vec.find(n*n+1));
	     }
	
	     if (col > 1 && isOpen(row, col - 1)) {
	    	 if(vec2.find(getVecIndex(row,col))!=vec2.find(getVecIndex(row,col-1))){
	         vec.union(getVecIndex(row, col), getVecIndex(row, col - 1));
	         vec2.union(getVecIndex(row, col), getVecIndex(row, col - 1));
	    	 }
	     }
	     if (col < n && isOpen(row, col + 1)) {
	    	 if(vec2.find(getVecIndex(row,col))!=vec2.find(getVecIndex(row,col+1))){
	         vec.union(getVecIndex(row, col), getVecIndex(row, col + 1));
	         vec2.union(getVecIndex(row, col), getVecIndex(row, col + 1));
	    	 }
	     }
	     if (row > 1 && isOpen(row - 1, col)) {
	    	 if(vec2.find(getVecIndex(row,col))!=vec2.find(getVecIndex(row-1,col))){
	        vec.union(getVecIndex(row, col), getVecIndex(row - 1, col));
	        vec2.union(getVecIndex(row, col), getVecIndex(row - 1, col));
	    	 }
	     }
	     if (row < n && isOpen(row + 1, col)) {
	    	 if(vec2.find(getVecIndex(row,col))!=vec2.find(getVecIndex(row+1,col))){
	        vec.union(getVecIndex(row, col), getVecIndex(row + 1, col));
	        vec2.union(getVecIndex(row, col), getVecIndex(row + 1, col));
	    	 }
	     }
	 }
	}
	
	public boolean isFull(int i, int j) {
		 return vec2.find(0)== vec2.find(getVecIndex(i,j));
	}
	 public int numberOfOpenSites() {
		  int numberOfOpenSite=0;
		  for(int i =1; i < color.length-1; i++)  
				  if (color[i] ) {
					  numberOfOpenSite++;
			  }
		  return numberOfOpenSite;
	 }
		
	public boolean percolates() {
		return vec.find(0)== vec.find(n*n+1);
	}  
	}
	
	

import edu.princeton.cs.algs4.WeightedQuickUnionUF;

import java.io.BufferedReader;
import java.io.FileReader;

public class Percolation {
    private class Site {
        private Integer col;
        private Integer row;
        private Boolean open;
        private Boolean full;

        public Site(Integer row, Integer col) {
            this.row = row;
            this.col = col;
            this.open = false;
            this.full = false;
        }

        public Integer getCol() {
            return col;
        }

        public void setCol(Integer col) {
            this.col = col;
        }

        public Integer getRow() {
            return row;
        }

        public void setRow(Integer row) {
            this.row = row;
        }

        public Boolean getOpen() {
            return open;
        }

        public void setOpen(Boolean open) {
            this.open = open;
        }

        public Boolean getFull() {
            return full;
        }

        public void setFull(Boolean full) {
            this.full = full;
        }
    }

    private class Grid {
        private Site[] sites;
        private boolean percolates;
        private WeightedQuickUnionUF uf;
        private int n;

        private int getPosition(int row, int col) {
            if(row>0 && row<=n && col>0 && col<=n) {
                return (row - 1) * n + (col - 1);
            }
            return -1;
        }

        private Site getSiteAtPosition(int row, int col) {
            int position = getPosition(row, col);

            if(position >= 0) {
                return sites[position];
            }
            return null;

        }

        public Grid(int n) {
            if(n < 0) {
                throw new java.lang.IllegalArgumentException();
            }
            // create n-by-n grid, with all sites blocked
            this.n = n;
            int numSites = n*n;
            sites = new Site[numSites];
            for(int row = 1; row <= n; row++) {
                for(int col = 1; col <= n; col++) {
                    sites[getPosition(row, col)] = new Site(row, col);
                }
            }
            percolates = false;
            uf = new WeightedQuickUnionUF((int)Math.pow(n, 2));
        }

        public Boolean isFull(int row, int col) {
            testRange(row, col);
            if(getSiteAtPosition(row, col).getOpen()){
                for(int i=1; i<=n; i++){
                    if(getSiteAtPosition(1, i).getOpen()){
                        if(uf.connected(getPosition(1, i), getPosition(row, col))){
                            return true;
                        }
                    }
                }
            }
            return false;
        }

        public void open(int row, int col) {
            testRange(row, col);
            Site site = getSiteAtPosition(row, col);
            if(!site.getOpen()){
                site.setOpen(true);
                if(row == 1) {
                    site.setFull(true);
                }
                Site[] neighbors = new Site[4];
                neighbors[0] = getSiteAtPosition(row, col-1);
                neighbors[1] = getSiteAtPosition(row, col+1);
                neighbors[2] = getSiteAtPosition(row-1, col);
                neighbors[3] = getSiteAtPosition(row+1, col);
                for(Site neighbor: neighbors){
                    if(neighbor != null && neighbor.getOpen()){
                        site.setOpen(true);
                        uf.union(getPosition(row, col), getPosition(neighbor.getRow(), neighbor.getCol()));
                    }
                }
                if(row==n) {
                    percolates = site.getFull();
                }
            }
        }

        public boolean isOpen(int row, int col) {
            testRange(row, col);
            Site site = getSiteAtPosition(row, col);
            return site.getOpen();
        }
        public boolean percolates() {
            return percolates;
        }

        private void testRange(int row, int col){
            if(!(row > 0 && row <= n) || !(col > 0 && col <= n)) {
                throw new java.lang.IllegalArgumentException();
            }
        }

    }

    private Grid grid;

    public Percolation(int n) {
        grid = new Grid(n);
    }

    public boolean isFull(int row, int col) { // is site (row, col) full?
        return grid.isFull(row, col);
    }

    public void open(int row, int col) {   // open site (row, col) if it is not open already
        grid.open(row, col);
    }

    public boolean isOpen(int row, int col) { // is site (row, col) open?
        return grid.isOpen(row, col);
    }

    public     int numberOfOpenSites()       // number of open sites

    public boolean percolates() {             // does the system percolate?
        return grid.percolates();
    }
}
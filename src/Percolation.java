import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {
    private final int n;
    private final Site[] sites;
    private boolean percolates;
    private final WeightedQuickUnionUF uf;
    private int openSites = 0;
//    private int calls=0;

    public Percolation(int n) {
        if (n <= 0) {
            throw new java.lang.IllegalArgumentException();
        }
        this.n = n;
        sites = new Site[n * n];
        for (int row = 1; row <= n; row++) {
            for (int col = 1; col <= n; col++) {
                sites[getPosition(row, col)] = new Site(row, col);
            }
        }
        percolates = false;
        uf = new WeightedQuickUnionUF((int) Math.pow(n, 2));

    }

    private void fill(Site currentSite) {
        currentSite.setFull(true);
        for (Site neighbor: getNeighbors(currentSite)) {
            if (neighbor != null && neighbor.isOpen() && !neighbor.isFull()) {
                fill(neighbor);
            }
        }
    }

    private Site[] getNeighbors(Site currentSite) {
        int row = currentSite.getRow();
        int col = currentSite.getCol();
        Site[] neighbors = {
                getSiteAtPosition(row, col-1),
                getSiteAtPosition(row, col+1),
                getSiteAtPosition(row-1, col),
                getSiteAtPosition(row+1, col)
        };
        return neighbors;
    }

    public void open(int row, int col) {
        testRange(row, col);
        Site site = getSiteAtPosition(row, col);
        if (!site.isOpen()) {
            site.setOpen(true);
            openSites++;
            Site[] neighbors = getNeighbors(site);
            for (Site neighbor: neighbors) {
                if (neighbor != null && neighbor.isOpen()) {
                    site.setOpen(true);
                    uf.union(getPosition(row, col), getPosition(neighbor.getRow(), neighbor.getCol()));
                }
            }
            boolean fillSite = row == 1;
            for (Site neighbor: neighbors) {
                fillSite |= (neighbor != null && neighbor.isFull());
            }
            if(fillSite) {
                fill(site);
            }
        }
    }

    public boolean isOpen(int row, int col) {
        testRange(row, col);
        Site site = getSiteAtPosition(row, col);
        return site.isOpen();
    }

    public boolean isFull(int row, int col) {
        testRange(row, col);
        Site currentSite = getSiteAtPosition(row, col);
        if (currentSite.isFull()) {
            return true;
        }

        return false;
    }

    public boolean percolates() {
        if(percolates) return true;
        int row = n;
        for (int col = 1; col <= n; col++) {
            if (isFull(row, col)) {
                percolates = true;
                return true;
            }
        }
        return percolates;
    }

    public int numberOfOpenSites() {
        return openSites;
    }

    private void testRange(int row, int col) {
        if (!(row > 0 && row <= n) || !(col > 0 && col <= n))
            throw new java.lang.IllegalArgumentException();
    }


    private int getPosition(int row, int col) {
        if (row > 0 && row <= n && col > 0 && col <= n) {
            return (row - 1) * n + (col - 1);
        }
        return -1;
    }

    private Site getSiteAtPosition(int row, int col) {
        int position = getPosition(row, col);

        if (position >= 0) {
            return sites[position];
        }
        return null;

    }

    private static class Site {
        private final int col;
        private final int row;
        private boolean open;
        private boolean full;

        public Site(int row, int col) {
            this.row = row;
            this.col = col;
            this.open = false;
        }

        public int getCol() {
            return col;
        }

        public int getRow() {
            return row;
        }

        public boolean isOpen() {
            return open;
        }

        public void setOpen(boolean open) {
            this.open = open;
        }

        public boolean isFull() {
            return full;
        }

        public void setFull(boolean full) {
            this.full = full;
        }

    }

//    public boolean isFull(int row, int col) { // is site (row, col) full?
//        return grid.isFull(row, col);
//    }

//    public void open(int row, int col) {   // open site (row, col) if it is not open already
//        grid.open(row, col);
//    }

//    public boolean isOpen(int row, int col) { // is site (row, col) open?
//        return grid.isOpen(row, col);
//    }

//    public int numberOfOpenSites() {
//        return grid.numberOfOpenSites();
//    }

//    public boolean percolates() {             // does the system percolate?
//        return grid.percolates();
//    }
}
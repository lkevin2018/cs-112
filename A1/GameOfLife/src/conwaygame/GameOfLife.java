package conwaygame;
import java.nio.file.SimpleFileVisitor;
import java.util.ArrayList;
/**
 * Conway's Game of Life Class holds various methods that will
 * progress the state of the game's board through it's many iterations/generations.
 *
 * Rules 
 * Alive cells with 0-1 neighbors die of loneliness.
 * Alive cells with >=4 neighbors die of overpopulation.
 * Alive cells with 2-3 neighbors survive.
 * Dead cells with exactly 3 neighbors become alive by reproduction.

 * @author Seth Kelley 
 * @author Maxwell Goldberg
 * @author Kevin Joseph
 *         kbj24
 *         kbj24@rutgers.edu
 *
 */
public class GameOfLife {

    // Instance variables
    private static final boolean ALIVE = true;
    private static final boolean  DEAD = false;

    private boolean[][] grid;    // The board has the current generation of cells
    private int totalAliveCells; // Total number of alive cells in the grid (board)

    /**
    * Default Constructor which creates a small 5x5 grid with five alive cells.
    * This variation does not exceed bounds and dies off after four iterations.
    */
    public GameOfLife() {
        grid = new boolean[5][5];
        totalAliveCells = 5;
        grid[1][1] = ALIVE;
        grid[1][3] = ALIVE;
        grid[2][2] = ALIVE;
        grid[3][2] = ALIVE;
        grid[3][3] = ALIVE;
    }

    /**
    * Constructor used that will take in values to create a grid with a given number
    * of alive cells
    * @param file is the input file with the initial game pattern formatted as follows:
    * An integer representing the number of grid rows, say r
    * An integer representing the number of grid columns, say c
    * Number of r lines, each containing c true or false values (true denotes an ALIVE cell)
    */
    public GameOfLife (String file) {
        StdIn.setFile(file);
        int rows = StdIn.readInt();
        int col = StdIn.readInt();
        grid = new boolean[rows][col];
        for(int i = 0; i < rows; i++){
            for(int j = 0; j < col; j++){
                boolean temp = StdIn.readBoolean();
                if(temp){
                    grid[i][j] = ALIVE;
                    totalAliveCells++;
                }
                else{
                    grid[i][j] = DEAD;
                } 
            }
        }
    }

    /**
     * Returns grid
     * @return boolean[][] for current grid
     */
    public boolean[][] getGrid () {
        return grid;
    }
    
    /**
     * Private method to return copy of grid
     * @return boolean[][] copy for current grid
     *
     */
    private boolean[][] gridCopy(){
        boolean[][] gridCopy = new boolean[grid.length][grid[0].length];
        for(int i = 0; i < grid.length; i++){
            for(int j = 0; j < grid[0].length; j++){
                if (grid[i][j]){
                    gridCopy[i][j] = ALIVE;
                }
                else{
                    gridCopy[i][j] = DEAD;
                }
            }
        }
        return gridCopy;
    }
    /**
     * Returns totalAliveCells
     * @return int for total number of alive cells in grid
     */
    public int getTotalAliveCells () {
        return totalAliveCells;
    }

    /**
     * Returns the status of the cell at (row,col): ALIVE or DEAD
     * @param row row position of the cell
     * @param col column position of the cell
     * @return true or false value "ALIVE" or "DEAD" (state of the cell)
     */
    public boolean getCellState (int row, int col) {

        return grid[row][col];
    }

    /**
     * Returns true if there are any alive cells in the grid
     * @return true if there is at least one cell alive, otherwise returns false
     */
    public boolean isAlive () {

        if(totalAliveCells > 0){
            return true;
        }
        else{
            return false;
        }
    }

    /**
     * Determines the number of alive cells around a given cell.
     * Each cell has 8 neighbor cells which are the cells that are 
     * horizontally, vertically, or diagonally adjacent.
     * 
     * @param col column position of the cell
     * @param row row position of the cell
     * @return neighboringCells, the number of alive cells (at most 8).
     */
    public int numOfAliveNeighbors (int row, int col) {
        int result = 0;
        int rowMax = grid.length - 1;
        int colMax = grid[0].length - 1;
        if(row != 0 && col != 0 && row != rowMax && col != colMax){
            if(grid[row-1][col-1]){
                result++;
            }
            if(grid[row-1][col]){
                result++;
            }
            if(grid[row-1][col+1]){
                result++;
            }
            if(grid[row][col-1]){
                result++;
            }
            if(grid[row][col+1]){
                result++;
            }
            if(grid[row+1][col-1]){
                result++;
            }
            if(grid[row+1][col]){
                result++;
            }
            if(grid[row+1][col+1]){
                result++;
            }
        }
        else if(row == 0 && col == 0){
            if(grid[rowMax][colMax]){
                result++;
            }
            if(grid[row][colMax]){
                result++;
            }
            if(grid[row+1][colMax]){
                result++;
            }
            if(grid[rowMax][col]){
                result++;
            }
            if(grid[rowMax][col+1]){
                result++;
            }
            if(grid[row][col+1]){
                result++;
            }
            if(grid[row+1][col]){
                result++;
            }
            if(grid[row+1][col+1]){
                result++;
            }
        }
        else if(row == 0 && col == colMax){
            if(grid[rowMax][0]){
                result++;
            }
            if(grid[row][0]){
                result++;
            }
            if(grid[row+1][0]){
                result++;
            }
            if(grid[rowMax][col-1]){
                result++;
            }
            if(grid[rowMax][col]){
                result++;
            }
            if(grid[row][col-1]){
                result++;
            }
            if(grid[row+1][col-1]){
                result++;
            }
            if(grid[row+1][col]){
                result++;
            }
        }
        else if(row == rowMax && col == 0){
            if(grid[0][colMax]){
                result++;
            }
            if(grid[0][col]){
                result++;
            }
            if(grid[0][col+1]){
                result++;
            }
            if(grid[row-1][colMax]){
                result++;
            }
            if(grid[row][colMax]){
                result++;
            }
            if(grid[row-1][col]){
                result++;
            }
            if(grid[row-1][col+1]){
                result++;
            }
            if(grid[row][col+1]){
                result++;
            }
        }
        else if(row == rowMax && col == colMax){
            if(grid[0][0]){
                result++;
            }
            if(grid[0][col-1]){
                result++;
            }
            if(grid[0][col]){
                result++;
            }
            if(grid[row-1][0]){
                result++;
            }
            if(grid[row][0]){
                result++;
            }
            if(grid[row-1][col-1]){
                result++;
            }
            if(grid[row-1][col]){
                result++;
            }
            if(grid[row][col-1]){
                result++;
            }
        }
        else if(row == 0){
            if(grid[rowMax][col-1]){
                result++;
            }
            if(grid[rowMax][col]){
                result++;
            }
            if(grid[rowMax][col+1]){
                result++;
            }
            if(grid[row][col-1]){
                result++;
            }
            if(grid[row][col+1]){
                result++;
            }
            if(grid[row+1][col-1]){
                result++;
            }
            if(grid[row+1][col]){
                result++;
            }
            if(grid[row+1][col+1]){
                result++;
            }                
        }
        else if(col == 0){
            if(grid[row-1][colMax]){
                result++;
            }
            if(grid[row-1][col]){
                result++;
            }
            if(grid[row-1][col+1]){
                result++;
            }
            if(grid[row][colMax]){
                result++;
            }
            if(grid[row][col+1]){
                result++;
            }
            if(grid[row+1][colMax]){
                result++;
            }
            if(grid[row+1][col]){
                result++;
            }
            if(grid[row+1][col+1]){
                result++;
            }                
        }
        else if(row == rowMax){
            if(grid[row-1][col-1]){
                result++;
            }
            if(grid[row-1][col]){
                result++;
            }
            if(grid[row-1][col+1]){
                result++;
            }
            if(grid[row][col-1]){
                result++;
            }
            if(grid[row][col+1]){
                result++;
            }
            if(grid[0][col-1]){
                result++;
            }
            if(grid[0][col]){
                result++;
            }
            if(grid[0][col+1]){
                result++;
            } 
        }
        else if(col == colMax){
            if(grid[row-1][col]){
                result++;
            }
            if(grid[row-1][col-1]){
                result++;
            }
            if(grid[row-1][0]){
                result++;
            }
            if(grid[row][col-1]){
                result++;
            }
            if(grid[row][0]){
                result++;
            }
            if(grid[row+1][col-1]){
                result++;
            }
            if(grid[row+1][col]){
                result++;
            }
            if(grid[row+1][0]){
                result++;
            }  
        }
        return result;
        }  

    /**
     * Creates a new grid with the next generation of the current grid using 
     * the rules for Conway's Game of Life.
     * 
     * @return boolean[][] of new grid (this is a new 2D array)
     */
    public boolean[][] computeNewGrid () {
        GameOfLife result = new GameOfLife();
        result.grid = gridCopy();
        for(int i = 0; i < grid.length; i++){
            for(int j = 0; j < grid[0].length; j++){
                int numAliveResult = numOfAliveNeighbors(i, j);
                if(grid[i][j] && numAliveResult <= 1){
                    result.grid[i][j] = DEAD;
                }

                if(!grid[i][j] && numAliveResult == 3){
                    result.grid[i][j] = ALIVE;
                }

                if(grid[i][j] && (numAliveResult == 2 || numAliveResult == 3)){
                    result.grid[i][j] = ALIVE;
                }

                if(grid[i][j] && numAliveResult >= 4){
                    result.grid[i][j] = DEAD;
                }
            }
        }
        return result.gridCopy();    
    }

    /**
     * Updates the current grid (the grid instance variable) with the grid denoting
     * the next generation of cells computed by computeNewGrid().
     * 
     * Updates totalAliveCells instance variable
     */
    public void nextGeneration () {
        grid = computeNewGrid();
        int tempAlive = 0;
        for(int i = 0; i < grid.length; i++){
            for(int j = 0; j < grid[0].length; j++){
                if (grid[i][j]){
                    tempAlive++;
                }
            }
        }
        totalAliveCells = tempAlive;
    }

    /**
     * Updates the current grid with the grid computed after multiple (n) generations. 
     * @param n number of iterations that the grid will go through to compute a new grid
     */
    public void nextGeneration (int n) {
        for(int i = 0; i < n; i++){
            nextGeneration();
        }
    }

    /**
     * Determines the number of separate cell communities in the grid
     * @return the number of communities in the grid, communities can be formed from edges
     */
    public int numOfCommunities() {

        WeightedQuickUnionUF qu = new WeightedQuickUnionUF(grid.length, grid[0].length);
        int result = 0;
        ArrayList<Integer> r = new ArrayList<Integer>();
        ArrayList<Integer> c = new ArrayList<Integer>();       
        ArrayList<Integer> parentRoots = new ArrayList<Integer>();

        for(int i = 0; i < grid.length; i++){
            for(int j = 0; j < grid[0].length; j++){
                if(grid[i][j]){
                    r.add(i);
                    c.add(j);
                }
            }
        }
        
        for(int i = 0; i < r.size(); i++){
            int row = r.get(i);
            int col = c.get(i);
            int rowMax = grid.length - 1;
            int colMax = grid[0].length - 1;
            if(row != 0 && col != 0 && row != rowMax && col != colMax){
                if(grid[row-1][col-1]){
                    qu.union(row, col, row-1, col-1);
                }
                if(grid[row-1][col]){
                    qu.union(row, col, row-1, col);
                }
                if(grid[row-1][col+1]){
                    qu.union(row, col, row-1, col+1);
                }
                if(grid[row][col-1]){
                    qu.union(row, col, row, col-1);
                }
                if(grid[row][col+1]){
                    qu.union(row, col, row, col+1);
                }
                if(grid[row+1][col-1]){
                    qu.union(row, col, row+1, col-1);
                }
                if(grid[row+1][col]){
                    qu.union(row, col, row+1, col);
                }
                if(grid[row+1][col+1]){
                    qu.union(row, col, row+1, col+1);
                }
            }
            else if(row == 0 && col == 0){
                if(grid[rowMax][colMax]){
                    qu.union(row, col, rowMax, colMax);
                }
                if(grid[row][colMax]){
                    qu.union(row, col, row, colMax);
                }
                if(grid[row+1][colMax]){
                    qu.union(row, col, row+1, colMax);
                }
                if(grid[rowMax][col]){
                    qu.union(row, col, rowMax, col);
                }
                if(grid[rowMax][col+1]){
                    qu.union(row, col, rowMax, col+1);
                }
                if(grid[row][col+1]){
                    qu.union(row, col, row, col+1);
                }
                if(grid[row+1][col]){
                    qu.union(row, col, row+1, col);
                }
                if(grid[row+1][col+1]){
                    qu.union(row, col, row+1, col+1);
                }
            }
            else if(row == 0 && col == colMax){
                if(grid[rowMax][0]){
                    qu.union(row, col, rowMax, 0);
                }
                if(grid[row][0]){
                    qu.union(row, col, row, 0);
                }
                if(grid[row+1][0]){
                    qu.union(row, col, row+1, 0);
                }
                if(grid[rowMax][col-1]){
                    qu.union(row, col, rowMax, col-1);
                }
                if(grid[rowMax][col]){
                    qu.union(row, col, rowMax, col);
                }
                if(grid[row][col-1]){
                    qu.union(row, col, row, col-1);
                }
                if(grid[row+1][col-1]){
                    qu.union(row, col, row+1, col-1);
                }
                if(grid[row+1][col]){
                    qu.union(row, col, row+1, col);
                }
            }
            else if(row == rowMax && col == 0){
                if(grid[0][colMax]){
                    qu.union(row, col, 0, colMax);
                }
                if(grid[0][col]){
                    qu.union(row, col, 0, col);
                }
                if(grid[0][col+1]){
                    qu.union(row, col, 0, col+1);
                }
                if(grid[row-1][colMax]){
                    qu.union(row, col, row-1, colMax);
                }
                if(grid[row][colMax]){
                    qu.union(row, col, row, colMax);
                }
                if(grid[row-1][col]){
                    qu.union(row, col, row-1, col);
                }
                if(grid[row-1][col+1]){
                    qu.union(row, col, row-1, col+1);
                }
                if(grid[row][col+1]){
                    qu.union(row, col, row, col+1);
                }
            }
            else if(row == rowMax && col == colMax){
                if(grid[0][0]){
                    qu.union(row, col, 0, 0);
                }
                if(grid[0][col-1]){
                    qu.union(row, col, 0, col-1);
                }
                if(grid[0][col]){
                    qu.union(row, col, 0, col);
                }
                if(grid[row-1][0]){
                    qu.union(row, col, row-1, 0);
                }
                if(grid[row][0]){
                    qu.union(row, col, row, 0);
                }
                if(grid[row-1][col-1]){
                    qu.union(row, col, row-1, col-1);
                }
                if(grid[row-1][col]){
                    qu.union(row, col, row-1, col);
                }
                if(grid[row][col-1]){
                    qu.union(row, col, row, col-1);
                }
            }
            else if(row == 0){
                if(grid[rowMax][col-1]){
                    qu.union(row, col, rowMax, col-1);
                }
                if(grid[rowMax][col]){
                    qu.union(row, col, rowMax, col);
                }
                if(grid[rowMax][col+1]){
                    qu.union(row, col, rowMax, col+1);
                }
                if(grid[row][col-1]){
                    qu.union(row, col, row, col-1);
                }
                if(grid[row][col+1]){
                    qu.union(row, col, row, col+1);
                }
                if(grid[row+1][col-1]){
                    qu.union(row, col, row+1, col-1);
                }
                if(grid[row+1][col]){
                    qu.union(row, col, row+1, col);
                }
                if(grid[row+1][col+1]){
                    qu.union(row, col, row+1, col+1);
                }                
            }
            else if(col == 0){
                if(grid[row-1][colMax]){
                    qu.union(row, col, row-1, colMax);
                }
                if(grid[row-1][col]){
                    qu.union(row, col, row-1, col);
                }
                if(grid[row-1][col+1]){
                    qu.union(row, col, row-1, col+1);
                }
                if(grid[row][colMax]){
                    qu.union(row, col, row, colMax);
                }
                if(grid[row][col+1]){
                    qu.union(row, col, row, col+1);
                }
                if(grid[row+1][colMax]){
                    qu.union(row, col, row+1, colMax);
                }
                if(grid[row+1][col]){
                    qu.union(row, col, row+1, col);
                }
                if(grid[row+1][col+1]){
                    qu.union(row, col, row+1, col+1);
                }                
            }
            else if(row == rowMax){
                if(grid[row-1][col-1]){
                    qu.union(row, col, row-1, col-1);
                }
                if(grid[row-1][col]){
                    qu.union(row, col, row-1, col);
                }
                if(grid[row-1][col+1]){
                    qu.union(row, col, row-1, col+1);
                }
                if(grid[row][col-1]){
                    qu.union(row, col, row, col-1);
                }
                if(grid[row][col+1]){
                    qu.union(row, col, row, col+1);
                }
                if(grid[0][col-1]){
                    qu.union(row, col, 0, col-1);
                }
                if(grid[0][col]){
                    qu.union(row, col, 0, col);
                }
                if(grid[0][col+1]){
                    qu.union(row, col, 0, col+1);
                } 
            }
            else if(col == colMax){
                if(grid[row-1][col]){
                    qu.union(row, col, row-1, col);
                }
                if(grid[row-1][col-1]){
                    qu.union(row, col, row-1, col-1);
                }
                if(grid[row-1][0]){
                    qu.union(row, col, row-1, 0);
                }
                if(grid[row][col-1]){
                    qu.union(row, col, row, col-1);
                }
                if(grid[row][0]){
                    qu.union(row, col, row, 0);
                }
                if(grid[row+1][col-1]){
                    qu.union(row, col, row+1, col-1);
                }
                if(grid[row+1][col]){
                    qu.union(row, col, row+1, col);
                }
                if(grid[row+1][0]){
                    qu.union(row, col, row+1, 0);
                }  
            }
        }
        for(int i = 0; i < r.size(); i++){
            if(!parentRoots.contains(qu.find(r.get(i), c.get(i)))){
                parentRoots.add(qu.find(r.get(i), c.get(i)));
            }   
        }
        result += parentRoots.size();
        return result; 
    }
}

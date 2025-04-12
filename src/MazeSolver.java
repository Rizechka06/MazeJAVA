
import java.util.Random;

public class MazeSolver {

    private int rows;
    private int cols;
    private int[][] maze;
    private boolean[][] visited;
    private int startRow;
    private int startCol;
    private int endRow;
    private int endCol;

    public MazeSolver(int rows, int cols) {
        this.rows = rows;
        this.cols = cols;
        this.maze = new int[rows][cols];
        this.visited = new boolean[rows][cols];
        this.startRow = 0;
        this.startCol = 0;
        this.endRow = rows - 1;
        this.endCol = cols - 1;
    }

    public void generateMaze() {
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                maze[i][j] = 1;
            }
        }
        generateMazeRecursive(1, 1);
        maze[startRow][startCol] = 0;
        maze[endRow][endCol] = 0;
    }

    private void generateMazeRecursive(int row, int col) {
        maze[row][col] = 0;
        int[] directions = generateRandomDirections();
        for (int direction : directions) {
            int newRow = row;
            int newCol = col;
            switch (direction) {
                case 0: newRow -= 2; break;
                case 1: newRow += 2; break;
                case 2: newCol -= 2; break;
                case 3: newCol += 2; break;
            }
            if (isValid(newRow, newCol) && maze[newRow][newCol] == 1) {
                maze[row + (newRow - row) / 2][col + (newCol - col) / 2] = 0;
                generateMazeRecursive(newRow, newCol);
            }
        }
    }

    private int[] generateRandomDirections() {
        int[] directions = {0, 1, 2, 3};
        Random random = new Random();
        for (int i = 0; i < directions.length; i++) {
            int randomIndex = random.nextInt(directions.length);
            int temp = directions[i];
            directions[i] = directions[randomIndex];
            directions[randomIndex] = temp;
        }
        return directions;
    }

    private boolean isValid(int row, int col) {
        return row > 0 && row < rows - 1 && col > 0 && col < cols - 1;
    }

    public boolean solveMaze() {
        return solveMazeRecursive(startRow, startCol);
    }

    private boolean solveMazeRecursive(int row, int col) {
        if (!isValid(row, col) || maze[row][col] == 1 || visited[row][col]) {
            return false;
        }
        visited[row][col] = true;
        if (row == endRow && col == endCol) {
            return true;
        }
        if (solveMazeRecursive(row - 1, col)) {
            return true;
        }
        if (solveMazeRecursive(row + 1, col)) {
            return true;
        }
        if (solveMazeRecursive(row, col - 1)) {
            return true;
        }
        if (solveMazeRecursive(row, col + 1)) {
            return true;
        }
        return false;
    }

    public void displayMaze() {
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                if (i == startRow && j == startCol){
                    System.out.print("S");
                } else if (i == endRow && j == endCol){
                    System.out.print("E");
                }
                else if (maze[i][j] == 1) {
                    System.out.print("#");
                } else if (visited[i][j]) {
                    System.out.print(".");
                } else {
                    System.out.print(" ");
                }
            }
            System.out.println();
        }
    }

    public static void main(String[] args) {
        int rows = 11;
        int cols = 15;
        MazeSolver mazeSolver = new MazeSolver(rows, cols);
        mazeSolver.generateMaze();
        mazeSolver.solveMaze();
        mazeSolver.displayMaze();
    }
}

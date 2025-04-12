import java.util.Random;

public class MazeSolver {

    private int rows; // Number of rows in the maze
    private int cols; // Number of columns in the maze
    private int[][] maze; // 2D array representing the maze
    private boolean[][] visited; // 2D array to track visited cells
    private int startRow; // Start row
    private int startCol; // Start column
    private int endRow; // End row
    private int endCol; // End column

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
        // Initialize maze with walls
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                maze[i][j] = 1; // 1 represents a wall
            }
        }

        // Recursive backtracking algorithm
        generateMazeRecursive(1, 1);

        // Set start and end points as paths
        maze[startRow][startCol] = 0; // 0 represents a path
        maze[endRow][endCol] = 0;
    }

    private void generateMazeRecursive(int row, int col) {
        maze[row][col] = 0; // Make current cell a path

        // Randomize direction order
        int[] directions = generateRandomDirections();

        for (int direction : directions) {
            int newRow = row;
            int newCol = col;

            switch (direction) {
                case 0: // Up
                    newRow -= 2;
                    break;
                case 1: // Down
                    newRow += 2;
                    break;
                case 2: // Left
                    newCol -= 2;
                    break;
                case 3: // Right
                    newCol += 2;
                    break;
            }

            if (isValid(newRow, newCol) && maze[newRow][newCol] == 1) {
                maze[row + (newRow - row) / 2][col + (newCol - col) / 2] = 0; // Carve path between cells
                generateMazeRecursive(newRow, newCol);
            }
        }
    }

    private int[] generateRandomDirections() {
        int[] directions = {0, 1, 2, 3}; // Up, Down, Left, Right
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
            return true; // Reached the end
        }

        // Explore adjacent cells
        if (solveMazeRecursive(row - 1, col)) { // Up
            return true;
        }
        if (solveMazeRecursive(row + 1, col)) { // Down
            return true;
        }
        if (solveMazeRecursive(row, col - 1)) { // Left
            return true;
        }
        if (solveMazeRecursive(row, col + 1)) { // Right
            return true;
        }

        return false; // Dead end
    }

    public void displayMaze() {
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                if (i == startRow && j == startCol) {
                    System.out.print("S");
                } else if (i == endRow && j == endCol) {
                    System.out.print("E");
                } else if (maze[i][j] == 1) {
                    System.out.print("#"); // Wall
                } else if (visited[i][j]) {
                    System.out.print("."); // Path
                } else {
                    System.out.print(" "); // Empty path
                }
            }
            System.out.println();
        }
    }

    public static void main(String[] args) {
        int rows = 11; // Example maze size
        int cols = 15;
        MazeSolver mazeSolver = new MazeSolver(rows, cols);
        mazeSolver.generateMaze();

        System.out.println("Generated Maze:");
        mazeSolver.displayMaze();

        if (mazeSolver.solveMaze()) {
            System.out.println("\nSolved Maze:");
            mazeSolver.displayMaze();
        } else {
            System.out.println("\nNo solution found.");
        }
    }
}
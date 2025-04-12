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
        if (rows < 3 || cols < 3 || rows % 2 == 0 || cols % 2 == 0) {
            throw new IllegalArgumentException("Maze dimensions must be odd and greater than 2.");
        }
        this.rows = rows;
        this.cols = cols;
        this.maze = new int[rows][cols];
        this.visited = new boolean[rows][cols];
        this.startRow = 1;
        this.startCol = 1;
        this.endRow = rows - 2;
        this.endCol = cols - 2;
    }

    public void generateMaze() {
        boolean solvable = false;
        while (!solvable) {
            // Reset everything before generating a new maze
            for (int i = 0; i < rows; i++) {
                for (int j = 0; j < cols; j++) {
                    maze[i][j] = 1; // Wall
                    visited[i][j] = false;
                }
            }

            generateMazeRecursive(startRow, startCol);

            maze[startRow][startCol] = 0; // Start position
            maze[endRow][endCol] = 0; // End position

            // Check if the maze is solvable
            resetVisited();
            solvable = solveMaze();
        }
    }

    private void generateMazeRecursive(int row, int col) {
        maze[row][col] = 0; // Path

        int[] directions = generateRandomDirections();
        for (int direction : directions) {
            int newRow = row, newCol = col;
            switch (direction) {
                case 0: newRow -= 2; break; // Up
                case 1: newRow += 2; break; // Down
                case 2: newCol -= 2; break; // Left
                case 3: newCol += 2; break; // Right
            }

            if (isValid(newRow, newCol) && maze[newRow][newCol] == 1) {
                maze[(row + newRow) / 2][(col + newCol) / 2] = 0; // Create a path between cells
                generateMazeRecursive(newRow, newCol);
            }
        }
    }

    private int[] generateRandomDirections() {
        int[] directions = {0, 1, 2, 3};
        Random random = new Random();
        for (int i = 0; i < directions.length; i++) {
            int j = random.nextInt(directions.length);
            int tmp = directions[i];
            directions[i] = directions[j];
            directions[j] = tmp;
        }
        return directions;
    }

    private boolean isValid(int row, int col) {
        return row > 0 && row < rows - 1 && col > 0 && col < cols - 1;
    }

    private void resetVisited() {
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                visited[i][j] = false;
            }
        }
    }

    public boolean solveMaze() {
        resetVisited();
        return solveMazeRecursive(startRow, startCol);
    }

    private boolean solveMazeRecursive(int row, int col) {
        if (!isValid(row, col) || maze[row][col] == 1 || visited[row][col]) {
            return false;
        }

        visited[row][col] = true;

        if (row == endRow && col == endCol) {
            maze[row][col] = 2; // Path to the exit
            return true;
        }

        // Try moving in 4 directions
        if (solveMazeRecursive(row - 1, col) || solveMazeRecursive(row + 1, col) ||
                solveMazeRecursive(row, col - 1) || solveMazeRecursive(row, col + 1)) {
            maze[row][col] = 2; // Path to the exit
            return true;
        }

        return false;
    }

    public void displayMaze() {
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                if (i == startRow && j == startCol) {
                    System.out.print("S"); // Start
                } else if (i == endRow && j == endCol) {
                    System.out.print("E"); // End
                } else if (maze[i][j] == 1) {
                    System.out.print("#"); // Wall
                } else if (maze[i][j] == 2) {
                    System.out.print("."); // Path
                } else {
                    System.out.print(" "); // Empty space
                }
            }
            System.out.println();
        }
    }

    public static void main(String[] args) {
        int rows = 21;
        int cols = 21;
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
import java.awt.Color;

/**
 * Represents the game board consisting of a grid of colors.
 * Provides functionality to manage and reset the board state.
 */
public class Board {
    final static int col = 10;
    final static int row = 20;
    private Color[][] grid = new Color[row][col];

    /**
     * Resets the entire grid by filling every cell with the default black color.
     */
    public void initGrid(){
        for(int r = 0; r < row; r++){
            for (int c = 0; c < col; c++){
                grid[r][c] = Color.BLACK;
            }
        }
    }
}
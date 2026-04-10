import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import javax.swing.JPanel;

/**
 * Represents the game board consisting of a grid of colors.
 * Provides functionality to manage and reset the board state.
 */
public class Board extends JPanel{
    final static int col = 10;
    final static int row = 20;
    private Color[][] grid = new Color[row][col];
    final static int width = 300;
    final static int height = 600;
    final static int cellRadius = 30;

    /**
   * Constructs a new Board and initializes the preferred size and background color.
   */
    public Board() {
        this.setPreferredSize(new Dimension(width, height));
        this.setBackground(Color.BLACK);
        this.setLayout(null);

        initGrid();
    }
    
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

    /**
   * Paints the board component and its contents.
   *
   * @param g the {@code Graphics} context used for drawing
   */
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        drawBoard(g);
        //.<----  function for drawing active piece goes here
    }

    /**
   * Draws the background grid and inactive pieces in a single pass.
   *
   * <p>Each cell is filled with its stored color and then outlined with a gray border.
   *
   * @param g the {@code Graphics} context used for drawing
   */
    public void drawBoard(Graphics g){
        g.setColor(Color.GRAY);
        for(int r = 0; r < row; r++) {
            for (int c = 0; c < col; c++){
                g.setColor(grid[r][c]);
                g.fillRect(c* cellRadius, r * cellRadius, cellRadius, cellRadius);

                g.setColor(Color.gray);
                g.drawRect(c*cellRadius, r*cellRadius, cellRadius, cellRadius);
            }
        }
    }
}
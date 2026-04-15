import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JPanel;
import javax.swing.Timer;

/**
 * Represents the game board consisting of a grid of colors.
 * Provides functionality to manage and reset the board state.
 */
public class Board extends JPanel implements ActionListener{
    final static int col = 10;
    final static int row = 20;
    private Color[][] grid = new Color[row][col];
    final static int width = 300;
    final static int height = 600;
    final static int cellRadius = 30;
    final static int delay = 500;
    private Timer timer;
    private Shapes activePiece;

    /**
   * Constructs a new Board and initializes the preferred size and background color.
   */
    public Board() {
        this.setPreferredSize(new Dimension(width, height));
        this.setBackground(Color.BLACK);
        this.setLayout(null);

        initGrid();

        this.timer = new Timer(delay, this);
    }

    /**
     * Starts the game loop.
     */
    public void startGame(){
        spawnNewPiece();
        timer.start();
    }

    /**
   * Invoked every time the timer ticks.
   */
    @Override
    public void actionPerformed(ActionEvent e){
        //movePieceDown();  <-- call for function that moves piece down
        repaint();
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
       // drawActivePiece(g); Behöver ha funktion för att hämta piece i shape annars kan denna funk inte köras
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

    /**
   * Draws the active tetromino by mapping its block offsets to the grid.
   *
   * <p>Calculates each block's position by adding its offset to the anchor
   * coordinates and rendering the result in pixel space via {@code cellRadius}.
   *
   * @param g the {@code Graphics} context used for drawing
   */
    public void drawActivePiece(Graphics g){
        int anchor[] = activePiece.getAnchor();
        int x = anchor[0];
        int y = anchor[1];
        int blocks[][] = activePiece.getBlocks();

        g.setColor(Color.blue);//ska ändras till shapes specifika färg.
        for(int[] block : blocks){
            int drawX = (x + block[0]) * cellRadius;
            int drawY = (y + block[1]) * cellRadius;

            g.fillRect(drawX, drawY, cellRadius, cellRadius);

            g.setColor(Color.GRAY);
            g.drawRect(drawX, drawY, cellRadius, cellRadius);
            g.setColor(Color.CYAN);
        }
    }

    public void spawnNewPiece(){
        //saknar bag funktion i shapes
    }
}
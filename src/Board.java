import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import javax.swing.JPanel;
import javax.swing.Timer;

/**
 * Represents the game board consisting of a grid of colors.
 * Provides functionality to manage and reset the board state.
 */
public class Board extends JPanel implements ActionListener, KeyListener{
    private List<Shapes.TetrominoType> bag = new ArrayList<Shapes.TetrominoType>();
    final static int col = 10;
    final static int row = 20;
    private Color[][] grid = new Color[row][col];
    final static int width = 300;
    final static int height = 600;
    final static int cellRadius = 30;
    final static int tickDelay = 500;
    final static int lockDelay = 500;
    private Timer tickTimer, lockTimer;
    private Shapes activePiece;
    private boolean isGameRunning;
    private int moveCounter = 0;
    private int lowestLevel = 0;
    
    /**
   * Constructs a new Board and initializes the preferred size and background color.
   */
    public Board() {
        this.setPreferredSize(new Dimension(width, height));
        this.setBackground(Color.BLACK);
        this.setLayout(null);

        initGrid();

        this.lockTimer = new Timer(lockDelay, e -> {
            lockActivePiece();
        });
        this.lockTimer.setRepeats(false);
        this.tickTimer = new Timer(tickDelay, this);
        this.addKeyListener(this);
        this.setFocusable(true);
    }

    /**
     * Starts the game loop.
     */
    public void startGame(){
        isGameRunning = true;
        spawnNewPiece();
        tickTimer.start();
    }

    /**
   * Invoked every time the timer ticks.
   */
    @Override
    public void actionPerformed(ActionEvent e){
        int[] anchor = activePiece.getAnchor();
        anchor[1]++;
        if (isValidMove(anchor)){
            activePiece.moveDown();
            updateLowest();
            
            if(lockTimer.isRunning()){
                lockTimer.stop();
            }  
        } else{
            if(!lockTimer.isRunning()){
                lockTimer.start();
            }
        }
        repaint();
    }

    /**
    * Invoked every time a key is pressed.
    */
    @Override
    public void keyPressed(KeyEvent e) {
        int key = e.getKeyCode();

        if (key == KeyEvent.VK_LEFT || key == KeyEvent.VK_RIGHT || key == KeyEvent.VK_DOWN) { // Movement key is pressed
            movement(key);
        } else if (key == KeyEvent.VK_SPACE) { // Hard drop is pressed
            for (int i = 0; i < 20; i++) {
                movement(KeyEvent.VK_DOWN);
            }         
            lockActivePiece();
        } else if (key == KeyEvent.VK_UP || key == KeyEvent.VK_Z) { // Rotation key is pressed
            rotation(key);
        } else if (key == KeyEvent.VK_C) { // Hold key pressed 
            // Hold function here 
        } else { // Any other key on the keyboard, do nothing 
            return; 
        }
        
        repaint();
    }
    
    @Override
    public void keyReleased(KeyEvent e) {} // Ignored

    @Override
    public void keyTyped(KeyEvent e){} // Ignored

    /**
    * Moves a tetromino left, right or down.
    * @param key the key the user has pressed.
    */
    private void movement(int key) {
        int[] anchor = activePiece.getAnchor();

        if (key == KeyEvent.VK_LEFT) {
            anchor[0]--;
            if (isValidMove(anchor)) {
                activePiece.moveLeft();
                if(lockTimer.isRunning() && incrementMoveCounter()){
                    lockTimer.restart();
                }
            }
        } else if (key == KeyEvent.VK_RIGHT) {
            anchor[0]++;
            if (isValidMove(anchor)) {
                activePiece.moveRight();
                if(lockTimer.isRunning() && incrementMoveCounter()){
                    lockTimer.restart();
                }
            }
        } else if (key == KeyEvent.VK_DOWN) {
            anchor[1]++;
            if (isValidMove(anchor)) {
                activePiece.moveDown();
                updateLowest();
            }
        }
    }

    /**
     * Handles clockwise and counterclockwise rotation
     * @param key
     */
    private void rotation(int key) {
        boolean success = false;

        int rotationFrom = activePiece.getRotation();
        int[] anchor = activePiece.getAnchor();
            
        if (key == KeyEvent.VK_UP){
            activePiece.rotateRight();
        } else {
            activePiece.rotateLeft();
        }

        int rotationTo = activePiece.getRotation();
        int[][] rotationPoints = activePiece.getRotationPoints(rotationFrom, rotationTo);

        
        // Super rotation system kick tests
        for (int[] points : rotationPoints) {
            int[] anchor2 = {anchor[0] + points[0], anchor[1] + points[1]};
            if (isValidMove(anchor2)) {
                activePiece.setAnchor(points[0] + anchor[0], points[1] + anchor[1]);
                success = true;
                if(lockTimer.isRunning() && incrementMoveCounter()){
                    lockTimer.restart();
                }
                break;
            }
        }
    
        // Undo rotation if failed
        if (success == false) {
            if (((rotationFrom + 1) % 4) == rotationTo) {
                activePiece.rotateLeft();
            } else {
                activePiece.rotateRight();
            }
        }
    }

    /**
     * Checks if every block of the active tetromino is in a valid position after movement/rotation.
     * @param anchor the new anchor position of activePiece being tested
     * @return true if move is valid; false otherwise
     */
    private boolean isValidMove(int[] anchor) {
        int x1 = anchor[0];
        int y1 = anchor[1];

        for (int[] block: activePiece.getBlocks()) {
            int x2 = x1 + block[0];
            int y2 = y1 + block[1];
            if (x2 < 0 || x2 > 9) {
                return false;
            } else if (y2 > 19) {
                return false;
            } else if (grid[y2][x2] != Color.BLACK) {
                return false;
            }
        }

        return true;
    }

    /**
     * Updates the lowest y-value reached by the activePiece.
     * Resets moveCounter if activePiece goes to a new lowest y-value.
     */
    private void updateLowest() {
        int lowest = 0;
        int height = activePiece.getY();

        for (int[] block: activePiece.getBlocks()) {
            if (lowest < (height + block[1])) {
                lowest = height + block[1];
            }
        }

        if (lowest > lowestLevel) {
            lowestLevel = lowest;
            moveCounter = 0;
        }
    }

    /**
     * Helps to increment moveCounter.
     * @return true if activePiece should lock
     */
    // Maybe remade to void and get rid of bool because hard drop is harder to make with this?
    private boolean incrementMoveCounter() {
        moveCounter++;
        return moveCounter < 15;
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

        if(isGameRunning){
            drawActivePiece(g);
        }
        else{
            //draw start menu.
        }
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

        //ska ändras till shapes specifika färg.
        for(int[] block : blocks){
            int drawX = (x + block[0]) * cellRadius;
            int drawY = (y + block[1]) * cellRadius;

            g.setColor(Color.blue);
            g.fillRect(drawX, drawY, cellRadius, cellRadius);
            
            g.setColor(Color.GRAY);
            g.drawRect(drawX, drawY, cellRadius, cellRadius);
        }
    }
    
    /**
     * Refills the bag with one of each tetromino type and then shuffles it
     */
    public void refillBag(){
        for (Shapes.TetrominoType i : Shapes.TetrominoType.values()){
            bag.add(i);
        }
        Collections.shuffle(bag);
    }

    /**
     * First fills the grab bag if it is empty. Then it removes the first element in the list
     * and assigns the activePiece that tetromino type and gives starting coordinates.
     */
    public void spawnNewPiece(){
        if (bag.isEmpty()){
            refillBag();
        }
        Shapes.TetrominoType nextType = bag.remove(0);
        activePiece = new Shapes(nextType, 4, 1);
        lowestLevel = activePiece.getY();
    }

    /**
     * Locks the active tetromino into the grid and resets game state for the next piece.
     * Stops the lock timer, updates the grid with the piece's color, and spawns a new piece.
     */
    public void lockActivePiece(){
        lockTimer.stop();

        int anchor[] = activePiece.getAnchor();
        int blocks[][] = activePiece.getBlocks();
        //get the specfic color of tetrominoe here
        for(int[] block : blocks){
            int gridX = anchor[0] + block[0];
            int gridY = anchor[1] + block[1];

            grid[gridY][gridX] = Color.BLUE;
        }

        moveCounter = 0;
        lowestLevel = 0;
        clearLine();
        spawnNewPiece();
    }

    /**
     * Scans the grid for full lines, clears them by shifting upper rows down, 
     * and clears the top row to prevent duplication.
     */
    public void clearLine(){
        int linesFound = 0;
        for (int r = row - 1; r >= 0; r--){
            boolean isFull = true;

            //checks the line for black, if no black rest of loop plays
            for (int c = 0; c < col; c++){
                if(grid[r][c] == Color.BLACK){
                    isFull = false;
                    break;
                }
            }

            //if no black square is found then all rows will be moved one place down until current row
            if(isFull){
                linesFound++;
                for (int shiftRow = r; shiftRow > 0; shiftRow--) {
                    for (int c = 0; c < col; c++) {
                        grid[shiftRow][c] = grid[shiftRow - 1][c];
                    }
                }

                    //incase of immediately clearing line when piece spawns.
                for (int c = 0; c < col; c++) {
                    grid[0][c] = Color.BLACK;
                }
                //checks the same row after other rows have been shifted down and if 4 rows have been cleared then it stops.
                r++;
                if(linesFound == 4){
                    break;
                }
            }
            
        }
    }
}
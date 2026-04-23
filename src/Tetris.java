import javax.swing.JFrame;

/**
 * <p>This class initializes the main game window and adds the game board to the frame.
 */
public class Tetris {

  /**
   * Configures the main application window and starts the game.
   *
   * @param args command-line arguments (not used)
   */
  public static void main(String[] args) {
    JFrame window = new JFrame();
    window.setResizable(false);

    Board board = new Board();
    window.add(board);
    window.pack();

    window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    window.setLocationRelativeTo(null);
    window.setVisible(true);
  }
}
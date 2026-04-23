import java.awt.Color;

/**
 * This class holds all of the necessary information to make instances of the tetrominos from tetris
 * as well as some helper functions, getters and setters.
 */

public class Shapes {
    // Every piece has to be at least one of these types.
    public enum TetrominoType {
        O(Color.YELLOW),
        I(Color.CYAN),
        T(Color.MAGENTA),
        L(Color.ORANGE),
        J(Color.BLUE),
        S(Color.GREEN),
        Z(Color.RED);

        private final Color color;

        TetrominoType(Color color) {
            this.color = color;
        }

        public Color getColor() {
            return color;
        }
    }

    private TetrominoType type;

    // coordinates for anchor
    private int x;
    private int y;

    private int rotation; // id for blocks array, 0,1,2,3
    private int[][][] blocks; // contains offsets for every block of the piece from the anchor, for every rotation.

    public Shapes(TetrominoType type, int x, int y) {
        this.type = type;
        this.x = x;
        this.y = y;
        this.rotation = 0;

        this.blocks = switch(type) {
            case O -> O_SHAPE;
            case I -> I_SHAPE;
            case T -> T_SHAPE;
            case L -> L_SHAPE;
            case J -> J_SHAPE;
            case S -> S_SHAPE;
            case Z -> Z_SHAPE;
        };
    }

    // Getters & setters
    public TetrominoType getType() {
        return this.type;
    }

    public Color getColor() {
        return this.type.getColor();
    }
    
    public int[][] getBlocks() {
        return this.blocks[this.rotation]; // get the 4 blocks of the piece currently.
    }

    /**
    * Returns the constant array of rotation points, used for figuring out if a rotation is possible or not.
    * @param from The current rotation index (0-3)
    * @param to The target rotation index being attempted (0-3)
    * @return The array of test offsets for the rotation, a 2d array
    */
    public int[][] getRotationPoints(int from, int to) {
        return switch (this.type) {
            case I -> I_ROTATION_POINTS[from][to];
            default -> TLJSZ_ROTATION_POINTS[from][to];
        };
    }

    public int getRotation() {
        return this.rotation;
    }

    public int[] getAnchor() {
        return new int[] {this.x,this.y}; // no tuples in java
    }

    public int getX() {
        return this.x;
    }

    public int getY() {
        return this.y;
    }

    public void setAnchor(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }

    /**
     * rotateLeft() and rotateRight() use modulo for rotations because rotating 90 degrees 4 times goes back to default position
     * rotateLeft() does + 3 instead of - 1 to avoid negative modulo interactions
     */
    public void rotateLeft() {
        this.rotation = (this.rotation + 3) % 4;
    }

    public void rotateRight() {
        this.rotation = (this.rotation + 1) % 4;
    }

    public void moveLeft() {
        this.x -= 1;
    }

    public void moveRight() {
        this.x += 1;
    }

    public void moveDown() {
        this.y += 1;
    }

    // All of these constants contain the offset coordinates of all of the blocks of a piece based on it's anchor piece coordinate
    private static final int[][][] O_SHAPE = {
        {{0,0}, {0,1}, {1,0}, {1,1}},
        {{0,0}, {0,1}, {1,0}, {1,1}},
        {{0,0}, {0,1}, {1,0}, {1,1}},
        {{0,0}, {0,1}, {1,0}, {1,1}},
    };

    private static final int[][][] I_SHAPE = {
    
        {{-1,0}, {0,0}, {1,0}, {2,0}},
        {{1,-1}, {1,0}, {1,1}, {1,2}},
        {{-1,1}, {0,1}, {1,1}, {2,1}},
        {{0,-1}, {0,0}, {0,1}, {0,2}},
        
    };

    private static final int[][][] T_SHAPE = {
        {{-1,0}, {0,-1}, {0,0}, {1,0}},
        {{0,-1}, {0,0}, {0,1}, {1,0}},
        {{-1,0}, {0,0}, {1,0}, {0,1}},
        {{-1,0}, {0,-1}, {0,0}, {0,1}},
    };

    private static final int[][][] L_SHAPE = {
        {{-1,0}, {0,0}, {1,-1}, {1,0}},
        {{0,-1}, {0,0}, {0,1}, {1,1}},
        {{-1,0}, {-1,1}, {0,0}, {1,0}},
        {{-1,-1}, {0,-1}, {0,0}, {0,1}},
    };

    private static final int[][][] J_SHAPE = {
        {{-1,-1}, {-1,0}, {0,0}, {1,0}},
        {{0,-1}, {0,0}, {0,1}, {1,-1}},
        {{-1,0}, {0,0}, {1,0}, {1,1}},
        {{-1,1}, {0,-1}, {0,0}, {0,1}},
    };

    private static final int[][][] S_SHAPE = {
        {{-1,0}, {0,-1}, {0,0}, {1,-1}},
        {{0,-1}, {0,0}, {1,0}, {1,1}},
        {{-1,1}, {0,0}, {0,1}, {1,0}},
        {{-1,-1}, {-1,0}, {0,0}, {0,1}},
    };

    private static final int[][][] Z_SHAPE = {
        {{-1,-1}, {0,-1}, {0,0}, {1,0}},
        {{0,0}, {0,1}, {1,-1}, {1,0}},
        {{-1,0}, {0,0}, {0,1}, {1,1}},
        {{-1,0}, {-1,1}, {0,-1}, {0,0}},
    };

    // These constants contain the test offsets to decide if a rotation is possible or not according to the tetris super rotation system
    // Made 4d for ease of use, making it contain alot of padding, empty arrays
    private static final int[][][][] I_ROTATION_POINTS = {
        // 0
        {   
            {},
            // 0 -> R
            {
                {0,0}, {-2,0}, {1,0}, {-2,-2}, {1,2}
            },
            {},
            // 0 -> L
            {
                {0,0}, {-1,0}, {2,0}, {-1,2}, {2,-2}
            }
        },
        // R
        {
            // R -> 0
            {
                {0,0}, {2,0}, {-1,0}, {2,1}, {-1,-2}
            },
            {},
            // R -> 2
            {
                {0,0}, {-1,0}, {2,0}, {-1,2}, {2,-1}
            },
            {},
        },
        // 2
        {
            {},
            // 2 -> R
            {
                {0,0}, {1,0}, {-2,0}, {1,-2}, {-2,1}
            },
            {},
            // 2 -> L
            {
                {0,0}, {2,0}, {-1,0}, {2,1}, {-1,-2}
            },
        },
        // L
        {
            // L -> 0
            {
                {0,0}, {1,0}, {-2,0}, {1,-2}, {-2,1}
            },
            {},
            // L -> 2
            {
                {0,0}, {-2,0}, {1,0}, {-2,-1}, {1,2}
            },
            {},
        }
    };

    private static final int[][][][] TLJSZ_ROTATION_POINTS = {
        // 0
        {   
            {},
            // 0 -> R
            {
                {0,0}, {-1,0}, {-1,1}, {0,-2}, {-1,-2}
            },
            {},
            // 0 -> L
            {
                {0,0}, {1,0}, {1,1}, {0,-2}, {1,-2}
            }
        },
        // R
        {
            // R -> 0
            {
                {0,0}, {1,0}, {1,-1}, {0,2}, {1,2}
            },
            {},
            // R -> 2
            {
                {0,0}, {1,0}, {1,-1}, {0,2}, {1,2}
            },
            {},
        },
        // 2
        {
            {},
            // 2 -> R
            {
                {0,0}, {-1,0}, {-1,1}, {0,-2}, {-1,-2}
            },
            {},
            // 2 -> L
            {
                {0,0}, {1,0}, {1,1}, {0,-2}, {1,-2}
            },
        },
        // L
        {
            // L -> 0
            {
                {0,0}, {-1,0}, {-1,-1}, {0,2}, {-1,2}
            },
            {},
            // L -> 2
            {
                {0,0}, {-1,0}, {-1,-1}, {0,2}, {-1,2}
            },
            {},
        }
    };
}
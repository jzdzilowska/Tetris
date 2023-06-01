package tetris;

import javafx.scene.paint.Color;

public class Constants {

    // width of each square
    public static final int SQUARE_WIDTH = 30;
    public static final int SCENE_WIDTH = 360;
    public static final int SCENE_HEIGHT = 700;
    // coordinates for squares in each tetris piece
    public static final int[][] O_PIECE_COORDS = {{0, 0}, {SQUARE_WIDTH, SQUARE_WIDTH}, {0, SQUARE_WIDTH}, {SQUARE_WIDTH, 0}};
    public static final int[][] S_PIECE_COORDS = {{0, SQUARE_WIDTH}, {SQUARE_WIDTH, SQUARE_WIDTH}, {SQUARE_WIDTH, 0}, {2* SQUARE_WIDTH, 0}};
    public static final int[][] Z_PIECE_COORDS = {{0, 0}, {SQUARE_WIDTH, 0}, {SQUARE_WIDTH, SQUARE_WIDTH}, {2 * SQUARE_WIDTH, SQUARE_WIDTH}};
    public static final int[][] L_PIECE_COORDS = {{0, 0}, {0, SQUARE_WIDTH}, {0, 2 * SQUARE_WIDTH}, {SQUARE_WIDTH, 2 * SQUARE_WIDTH}};
    public static final int[][] J_PIECE_COORDS = {{SQUARE_WIDTH, 0}, {SQUARE_WIDTH, SQUARE_WIDTH}, {SQUARE_WIDTH, 2 * SQUARE_WIDTH}, {0, 2 * SQUARE_WIDTH}};
    public static final int[][] I_PIECE_COORDS = {{0, 0}, {0, SQUARE_WIDTH}, {0, 2 * SQUARE_WIDTH}, {0, 3 * SQUARE_WIDTH}};
    public static final int[][] T_PIECE_COORDS = {{0, 0}, {0, SQUARE_WIDTH}, {0, 2 * SQUARE_WIDTH}, {SQUARE_WIDTH, SQUARE_WIDTH}};

    public static final Color O_PIECE_COLOR = Color.LIGHTPINK;
    public static final Color S_PIECE_COLOR = Color.LIGHTBLUE;
    public static final Color Z_PIECE_COLOR = Color.VIOLET;
    public static final Color L_PIECE_COLOR = Color.SKYBLUE;
    public static final Color J_PIECE_COLOR = Color.LIGHTSKYBLUE;
    public static final Color I_PIECE_COLOR = Color.GREEN;
    public static final Color T_PIECE_COLOR = Color.DEEPPINK;

}

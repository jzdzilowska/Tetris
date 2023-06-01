package tetris;

import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * Please include your tests for your program in this class.
 *
 * Remember that you can run your tests by clicking the play button to the left of each testing method,
 * or you can run all tests at once by clicking the play button at the top of the class.
 *
 * Go over lab7 or the Design Patterns 2 lecture for a review on testing!
 */
public class TetrisTests {

    /**
     * Here's an example of a basic test that tests addition
     */
    @Test
    public void testAddition() {
        assertTrue(1 + 1 == 2);
    }

    /**
     * TODO: Write a test that tests the properties of your board here
     */
    @Test
    public void testBoard(){
        // TODO: write a test here to a test that tests your board
        Pane gamePane = new Pane();
        BoardSquare[][] board = new BoardSquare[22][12];
        Game game = new Game(gamePane);
        for (int i = 0; i < 22; i++) {
            for (int j = 0; j < 12; j++) {
                BoardSquare square = new BoardSquare(gamePane, Color.BLACK);
                board[i][j] = square;
                square.addToPane(gamePane);
            }
        }
        assertTrue(!game.isRowFull(board[0]));
    }

    /**
     * TODO: Write a test that tests a wrapper class that isn't your board here
     */
    @Test
    public void testWrapperClass(){
        // TODO: write a test here so that it tests one of your wrapper classes (a piece for example)
        Pane gamePane = new Pane();
        Game game = new Game(gamePane);
        BoardSquare[][] board = new BoardSquare[22][12];
        for (int i = 0; i < 22; i++) {
            for (int j = 0; j < 12; j++) {
                BoardSquare square = new BoardSquare(gamePane, Color.BLACK);
                board[i][j] = square;
                square.addToPane(gamePane);
            }
        }

        Piece piece = new Piece(game, gamePane, Constants.I_PIECE_COORDS, Constants.I_PIECE_COLOR, board);
        piece.move(1,1);
        assertTrue(piece.canItMove(1,0));
    }
}

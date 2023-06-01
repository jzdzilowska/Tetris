package tetris;

import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

/**
 * The BoardSquare class is a wrapper class of Java's Rectangle - it represents a singular
 * square that can be later added to the game as a component of the board (the 2D Array),
 * or of a piece (comprised of four BoardSquares).
 * It is contained both by the Piece class (where the 1D Array is created) and by the
 * Game class (2D Array - board, no null spaces, filled ), and is associated with the gamePane.
 *
 */
public class BoardSquare {
    private Rectangle gameSquare;
    private Pane gamePane;
    private Color color;

    /**
     * In the constructor, an association is set up with the gamePane (child node of
     * the root BorderPane), and one gameSquare of a specified color (passed in as a
     * parameter) is created.
     */
    public BoardSquare(Pane gamePane, Color color) {
        this.gamePane = gamePane;
        this.createGameSquare(color);
    }

    /**
     * In the method below, a gameSquare (Java's Rectangle that will be either a component of a piece among three other
     * BoardSquares, or of the board which is made entirely of black / grey BoardSquares) is created.
     * The instance variable is assigned to a color passed in as a parameter so that an appropriate method
     * can return or set its value when prompted by the Piece class, and the newly created rectangle is filled with such
     * color.
     */
    public void createGameSquare(Color color) {
        this.color = color;
        this.gameSquare = new Rectangle(Constants.SQUARE_WIDTH, Constants.SQUARE_WIDTH);
        this.gameSquare.setFill(this.color);
        this.gameSquare.setStroke(Color.BLACK);
    }

    /**
     * Methods below set either the X or Y Position of the square. This will be crucial for generating
     * the board and its border, moving the falling piece on the screen, or rotating it.
     */
    public void setXPos(double xPos) {
        this.gameSquare.setX(xPos);
    }

    public void setYPos(double yPos) {
        this.gameSquare.setY(yPos);
    }

    /**
     * The accessor methods below change either the X or Y Position of the square. This method will allow
     * to, for example, change the color of BoardSquares representing the board
     * to the shade of a falling piece by accessing its position (thus the position of the squares
     * constituting its components), or to move a piece once it's created.
     */
    public double getXPos() {
        return this.gameSquare.getX();
    }

    public double getYPos() {
        return this.gameSquare.getY();
    }

    /**
     * This method changes the color of a particular square - used f.e. by the row clearing method,
     * where rather than the location of squares above a filled row being set to one column lower,
     * the color of appropriate squares within these rows is set to the color of squares in rows
     * above them (creating the illusion of rows being moved down).
     */
    public void setColor(Color color) {
        this.gameSquare.setFill(color);
        this.color = color;
    }

    /**
     * Method below returns the color of a square. Used for f.e. searching for filled rows or for clearing
     * lines.
     */
    public Color getColor() {
        return this.color;
    }

    /**
     * This boolean method is called within the Game's "isRowFull" method and the "hasGameEnded" method.
     * Depending on whether the square on which the method is called is black or not (based on the
     * instance variable), it returns an appropriate value.
     */
    public boolean isBlack() {
        if (this.color == Color.BLACK) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * Methods below respectively add or remove a square from the main gamePane - addToPane is used when the
     * board is generated, while removeFromPane is called once a piece stops moving after it collides with an already
     * fallen one.
     */
    public void addToPane(Pane gamePane) {
        gamePane.getChildren().addAll(this.gameSquare);
    }

    public void removeFromPane(Pane gamePane) {
        gamePane.getChildren().removeAll(this.gameSquare);
    }
}


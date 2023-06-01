package tetris;

import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;

/**
 * This Piece class handles the appearance and movement of the Piece object that can be controlled by the user.
 * It is contained by the Game class and is associated with it, along with the gamePane (so that
 * it can f.e. add itself to it). It has 2D array of coordinates and the 2D boardArray created
 * in the Game class passed in as a parameter, so that once it's "added" to the board, it represents an
 * appropriate tetris piece. It also takes in an appropriate color as a parameter, that is
 * predefined for each particular piece.
 */
public class Piece {
    private BoardSquare[] squareArray;
    private BoardSquare[][] boardArray2D;
    private Pane gamePane;
    private int[][] coords;
    private Game game;
    private Color color;

    /**
     * Constructor below sets up the association by initializing instance variables, as well as
     * creates a 1D array of BoardSquares, thus creating a piece comprised of four squares
     * which location in relation to each other on the board is determined by the
     * 2D array of coordinates determined by the passed in parameter. Then, it calls the helper
     * method responsible for generating squares ("filling" each index of the 1D array - one
     * that was previously null - with a square.
     */
    public Piece(Game game, Pane gamePane, int[][] coordinates, Color color, BoardSquare[][] boardArray2D) {
        this.game = game;
        this.gamePane = gamePane;
        this.coords = coordinates;
        this.color = color;
        this.boardArray2D = boardArray2D;
        this.squareArray = new BoardSquare[4];
        this.generateSquares(this.color);
    }

    /**
     * Method below accesses all indexes of the 1D array and initializes them to be BoardSquares
     * (of which the piece is comprised). It later adds them to the Pane, and calls the helper method
     * responsible for arranging the squares depending on their coordinates.
     */
    public void generateSquares(Color color) {
        for (int i = 0; i < 4; i++) {
            this.squareArray[i] = new BoardSquare(this.gamePane, color);
            this.squareArray[i].addToPane(this.gamePane);
        }
        this.arrangeSquares();
    }

    /**
     * Method below, similarly to the previous one, accesses all elements of the 1D array
     * and sets their location on the board to one that utilizes square's appropriate coordinates.
     * It sets the XPosition to a location in the arbitrary middle of the board (board comprised of 12 rows -
     * if a piece has two squares on top, the location of the top-left square's corner should be the 6th row;
     * right corner of the 5th row). The YPosition is set similarly; the top side of an element should be the
     * top side of the first row - in pixels, the width of a square.
     */
    public void arrangeSquares() {
        for (int i = 0; i < this.squareArray.length; i++) {
            this.squareArray[i].setXPos(5 * Constants.SQUARE_WIDTH + this.coords[i][1]);
            this.squareArray[i].setYPos(Constants.SQUARE_WIDTH + this.coords[i][0]);
        }
    }

    /**
     * Method below accesses every BoardSquare of which the 2D array is composed of and changes its color
     * to one determined by the piece that has fallen (method called once the piece intersects with another one).
     * The element of the array that's supposed to be changed is dependent of the location of an appropriate
     * square of which the piece that has fallen is made out of (f.e. the row index is the Y position of a square
     * divided by 30 - the width of the squares that the board is composed of). It later removes these squares
     * (that make a piece) from the main gamePane.
     */
    public void changeColor(Color color) {
        for (BoardSquare square : squareArray) {
            boardArray2D[(int) (square.getYPos() / Constants.SQUARE_WIDTH)]
                    [(int) (square.getXPos() / Constants.SQUARE_WIDTH)].setColor(color);
            square.removeFromPane(this.gamePane);
        }
    }

    /**
     * Method below returns the piece's color - useful for checking whether an element of a board is
     * "empty" - black, part of the background - or not (a piece that has fallen).
     */
    public Color getColor() {
        return this.color;
    }

    /**
     * fallDown method below is called whenever a new piece is created, and with the end of
     * each KeyFrame. It checks whether the piece can move (if the location in the same column, one row below
     * is "empty" (black)), or not (same column, one row below is colorful; represents
     * a piece that has already fallen), and if yes, calls the move method that actually changes the location
     * of a piece to same column, one row below. Although the canItMove method is already called in the move
     * method, not doing so initially in this instance produced bugs.
     */
    public void fallDown() {
        if (this.canItMove(1, 0)) {
            this.move(1, 0);
        }
    }

    /**
     * The move method below actually changes the location of a piece. It takes in the change in rows and
     * columns that's supposed to be made as a parameter. Firstly, it checks whether the game is paused;
     * if not, it checks whether a piece can move, and if yes, loops through the array of squares of
     * which the piece is made of, and sets their position to be 30 pixels (row/columnChange * square width)
     * lower, to the right, or to the left.
     */
    public void move(int rowChange, int columnChange) {
        if (!this.game.isItPaused()) {
            if (this.canItMove(rowChange, columnChange)) {
                for (int i = 0; i < this.squareArray.length; i++) {
                    this.squareArray[i].setXPos(this.squareArray[i].getXPos() + (columnChange * Constants.SQUARE_WIDTH));
                    this.squareArray[i].setYPos(this.squareArray[i].getYPos() + (rowChange * Constants.SQUARE_WIDTH));
                }
            }
        }
    }

    /**
     * This method returns a boolean depending on whether a piece can move or not. It does so by
     * looping through the elements of the 2D array of BoardSquares that are in a location to which the piece
     * is supposed to be moved depending on the parameter, and checking whether they're "empty" (black - then
     * it can move), or not (colorful - they represent a piece that has already fallen).
     */
    public boolean canItMove(int rowChange, int columnChange) {
        for (int i = 0; i < 4; i++) {
            if (boardArray2D[(int) (squareArray[i].getYPos() / Constants.SQUARE_WIDTH) + rowChange][(int) (squareArray[i].getXPos() / Constants.SQUARE_WIDTH) + columnChange].getColor() != Color.BLACK) {
                return false;
            }
        }
        return true;
    }

    /**
     * This method is responsible for rotating a particular piece depending on its initial location
     * and a predefined center of rotation that's *usually* (depending on the array of coordinates from the
     * constants class) the top-left square of a piece. It then sets that piece's location to that newly
     * calculated location, unless that piece is a square (lightpink color).
     */
    public void rotate() {
        if (this.color != Constants.O_PIECE_COLOR) {
            double centerOfRotationX = this.squareArray[0].getXPos();
            double centerOfRotationY = this.squareArray[0].getYPos();

            if (this.canItRotate()) {
                for (int i = 0; i < this.squareArray.length; i++) {
                    double oldXLocation = this.squareArray[i].getXPos();
                    double oldYLocation = this.squareArray[i].getYPos();

                    double newXLocation = centerOfRotationX - centerOfRotationY + oldYLocation;
                    double newYLocation = centerOfRotationY + centerOfRotationX - oldXLocation;

                    this.squareArray[i].setXPos(newXLocation);
                    this.squareArray[i].setYPos(newYLocation);
                }
            }
        }
    }

    /**
     * This method checks whether a piece can rotate. Similarly to the previous method, it calculates
     * a new location (I've decided to refrain from creating a helper method, as it would be called only
     * twice and wouldn't make the code any clearer), and if that new location exceeds the bounds of the board,
     * or it if collides with any squares that are filled with color (represent pieces that have already fallen)
     * returns false.
     */
    public boolean canItRotate() {
        double centerOfRotationX = this.squareArray[0].getXPos();
        double centerOfRotationY = this.squareArray[0].getYPos();

        for (int i = 0; i < this.squareArray.length; i++) {
            double oldXLocation = this.squareArray[i].getXPos();
            double oldYLocation = this.squareArray[i].getYPos();

            double newXLocation = centerOfRotationX - centerOfRotationY + oldYLocation;
            double newYLocation = centerOfRotationY + centerOfRotationX - oldXLocation;

            if ((newXLocation <= 0)
                    || (newXLocation >= 11 * Constants.SQUARE_WIDTH)
                    || (newYLocation <= (0))
                    || (newYLocation >= 21 * Constants.SQUARE_WIDTH)) {
                return false;
            } else if (!this.checkIfEmpty(newYLocation, newXLocation)) {
                return false;
            }
        }
        return true;
    }

    /**
     * Method below checks whether a particular element of an 2D board array (calculated by dividing the exact
     * x- and y- coordinates passed in as parameters by the width of all board's squares - thus returning
     * rows and columns) is "empty" (black) or not.
     */
    public boolean checkIfEmpty(double y, double x) {
        if (boardArray2D[(int) (y / Constants.SQUARE_WIDTH)][(int) (x / Constants.SQUARE_WIDTH)].getColor() != Color.BLACK) {
            return false;
        } return true;
    }

}
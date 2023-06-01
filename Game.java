package tetris;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.util.Duration;

/**
 * This top-level logic class handles the user interaction and overall
 * logic behind the Tetris game. The class is contained by the PaneOrganizer class and
 * is associated with the main gamePane so that it can call methods on it
 * (such as add labels to it), or associate its own components with it (f.e. the
 * pieces). It's responsible for handling the timeline,
 */
public class Game {
    private Pane gamePane;
    private BoardSquare[][] boardArray2D;
    private Timeline timeline;
    private Piece piece;
   private boolean isPaused;
   private HBox labelBox;

    /**
     * In the constructor below, instance variables are initialized (a isPaused variable is set to
     * false since the timeline is running), association is set up with the main gamePane, and
     * a 2D BoardSquare array (created with a row-column major) is instantiated. After that, two
     * helper methods responsible for creating the board are called (explanation as to why
     * there's no separate board class is provided in the readme), and a piece is spawned on
     * the top of the board. Finally, a startGame method is called, responsible for setting up
     * the timeline and handling the visual changes happening on screen over time.
     */
    public Game(Pane gamePane) {
        this.isPaused = false;
        this.gamePane = gamePane;
        this.boardArray2D = new BoardSquare[22][12];

        this.generateBoard();
        this.organizeBoard();

        this.spawnPiece();
        this.startGame();
    }

    /**
     * Method below is responsible for generating a board by looping through the 2D BoardArray
     * and initializing its indexes to be BoardSquares.
     * It later adds them to the pane, after firstly setting their color to be one that depends on their
     * row and column 2D Array index (if it's the first or the last column or row, the color is grey which represents a
     * border - if any other one, it's initially black).
     */
    public void generateBoard() {
        for (int i = 0; i < 22; i++) {
            for (int j = 0; j < 12; j++) {
                Color color = Color.BLACK;
                if ((i == 0) || (i == 21) || (j == 0) || (j == 11)) {
                    color = Color.GREY;
                }
                BoardSquare square = new BoardSquare(this.gamePane, color);
                boardArray2D[i][j] = square;
                square.addToPane(this.gamePane);
            }
        }
    }

    /**
     * Method below arranges the board by setting the location of all the BoardSquares created
     * in the generateBoard method. The location is dependent on the row- and column- index of
     * a particular element that is multiplied by the width of a square (if f.e. a square is in the
     * first row, second column, the coordinates of it's top-left corner would be (10,20).
     */
    public void organizeBoard() {
        for (int i = 0; i < 22; i++) {
            for (int j = 0; j < 12; j++) {
                boardArray2D[i][j].setXPos(j * Constants.SQUARE_WIDTH);
                boardArray2D[i][j].setYPos(i * Constants.SQUARE_WIDTH);
            }
        }
    }

    /**
     * Method below is responsible for setting up a Timeline and a KeyEvent that calls an
     * appropriate method with the end of each KeyFrame. It's called just once from within the Game
     * constructor when the game is first instantiated.
     */
    private void startGame() {
        KeyFrame kf = new KeyFrame(Duration.seconds(0.3),
                (ActionEvent e) -> this.update());
        this.timeline = new Timeline(kf);
        this.timeline.setCycleCount(Animation.INDEFINITE);
        this.timeline.play();
    }

    /**
     * Method below is called with the end of each KeyFrame. Firstly, it checks whether a game has
     * ended, and if not, it prompts the piece to move down. If the piece can no longer move (since it
     * has collided with another, already fallen piece, or the bottom of the screen, according to whether
     * the location to which it's supposed to be moved is black or not), ot changes the colors of the board squares
     * to which it was supposed to be moved to the color of that piece, clears lines (if there are any already
     * filled), and spawns a new piece at the top of the screen.
     */
    public void update() {
        this.hasGameEnded();
        this.piece.fallDown();
        if (!this.piece.canItMove(1, 0)) {
            this.piece.changeColor(this.piece.getColor());
            this.clearLines();
            this.startNewPiece();
        }
    }

    /**
     * This method below returns a new, randomly generated piece each time it's called and assigns
     * it to a previously initialized instance variable (so that no two moving (actual) pieces
     * can exist simultaneously).
     */
    private Piece spawnPiece() {
        int randInt = (int) (Math.random() * 7);
        switch (randInt) {
            case 0:
                this.piece = new Piece(this, this.gamePane, Constants.I_PIECE_COORDS, Constants.I_PIECE_COLOR, this.boardArray2D);
                break;
            case 1:
                this.piece = new Piece(this, this.gamePane, Constants.J_PIECE_COORDS, Constants.J_PIECE_COLOR, this.boardArray2D);
                break;
            case 2:
                this.piece = new Piece(this, this.gamePane, Constants.T_PIECE_COORDS, Constants.T_PIECE_COLOR, this.boardArray2D);
                break;
            case 3:
                this.piece = new Piece(this, this.gamePane, Constants.Z_PIECE_COORDS, Constants.Z_PIECE_COLOR, this.boardArray2D);
                break;
            case 4:
                this.piece = new Piece(this, this.gamePane, Constants.S_PIECE_COORDS, Constants.S_PIECE_COLOR, this.boardArray2D);
                break;
            case 5:
                this.piece = new Piece(this, this.gamePane, Constants.O_PIECE_COORDS, Constants.O_PIECE_COLOR, this.boardArray2D);
                break;
            default:
                this.piece = new Piece(this, this.gamePane, Constants.L_PIECE_COORDS, Constants.L_PIECE_COLOR, this.boardArray2D);
                break;
        }
        return this.piece;
    }

    /**
     * Method below creates a new piece by calling the spawnPiece method, as long as the game hasn't yet ended.
     */
    public void startNewPiece() {
        if (!hasGameEnded()) {
            this.spawnPiece();
        }
    }

    /**
     * Method below is responsible for checking whether a game has ended. It loops through the squares within
     * the second top row (first one is a part of the border), and if any of them aren't black
     * (excluding ones in the first or last column), it stops the timeline, sets up a label informing user of
     * the fact that the game has ended, and returns true.
     */
    public boolean hasGameEnded() {
        for (int i = 1; i < (boardArray2D[1].length - 1); i++) {
            if ((!boardArray2D[1][i].isBlack())) {
                this.timeline.stop();
                this.setupLabel("Game Over!");
                return true;
            }
        }
        return false;
    }

    /**
     * Method below is responsible for setting up a label with a string that's supposed to be shown on screen
     * passed in as a parameter. It creates a new Label and an HBox, adds that Label to the HBox, and
     * whenever called, adds that node to the main gamePane on top of other (currently static) elements.
     */
    private void setupLabel(String s) {
        Label label = new Label(s);
        this.labelBox = new HBox();
        this.labelBox.setAlignment(Pos.CENTER);
        this.labelBox.setPrefWidth(this.gamePane.getWidth());
        this.labelBox.setPrefHeight(this.gamePane.getHeight());
        label.setFont(Font.font("Arial", FontWeight.BOLD, FontPosture.ITALIC, 50));
        label.setTextFill(Color.WHITE);

        this.labelBox.getChildren().add(label);
        this.gamePane.getChildren().add(this.labelBox);
    }

    /**
     * Method below deletes a labelBox. It's used just once, and gets rid of the "Pause" label that appears
     * whenever a game is paused.
     */
    private void deleteLabel() {
        this.gamePane.getChildren().remove(this.labelBox);
    }

    /**
     * This method is called when setting up the KeyEvent in the gamePane,
     * within the constructor of PaneOrganizer. Using a switch statement, the method handles movement
     * of a Piece depending on user's input, moving it by changing its column or row. When
     * the Space bar is pressed, it firstly checks whether a game has been initially paused - only
     * if it hasn't, it checks whether a piece can move to a newly defined location, and if yes,
     * moves the piece (although both if the former methods are included in the Move method,
     * not calling them initially in here causes the game to freeze up). When the P key is pressed,
     * on the other hand, it firstly checks whether the timeline hasn't already been paused, and
     * if not, reassigns the isPaused variable to true, pauses the timeline, and sets up a label.
     * In case the timeline has already been paused, it starts the timeline again, reassigns the isPaused
     * variable, and deletes the previously created label.
     */
    public void onKeyPress(KeyEvent event) {
        KeyCode keyPressed = event.getCode();
        switch (keyPressed) {
            case LEFT:
                this.piece.move(0, -1);
                break;
            case RIGHT:
                this.piece.move(0, 1);
                break;
            case DOWN:
                this.piece.move(1, 0);
                break;
            case SPACE:
                if (!this.isPaused) {
                    while (this.piece.canItMove(1, 0)) {
                        this.piece.move(1, 0);
                    }
                }
                break;
            case P:
                if (this.timeline.getStatus() == Animation.Status.RUNNING) {
                    this.isPaused = true;
                    this.timeline.pause();
                    this.setupLabel("Paused!");
                } else {
                    this.isPaused = false;
                    this.timeline.play();
                    this.deleteLabel();
                }
                break;
            case UP:
                this.piece.rotate();
                break;
            default:
                break;
                    }
        event.consume();
    }

    /**
     * Method below is responsible for clearing filled lines of the board. It firstly loops through each
     * row of the board from top to bottom (except for first, and last row, that represent the border),
     * and checks whether the row is full by looping through the squares within that particular row and checking
     * if they're colorful. If yes, it then loops through all those squares in a row (except for the first and
     * last one that are a part of the border), and sets their color to black so that the row above
     * can be moved down. After that, it loops through all the rows from the row that has been cleared to the top,
     * and for each element within a row sets its color to one of an element in a row above, creating an
     * illusion of rows being deleted.
     */
    public void clearLines() {
        for (int i = 1; i < (boardArray2D.length - 1); i++) {
            if (this.isRowFull(boardArray2D[i])) {
                for (int j = 1; j < (boardArray2D[i].length - 1); j++) {
                    boardArray2D[i][j].setColor(Color.BLACK);
                }
                for (int k = i; k > 1; k--) {
                    for (int l = 1; l < (boardArray2D[k].length - 1); l++) {
                        boardArray2D[k][l].setColor(boardArray2D[k-1][l].getColor());
                    }
                }
            }
        }
    }

    /**
     * Boolean method below is responsible for checking whether a row is full, and is crucial for clearing filled lines.
     * It firstly sets a counter to be 0, and then loops through the elements of each row (except
     * for the first, and last square, that represent the border). If a particular element
     * within a row isn't black (is colorful - which represents a piece that has already fallen), it increments
     * the counter. When the counter is equal to 10 (the number of squares in a row within a board that aren't
     * part of the border), the row is full and method returns true.
     */
    public boolean isRowFull(BoardSquare[] row) {
        int counter = 0;
        for (int i = 1; i < row.length - 1; i++) {
            if (!row[i].isBlack()) {
                counter++;
            }
        }
        if (counter == 10) {
            return true;
        } return false;
    }

    /**
     * Method below checks whether the game has been paused, depending on a variable that's been reassigned based
     * on user's input ("p" key).
     */
     public boolean isItPaused() {
     return this.isPaused;
     }
}
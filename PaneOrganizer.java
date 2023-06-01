package tetris;

import javafx.event.ActionEvent;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;

/**
 * This is the top-level logical class which contains the root pane
 * and its children (gamePane, buttonPane), button, and an instance of the
 * Game class associated with the gamePane. In addition to overseeing the GUI visually, it's responsible
 * for handling the action event that closes the game according to user's input through the Quit button.
 */
public class PaneOrganizer {
    private BorderPane root;

    /**
     * Constructor below sets up a root pane, and calls other methods
     * responsible for setting up additional panes contained (graphically) in it.
     * Methods for panes to be added into the root node are called from
     * within the methods creating them - this way, these nodes can be stored
     * as local variables. Then, the constructor creates a new Game instance
     * and uses a lambda expression to set the KeyEvent responding to user's input.
     */
    public PaneOrganizer() {
        this.root = new BorderPane();
        Pane gamePane = new Pane();
        gamePane.setFocusTraversable(true);
        this.root.setFocusTraversable(false);
        this.root.setCenter(gamePane);

        Game game = new Game(gamePane);
        gamePane.setOnKeyPressed((KeyEvent e) -> game.onKeyPress(e));

        this.createButtonPane();
    }

    /**
     * Helper method below creates a buttonPane while specifying its background and alignment.
     * It creates a quit button responsible for closing the game depending on user's input.
     * The Button is then graphically added to the HBox.
     * Because the buttonPane is added to the gamePane through the method by which it's instantiated,
     * it's stored as a local variable.
     */
    private void createButtonPane() {
        HBox buttonPane = new HBox();
        buttonPane.setStyle("-fx-background-color: #F1F1F1;");
        Button quitButton = new Button("Quit!");
        quitButton.setOnAction((ActionEvent e) -> System.exit(0));
        buttonPane.getChildren().add(quitButton);

        this.root.setBottom(buttonPane);
        buttonPane.setAlignment(Pos.CENTER);
    }

    /**
     * Accessor method below returns the root pane, so that it can be passed into the Scene's constructor
     * (accessed from within the App class).
     */
    public BorderPane getRoot() {
        return this.root;
    }
}

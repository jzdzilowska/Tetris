package tetris;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * This is the main class where your Tetris game will start.
 * The main method of this application calls launch, a JavaFX method
 * which eventually calls the start method below. You will need to fill
 * in the start method to start your game!
 *
 * Class comments here...
 *
 * This App class makes the Tetris game appear by instantiating the PaneOrganizer class
 * responsible for the game's GUI. This class also instantiates Scene, associates
 * it with the root BorderPane, and adds the scene to the stage, thus letting it appear.
 */

public class App extends Application {

    @Override
    public void start(Stage stage) {
        // Create top-level object, set up the scene, and show the stage here.
        PaneOrganizer organizer = new PaneOrganizer();
        stage.setScene(new Scene(organizer.getRoot(), Constants.SCENE_WIDTH, Constants.SCENE_HEIGHT));
        stage.setTitle("tetris");
        stage.show();
    }

    /*
     * Here is the mainline! No need to change this.
     */
    public static void main(String[] argv) {
        // launch is a method inherited from Application
        launch(argv);
    }
}

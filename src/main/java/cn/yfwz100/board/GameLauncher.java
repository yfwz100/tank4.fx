package cn.yfwz100.board;

import de.codecentric.centerdevice.MenuToolkit;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCombination;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

/**
 * The window of the board game.
 *
 * @author yfwz100
 */
public class GameLauncher extends Application {

    /**
     * The main entrance of the application.
     *
     * @param args the arguments.
     */
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage theStage) {
        theStage.setTitle("Block Tank Game");

        BorderPane borderPane = new BorderPane();
        borderPane.setPrefSize(600, 400);
        Scene mainScene = new Scene(borderPane);
        theStage.setScene(mainScene);

        //<editor-fold defaultState="collapsed" desc="Construct the welcomePane.">
        AnchorPane welcomePane = new AnchorPane();
        ImageView bannerImage = new ImageView("cn/yfwz100/board/TankIIBanner.png");
        AnchorPane.setTopAnchor(bannerImage, 200.0 - bannerImage.getImage().getHeight() / 2);
        AnchorPane.setLeftAnchor(bannerImage, 300.0 - bannerImage.getImage().getWidth() / 2);
        welcomePane.getChildren().add(bannerImage);
        //</editor-fold>

        //<editor-fold defaultState="collapsed" desc="Construct the gameCanvas and gameLoop.">
        Canvas gameCanvas = new Canvas(600, 400);
        GameControl gameLoop = new GameLoop(gameCanvas);
        gameCanvas.setCache(true);
        //</editor-fold>

        //<editor-fold defaultState="collapsed" desc="Construct the scorePane.">
        VBox scorePane = new VBox();
        Label label = new Label("Score: 0");
        scorePane.getChildren().add(label);
        //</editor-fold>

        //<editor-fold defaultState="collapsed" desc="Construct main menus.">
        final Menu gameMenu = new Menu("Game");
        {
            MenuItem startMenuItem = new MenuItem("Start");
            startMenuItem.disableProperty().bind(gameLoop.activeProperty());
            startMenuItem.setAccelerator(KeyCombination.valueOf("meta+s"));
            startMenuItem.setOnAction(e -> Platform.runLater(() -> {
                borderPane.setCenter(gameCanvas);
                gameLoop.start();
            }));
            gameMenu.getItems().add(startMenuItem);

            MenuItem stopMenuItem = new MenuItem("Stop");
            stopMenuItem.disableProperty().bind(gameLoop.activeProperty().not());
            stopMenuItem.setAccelerator(KeyCombination.valueOf("meta+e"));
            stopMenuItem.setOnAction(e -> Platform.runLater(() -> {
                borderPane.setCenter(scorePane);
                gameLoop.stop();
            }));
            gameMenu.getItems().add(stopMenuItem);
        }

        final Menu sessionMenu = new Menu("Session");
        {
            sessionMenu.visibleProperty().bind(gameLoop.activeProperty());

            MenuItem resumeMenuItem = new MenuItem("Resume");
            resumeMenuItem.disableProperty().bind(gameLoop.activeProperty().not().or(gameLoop.runningProperty()));
            resumeMenuItem.setAccelerator(KeyCombination.valueOf("meta+r"));
            resumeMenuItem.setOnAction(e -> Platform.runLater(() ->{
                borderPane.setCenter(gameCanvas);
                gameLoop.resume();
            }));
            sessionMenu.getItems().add(resumeMenuItem);

            MenuItem pauseMenuItem = new MenuItem("Pause");
            pauseMenuItem.disableProperty().bind(gameLoop.activeProperty().not().or(gameLoop.runningProperty().not()));
            pauseMenuItem.setAccelerator(KeyCombination.valueOf("meta+p"));
            pauseMenuItem.setOnAction(e -> Platform.runLater(() -> {
                borderPane.setCenter(gameCanvas);
                gameLoop.pause();
            }));
            sessionMenu.getItems().add(pauseMenuItem);
        }

        final Menu helpMenu = new Menu("Help");
        {
            MenuItem howToMenuItem = new MenuItem("How to play?");
            helpMenu.getItems().add(howToMenuItem);
        }

        // set the mac stuff.
        MenuToolkit tk = MenuToolkit.toolkit();
        Menu appMenu = tk.createDefaultApplicationMenu("TankII");
        appMenu.getItems().get(0).setOnAction(e -> {
            gameLoop.pause();
            borderPane.setCenter(welcomePane);
        });
        tk.setApplicationMenu(appMenu);

        MenuBar menuBar = new MenuBar();
        menuBar.getMenus().addAll(gameMenu, sessionMenu, helpMenu);
        menuBar.setUseSystemMenuBar(true);

        borderPane.setTop(menuBar);
        //</editor-fold>

        borderPane.setCenter(welcomePane);
        theStage.show();
    }
}

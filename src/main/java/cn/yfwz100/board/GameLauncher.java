package cn.yfwz100.board;

import de.codecentric.centerdevice.MenuToolkit;
import javafx.animation.FadeTransition;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.HPos;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.geometry.VPos;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCombination;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;
import javafx.util.Duration;

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

    /**
     * The game canvas.
     */
    private Canvas gameCanvas;

    /**
     * The about pane.
     */
    private FlowPane aboutPane;

    /**
     * The score pane.
     */
    private FlowPane scorePane;

    /**
     * The game loop.
     */
    private GameLoop gameLoop;

    @Override
    public void start(Stage theStage) {
        theStage.setTitle("Block Tank Game");

        BorderPane mainPane = new BorderPane();
        {
            FadeTransition fadeTransition = new FadeTransition(Duration.seconds(5), mainPane);
            fadeTransition.setFromValue(0);
            fadeTransition.setToValue(1.0);
            fadeTransition.play();
        }
        mainPane.setPrefSize(600, 400);
        Scene mainScene = new Scene(mainPane);
        theStage.setScene(mainScene);

        //<editor-fold defaultState="collapsed" desc="Init event handlers">
        EventHandler<ActionEvent> startActionHandler = e -> Platform.runLater(() -> {
            mainPane.setCenter(gameCanvas);

            FadeTransition fadeTransition = new FadeTransition(Duration.millis(1000), aboutPane);
            fadeTransition.setFromValue(0);
            fadeTransition.setToValue(1.0);
            fadeTransition.setCycleCount(1);
            fadeTransition.setAutoReverse(false);
            fadeTransition.setOnFinished(fe -> gameLoop.start());
            fadeTransition.play();
        });
        EventHandler<ActionEvent> aboutActionHandler = e -> Platform.runLater(() -> {
            mainPane.setCenter(aboutPane);

            FadeTransition fadeTransition = new FadeTransition(Duration.millis(1000), aboutPane);
            fadeTransition.setFromValue(0);
            fadeTransition.setToValue(1.0);
            fadeTransition.setCycleCount(1);
            fadeTransition.setAutoReverse(false);
            fadeTransition.play();
        });
        //</editor-fold>

        //<editor-fold defaultState="collapsed" desc="Construct the welcomePane.">
        FlowPane welcomePane = new FlowPane();
        {
            welcomePane.setAlignment(Pos.CENTER);
            welcomePane.setColumnHalignment(HPos.CENTER);
            welcomePane.setOrientation(Orientation.VERTICAL);
            welcomePane.setVgap(20);

            ImageView bannerImage = new ImageView("cn/yfwz100/board/TankIIBanner.png");

            Label versionLabel = new Label("Version: 2.0 (by yfwz100)");
            versionLabel.setTextAlignment(TextAlignment.CENTER);

            VBox buttonBar = new VBox();
            Button startBtn = new Button("Start");
            startBtn.setOnAction(startActionHandler);
            startBtn.setPrefWidth(bannerImage.getImage().getWidth() * 0.5);

            Button aboutBtn = new Button("About");
            aboutBtn.setOnAction(aboutActionHandler);
            aboutBtn.setPrefWidth(bannerImage.getImage().getWidth() * 0.5);

            buttonBar.setAlignment(Pos.CENTER);
            buttonBar.setSpacing(5);
            buttonBar.getChildren().addAll(startBtn, aboutBtn);

            welcomePane.getChildren().addAll(bannerImage, versionLabel, buttonBar);
        }
        //</editor-fold>

        //<editor-fold defaultState="collapsed" desc="Construct the gameCanvas and gameLoop.">
        gameCanvas = new Canvas(600, 400);
        {
            GraphicsContext g = gameCanvas.getGraphicsContext2D();
            g.save();
            g.setFill(Color.WHITE);
            g.fillRect(0, 0, gameCanvas.getWidth(), gameCanvas.getHeight());
            g.restore();
        }
        gameLoop = new GameLoop(gameCanvas);
//        mainScene.setOnKeyPressed(e -> gameLoop.keyMapProperty().put(e.getCode(), true));
//        mainScene.setOnKeyReleased(e -> gameLoop.keyMapProperty().put(e.getCode(), false));
        gameCanvas.setCache(true);
        //</editor-fold>

        //<editor-fold defaultState="collapsed" desc="Construct the scorePane.">
        scorePane = new FlowPane();
        {
            scorePane.setAlignment(Pos.CENTER);
            scorePane.setOrientation(Orientation.VERTICAL);
            scorePane.setColumnHalignment(HPos.CENTER);
            scorePane.setVgap(20);

            ImageView logoView = new ImageView("cn/yfwz100/board/TankIIBanner.png");

            GridPane detailBox = new GridPane();
            {
                detailBox.addRow(0, new Label("Achievement: "), styledLabel("Ordinary Hero!"));
                detailBox.addRow(1, new Label("Score: "), styledLabel("1000"));
                detailBox.addRow(2, new Label("Killed: "), styledLabel("10"));

                ColumnConstraints headerConstraints = new ColumnConstraints();
                headerConstraints.setHalignment(HPos.RIGHT);
                ColumnConstraints valueConstraints = new ColumnConstraints();
                valueConstraints.setHalignment(HPos.LEFT);
                detailBox.getColumnConstraints().addAll(headerConstraints, valueConstraints);

                RowConstraints rowConstraints = new RowConstraints();
                rowConstraints.setValignment(VPos.BASELINE);
                detailBox.getRowConstraints().addAll(rowConstraints, rowConstraints, rowConstraints);

                detailBox.setVgap(5);
                detailBox.setAlignment(Pos.CENTER);
                detailBox.setPrefSize(Region.USE_COMPUTED_SIZE, Region.USE_COMPUTED_SIZE);
            }

            HBox buttonBar = new HBox();
            Button shareBtn = new Button("↗ Share");
            shareBtn.setDefaultButton(true);
            Button restartBtn = new Button("↺ Restart");
            restartBtn.setOnAction(startActionHandler);
            buttonBar.setAlignment(Pos.CENTER);
            buttonBar.setSpacing(10);
            buttonBar.getChildren().addAll(shareBtn, restartBtn);

            scorePane.getChildren().addAll(logoView, detailBox, buttonBar);
        }
        //</editor-fold>

        //<editor-fold defaultState="" desc="Construct the aboutPane.">
        aboutPane = new FlowPane();
        {
            ImageView bannerImage = new ImageView("cn/yfwz100/board/TankIIBanner.png");

            Label descLabel = new Label("Press any key to continue.");
            {
                FadeTransition fadeTransition = new FadeTransition(Duration.millis(1000), descLabel);
                fadeTransition.setFromValue(0.5);
                fadeTransition.setToValue(1.0);
                fadeTransition.setCycleCount(Timeline.INDEFINITE);
                fadeTransition.setAutoReverse(true);
                fadeTransition.play();
            }

            aboutPane.setOrientation(Orientation.VERTICAL);
            aboutPane.setColumnHalignment(HPos.CENTER);
            aboutPane.setAlignment(Pos.CENTER);
            aboutPane.getChildren().addAll(bannerImage, descLabel);
        }
        //</editor-fold>

        //<editor-fold defaultState="collapsed" desc="Construct main menus.">
        final Menu gameMenu = new Menu("Game");
        {
            MenuItem startMenuItem = new MenuItem("Start");
            startMenuItem.disableProperty().bind(gameLoop.activeProperty());
            startMenuItem.setAccelerator(KeyCombination.valueOf("meta+s"));
            startMenuItem.setOnAction(startActionHandler);
            gameMenu.getItems().add(startMenuItem);

            MenuItem stopMenuItem = new MenuItem("Stop");
            stopMenuItem.disableProperty().bind(gameLoop.activeProperty().not());
            stopMenuItem.setAccelerator(KeyCombination.valueOf("meta+e"));
            stopMenuItem.setOnAction(e -> Platform.runLater(() -> {
                mainPane.setCenter(scorePane);
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
            resumeMenuItem.setOnAction(e -> Platform.runLater(() -> {
                mainPane.setCenter(gameCanvas);
                gameLoop.resume();
            }));
            sessionMenu.getItems().add(resumeMenuItem);

            MenuItem pauseMenuItem = new MenuItem("Pause");
            pauseMenuItem.disableProperty().bind(gameLoop.activeProperty().not().or(gameLoop.runningProperty().not()));
            pauseMenuItem.setAccelerator(KeyCombination.valueOf("meta+p"));
            pauseMenuItem.setOnAction(e -> Platform.runLater(() -> {
                mainPane.setCenter(gameCanvas);
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
            if (gameLoop.isActive()) {
                gameLoop.pause();
            }
            // delegate to the about action.
            aboutActionHandler.handle(e);
        });
        tk.setApplicationMenu(appMenu);

        MenuBar menuBar = new MenuBar();
        menuBar.getMenus().addAll(gameMenu, sessionMenu, helpMenu);
        menuBar.setUseSystemMenuBar(true);

        mainPane.setTop(menuBar);
        //</editor-fold>

        mainPane.setCenter(welcomePane);
        theStage.show();
    }

    private static Label styledLabel(String text) {
        Label label = new Label(text);
        label.setFont(Font.font("HanziPen SC", Font.getDefault().getSize() + 2));
        return label;
    }

}

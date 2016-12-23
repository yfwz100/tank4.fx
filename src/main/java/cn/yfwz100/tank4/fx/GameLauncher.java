package cn.yfwz100.tank4.fx;

import cn.yfwz100.story.fx.GameLoop;
import cn.yfwz100.tank4.BaseTank;
import cn.yfwz100.tank4.PlayerTank;
import cn.yfwz100.tank4.Tank4Story;
import cn.yfwz100.tank4.TankScoreBoard;
import cn.yfwz100.tank4.fx.battle.ScriptBasedTankBattle;
import de.codecentric.centerdevice.MenuToolkit;
import javafx.animation.FadeTransition;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
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

import javax.script.ScriptException;
import java.util.Locale;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

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
     * The main pane.
     */
    private BorderPane mainPane;

    /**
     * The game pane.
     */
    private StackPane gamePane;

    /**
     * The game loop.
     */
    private GameLoop gameLoop;

    /**
     * The game label.
     */
    private Label gameCountDownLabel;

    /**
     * The game message.
     */
    private Label gameMessageLabel;

    /**
     * The about pane.
     */
    private FlowPane aboutPane;

    /**
     * The score pane.
     */
    private FlowPane scorePane;

    /**
     * The level choice box.
     */
    private ChoiceBox<LevelInfo> levelChoiceBox;

    /**
     * Current story.
     */
    private Tank4Story story;

    /**
     * The score model connecting GUI and story.
     */
    private final ScoreModel scoreModel = new ScoreModel();

    /**
     * Game counter.
     */
    private AtomicInteger counter = new AtomicInteger();
    {
        ScheduledExecutorService service = Executors.newScheduledThreadPool(1);
        service.scheduleAtFixedRate(() -> {
            if (gameLoop != null && gameLoop.isRunning()) {
                int remains = counter.getAndDecrement();
                Platform.runLater(() -> gameCountDownLabel.setText(String.format("Remain Time: %ds", remains)));
                if (remains <= 0) {
                    Platform.runLater(() -> gameMessageLabel.setText("Time's UP!"));
                    Platform.runLater(this::stopGame);
                }
            }
        }, 0, 1, TimeUnit.SECONDS);
    }

    private void stopGame() {
        gameLoop.stop();

        //<editor-fold defaultState="collapsed" desc="Sync story and score board...">
        if (story != null) {
            TankScoreBoard scoreBoard = story.getScoreBoard();
            scoreModel.score.set(String.valueOf(scoreBoard.getScore()));
            scoreModel.maxKillTimes.set(String.valueOf(scoreBoard.getMaxKillTimes()));
            scoreModel.description.set(scoreBoard.getDescription());
        }
        //</editor-fold>

        FadeTransition fadeTransition = new FadeTransition(Duration.millis(2000), gamePane);
        fadeTransition.setFromValue(1.0);
        fadeTransition.setToValue(0.0);
        fadeTransition.setCycleCount(1);
        fadeTransition.setAutoReverse(false);
        fadeTransition.setOnFinished(e2 -> mainPane.setCenter(scorePane));
        fadeTransition.play();
    }

    private void showAbout() {
        if (gameLoop.isActive()) {
            gameLoop.pause();
        }

        mainPane.setCenter(aboutPane);

        FadeTransition fadeTransition = new FadeTransition(Duration.millis(1000), aboutPane);
        fadeTransition.setFromValue(0);
        fadeTransition.setToValue(1.0);
        fadeTransition.setCycleCount(1);
        fadeTransition.setAutoReverse(false);
        fadeTransition.play();
    }

    private void startGame() {
        gameMessageLabel.setText("");

        gameLoop.setStory(story = levelChoiceBox.getValue().getStory());
        gameLoop.start();
        mainPane.setCenter(gamePane);

        counter.set(60);

        FadeTransition fadeTransition = new FadeTransition(Duration.millis(1000), gamePane);
        fadeTransition.setFromValue(0);
        fadeTransition.setToValue(1.0);
        fadeTransition.setCycleCount(1);
        fadeTransition.setAutoReverse(false);
        fadeTransition.play();
    }

    @Override
    public void start(Stage theStage) {
        theStage.setTitle("Block Tank Game");

        mainPane = new BorderPane();
        {
            FadeTransition fadeTransition = new FadeTransition(Duration.seconds(5), mainPane);
            fadeTransition.setFromValue(0);
            fadeTransition.setToValue(1.0);
            fadeTransition.play();
        }
        mainPane.setPrefSize(600, 400);
        Scene mainScene = new Scene(mainPane);
        theStage.setScene(mainScene);

        //<editor-fold defaultState="collapsed" desc="Init event handlers.">
        EventHandler<ActionEvent> startActionHandler = e -> Platform.runLater(this::startGame);
        EventHandler<ActionEvent> aboutActionHandler = e -> Platform.runLater(this::showAbout);
        EventHandler<ActionEvent> stopActionHandler = e -> Platform.runLater(this::stopGame);
        //</editor-fold>

        //<editor-fold defaultState="collapsed" desc="Construct the welcomePane.">
        FlowPane welcomePane = new FlowPane();
        {
            welcomePane.setAlignment(Pos.CENTER);
            welcomePane.setColumnHalignment(HPos.CENTER);
            welcomePane.setOrientation(Orientation.VERTICAL);
            welcomePane.setVgap(20);

            ImageView bannerImage = new ImageView("cn/yfwz100/tank4/fx/TankIIBanner.png");

            Label versionLabel = new Label("Version: 2.0 (by yfwz100)");
            versionLabel.setTextAlignment(TextAlignment.CENTER);

            VBox buttonBar = new VBox();
            Button startBtn = new Button("Start");
            startBtn.setOnAction(startActionHandler);
            startBtn.setPrefWidth(bannerImage.getImage().getWidth() * 0.5);

            levelChoiceBox = new ChoiceBox<>();
            {
                levelChoiceBox.setPrefWidth(bannerImage.getImage().getWidth() * 0.5);
                LevelInfo defaultLevel;
                levelChoiceBox.getItems().addAll(
                        defaultLevel = new LevelInfo("Basic", "basic.js"),
                        new LevelInfo("Medium", "medium.js"),
                        new LevelInfo("Hard", "hard.js")
                );
                levelChoiceBox.setValue(defaultLevel);
            }

            Button aboutBtn = new Button("About");
            aboutBtn.setOnAction(aboutActionHandler);
            aboutBtn.setPrefWidth(bannerImage.getImage().getWidth() * 0.5);

            buttonBar.setAlignment(Pos.CENTER);
            buttonBar.setSpacing(5);
            buttonBar.getChildren().addAll(startBtn, levelChoiceBox, aboutBtn);

            welcomePane.getChildren().addAll(bannerImage, versionLabel, buttonBar);
        }
        //</editor-fold>

        //<editor-fold defaultState="collapsed" desc="Construct the gameCanvas and gameLoop.">
        gamePane = new StackPane();
        {
            Canvas gameCanvas = new Canvas(600, 400);
            {
                // create the initial screen.
                GraphicsContext g = gameCanvas.getGraphicsContext2D();
                g.save();
                g.setFill(Color.WHITE);
                g.fillRect(0, 0, gameCanvas.getWidth(), gameCanvas.getHeight());
                g.restore();

                // create the game loop.
                gameLoop = new GameLoop(gameCanvas);
                gameLoop.setOnExit(e -> {
                    Platform.runLater(() -> gameMessageLabel.setText("Game Over!"));
                    Platform.runLater(this::stopGame);
                });

                gameCanvas.setCache(true);
            }

            gameCountDownLabel = new Label();
            gameCountDownLabel.setAlignment(Pos.CENTER);

            StackPane centerPane = new StackPane();
            gameMessageLabel = new Label();
            gameMessageLabel.setFont(Font.font("HanziPen SC", 22));
            centerPane.getChildren().addAll(gameMessageLabel);

            gamePane.setAlignment(Pos.TOP_RIGHT);
            gamePane.getChildren().addAll(gameCanvas, gameCountDownLabel, centerPane);
        }
        //</editor-fold>

        //<editor-fold defaultState="collapsed" desc="Register the keyboard event listeners.">
        mainScene.setOnKeyPressed(e -> {
            if (gameLoop.isRunning() && gameLoop.getStory() instanceof Tank4Story) {
                PlayerTank hero = ((Tank4Story) gameLoop.getStory()).getPlayer();
                switch (e.getCode()) {
                    case A:
                    case LEFT:
                        hero.setMoveState(BaseTank.MoveState.LEFT);
                        break;
                    case D:
                    case RIGHT:
                        hero.setMoveState(BaseTank.MoveState.RIGHT);
                        break;
                    case W:
                    case UP:
                        hero.setMoveState(BaseTank.MoveState.UP);
                        break;
                    case S:
                    case DOWN:
                        hero.setMoveState(BaseTank.MoveState.DOWN);
                        break;
                    case K:
                        hero.setGunState(BaseTank.GunState.CLOCKWISE);
                        break;
                    case J:
                        hero.setGunState(BaseTank.GunState.ANTICLOCKWISE);
                        break;
                }
            }
        });
        mainScene.setOnKeyReleased(e -> {
            if (gameLoop.isRunning() && gameLoop.getStory() instanceof Tank4Story) {
                PlayerTank hero = ((Tank4Story) gameLoop.getStory()).getPlayer();
                switch (e.getCode()) {
                    case A:
                    case LEFT:
                        if (hero.getMoveState() == BaseTank.MoveState.LEFT) {
                            hero.setMoveState(BaseTank.MoveState.STOP);
                        }
                        break;
                    case D:
                    case RIGHT:
                        if (hero.getMoveState() == BaseTank.MoveState.RIGHT) {
                            hero.setMoveState(BaseTank.MoveState.STOP);
                        }
                        break;
                    case W:
                    case UP:
                        if (hero.getMoveState() == BaseTank.MoveState.UP) {
                            hero.setMoveState(BaseTank.MoveState.STOP);
                        }
                        break;
                    case S:
                    case DOWN:
                        if (hero.getMoveState() == BaseTank.MoveState.DOWN) {
                            hero.setMoveState(BaseTank.MoveState.STOP);
                        }
                        break;
                    case J:
                    case K:
                        hero.setGunState(BaseTank.GunState.STOP);
                        break;
                    case SPACE:
                        hero.setShooting(true);
                        break;
                    default:
                        hero.setMoveState(BaseTank.MoveState.STOP);
                }
            }
        });
        //</editor-fold>

        //<editor-fold defaultState="collapsed" desc="Construct the scorePane.">
        scorePane = new FlowPane();
        {
            scorePane.setAlignment(Pos.CENTER);
            scorePane.setOrientation(Orientation.VERTICAL);
            scorePane.setColumnHalignment(HPos.CENTER);
            scorePane.setVgap(20);

            ImageView logoView = new ImageView("cn/yfwz100/tank4/fx/TankIIBanner.png");

            GridPane detailBox = new GridPane();
            {
                detailBox.addRow(0, new Label("Achievement: "), styledLabel(scoreModel.description));
                detailBox.addRow(1, new Label("Score: "), styledLabel(scoreModel.score));
                detailBox.addRow(2, new Label("Killed: "), styledLabel(scoreModel.maxKillTimes));

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
            ImageView bannerImage = new ImageView("cn/yfwz100/tank4/fx/TankIIBanner.png");

            Label descLabel = new Label("Brought to you by yfwz100.\nAs a gift to SanDuck at Christmas. ");
            {
                descLabel.setFont(Font.font("Chalkduster"));
                descLabel.setTextAlignment(TextAlignment.CENTER);

                FadeTransition fadeTransition = new FadeTransition(Duration.millis(1000), descLabel);
                fadeTransition.setFromValue(0.5);
                fadeTransition.setToValue(1.0);
                fadeTransition.setCycleCount(Timeline.INDEFINITE);
                fadeTransition.setAutoReverse(true);
                fadeTransition.play();
            }

            Button backButton = new Button("← Back");
            {
                backButton.setOnAction(e -> Platform.runLater(() -> {
                    mainPane.setCenter(welcomePane);
                    {
                        FadeTransition fadeTransition = new FadeTransition(Duration.seconds(1), welcomePane);
                        fadeTransition.setFromValue(0);
                        fadeTransition.setToValue(1.0);
                        fadeTransition.play();
                    }
                }));
                backButton.setPrefWidth(bannerImage.getImage().getWidth() * 0.5);
            }

            aboutPane.setOrientation(Orientation.VERTICAL);
            aboutPane.setColumnHalignment(HPos.CENTER);
            aboutPane.setAlignment(Pos.CENTER);
            aboutPane.setVgap(20);
            aboutPane.getChildren().addAll(bannerImage, descLabel, backButton);
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
            stopMenuItem.setOnAction(stopActionHandler);
            gameMenu.getItems().add(stopMenuItem);
        }

        final Menu sessionMenu = new Menu("Session");
        {
            sessionMenu.visibleProperty().bind(gameLoop.activeProperty());

            MenuItem resumeMenuItem = new MenuItem("Resume");
            resumeMenuItem.disableProperty().bind(gameLoop.activeProperty().not().or(gameLoop.runningProperty()));
            resumeMenuItem.setAccelerator(KeyCombination.valueOf("meta+r"));
            resumeMenuItem.setOnAction(e -> Platform.runLater(() -> {
                mainPane.setCenter(gamePane);
                gameLoop.resume();
            }));
            sessionMenu.getItems().add(resumeMenuItem);

            MenuItem pauseMenuItem = new MenuItem("Pause");
            pauseMenuItem.disableProperty().bind(gameLoop.activeProperty().not().or(gameLoop.runningProperty().not()));
            pauseMenuItem.setAccelerator(KeyCombination.valueOf("meta+p"));
            pauseMenuItem.setOnAction(e -> Platform.runLater(() -> {
                mainPane.setCenter(gamePane);
                gameLoop.pause();
            }));
            sessionMenu.getItems().add(pauseMenuItem);
        }

        final Menu helpMenu = new Menu("Help");
        {
            MenuItem howToMenuItem = new MenuItem("How to play?");
            helpMenu.getItems().add(howToMenuItem);
        }

        MenuBar menuBar = new MenuBar();
        menuBar.getMenus().addAll(gameMenu, sessionMenu, helpMenu);

        // set the mac stuff.
        if (System.getProperty("os.name").startsWith("Mac")) {
            menuBar.setUseSystemMenuBar(true);

            MenuToolkit tk = MenuToolkit.toolkit();
            Menu appMenu = tk.createDefaultApplicationMenu("Tank4");
            appMenu.getItems().get(0).setOnAction(aboutActionHandler);
            tk.setApplicationMenu(appMenu);
        } else {
            MenuItem aboutItem = new MenuItem("About");
            aboutItem.setOnAction(aboutActionHandler);
            helpMenu.getItems().add(aboutItem);
        }

        mainPane.setTop(menuBar);
        //</editor-fold>

        mainPane.setCenter(welcomePane);
        theStage.show();
    }

    private static Label styledLabel(StringProperty text) {
        Label label = new Label();
        label.textProperty().bind(text);
        label.setFont(Font.font("HanziPen SC", Font.getDefault().getSize() + 2));
        return label;
    }

    /**
     * The score model connecting GUI and story.
     */
    private class ScoreModel {
        /**
         * The score.
         */
        private final StringProperty score = new SimpleStringProperty();

        /**
         * The max killing times.
         */
        private final StringProperty maxKillTimes = new SimpleStringProperty();

        /**
         * The description.
         */
        private final StringProperty description = new SimpleStringProperty();
    }

    /**
     * The level info class.
     */
    private class LevelInfo {

        private String name;
        private String fileName;

        LevelInfo(String name, String fileName) {
            this.name = name;
            this.fileName = fileName;
        }

        public Tank4Story getStory() {
            try {
                return new ScriptBasedTankBattle(fileName);
            } catch (ScriptException e) {
                e.printStackTrace();
                return null;
            }
        }

        @Override
        public String toString() {
            return name;
        }
    }

}

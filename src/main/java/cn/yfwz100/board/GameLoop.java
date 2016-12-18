package cn.yfwz100.board;

import javafx.animation.AnimationTimer;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

/**
 * The game loop of the board.
 *
 * @author yfwz100
 */
public class GameLoop extends AnimationTimer implements GameControl {

    private BooleanProperty runningProperty = new SimpleBooleanProperty(false);

    private BooleanProperty activeProperty = new SimpleBooleanProperty(false);

    /**
     * The canvas that the game is managing.
     */
    private Canvas canvas;

    /**
     * The position.
     */
    private int pos;

    GameLoop(Canvas canvas) {
        this.canvas = canvas;
    }

    @Override
    public void handle(long now) {
        if (runningProperty.get()) {
            GraphicsContext g = canvas.getGraphicsContext2D();
            g.save();
            {
                g.setFill(Color.WHITE);
                g.fillRect(0, 0, canvas.getWidth(), canvas.getHeight());

                g.setFill(Color.RED);
                g.setStroke(Color.BLACK);
                g.setLineWidth(2);
                Font theFont = Font.font("Times", FontWeight.BOLD, 48);
                g.setFont(theFont);
                g.fillText("Hello, World!", 60 + (Math.abs((pos = (pos + 1) % 100) - 50) % 100), 100);
                g.strokeText("Hello, World!", 60, 50);
            }
            g.restore();
        }
    }

    public BooleanProperty activeProperty() {
        return activeProperty;
    }

    public boolean isActive() {
        return activeProperty.get();
    }

    public BooleanProperty runningProperty() {
        return runningProperty;
    }

    public boolean isRunning() {
        return runningProperty.get();
    }

    public void start() {
        runningProperty.set(true);
        activeProperty.set(true);
        super.start();
    }

    public void resume() {
        runningProperty.set(true);
    }

    public void pause() {
        runningProperty.set(false);
    }

    public void stop() {
        runningProperty.set(false);
        activeProperty.set(false);

        //<editor-fold defaultState="collapsed" desc="canvas.clear();">
        {
            GraphicsContext g = canvas.getGraphicsContext2D();
            g.save();

            g.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());

            g.restore();
        }
        //</editor-fold>

        super.stop();
    }
}

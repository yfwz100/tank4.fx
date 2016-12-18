package cn.yfwz100.board;

import javafx.beans.property.BooleanProperty;

/**
 * The game loop control interface.
 *
 * @author yfwz100
 */
public interface GameControl {

    /**
     * Start the game.
     */
    void start();

    /**
     * Stop the game.
     */
    void stop();

    /**
     * Pause and save a checkpoint.
     */
    void pause();

    /**
     * Restore the last check point.
     */
    void resume();

    /**
     * Check if the game is active.
     *
     * @return true if active.
     */
    boolean isActive();

    /**
     * The active property.
     *
     * @return the property.
     */
    BooleanProperty activeProperty();

    /**
     * Check if the game is running(not paused).
     *
     * @return true if running.
     */
    boolean isRunning();

    /**
     * The running property.
     *
     * @return the property.
     */
    BooleanProperty runningProperty();
}

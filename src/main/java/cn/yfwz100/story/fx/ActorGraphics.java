package cn.yfwz100.story.fx;

import javafx.scene.canvas.GraphicsContext;

/**
 * The game actor.
 *
 * @author yfwz100
 */
public interface ActorGraphics {

    /**
     * The draw strategy.
     *
     * @param g the graphics context.
     */
    void paint(GraphicsContext g);

}

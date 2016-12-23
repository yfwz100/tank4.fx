package cn.yfwz100.tank4.fx.actor;

import cn.yfwz100.story.fx.ActorGraphics;
import cn.yfwz100.tank4.Block;
import cn.yfwz100.tank4.Tank4Story;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

/**
 * The Styled Block.
 *
 * @author yfwz100
 */
public class StyledBlock extends Block implements ActorGraphics {

    /**
     * Construct a block.
     *
     * @param story  the game story..
     * @param x      the position.
     * @param y      the position.
     * @param width  the width.
     * @param height the height.
     * @param a      the angle, in radian unit.
     */
    public StyledBlock(Tank4Story story, float x, float y, float width, float height, float a) {
        super(story, x, y, width, height, a);
    }

    @Override
    public void paint(GraphicsContext g) {
        g.save();

        g.translate(getBody().getPosition().x, getBody().getPosition().y);
        g.rotate(Math.toDegrees(getBody().getAngle()));
        g.setFill(Color.RED);
        g.fillRect(-getWidth() / 2, -getHeight() / 2, getWidth(), getHeight());

        g.restore();
    }
}

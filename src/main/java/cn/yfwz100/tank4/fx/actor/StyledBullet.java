package cn.yfwz100.tank4.fx.actor;

import cn.yfwz100.story.fx.ActorGraphics;
import cn.yfwz100.tank4.Bullet;
import cn.yfwz100.tank4.Tank4Story;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import org.jbox2d.common.Vec2;


/**
 * The Bullet with Style.
 *
 * @author yfwz100
 */
public class StyledBullet extends Bullet implements ActorGraphics {

    /**
     * Construct a bullet object in the story.
     *
     * @param story the story.
     * @param pos   the position.
     * @param vel   the velocity.
     */
    StyledBullet(Tank4Story story, Vec2 pos, Vec2 vel) {
        super(story, pos, vel);
    }

    @Override
    public void paint(GraphicsContext g) {
        g.save();
        g.scale(0.5, 0.5);

        g.translate(getBody().getPosition().x, getBody().getPosition().y);
        g.setFill(Color.RED);
        g.fillOval(-5, -5, 10, 10);

        g.restore();
    }
}

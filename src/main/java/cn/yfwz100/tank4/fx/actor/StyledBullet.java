package cn.yfwz100.tank4.fx.actor;

import cn.yfwz100.story.fx.ActorGraphics;
import cn.yfwz100.tank4.BaseTank;
import cn.yfwz100.tank4.Bullet;
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
     * @param tank the owner.
     * @param pos  the position.
     * @param vel  the VELOCITY.
     */
    StyledBullet(BaseTank tank, Vec2 pos, Vec2 vel) {
        super(tank, pos, vel);
    }

    @Override
    public void paint(GraphicsContext g) {
        g.save();

        g.translate(getBody().getPosition().x, getBody().getPosition().y);
        g.setFill(Color.RED);
        g.fillOval(-0.5, -0.5, 1, 1);

        g.restore();
    }
}

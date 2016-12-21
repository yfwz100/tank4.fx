package cn.yfwz100.tank4.fx.actor;

import cn.yfwz100.story.fx.ActorGraphics;
import cn.yfwz100.tank4.BaseTank;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

/**
 * The Graphics for Tank - the Colored Tank.
 *
 * @author yfwz100
 */
public interface ColoredTankGraphics extends BaseTank, ActorGraphics {

    /**
     * Get the main color of the tank.
     *
     * @return the main color.
     */
    Color getMainColor();

    @Override
    default void paint(GraphicsContext g) {
        g.save();

        //<editor-fold defaultState="collapsed" desc="draw the tank.">
        g.translate(getTankBody().getPosition().x, getTankBody().getPosition().y);
        g.rotate(Math.toDegrees(getTankBody().getAngle()));
        g.setFill(getMainColor().interpolate(Color.WHITE, 1 - getHealth() / 100));
        g.fillRoundRect(-getTankWidth() / 2, -getTankHeight() / 2, getTankWidth(), getTankHeight(), 1, 1);
        g.setStroke(Color.BLACK);
        g.strokeRoundRect(-getTankWidth() / 2, -getTankHeight() / 2, getTankWidth(), getTankHeight(), 1, 1);
        //</editor-fold>

        //<editor-fold defaultState="" desc="draw the gun.">
        g.rotate(Math.toDegrees(getGunBody().getAngle() - getTankBody().getAngle()));
        g.setFill(Color.BLACK);
        g.fillRoundRect(-0.75, -1, 1.5, 5, 1,1);
        g.setStroke(Color.BLACK);
        g.strokeRoundRect(-0.75, -1, 1.5, 5, 1,1);
        g.setFill(Color.BLACK);
        g.fillRoundRect(-1.25, -1.25, 2.5, 2.5, 1, 1);
        //</editor-fold>

        g.restore();
    }
}

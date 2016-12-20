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
        g.scale(0.5, 0.5);

        //<editor-fold defaultState="collapsed" desc="draw the tank.">
        g.translate(getTankBody().getPosition().x, getTankBody().getPosition().y);
        g.rotate(Math.toDegrees(getTankBody().getAngle()));
        g.setFill(getMainColor().interpolate(Color.WHITE, 1 - getHealth() / 100));
        g.fillRoundRect(-25, -25, 50, 50, 10, 10);
        g.setStroke(Color.BLACK);
        g.strokeRoundRect(-25, -25, 50, 50, 10, 10);
        //</editor-fold>

        //<editor-fold defaultState="" desc="draw the gun.">
        g.rotate(Math.toDegrees(getGunBody().getAngle() - getTankBody().getAngle()));
        g.setFill(Color.BLACK);
        g.fillRoundRect(-7.5, -10, 15, 50, 10,10);
        g.setStroke(Color.BLACK);
        g.strokeRoundRect(-7.5, -10, 15, 50, 10,10);
        g.setFill(Color.BLACK);
        g.fillRoundRect(-12.5, -12.5, 25, 25, 10, 10);
        //</editor-fold>

        g.restore();
    }
}

package cn.yfwz100.tank4.fx.actor;

import cn.yfwz100.tank4.PlayerTank;
import cn.yfwz100.tank4.Tank4Story;
import javafx.scene.paint.Color;

/**
 * The styled tank of player.
 *
 * @author yfwz100
 */
public class StyledPlayerTank extends PhysicalBaseTank implements PlayerTank, ColoredTankGraphics {

    /**
     * Create a player tank.
     *
     * @param story the story.
     * @param x the position x.
     * @param y the position y.
     */
    public StyledPlayerTank(Tank4Story story, float x, float y) {
        super(story, x, y);
    }

    @Override
    public Color getMainColor() {
        return Color.LIGHTBLUE;
    }
}

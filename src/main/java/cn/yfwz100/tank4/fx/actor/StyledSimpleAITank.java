package cn.yfwz100.tank4.fx.actor;

import cn.yfwz100.tank4.SimpleAITank;
import cn.yfwz100.tank4.Tank4Story;
import javafx.scene.paint.Color;

/**
 * The Styled Tank backed by Simple AI.
 *
 * @author yfwz100
 */
public class StyledSimpleAITank extends PhysicalBaseTank implements SimpleAITank, ColoredTankGraphics {

    /**
     * The time of last movement.
     */
    private long lastMoveTime = 0;

    /**
     * The rand seed.
     */
    private int rand;

    /**
     * Construct a styled tank backed by a simple AI.
     *
     * @param story the story.
     * @param x     the position x.
     * @param y     the position y.
     */
    public StyledSimpleAITank(Tank4Story story, float x, float y) {
        super(story, x, y);
    }

    @Override
    public long getLastMoveTime() {
        return lastMoveTime;
    }

    @Override
    public void setLastMoveTime(long time) {
        this.lastMoveTime = time;
    }

    @Override
    public MoveState getNextDirection() {
        rand = (int) ((Math.random() * 4) + rand) % 5;
        switch (rand) {
            case 0:
                return MoveState.LEFT;
            case 1:
                return MoveState.RIGHT;
            case 2:
                return MoveState.UP;
            case 4:
                return MoveState.DOWN;
            default:
                return MoveState.STOP;
        }
    }

    @Override
    public Color getMainColor() {
        return Color.DARKGRAY;
    }
}

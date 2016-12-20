package cn.yfwz100.tank4;

/**
 * The Tank of Player.
 *
 * @author yfwz100
 */
public interface PlayerTank extends BaseTank {

    @Override
    default float getShotInterval() {
        return 50;
    }
}

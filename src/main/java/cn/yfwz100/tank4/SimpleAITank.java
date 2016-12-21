package cn.yfwz100.tank4;

import org.jbox2d.dynamics.Body;

import java.util.Optional;

/**
 * The Tank backed by a Simple AI.
 *
 * @author yfwz100
 */
public interface SimpleAITank extends AITank {

    /**
     * The time of last movement.
     */
    long getLastMoveTime();

    /**
     * Set the time of last movement.
     */
    void setLastMoveTime(long time);

    /**
     * The random number.
     */
    MoveState getNextDirection();

    @Override
    default float getShotInterval() {
        return 1000;
    }

    @Override
    default boolean update() {
        BaseTank tank = getStory().getPlayer();
        if (tank == null) return false;

        // firstly, assume that the player is visible.
        BoolRef playerIsVisible = new BoolRef(true);
        getStory().getWorld().raycast((fixture, point, normal, fraction) -> {
            Body b = fixture.getBody();
            if (!(b.getUserData() instanceof BaseTank) && !(b.getUserData() instanceof Bullet)) {
                playerIsVisible.setValue(false);
                return 0;
            }
            return 1;
        }, tank.getTankBody().getWorldCenter(), getTankBody().getWorldCenter());

        // secondly, the callback will decide whether the player is visible or not.
        if (playerIsVisible.getValue()) {
            this.setGunState(GunState.STOP);

            double dy = tank.getTankBody().getPosition().y - getTankBody().getPosition().y;
            double dx = tank.getTankBody().getPosition().x - getTankBody().getPosition().x;
            double da = (Math.atan2(-dx, dy) + Math.PI * 2) % (Math.PI * 2);
            double a = getGunBody().getAngle() % (Math.PI * 2);
            if (Math.abs(a - da) > 0.1) {
                getGunBody().setTransform(getTankBody().getPosition(), (float) da);
            }

            this.setShooting(true);
        } else {
            this.setGunState(GunState.CLOCKWISE);

            if (System.currentTimeMillis() - getLastMoveTime() > 1000) {
                this.setMoveState(getNextDirection());

                // prevent frequent movement.
                setLastMoveTime(System.currentTimeMillis());
            }
        }

        return AITank.super.update();
    }
}


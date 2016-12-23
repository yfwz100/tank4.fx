package cn.yfwz100.tank4;

import cn.yfwz100.story.Actor;
import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.Body;

/**
 * The BaseTank of the Game.
 *
 * @author yfwz100
 */
public interface BaseTank extends Actor, Killable {

    /**
     * The body of the tank.
     *
     * @return the tank body.
     */
    Body getTankBody();

    /**
     * The width of the tank.
     *
     * @return the width of the tank.
     */
    float getTankWidth();

    /**
     * The height of the tank.
     *
     * @return the height of the tank.
     */
    float getTankHeight();

    /**
     * The body of the gun.
     *
     * @return the gun body.
     */
    Body getGunBody();

    /**
     * The moving state of the actor.
     */
    MoveState getMoveState();

    /**
     * Set the move state.
     *
     * @param state the state.
     */
    void setMoveState(MoveState state);

    /**
     * Get the state of the gun.
     *
     * @return the state of the gun.
     */
    GunState getGunState();

    /**
     * Set the state of the gun.
     *
     * @param gunState the state of the gun.
     */
    void setGunState(GunState gunState);

    /**
     * Check if the tank is shooting.
     *
     * @return true if it's shooting.
     */
    boolean isShooting();

    /**
     * Set if it's shooting.
     *
     * @param shooting the flag.
     */
    void setShooting(boolean shooting);

    /**
     * The last shot time.
     */
    long getLastShotTime();

    /**
     * Set the last shot time.
     *
     * @param shotTime the last shot time.
     */
    void setLastShotTime(long shotTime);

    //<editor-fold defaultState="collapsed" desc="Declare the properties.">
    float GUN_ANGLE_VELOCITY = (float) (20 * Math.PI / 180);
    float VELOCITY = 5;
    float BULLET_VELOCITY = 10;
    //</editor-fold>

    /**
     * Get the interval between two shots.
     *
     * @return the interval.
     */
    float getShotInterval();

    @Override
    default boolean update() {
        //<editor-fold defaultState="collapsed" desc="Update the movement of tank.">
        Vec2 destPos = this.getMoveState().getDirectionVector();
        Vec2 cv = getTankBody().getLinearVelocity();
        Vec2 dv = destPos.sub(cv).mulLocal(getTankBody().getMass());
        getTankBody().applyLinearImpulse(dv, getTankBody().getWorldCenter());
        //</editor-fold>

        //<editor-fold defaultState="collapsed" desc="Update behaviors of the gun.">
        switch (getGunState()) {
            case CLOCKWISE:
                getGunBody().setAngularVelocity(GUN_ANGLE_VELOCITY);
                break;
            case ANTICLOCKWISE:
                getGunBody().setAngularVelocity(-GUN_ANGLE_VELOCITY);
                break;
            default:
                getGunBody().setAngularVelocity(0);
        }
        //</editor-fold>

        //<editor-fold defaultState="collapsed" desc="Update the shooting behaviors.">
        if (isShooting()) {
            if (System.currentTimeMillis() - getLastShotTime() > getShotInterval()) {
                Vec2 vel = new Vec2(
                        (float) (-Math.sin(getGunBody().getAngle())),
                        (float) (Math.cos(getGunBody().getAngle()))
                );
                getStory().getBullets().add(
                        createBullet(getTankBody().getPosition().add(vel.mul(5f)), vel.mul(BULLET_VELOCITY))
                );
                setShooting(false);
                setLastShotTime(System.currentTimeMillis());
            }
        }
        //</editor-fold>

        return isAlive();
    }

    @Override
    Tank4Story getStory();

    /**
     * Create the bullet.
     *
     * @param pos the position of the bullet.
     * @param vel the VELOCITY of the bullet.
     * @return the bullet.
     */
    Bullet createBullet(Vec2 pos, Vec2 vel);

    @Override
    default void cleanup() {
        getStory().getWorld().destroyBody(getTankBody());
        getStory().getWorld().destroyBody(getGunBody());
    }

    /**
     * The state of the tank.
     */
    enum MoveState {
        LEFT(new Vec2(-VELOCITY, 0)),
        RIGHT(new Vec2(VELOCITY, 0)),
        UP(new Vec2(0, -VELOCITY)),
        DOWN(new Vec2(0, VELOCITY)),
        STOP(new Vec2());

        private final Vec2 direction;

        /**
         * Embed the direction into the move state.
         *
         * @param direction the direction vector.
         */
        MoveState(Vec2 direction) {
            this.direction = direction;
        }

        /**
         * Get the direction vector.
         *
         * @return the direction.
         */
        public Vec2 getDirectionVector() {
            return direction;
        }
    }

    /**
     * Set the gun's state.
     */
    enum GunState {
        CLOCKWISE, ANTICLOCKWISE, STOP
    }
}

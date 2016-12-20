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
    float gunAngleVelocity = (float) (20 * Math.PI / 180);
    float velocity = 20;
    float bulletVelocity = 100;
    float shotInterval = 1000;
    //</editor-fold>

    @Override
    default boolean update() {
        Vec2 destPos;
        switch (this.getMoveState()) {
            case LEFT:
                destPos = new Vec2(-velocity, 0);
                break;
            case RIGHT:
                destPos = new Vec2(velocity, 0);
                break;
            case UP:
                destPos = new Vec2(0, -velocity);
                break;
            case DOWN:
                destPos = new Vec2(0, velocity);
                break;
            default:
                destPos = new Vec2();
        }
        Vec2 cv = getTankBody().getLinearVelocity();
        Vec2 dv = destPos.sub(cv).mulLocal(getTankBody().getMass());
        getTankBody().applyLinearImpulse(dv, getTankBody().getWorldCenter());

        switch (getGunState()) {
            case CLOCKWISE:
                getGunBody().setAngularVelocity(gunAngleVelocity);
                break;
            case ANTICLOCKWISE:
                getGunBody().setAngularVelocity(-gunAngleVelocity);
                break;
            default:
                getGunBody().setAngularVelocity(0);
        }

        if (isShooting()) {
            if (System.currentTimeMillis() - getLastShotTime() > shotInterval) {
                Vec2 vel = new Vec2(
                        (float) (-Math.sin(getGunBody().getAngle())),
                        (float) (Math.cos(getGunBody().getAngle()))
                );
                getStory().getBullets().add(
                        createBullet(getTankBody().getPosition().add(vel.mul(50f)), vel.mul(bulletVelocity))
                );
                setShooting(false);
                setLastShotTime(System.currentTimeMillis());
            }
        }

        return isAlive();
    }

    @Override
    Tank4Story getStory();

    /**
     * Create the bullet.
     *
     * @param pos the position of the bullet.
     * @param vel the velocity of the bullet.
     * @return the bullet.
     */
    Bullet createBullet(Vec2 pos, Vec2 vel);

    /**
     * The state of the tank.
     */
    enum MoveState {
        LEFT,
        RIGHT,
        UP,
        DOWN,
        STOP
    }

    /**
     * Set the gun's state.
     */
    enum GunState {
        CLOCKWISE, ANTICLOCKWISE, STOP
    }
}

package cn.yfwz100.tank4.fx.actor;

import cn.yfwz100.tank4.BaseTank;
import cn.yfwz100.tank4.Bullet;
import cn.yfwz100.tank4.Tank4Story;
import org.jbox2d.collision.shapes.PolygonShape;
import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.Body;
import org.jbox2d.dynamics.BodyDef;
import org.jbox2d.dynamics.BodyType;
import org.jbox2d.dynamics.FixtureDef;
import org.jbox2d.dynamics.joints.RevoluteJointDef;

/**
 * The Base Tank.
 *
 * @author yfwz100
 */
public abstract class PhysicalBaseTank implements BaseTank {

    /**
     * The backed story.
     */
    protected Tank4Story story;

    /**
     * The movement state.
     */
    private MoveState moveState;

    /**
     * The gunBody state.
     */
    private GunState gunState;

    /**
     * The shooting flag.
     */
    private boolean shooting;

    /**
     * The physical tankBody.
     */
    private Body tankBody;

    /**
     * The physical tankBody of gunBody component.
     */
    private Body gunBody;

    /**
     * Time control variable.
     */
    private long shotTime = 0;

    /**
     * The health value.
     */
    private double health = 100;

    /**
     * Initialize the physical part of the tank.
     *
     * @param story the story.
     * @param x     the x-coordinate.
     * @param y     the y-coordinate.
     */
    PhysicalBaseTank(Tank4Story story, float x, float y) {
        this.story = story;

        //<editor-fold defaultState="collapsed" desc="physics.install(tank);">
        {
            BodyDef tankBodyDef = new BodyDef();
            tankBodyDef.type = BodyType.DYNAMIC;
            tankBodyDef.active = true;
            tankBodyDef.fixedRotation = true;
            tankBodyDef.position = new Vec2(x, y);
            tankBodyDef.userData = this;
            tankBody = story.getWorld().createBody(tankBodyDef);

            PolygonShape rect = new PolygonShape();
            rect.setAsBox(25f, 25f);
            FixtureDef bodyFixtureDef = new FixtureDef();
            bodyFixtureDef.shape = rect;
            bodyFixtureDef.density = 20;
//            bodyFixtureDef.filter.categoryBits = 0x2;
            bodyFixtureDef.friction = 0;
            tankBody.createFixture(bodyFixtureDef);
            tankBody.resetMassData();
        }
        //</editor-fold>

        //<editor-fold defaultState="collapsed" desc="physics.install(gun);">
        {
            BodyDef gunBodyDef = new BodyDef();
            gunBodyDef.type = BodyType.DYNAMIC;
            gunBodyDef.angle = 0;
            gunBodyDef.active = true;
            gunBodyDef.fixedRotation = false;
            gunBodyDef.position = new Vec2(x, y);
            gunBodyDef.userData = this;
            gunBody = story.getWorld().createBody(gunBodyDef);

            PolygonShape rect = new PolygonShape();
            rect.setAsBox(10, 25, new Vec2(0, 15), 0);
            FixtureDef gunFixtureDef = new FixtureDef();
            gunFixtureDef.shape = rect;
            gunFixtureDef.density = 20;
            gunFixtureDef.friction = 20;
//            gunFixtureDef.filter.categoryBits = 0x2;
            gunBody.createFixture(gunFixtureDef);
        }
        //</editor-fold>

        //<editor-fold defaultState="collapsed" desc="joint.connect(tank, gun);">
        RevoluteJointDef revJointDef = new RevoluteJointDef();
        revJointDef.initialize(tankBody, gunBody, tankBody.getWorldCenter());
        revJointDef.localAnchorA.set(0, 0);
        revJointDef.localAnchorB.set(0, 0);
        revJointDef.referenceAngle = 0;
        revJointDef.collideConnected = false;
        revJointDef.enableMotor = true;
        revJointDef.motorSpeed = 0f;
        revJointDef.maxMotorTorque = 3f;
        story.getWorld().createJoint(revJointDef);
        //</editor-fold>

        // set the state flag.
        moveState = MoveState.STOP;
        gunState = GunState.STOP;
        shooting = false;
    }

    @Override
    public Body getTankBody() {
        return tankBody;
    }

    @Override
    public Body getGunBody() {
        return gunBody;
    }

    @Override
    public MoveState getMoveState() {
        return moveState;
    }

    @Override
    public void setMoveState(MoveState moveState) {
        this.moveState = moveState;
    }

    @Override
    public GunState getGunState() {
        return gunState;
    }

    @Override
    public void setGunState(GunState gunState) {
        this.gunState = gunState;
    }

    @Override
    public boolean isShooting() {
        return shooting;
    }

    @Override
    public void setShooting(boolean shooting) {
        this.shooting = shooting;
    }

    @Override
    public long getLastShotTime() {
        return shotTime;
    }

    @Override
    public void setLastShotTime(long shotTime) {
        this.shotTime = shotTime;
    }

    @Override
    public Tank4Story getStory() {
        return story;
    }

    @Override
    public double getX() {
        return 0;
    }

    @Override
    public double getY() {
        return 0;
    }

    @Override
    public double getHealth() {
        return health;
    }

    @Override
    public void kill(double point) {
        health -= point;
    }

    @Override
    public Bullet createBullet(Vec2 pos, Vec2 vel) {
        return new StyledBullet(getStory(), pos, vel);
    }
}

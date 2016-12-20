package cn.yfwz100.tank4;

import cn.yfwz100.story.Actor;
import org.jbox2d.collision.shapes.CircleShape;
import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.Body;
import org.jbox2d.dynamics.BodyDef;
import org.jbox2d.dynamics.BodyType;

/**
 * The actor of the bullet.
 *
 * @author yfwz100
 */
public abstract class Bullet implements Actor {

    /**
     * The physical body.
     */
    private Body body;

    /**
     * The story of the actor.
     */
    private Tank4Story story;

    /**
     * The flag indicating the life state.
     */
    private boolean alive = true;

    /**
     * Construct a bullet object in the story.
     *
     * @param story the story.
     * @param pos the position.
     * @param vel the velocity.
     */
    protected Bullet(Tank4Story story, Vec2 pos, Vec2 vel) {
        this.story = story;

        BodyDef bodyDef = new BodyDef();
        bodyDef.position = pos;
        bodyDef.fixedRotation = true;
        bodyDef.type = BodyType.DYNAMIC;
        bodyDef.userData = this;
        body = story.getWorld().createBody(bodyDef);

        CircleShape circle = new CircleShape();
        circle.m_radius = 5;
        body.createFixture(circle, 20);
        body.resetMassData();

//        body.setLinearVelocity(vel.mulLocal(body.getMass()));
        body.applyLinearImpulse(vel.mulLocal(body.getMass()), body.getWorldCenter());
    }

    @Override
    public boolean update() {
        return alive;
    }

    @Override
    public Tank4Story getStory() {
        return story;
    }

    @Override
    public double getX() {
        return body.getWorldCenter().x;
    }

    @Override
    public double getY() {
        return body.getWorldCenter().y;
    }

    public void kill() {
        alive = false;
    }

    /**
     * Get the power of the bullet.
     *
     * @return the power.
     */
    public int getPower() {
        return 10;
    }

    /**
     * Get the physical body of the bullet.
     *
     * @return the body.
     */
    protected Body getBody() {
        return body;
    }

    @Override
    public void cleanup() {
        story.getWorld().destroyBody(body);
    }
}

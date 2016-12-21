package cn.yfwz100.tank4;

import cn.yfwz100.story.Actor;
import org.jbox2d.collision.shapes.CircleShape;
import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.Body;
import org.jbox2d.dynamics.BodyDef;
import org.jbox2d.dynamics.BodyType;
import org.jbox2d.dynamics.FixtureDef;

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
    private BaseTank owner;

    /**
     * The flag indicating the life state.
     */
    private boolean alive = true;

    /**
     * Construct a bullet object in the story.
     *
     * @param owner the owner.
     * @param pos   the position.
     * @param vel   the VELOCITY.
     */
    protected Bullet(BaseTank owner, Vec2 pos, Vec2 vel) {
        this.owner = owner;

        BodyDef bodyDef = new BodyDef();
        bodyDef.position = pos;
        bodyDef.fixedRotation = true;
        bodyDef.type = BodyType.DYNAMIC;
        bodyDef.userData = this;
        body = getStory().getWorld().createBody(bodyDef);

        CircleShape circle = new CircleShape();
        circle.m_radius = 0.5f;
        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.density = 0.1f;
        fixtureDef.friction = 0;
        fixtureDef.shape = circle;
        body.createFixture(fixtureDef);

        body.setLinearVelocity(vel);
//        body.applyLinearImpulse(vel.mul(body.getMass()), body.getWorldCenter());
    }

    @Override
    public boolean update() {
        return alive;
    }

    /**
     * The owner of the bullet.
     *
     * @return the owner.
     */
    public BaseTank getOwner() {
        return owner;
    }

    @Override
    public Tank4Story getStory() {
        return owner.getStory();
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
        getStory().getWorld().destroyBody(body);
    }
}

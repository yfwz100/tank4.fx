package cn.yfwz100.tank4;

import cn.yfwz100.story.Actor;
import org.jbox2d.collision.shapes.PolygonShape;
import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.Body;
import org.jbox2d.dynamics.BodyDef;
import org.jbox2d.dynamics.BodyType;

/**
 * The block.
 *
 * @author yfwz100
 */
public abstract class Block implements Actor {

    /**
     * The backed story.
     */
    protected Tank4Story story;

    /**
     * The physical body.
     */
    private Body body;

    /**
     * The body definition.
     */
    private BodyDef bodyDef;

    /**
     * The width of the block.
     */
    private float width;

    /**
     * The height of the block.
     */
    private float height;

    /**
     * Construct a block.
     *
     * @param story     the game story..
     * @param x         the position.
     * @param y         the position.
     * @param width     the width.
     * @param height    the height.
     * @param a         the initialAngle, in radian unit.
     */
    protected Block(Tank4Story story, float x, float y, float width, float height, float a) {
        this.story = story;

        bodyDef = new BodyDef();
        bodyDef.type = BodyType.STATIC;
        bodyDef.angle = a;
        bodyDef.allowSleep = true;
        bodyDef.fixedRotation = true;
        bodyDef.position = new Vec2(x, y);
        bodyDef.userData = this;
        body = story.getWorld().createBody(bodyDef);

        PolygonShape rect = new PolygonShape();
        rect.setAsBox(width / 2, height / 2);
        body.createFixture(rect, 1);

        this.width = width;
        this.height = height;
    }

    @Override
    public boolean update() { return true; }

    @Override
    public Tank4Story getStory() {
        return story;
    }

    @Override
    public void cleanup() {
        story.getWorld().destroyBody(body);
    }

    @Override
    public double getX() {
        return body.getWorldCenter().x;
    }

    @Override
    public double getY() {
        return body.getWorldCenter().y;
    }

    /**
     * Get the width of the block.
     *
     * @return the width.
     */
    public float getWidth() {
        return width;
    }

    /**
     * Get the height of the block.
     *
     * @return the height.
     */
    public float getHeight() {
        return height;
    }

    /**
     * Get the physical body of the actor.
     *
     * @return the body.
     */
    protected Body getBody() {
        return body;
    }

    @Override
    public String toString() {
        return "[Block at" + body.getPosition() + "]";
    }
}

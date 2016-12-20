package cn.yfwz100.tank4.fx.effect;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import org.jbox2d.common.Vec2;

/**
 * The exploding effect.
 *
 * @author yfwz100
 */
public class SimpleExplodeEffect implements VisualEffect {

    /**
     * The time attribute of an effect.
     */
    private int time;

    /**
     * the x coordinate.
     */
    private float x;

    /**
     * the y coordinate.
     */
    private float y;

    /**
     * the radius.
     */
    private float rad;

    /**
     * Construct a simple exploding effect.
     *
     * @param x the x coordinate.
     * @param y the y coordinate.
     */
    private SimpleExplodeEffect(float x, float y) {
        this.x = x;
        this.y = y;
        this.rad = 0;
        this.time = 100;
    }

    /**
     * Construct a simple exploding effect.
     *
     * @param pos the position.
     */
    public SimpleExplodeEffect(Vec2 pos) {
        this(pos.x, pos.y);
    }

    @Override
    public void paint(GraphicsContext g) {
        g.save();
        g.scale(0.5, 0.5);
        g.setFill(new Color(0.8, 1, 0, time / 200.0));
        g.fillOval(x - rad, y - rad, 2 * rad, 2 * rad);
        g.restore();
    }

    @Override
    public boolean update() {
        rad += 0.5f;
        time -= 1f;

        return time > 0;
    }

    @Override
    public double getX() {
        return x;
    }

    @Override
    public double getY() {
        return y;
    }
}

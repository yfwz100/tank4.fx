package cn.yfwz100.tank4;

/**
 * Check if the class is killable.
 *
 * @author yfwz100
 */
public interface Killable {

    default boolean isAlive() {
        return getHealth() > 0;
    }

    /**
     * The health of the killable object.
     * If the health value is less than or equal to 0, the object is dead.
     *
     * @return the health.
     */
    double getHealth();

    /**
     * Decrease the health by given point.
     *
     * @param point the point to decrease.
     */
    void kill(double point);
}

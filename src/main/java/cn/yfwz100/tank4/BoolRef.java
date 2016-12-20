package cn.yfwz100.tank4;

/**
 * The boolean reference holder.
 *
 * @author yfwz100
 */
class BoolRef {

    /**
     * The underlying value.
     */
    private boolean underlying;

    /**
     * Create boolean reference with initial value.
     *
     * @param init the initial value.
     */
    BoolRef(boolean init) {
        this.underlying = init;
    }

    /**
     * Get current value of the reference.
     *
     * @return the current value.
     */
    boolean getValue() {
        return underlying;
    }

    /**
     * Set the value to the given value.
     *
     * @param value the value to set.
     */
    void setValue(boolean value) {
        this.underlying = value;
    }
}

package cn.yfwz100.tank4;

import cn.yfwz100.story.ActorIterator;
import cn.yfwz100.story.Story;

import java.util.Collection;
import java.util.Iterator;

/**
 * Tank4's Game Logic.
 *
 * @author yfwz100
 */
public interface Tank4Story extends Story {

    /**
     * Get the bullets.
     *
     * @return the bullets.
     */
    Collection<Bullet> getBullets();

    /**
     * Get the tanks (AI), except the player's tank.
     *
     * @return the tanks.
     */
    Collection<BaseTank> getTanks();

    /**
     * Get blocks.
     *
     * @return the blocks.
     */
    Collection<Block> getBlocks();

    /**
     * Get the player's tank.
     *
     * @return the tank.
     */
    PlayerTank getPlayer();

    @Override
    default ActorIterator actorIterator() {
        return new ActorIterator(
                getBlocks().iterator(),
                getTanks().iterator(),
                getBullets().iterator(),
                new SingleTankIterator(getPlayer(), this::onEndEvent)
        );
    }

    /**
     * Call on end event.
     */
    void onEndEvent();

    /**
     * The single tank iterator.
     *
     * @author yfwz100
     */
    class SingleTankIterator implements Iterator<PlayerTank> {

        private PlayerTank tank;
        private boolean hasNext = true;
        private Runnable onEndEvent;

        SingleTankIterator(PlayerTank tank, Runnable onEndEvent) {
            this.tank = tank;
            this.onEndEvent = onEndEvent;
        }

        @Override
        public boolean hasNext() {
            return hasNext;
        }

        @Override
        public PlayerTank next() {
            hasNext = false;
            return tank;
        }

        @Override
        public void remove() {
            this.onEndEvent.run();
        }
    }
}

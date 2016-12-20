package cn.yfwz100.story;

import org.jbox2d.dynamics.World;

/**
 * The story of a game.
 *
 * @author yfwz100
 */
public interface Story {

    /**
     * Update the story.
     */
    default Story update() {
        //<editor-fold defaultState="collapsed" desc="world.update();">
        World world = getWorld();
        if (world != null) {
            world.step(1 / 20f, 10, 10);
        }
        //</editor-fold>

        //<editor-fold defaultState="collapsed" desc="actors*.update();">
        ActorIterator actorIterator = actorIterator();
        if (actorIterator != null) {
            while (actorIterator.hasNext()) {
                Actor actor = actorIterator.next();
                boolean alive = actor.update();
                if (!alive) {
                    actorIterator.remove();
                }
            }
        }
        //</editor-fold>

        return this;
    }

    /**
     * Get the physics world of the story.
     *
     * @return the world.
     */
    World getWorld();

    /**
     * Get the actors in the story.
     *
     * @return the iterator of actors.
     */
    ActorIterator actorIterator();

}

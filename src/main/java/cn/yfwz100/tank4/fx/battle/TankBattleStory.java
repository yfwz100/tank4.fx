package cn.yfwz100.tank4.fx.battle;

import cn.yfwz100.story.Story;
import cn.yfwz100.tank4.*;
import cn.yfwz100.tank4.fx.actor.StyledPlayerTank;
import org.jbox2d.callbacks.ContactImpulse;
import org.jbox2d.callbacks.ContactListener;
import org.jbox2d.collision.Manifold;
import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.Body;
import org.jbox2d.dynamics.World;
import org.jbox2d.dynamics.contacts.Contact;

import java.util.Collection;
import java.util.Iterator;
import java.util.concurrent.ConcurrentLinkedDeque;

/**
 * The UI for Tank4Story.
 *
 * @author yfwz100
 */
public abstract class TankBattleStory implements Tank4Story {

    /**
     * The physical world.
     */
    private World world;

    /**
     * The tank controlled by player.
     */
    private final PlayerTank player;

    /**
     * The bullets.
     */
    private final Collection<Bullet> bullets;

    /**
     * The tanks.
     */
    private final Collection<BaseTank> tanks;

    /**
     * The blocks.
     */
    private final Collection<Block> blocks;

    /**
     * The next story.
     */
    private Story nextStory = this;

    /**
     * Construct a UI of the extended story.
     */
    TankBattleStory() {
        world = new World(new Vec2(0, 0));
        world.setContactListener(new ContactListener() {

            @Override
            public void beginContact(Contact contact) {
            }

            @Override
            public void endContact(Contact contact) {
            }

            private void kill(Bullet bullet, Killable obj) {
                bullet.kill();
//                    WorldManifold manifold = new WorldManifold();
//                    contact.getWorldManifold(manifold);
//                    addVisualEffect(new SimpleExploreEffect(manifold.points[0]));
            }

            @Override
            public void preSolve(Contact contact, Manifold oldManifold) {
                Body a = contact.getFixtureA().getBody();
                Body b = contact.getFixtureB().getBody();
                if (a.getUserData() instanceof Bullet) {
                    Bullet bullet = (Bullet) a.getUserData();
                    bullet.kill();
                    if (b.getUserData() instanceof Killable) {
                        ((Killable) b.getUserData()).kill(bullet.getPower());
                    }
                }
                if (b.getUserData() instanceof Bullet) {
                    Bullet bullet = (Bullet) b.getUserData();
                    bullet.kill();
                    if (a.getUserData() instanceof Killable) {
                        ((Killable) a.getUserData()).kill(bullet.getPower());
                    }
                }
            }

            @Override
            public void postSolve(Contact contact, ContactImpulse impulse) {
            }
        });

        // place on (0, 0) of the world.
        player = new StyledPlayerTank(this, 0, 0);

        bullets = new ConcurrentLinkedDeque<>();

        tanks = new ConcurrentLinkedDeque<>();

        blocks = new ConcurrentLinkedDeque<>();
    }

    @Override
    public Story update() {
        Tank4Story.super.update();

        return nextStory;
    }

    @Override
    public Collection<Bullet> getBullets() {
        return bullets;
    }

    @Override
    public Collection<BaseTank> getTanks() {
        return tanks;
    }

    @Override
    public PlayerTank getPlayer() {
        return player;
    }

    @Override
    public Collection<Block> getBlocks() {
        return blocks;
    }

    @Override
    public World getWorld() {
        return world;
    }

    @Override
    public void onEndEvent() {
        nextStory = null;
    }
}

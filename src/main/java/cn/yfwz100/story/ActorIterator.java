package cn.yfwz100.story;

import java.lang.ref.WeakReference;
import java.util.*;

/**
 * The iterator of actor.
 *
 * @author yfwz100
 */
public class ActorIterator implements Iterator<Actor> {

    /**
     * The encapsulated iterator.
     */
    private Queue<Iterator<? extends Actor>> actorIterators;

    /**
     * The current actor (weak reference).
     */
    private WeakReference<? extends Actor> currentActorRef;

    /**
     * Enncapsulate the real iterator.
     *
     * @param actorIterators the encapsulated iterator.
     */
    @SafeVarargs
    public ActorIterator(Iterator<? extends Actor> ... actorIterators) {
        this.actorIterators = new ArrayDeque<>(actorIterators.length);
        appendIterators(actorIterators);
    }

    @SafeVarargs
    public final ActorIterator appendIterators(Iterator<? extends Actor> ... actorIterators) {
        Collections.addAll(this.actorIterators, actorIterators);
        return this;
    }

    @SafeVarargs
    public final ActorIterator prependIterators(Iterator<? extends Actor> ... actorIterators) {
        for (Iterator<? extends Actor> actorIterator: actorIterators) {
            this.actorIterators.offer(actorIterator);
        }
        return this;
    }

    @Override
    public boolean hasNext() {
        while (!actorIterators.isEmpty()) {
            Iterator<? extends Actor> actorIterator = actorIterators.peek();
            boolean hasNext = actorIterator.hasNext();
            if (!hasNext) {
                actorIterators.poll();
            } else {
                return true;
            }
        }
        return false;
    }

    @Override
    public Actor next() {
        try {
            Actor actor = actorIterators.peek().next();
            currentActorRef = new WeakReference<>(actor);
            return actor;
        } catch (Exception e) {
            // set the current actor reference to null so that the remove() method is invalid.
            currentActorRef = null;
            throw e;
        }
    }

    /**
     * Remove the actor and do the related clean-up stuff.
     */
    @Override
    public void remove() {
        Actor actor = currentActorRef.get();
        if (actor != null) {
            actor.cleanup();
        }
        actorIterators.peek().remove();
    }
}

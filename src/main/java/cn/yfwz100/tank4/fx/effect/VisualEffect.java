package cn.yfwz100.tank4.fx.effect;

import cn.yfwz100.story.Actor;
import cn.yfwz100.story.Story;
import cn.yfwz100.story.fx.ActorGraphics;
import cn.yfwz100.tank4.Killable;

/**
 * The basic interface for visual effect.
 *
 * @author yfwz100
 */
public interface VisualEffect extends Actor, ActorGraphics {

    /**
     * Visual effect does not need a story.
     * @return null
     */
    @Override
    default Story getStory() {
        return null;
    }
}

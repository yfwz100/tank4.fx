package cn.yfwz100.story;

/**
 * The Score Board of the Story.
 * Different story should implements its own tracking and scoring strategy.
 *
 * @author yfwz100
 */
public interface ScoreBoard {

    /**
     * Get the (current) score of the story.
     *
     * @return the score.
     */
    double getScore();
}

package cn.yfwz100.tank4;

import java.util.Map;

import cn.yfwz100.story.ScoreBoard;

/**
 * The Score Board for Tank Game.
 *
 * @author yfwz100
 */
public interface TankScoreBoard extends ScoreBoard {

    /**
     * Get the maximum times of continuous killing.
     *
     * @return the killing times.
     */
    int getMaxKillTimes();

    /**
     * Get the kill times and related times.
     *
     * @return the map.
     */
    Map<Integer, Integer> getKillTimesCounter();

    /**
     * Get the description of the current game board.
     *
     * @return the description.
     */
    default String getDescription() {
        double score = getScore();
        if (score < 100) {
            return "Needs Fighting!";
        } else if (score < 500) {
            return "Ordinary Hero!";
        } else {
            return "Extraordinary!";
        }
    }
}

package cn.yfwz100.tank4.fx.battle;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.Map;
import java.util.HashMap;
import static java.util.stream.Collectors.toMap;

import cn.yfwz100.tank4.BaseTank;
import cn.yfwz100.tank4.TankScoreBoard;

/**
 * The implementation of tank score board.
 *
 * @author yfwz100
 */
class TankScoreBoardImpl implements TankScoreBoard {

    /**
     * The maximum kill times.
     */
    private int maxKillTimes = 0;
    private Map<Integer, AtomicInteger> killTimesMap = new HashMap<>();

    private int lastKillTimes = 0;
    private BaseTank lastTank;

    /**
     * Track that the player kills the given tank.
     *
     * @param tank the tank to kill.
     */
    void trackPlayerKill(BaseTank tank) {
        if (tank == lastTank) {
            lastKillTimes += 1;
        } else {
            if (lastKillTimes > maxKillTimes) {
                maxKillTimes = lastKillTimes;
            }
            killTimesMap.computeIfAbsent(lastKillTimes, k -> new AtomicInteger()).incrementAndGet();
            lastKillTimes = 0;
        }
        lastTank = tank;
    }

    @Override
    public Map<Integer, Integer> getKillTimesCounter() {
        return killTimesMap.entrySet().stream().collect(toMap(Map.Entry::getKey, p -> p.getValue().get()));
    }

    @Override
    public int getMaxKillTimes() {
        return maxKillTimes;
    }

    @Override
    public double getScore() {
        return maxKillTimes * 50;
    }

    @Override
    public String toString() {
        return "TankScoreBoardImpl{" +
                "maxKillTimes=" + maxKillTimes +
                ", killTimesMap=" + killTimesMap +
                ", lastKillTimes=" + lastKillTimes +
                ", lastTank=" + lastTank +
                '}';
    }
}

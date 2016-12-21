package cn.yfwz100.tank4.fx.battle;

import cn.yfwz100.tank4.fx.actor.StyledBlock;
import cn.yfwz100.tank4.fx.actor.StyledSimpleAITank;

/**
 * The basic implementation of tank battle.
 *
 * @author yfwz100
 */
public class BasicTankBattleStory extends TankBattleStory {

    //<editor-fold desc="Init the environment of the battle.">
    {
        getTanks().add(new StyledSimpleAITank(this, 10, 30));
        getTanks().add(new StyledSimpleAITank(this, 20, 45));
        getTanks().add(new StyledSimpleAITank(this, 3, 65));
        getTanks().add(new StyledSimpleAITank(this, 100, 70));

        getBlocks().add(new StyledBlock(this, 0, 20, 30, 1, (float) (Math.PI / 18)));
        getBlocks().add(new StyledBlock(this, 18, 40, 20, 1, (float) (-Math.PI / 18)));
        getBlocks().add(new StyledBlock(this, 70, 60, 30, 1, 0));
        getBlocks().add(new StyledBlock(this, 50, 20, 20, 1, (float) (Math.PI / 2)));
    }
    //</editor-fold>

}

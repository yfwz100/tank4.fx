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
        getTanks().add(new StyledSimpleAITank(this, 100, 300));
        getTanks().add(new StyledSimpleAITank(this, 200, 450));
        getTanks().add(new StyledSimpleAITank(this, 300, 650));
        getTanks().add(new StyledSimpleAITank(this, 1000, 700));

        getBlocks().add(new StyledBlock(this, 0, 200, 300, 10, (float) (Math.PI / 18)));
        getBlocks().add(new StyledBlock(this, 180, 400, 200, 10, (float) (-Math.PI / 18)));
        getBlocks().add(new StyledBlock(this, 700, 600, 300, 10, 0));
        getBlocks().add(new StyledBlock(this, 500, 200, 200, 10, (float) (Math.PI / 2)));
    }
    //</editor-fold>

}

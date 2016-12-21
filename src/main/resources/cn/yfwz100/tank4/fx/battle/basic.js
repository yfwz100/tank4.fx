//load("nashorn:mozilla_compat.js");

//cn.yfwz100.tank4.fx.actor.StyledSimpleAITank;
//cn.yfwz100.tank4.fx.actor.StyledBlock;

var StyledSimpleAITank = Packages.cn.yfwz100.tank4.fx.actor.StyledSimpleAITank;
var StyledBlock = Packages.cn.yfwz100.tank4.fx.actor.StyledBlock;

// initialize the tanks.
battle.getTanks().add(new StyledSimpleAITank(battle,  10, 30));
battle.getTanks().add(new StyledSimpleAITank(battle,  20, 45));
battle.getTanks().add(new StyledSimpleAITank(battle,   3, 65));
battle.getTanks().add(new StyledSimpleAITank(battle, 100, 70));

// initialize the blocks.
battle.getBlocks().add(new StyledBlock(battle, 0, 20, 30, 1, Math.PI / 18));
battle.getBlocks().add(new StyledBlock(battle, 18, 40, 20, 1, -Math.PI / 18));
battle.getBlocks().add(new StyledBlock(battle, 70, 60, 30, 1, 0));
battle.getBlocks().add(new StyledBlock(battle, 50, 20, 20, 1, Math.PI / 2));

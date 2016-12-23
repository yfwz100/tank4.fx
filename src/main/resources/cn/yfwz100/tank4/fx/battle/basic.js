//load("nashorn:mozilla_compat.js");

//cn.yfwz100.tank4.fx.actor.StyledSimpleAITank;
//cn.yfwz100.tank4.fx.actor.StyledBlock;

var StyledSimpleAITank = Packages.cn.yfwz100.tank4.fx.actor.StyledSimpleAITank;
var StyledBlock = Packages.cn.yfwz100.tank4.fx.actor.StyledBlock;

function block(x, y, w, h) {
    x /= 5;
    y /= 5;
    w /= 5;
    h /= 5;
    return new StyledBlock(battle, x + w / 2, y + h / 2, w, h, 0);
}

function tank(x, y) {
    return new StyledSimpleAITank(battle, x / 5, y / 5);
}

// the border of the battle.
//battle.getBlocks().add(new StyledBlock(battle,  60,  -1, 120,  1, 0));
//battle.getBlocks().add(new StyledBlock(battle, 121,  40,   1, 80, 0));
//battle.getBlocks().add(new StyledBlock(battle,  60,  81, 120,  1, 0));
//battle.getBlocks().add(new StyledBlock(battle,   -1,  40,   1, 80, 0));

battle.getBlocks().add(block(0, -1, 600, 1, 0));
battle.getBlocks().add(block(601, 0, 1, 400));
battle.getBlocks().add(block(0, 400, 600, 1));
battle.getBlocks().add(block(-1, 0, 1, 400));

// initialize the tanks.

//battle.getTanks().add(tank( 20, 45));
//battle.getTanks().add(tank(  3, 65));
//battle.getTanks().add(tank(100, 70));

//function randomAddTank() {
//    var x = math.random() * 600, y = math.random() * 400;
//    battle.getTanks().add(tank(x, y));
//}
//setTimeout(randomAddTank, 5000);

battle.getBlocks().add(block(100,  0, 10,100));
battle.getBlocks().add(block(100,100, 90, 10));

battle.getTanks().add(tank(500, 20));
battle.getBlocks().add(block(500,  0, 10,100));
battle.getBlocks().add(block(450,100, 90, 10));

battle.getTanks().add(tank(300, 10));
battle.getBlocks().add(block(  0,300,150, 10));
battle.getBlocks().add(block(150,250, 10,100));

for (var i=0; i<25; i++) {
    battle.getTanks().add(tank(300 + (i%5) * 50, 200 + (i/5) * 50));
}

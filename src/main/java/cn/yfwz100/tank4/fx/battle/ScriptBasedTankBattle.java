package cn.yfwz100.tank4.fx.battle;

import javax.script.*;
import java.io.InputStreamReader;

/**
 * Delegation to JS-based tank battle definition.
 *
 * @author yfwz100
 */
public class ScriptBasedTankBattle extends TankBattleStory {

    /**
     * The script engine.
     */
    private static final ScriptEngine scriptEngine;

    static {
        ScriptEngineManager scriptEngineManager = new ScriptEngineManager();
        scriptEngine = scriptEngineManager.getEngineByMimeType("text/javascript");
    }

    /**
     * Construct a script-based tank battle.
     * The given script should be within the same package so as to search for.
     *
     * @param name the name of the script.
     * @throws ScriptException if the script encounters any problems.
     */
    public ScriptBasedTankBattle(String name) throws ScriptException {
        scriptEngine.put("battle", this);
        scriptEngine.eval(new InputStreamReader(getClass().getResourceAsStream(name)));
    }
}

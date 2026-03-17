package com.goldcascade601180.app.screens;

import com.goldcascade601180.app.Constants;
import com.goldcascade601180.app.MainGame;

/**
 * Deep Vein — intermediate difficulty Plinko level.
 * 7 pegs/row × 8 rows, 6 carts.
 */
public class DeepVeinScreen extends BaseGameScreen {

    private static final String BG = "backgrounds/game/Pale_bg.png";

    public DeepVeinScreen(MainGame game) {
        super(game, BG);
        restartAction = () -> game.setScreen(new DeepVeinScreen(game));
        initPegs();
    }

    @Override protected int    getPegsPerRow()  { return Constants.PEGS_PER_ROW_DEEP; }
    @Override protected int    getPegRows()     { return Constants.PEG_ROWS_DEEP; }
    @Override protected int[]  getCartValues()  { return Constants.CART_VALUES_DEEP; }
    @Override protected String getMineName()    { return "DEEP VEIN"; }
    @Override protected String getBackgroundPath() { return BG; }
}

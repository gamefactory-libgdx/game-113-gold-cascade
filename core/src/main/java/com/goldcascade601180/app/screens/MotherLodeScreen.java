package com.goldcascade601180.app.screens;

import com.goldcascade601180.app.Constants;
import com.goldcascade601180.app.MainGame;

/**
 * Mother Lode — expert difficulty Plinko level.
 * 9 pegs/row × 10 rows, 8 carts including premium high-value slots.
 */
public class MotherLodeScreen extends BaseGameScreen {

    private static final String BG = "backgrounds/game/Bright_bg.png";

    public MotherLodeScreen(MainGame game) {
        super(game, BG);
        restartAction = () -> game.setScreen(new MotherLodeScreen(game));
        initPegs();
    }

    @Override protected int    getPegsPerRow()  { return Constants.PEGS_PER_ROW_MOTHER; }
    @Override protected int    getPegRows()     { return Constants.PEG_ROWS_MOTHER; }
    @Override protected int[]  getCartValues()  { return Constants.CART_VALUES_MOTHER; }
    @Override protected String getMineName()    { return "MOTHER LODE"; }
    @Override protected String getBackgroundPath() { return BG; }
}

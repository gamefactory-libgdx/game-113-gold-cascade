package com.goldcascade601180.app.screens;

import com.goldcascade601180.app.Constants;
import com.goldcascade601180.app.MainGame;

/**
 * Shallow Seam — beginner difficulty Plinko level.
 * 5 pegs/row × 6 rows, 4 carts.
 */
public class ShallowSeamScreen extends BaseGameScreen {

    private static final String BG = "backgrounds/game/Bright_bg.png";

    public ShallowSeamScreen(MainGame game) {
        super(game, BG);
        restartAction = () -> game.setScreen(new ShallowSeamScreen(game));
        initPegs();
    }

    @Override protected int    getPegsPerRow()  { return Constants.PEGS_PER_ROW_SHALLOW; }
    @Override protected int    getPegRows()     { return Constants.PEG_ROWS_SHALLOW; }
    @Override protected int[]  getCartValues()  { return Constants.CART_VALUES_SHALLOW; }
    @Override protected String getMineName()    { return "SHALLOW SEAM"; }
    @Override protected String getBackgroundPath() { return BG; }
}

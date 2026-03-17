package com.goldcascade601180.app;

public class Constants {

    // World dimensions
    public static final float WORLD_WIDTH  = 480f;
    public static final float WORLD_HEIGHT = 854f;

    // Font sizes
    public static final int FONT_SIZE_TITLE  = 48;
    public static final int FONT_SIZE_HEADER = 32;
    public static final int FONT_SIZE_BUTTON = 20;
    public static final int FONT_SIZE_BODY   = 14;
    public static final int FONT_SIZE_SMALL  = 12;
    public static final int FONT_SIZE_SCORE  = 52;

    // UI sizes
    public static final float BUTTON_WIDTH_MAIN   = 280f;
    public static final float BUTTON_WIDTH_WIDE   = 320f;
    public static final float BUTTON_HEIGHT        = 56f;
    public static final float BUTTON_HEIGHT_SMALL  = 40f;
    public static final float CARD_WIDTH           = 320f;
    public static final float CARD_HEIGHT          = 100f;
    public static final float PANEL_WIDTH          = 340f;
    public static final float PANEL_HEIGHT_RESULT  = 380f;
    public static final float PANEL_HEIGHT_GAMEOVER= 420f;
    public static final float PANEL_HEIGHT_SETTINGS= 480f;

    // Game area
    public static final float GAME_AREA_WIDTH  = 340f;
    public static final float GAME_AREA_HEIGHT = 520f;

    // Plinko / pegboard
    public static final int NUGGETS_PER_ROUND = 10;
    public static final float PEG_RADIUS = 8f;
    public static final float NUGGET_RADIUS = 10f;
    public static final float NUGGET_FALL_SPEED = 300f;
    public static final float GRAVITY = -800f;
    public static final float BOUNCE_FACTOR = 0.5f;
    public static final float HORIZONTAL_SCATTER = 120f;

    // Difficulty — peg counts per row
    public static final int PEGS_PER_ROW_SHALLOW    = 5;
    public static final int PEGS_PER_ROW_DEEP       = 7;
    public static final int PEGS_PER_ROW_MOTHER     = 9;
    public static final int PEG_ROWS_SHALLOW        = 6;
    public static final int PEG_ROWS_DEEP           = 8;
    public static final int PEG_ROWS_MOTHER         = 10;

    // Cart counts per difficulty
    public static final int CART_COUNT_SHALLOW = 4;
    public static final int CART_COUNT_DEEP    = 6;
    public static final int CART_COUNT_MOTHER  = 8;

    // Score multipliers — shallow (1 tier)
    public static final int[] CART_VALUES_SHALLOW = { 100, 200, 300, 500 };
    // Score multipliers — deep (2 tiers)
    public static final int[] CART_VALUES_DEEP    = { 100, 150, 200, 300, 400, 600 };
    // Score multipliers — mother lode (3 tiers)
    public static final int[] CART_VALUES_MOTHER  = { 100, 200, 300, 500, 750, 1000, 1500, 3000 };

    // Score counter animation
    public static final float SCORE_ANIM_DURATION = 1.5f;

    // Parallax / animation
    public static final float CLOUD_SPEED    = 20f;
    public static final float FLICKER_PERIOD = 0.4f;
    public static final float PULSE_PERIOD   = 1.2f;

    // Padding / margins
    public static final float PAD_TOP    = 24f;
    public static final float PAD_BOTTOM = 40f;
    public static final float PAD_SIDE   = 20f;
    public static final float GAP_CARD   = 20f;
    public static final float GAP_BUTTON = 16f;

    // Colors (RGBA as floats — use with Color class)
    public static final float[] COLOR_DEEP_MINE_BLUE = { 0.102f, 0.227f, 0.322f, 1f };
    public static final float[] COLOR_GOLD_ORE       = { 1.000f, 0.843f, 0.000f, 1f };
    public static final float[] COLOR_RICH_COPPER    = { 0.722f, 0.451f, 0.200f, 1f };
    public static final float[] COLOR_SLATE_GRAY     = { 0.239f, 0.290f, 0.361f, 1f };
    public static final float[] COLOR_EMERALD        = { 0.180f, 0.800f, 0.443f, 1f };
    public static final float[] COLOR_WARM_AMBER     = { 0.953f, 0.612f, 0.071f, 1f };

    // SharedPreferences keys
    public static final String PREFS_NAME    = "GoldCascadePrefs";
    public static final String PREF_MUSIC    = "musicEnabled";
    public static final String PREF_SFX      = "sfxEnabled";
    public static final String PREF_HIGH_SCORE        = "highScore";
    public static final String PREF_DIFFICULTY        = "difficulty";
    public static final String PREF_TOTAL_SCORE       = "totalScore";
    public static final String PREF_ROUNDS_PLAYED     = "roundsPlayed";
    public static final String PREF_NUGGETS_DROPPED   = "nuggetsDropped";

    // Leaderboard
    public static final int LEADERBOARD_SIZE = 10;
    public static final String PREF_LB_SCORE_PREFIX = "lb_score_";
    public static final String PREF_LB_NAME_PREFIX  = "lb_name_";

    // Difficulty values for settings
    public static final int DIFFICULTY_EASY   = 0;
    public static final int DIFFICULTY_NORMAL = 1;
    public static final int DIFFICULTY_HARD   = 2;
}

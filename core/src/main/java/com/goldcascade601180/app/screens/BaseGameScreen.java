package com.goldcascade601180.app.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.goldcascade601180.app.Constants;
import com.goldcascade601180.app.MainGame;
import com.goldcascade601180.app.UiFactory;

/**
 * Abstract base for all three Plinko gameplay screens.
 * Subclasses supply difficulty parameters; this class handles physics, rendering, input.
 *
 * Layout (world units, portrait 480×854):
 *   y=0  .. y=50  : instruction label area
 *   y=50 .. y=140 : cart area
 *   y=140.. y=800 : peg playing area
 *   y=800.. y=854 : HUD (score, nugget counter, pause button)
 */
public abstract class BaseGameScreen implements Screen {

    // ---- Layout constants ----
    protected static final float AREA_X       = (Constants.WORLD_WIDTH - Constants.GAME_AREA_WIDTH) / 2f; // 70
    protected static final float AREA_WIDTH   = Constants.GAME_AREA_WIDTH;  // 340
    protected static final float CART_Y       = 50f;
    protected static final float CART_H       = 90f;
    protected static final float PEG_BOTTOM   = CART_Y + CART_H;   // 140
    protected static final float PEG_TOP      = 800f;

    // Pause button bounds (top-right)
    protected static final float PAUSE_X = 424f;
    protected static final float PAUSE_Y = 808f;
    protected static final float PAUSE_W = 46f;
    protected static final float PAUSE_H = 36f;

    // ---- Abstract API ----
    protected abstract int    getPegsPerRow();
    protected abstract int    getPegRows();
    protected abstract int[]  getCartValues();
    protected abstract String getMineName();
    protected abstract String getBackgroundPath();

    // ---- Shared state ----
    protected final MainGame game;
    protected final OrthographicCamera camera;
    protected final StretchViewport viewport;
    protected final GlyphLayout layout = new GlyphLayout();

    // Peg grid
    private float[] pegX;
    private float[] pegY;
    private int pegCount;

    // Nugget physics
    private float nugX, nugY, nugVX, nugVY;
    private boolean nugActive      = false;
    private boolean roundComplete  = false;

    // Score
    protected int roundScore     = 0;
    protected int nuggetsDropped = 0;

    // Restart action — set by subclass constructor
    protected Runnable restartAction;

    // Background
    private final String bgPath;

    protected BaseGameScreen(MainGame game, String backgroundPath) {
        this.game  = game;
        this.bgPath = backgroundPath;

        camera   = new OrthographicCamera();
        viewport = new StretchViewport(Constants.WORLD_WIDTH, Constants.WORLD_HEIGHT, camera);

        if (!game.manager.isLoaded(bgPath)) {
            game.manager.load(bgPath, Texture.class);
            game.manager.finishLoading();
        }
        game.playMusic("sounds/music/music_gameplay.ogg");
    }

    /** Must be called by each concrete subclass after super() and after restartAction is set. */
    protected void initPegs() {
        buildPegGrid();
    }

    // ---- Input ----

    @Override
    public void show() {
        Gdx.input.setInputProcessor(new InputAdapter() {
            @Override
            public boolean keyDown(int keycode) {
                if (keycode == Input.Keys.BACK) {
                    game.setScreen(new MainMenuScreen(game));
                    return true;
                }
                return false;
            }
        });
    }

    // ---- Game update ----

    @Override
    public void render(float delta) {
        // Poll touch input every frame
        handleTouch();

        if (!roundComplete) {
            updateNugget(delta);
        }

        draw();
    }

    private void handleTouch() {
        if (!Gdx.input.justTouched()) return;
        Vector3 t = camera.unproject(new Vector3(Gdx.input.getX(), Gdx.input.getY(), 0));

        // Pause button hit?
        if (hit(t, PAUSE_X, PAUSE_Y, PAUSE_W, PAUSE_H)) {
            game.playSound("sounds/sfx/sfx_button_click.ogg");
            game.setScreen(new PauseScreen(game, this, restartAction));
            return;
        }

        // Drop nugget if none is active and round not complete
        if (!nugActive && !roundComplete) {
            float spawnX = MathUtils.clamp(t.x,
                    AREA_X + Constants.NUGGET_RADIUS,
                    AREA_X + AREA_WIDTH - Constants.NUGGET_RADIUS);
            nugX  = spawnX;
            nugY  = PEG_TOP - 20f;
            nugVX = 0f;
            nugVY = 0f;
            nugActive = true;
        }
    }

    private void updateNugget(float delta) {
        if (!nugActive) return;

        // Gravity
        nugVY += Constants.GRAVITY * delta;
        nugX  += nugVX * delta;
        nugY  += nugVY * delta;

        float sumR = Constants.PEG_RADIUS + Constants.NUGGET_RADIUS;

        // Peg collisions
        for (int i = 0; i < pegCount; i++) {
            float dx = nugX - pegX[i];
            float dy = nugY - pegY[i];
            float distSq = dx * dx + dy * dy;
            if (distSq < sumR * sumR && distSq > 0.001f) {
                float dist = (float) Math.sqrt(distSq);
                float nx   = dx / dist;
                float ny   = dy / dist;
                // Push nugget out of overlap
                float overlap = sumR - dist;
                nugX += nx * overlap;
                nugY += ny * overlap;
                // Reflect velocity and dampen
                float dot = nugVX * nx + nugVY * ny;
                nugVX = (nugVX - 2f * dot * nx) * Constants.BOUNCE_FACTOR;
                nugVY = (nugVY - 2f * dot * ny) * Constants.BOUNCE_FACTOR;
                // Slight random horizontal nudge for variety
                nugVX += MathUtils.random(-80f, 80f);
            }
        }

        // Wall collisions
        float leftWall  = AREA_X;
        float rightWall = AREA_X + AREA_WIDTH;
        if (nugX - Constants.NUGGET_RADIUS < leftWall) {
            nugX  = leftWall + Constants.NUGGET_RADIUS;
            nugVX = Math.abs(nugVX) * Constants.BOUNCE_FACTOR;
        }
        if (nugX + Constants.NUGGET_RADIUS > rightWall) {
            nugX  = rightWall - Constants.NUGGET_RADIUS;
            nugVX = -Math.abs(nugVX) * Constants.BOUNCE_FACTOR;
        }

        // Landed in cart area?
        if (nugY < CART_Y + CART_H || nugY < 0) {
            landNugget();
        }
    }

    private void landNugget() {
        int   cartCount  = getCartValues().length;
        float cartW      = AREA_WIDTH / cartCount;
        int   cartIdx    = MathUtils.clamp((int) ((nugX - AREA_X) / cartW), 0, cartCount - 1);
        roundScore      += getCartValues()[cartIdx];
        nuggetsDropped++;
        nugActive = false;
        game.playSound("sounds/sfx/sfx_coin.ogg");

        if (nuggetsDropped >= Constants.NUGGETS_PER_ROUND) {
            roundComplete = true;
            game.playSound("sounds/sfx/sfx_level_complete.ogg");
            game.setScreen(new RoundResultScreen(game, roundScore, nuggetsDropped, getMineName(), restartAction));
        }
    }

    // ---- Rendering ----

    private void draw() {
        Gdx.gl.glClearColor(UiFactory.COLOR_BACKGROUND.r, UiFactory.COLOR_BACKGROUND.g,
                UiFactory.COLOR_BACKGROUND.b, 1f);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        camera.update();
        game.batch.setProjectionMatrix(camera.combined);
        game.shapeRenderer.setProjectionMatrix(camera.combined);

        // Background texture
        game.batch.begin();
        game.batch.draw(game.manager.get(bgPath, Texture.class),
                0, 0, Constants.WORLD_WIDTH, Constants.WORLD_HEIGHT);
        game.batch.end();

        // Semi-transparent overlay for game area
        Gdx.gl.glEnable(GL20.GL_BLEND);
        Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);
        game.shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        game.shapeRenderer.setColor(0f, 0f, 0f, 0.35f);
        game.shapeRenderer.rect(AREA_X, CART_Y, AREA_WIDTH, PEG_TOP - CART_Y + 4f);
        game.shapeRenderer.end();

        // Carts
        drawCarts();

        // Pegs
        Gdx.gl.glEnable(GL20.GL_BLEND);
        Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);
        game.shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        game.shapeRenderer.setColor(0.24f, 0.29f, 0.36f, 1f);
        for (int i = 0; i < pegCount; i++) {
            game.shapeRenderer.circle(pegX[i], pegY[i], Constants.PEG_RADIUS, 12);
        }

        // Active nugget
        if (nugActive) {
            game.shapeRenderer.setColor(1f, 0.85f, 0f, 1f);
            game.shapeRenderer.circle(nugX, nugY, Constants.NUGGET_RADIUS, 14);
        }
        game.shapeRenderer.end();

        // HUD text
        drawHUD();

        // Pause button
        UiFactory.drawButton(game.shapeRenderer, game.batch, game.fontSmall, "II",
                PAUSE_X, PAUSE_Y, PAUSE_W, PAUSE_H);

        // Instruction when waiting
        if (!nugActive && !roundComplete) {
            game.batch.begin();
            game.fontSmall.setColor(UiFactory.COLOR_PRIMARY);
            layout.setText(game.fontSmall, "TAP TO DROP");
            game.fontSmall.draw(game.batch, "TAP TO DROP",
                    (Constants.WORLD_WIDTH - layout.width) / 2f, 38f);
            game.batch.end();
        }
    }

    private void drawCarts() {
        int   count   = getCartValues().length;
        float cartW   = AREA_WIDTH / count;
        int[] values  = getCartValues();

        Gdx.gl.glEnable(GL20.GL_BLEND);
        Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);
        game.shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);

        for (int i = 0; i < count; i++) {
            float t  = (count > 1) ? (float) i / (count - 1) : 0f;
            // Gradient: copper (#B87333) at t=0 → gold (#FFD700) at t=1
            float cr = 0.72f + t * 0.28f;
            float cg = 0.45f + t * 0.40f;
            float cb = 0.20f;
            game.shapeRenderer.setColor(cr, cg, cb, 0.85f);
            game.shapeRenderer.rect(AREA_X + i * cartW + 2f, CART_Y + 2f, cartW - 4f, CART_H - 4f);
        }
        game.shapeRenderer.end();

        // Cart point labels
        game.batch.begin();
        for (int i = 0; i < count; i++) {
            float cx = AREA_X + i * cartW;
            String label = String.valueOf(values[i]);
            game.fontSmall.setColor(1f, 1f, 1f, 1f);
            layout.setText(game.fontSmall, label);
            game.fontSmall.draw(game.batch, label,
                    cx + (cartW - layout.width) / 2f,
                    CART_Y + CART_H / 2f + layout.height / 2f);
        }
        game.batch.end();
    }

    private void drawHUD() {
        game.batch.begin();

        // Mine name top-center
        game.fontBody.setColor(UiFactory.COLOR_PRIMARY);
        layout.setText(game.fontBody, getMineName());
        game.fontBody.draw(game.batch, getMineName(),
                (Constants.WORLD_WIDTH - layout.width) / 2f, 845f);

        // Score top-right area
        String scoreStr = "SCORE: " + roundScore;
        game.fontBody.setColor(UiFactory.COLOR_TEXT);
        game.fontBody.draw(game.batch, scoreStr, AREA_X, 845f);

        // Nuggets left top-left
        int left = Constants.NUGGETS_PER_ROUND - nuggetsDropped;
        String nugStr = "NUGGETS: " + left;
        game.fontSmall.setColor(UiFactory.COLOR_ACCENT);
        layout.setText(game.fontSmall, nugStr);
        game.fontSmall.draw(game.batch, nugStr,
                AREA_X + AREA_WIDTH - layout.width, 830f);

        game.batch.end();
    }

    // ---- Lifecycle ----

    @Override public void resize(int w, int h) { viewport.update(w, h, true); }
    @Override public void pause()  {}
    @Override public void resume() {}
    @Override public void hide()   {}

    @Override
    public void dispose() {
        if (game.manager.isLoaded(bgPath)) game.manager.unload(bgPath);
    }

    // ---- Peg grid builder ----

    private void buildPegGrid() {
        int   rows      = getPegRows();
        int   perRow    = getPegsPerRow();
        float rowH      = (PEG_TOP - PEG_BOTTOM) / (rows + 1f);
        float colW      = AREA_WIDTH / (perRow + 1f);

        // Count total pegs
        int total = 0;
        for (int r = 0; r < rows; r++) {
            total += (r % 2 == 0) ? perRow : Math.max(1, perRow - 1);
        }
        pegX = new float[total];
        pegY = new float[total];

        int idx = 0;
        for (int r = 0; r < rows; r++) {
            float y       = PEG_BOTTOM + (r + 1f) * rowH;
            int   count   = (r % 2 == 0) ? perRow : Math.max(1, perRow - 1);
            float offset  = (r % 2 == 0) ? 0f : colW / 2f;
            for (int c = 0; c < count; c++) {
                pegX[idx] = AREA_X + offset + (c + 1f) * colW;
                pegY[idx] = y;
                idx++;
            }
        }
        pegCount = total;
    }

    protected boolean hit(Vector3 t, float x, float y, float w, float h) {
        return t.x >= x && t.x <= x + w && t.y >= y && t.y <= y + h;
    }
}

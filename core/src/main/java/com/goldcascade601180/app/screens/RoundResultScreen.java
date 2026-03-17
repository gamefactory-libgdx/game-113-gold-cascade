package com.goldcascade601180.app.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.goldcascade601180.app.Constants;
import com.goldcascade601180.app.MainGame;
import com.goldcascade601180.app.UiFactory;

/**
 * Shown after all 10 nuggets are dropped in a mine run.
 * Animates the score counter, then offers "Play Again" or "End Session".
 */
public class RoundResultScreen implements Screen {

    private final MainGame game;
    private final int      finalScore;
    private final int      nuggetsDropped;
    private final String   mineName;
    private final Runnable replayAction;  // recreates the same mine type

    private final OrthographicCamera camera;
    private final StretchViewport    viewport;
    private final GlyphLayout        layout = new GlyphLayout();

    // Animated score counter
    private float displayScore = 0f;
    private float animTimer    = 0f;
    private static final float ANIM_DURATION = Constants.SCORE_ANIM_DURATION;

    // Button geometry
    private static final float BTN_W      = Constants.BUTTON_WIDTH_MAIN;
    private static final float BTN_H      = Constants.BUTTON_HEIGHT;
    private static final float BTN_X      = (Constants.WORLD_WIDTH - BTN_W) / 2f;
    private static final float PLAY_Y     = 180f;
    private static final float SESSION_Y  = PLAY_Y     - BTN_H - Constants.GAP_BUTTON;
    private static final float MENU_Y     = SESSION_Y  - BTN_H - Constants.GAP_BUTTON;

    public RoundResultScreen(MainGame game, int score, int nuggetsDropped,
                             String mineName, Runnable replayAction) {
        this.game           = game;
        this.finalScore     = score;
        this.nuggetsDropped = nuggetsDropped;
        this.mineName       = mineName;
        this.replayAction   = replayAction;

        camera   = new OrthographicCamera();
        viewport = new StretchViewport(Constants.WORLD_WIDTH, Constants.WORLD_HEIGHT, camera);

        game.playMusic("sounds/music/music_menu.ogg");
    }

    @Override
    public void show() {
        Gdx.input.setInputProcessor(new InputAdapter() {
            @Override
            public boolean touchDown(int sx, int sy, int pointer, int button) {
                Vector3 t = camera.unproject(new Vector3(sx, sy, 0));
                if (hit(t, BTN_X, PLAY_Y, BTN_W, BTN_H)) {
                    game.playSound("sounds/sfx/sfx_button_click.ogg");
                    // Play Again — same mine
                    replayAction.run();
                    return true;
                }
                if (hit(t, BTN_X, SESSION_Y, BTN_W, BTN_H)) {
                    game.playSound("sounds/sfx/sfx_button_click.ogg");
                    // End Session → GameOverScreen with session total
                    game.setScreen(new GameOverScreen(game, finalScore, nuggetsDropped));
                    return true;
                }
                if (hit(t, BTN_X, MENU_Y, BTN_W, BTN_H)) {
                    game.playSound("sounds/sfx/sfx_button_back.ogg");
                    game.setScreen(new MainMenuScreen(game));
                    return true;
                }
                return false;
            }

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

    @Override
    public void render(float delta) {
        // Animate score counter
        animTimer += delta;
        float progress  = Math.min(animTimer / ANIM_DURATION, 1f);
        displayScore    = finalScore * progress;

        Gdx.gl.glClearColor(UiFactory.COLOR_BACKGROUND.r, UiFactory.COLOR_BACKGROUND.g,
                UiFactory.COLOR_BACKGROUND.b, 1f);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        camera.update();
        game.batch.setProjectionMatrix(camera.combined);
        game.shapeRenderer.setProjectionMatrix(camera.combined);

        // Result panel background
        float panelW = Constants.PANEL_WIDTH;
        float panelH = Constants.PANEL_HEIGHT_RESULT;
        float panelX = (Constants.WORLD_WIDTH  - panelW) / 2f;
        float panelY = MENU_Y - 30f;

        Gdx.gl.glEnable(GL20.GL_BLEND);
        Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);
        game.shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        game.shapeRenderer.setColor(0.18f, 0.35f, 0.48f, 0.88f);
        game.shapeRenderer.rect(panelX, panelY, panelW, panelH);
        game.shapeRenderer.end();

        // Text
        game.batch.begin();

        // "ROUND COMPLETE"
        game.fontTitle.setColor(UiFactory.COLOR_PRIMARY);
        layout.setText(game.fontTitle, "ROUND COMPLETE");
        game.fontTitle.draw(game.batch, "ROUND COMPLETE",
                (Constants.WORLD_WIDTH - layout.width) / 2f, 780f);

        // Mine name
        game.fontBody.setColor(UiFactory.COLOR_TEXT);
        layout.setText(game.fontBody, mineName);
        game.fontBody.draw(game.batch, mineName,
                (Constants.WORLD_WIDTH - layout.width) / 2f, 730f);

        // Animated score
        String scoreStr = (int) displayScore + " PTS";
        game.fontScore.setColor(UiFactory.COLOR_PRIMARY);
        layout.setText(game.fontScore, scoreStr);
        game.fontScore.draw(game.batch, scoreStr,
                (Constants.WORLD_WIDTH - layout.width) / 2f, 650f);

        // Breakdown
        game.fontSmall.setColor(UiFactory.COLOR_ACCENT);
        String nuggStr = "Gold Collected: " + nuggetsDropped + " nuggets";
        layout.setText(game.fontSmall, nuggStr);
        game.fontSmall.draw(game.batch, nuggStr,
                (Constants.WORLD_WIDTH - layout.width) / 2f, 580f);

        game.fontSmall.setColor(UiFactory.COLOR_TEXT);
        String avgStr = "Average per nugget: " + (nuggetsDropped > 0 ? finalScore / nuggetsDropped : 0);
        layout.setText(game.fontSmall, avgStr);
        game.fontSmall.draw(game.batch, avgStr,
                (Constants.WORLD_WIDTH - layout.width) / 2f, 555f);

        game.batch.end();

        // Buttons
        UiFactory.drawButton(game.shapeRenderer, game.batch, game.fontBody,
                "PLAY AGAIN",   BTN_X, PLAY_Y,    BTN_W, BTN_H);
        UiFactory.drawButton(game.shapeRenderer, game.batch, game.fontBody,
                "END SESSION",  BTN_X, SESSION_Y,  BTN_W, BTN_H);
        UiFactory.drawButton(game.shapeRenderer, game.batch, game.fontBody,
                "MAIN MENU",    BTN_X, MENU_Y,     BTN_W, BTN_H);
    }

    @Override public void resize(int w, int h) { viewport.update(w, h, true); }
    @Override public void pause()  {}
    @Override public void resume() {}
    @Override public void hide()   {}
    @Override public void dispose() {}

    private boolean hit(Vector3 t, float x, float y, float w, float h) {
        return t.x >= x && t.x <= x + w && t.y >= y && t.y <= y + h;
    }
}

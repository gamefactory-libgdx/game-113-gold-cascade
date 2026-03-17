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
 * Pause overlay — shown when the player taps the pause button during gameplay.
 *
 * Resume    → returns to the SAME previous screen instance (show() is called on it).
 * Restart   → invokes restartAction which creates a fresh gameplay screen instance.
 * Main Menu → goes to MainMenuScreen.
 */
public class PauseScreen implements Screen {

    private final MainGame game;
    private final Screen   previousScreen;
    private final Runnable restartAction;

    private final OrthographicCamera camera;
    private final StretchViewport    viewport;
    private final GlyphLayout        layout = new GlyphLayout();

    // Button geometry — centred vertical stack
    private static final float BTN_W = Constants.BUTTON_WIDTH_MAIN;
    private static final float BTN_H = Constants.BUTTON_HEIGHT;
    private static final float BTN_X = (Constants.WORLD_WIDTH - BTN_W) / 2f;

    private static final float RESUME_Y  = 480f;
    private static final float RESTART_Y = RESUME_Y  - BTN_H - Constants.GAP_BUTTON;
    private static final float MENU_Y    = RESTART_Y - BTN_H - Constants.GAP_BUTTON;

    public PauseScreen(MainGame game, Screen previousScreen, Runnable restartAction) {
        this.game           = game;
        this.previousScreen = previousScreen;
        this.restartAction  = restartAction;

        camera   = new OrthographicCamera();
        viewport = new StretchViewport(Constants.WORLD_WIDTH, Constants.WORLD_HEIGHT, camera);
    }

    @Override
    public void show() {
        Gdx.input.setInputProcessor(new InputAdapter() {
            @Override
            public boolean touchDown(int sx, int sy, int pointer, int button) {
                Vector3 t = camera.unproject(new Vector3(sx, sy, 0));
                if (hit(t, BTN_X, RESUME_Y,  BTN_W, BTN_H)) {
                    game.playSound("sounds/sfx/sfx_button_click.ogg");
                    // Resume: go back to the SAME screen instance so state is preserved
                    game.setScreen(previousScreen);
                    return true;
                }
                if (hit(t, BTN_X, RESTART_Y, BTN_W, BTN_H)) {
                    game.playSound("sounds/sfx/sfx_button_click.ogg");
                    // Restart: new instance — reset all game state
                    restartAction.run();
                    return true;
                }
                if (hit(t, BTN_X, MENU_Y,    BTN_W, BTN_H)) {
                    game.playSound("sounds/sfx/sfx_button_back.ogg");
                    game.setScreen(new MainMenuScreen(game));
                    return true;
                }
                return false;
            }

            @Override
            public boolean keyDown(int keycode) {
                if (keycode == Input.Keys.BACK) {
                    // Back = resume
                    game.setScreen(previousScreen);
                    return true;
                }
                return false;
            }
        });
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(UiFactory.COLOR_BACKGROUND.r, UiFactory.COLOR_BACKGROUND.g,
                UiFactory.COLOR_BACKGROUND.b, 1f);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        camera.update();
        game.batch.setProjectionMatrix(camera.combined);
        game.shapeRenderer.setProjectionMatrix(camera.combined);

        // Dark overlay panel
        Gdx.gl.glEnable(GL20.GL_BLEND);
        Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);
        game.shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        game.shapeRenderer.setColor(0f, 0f, 0f, 0.75f);
        float panelW = 320f;
        float panelH = 360f;
        float panelX = (Constants.WORLD_WIDTH  - panelW) / 2f;
        float panelY = MENU_Y - 20f;
        game.shapeRenderer.rect(panelX, panelY, panelW, panelH);
        game.shapeRenderer.end();

        // Title
        game.batch.begin();
        game.fontTitle.setColor(UiFactory.COLOR_PRIMARY);
        layout.setText(game.fontTitle, "PAUSED");
        game.fontTitle.draw(game.batch, "PAUSED",
                (Constants.WORLD_WIDTH - layout.width) / 2f, RESUME_Y + BTN_H + 60f);
        game.batch.end();

        // Buttons
        UiFactory.drawButton(game.shapeRenderer, game.batch, game.fontBody,
                "RESUME",    BTN_X, RESUME_Y,  BTN_W, BTN_H);
        UiFactory.drawButton(game.shapeRenderer, game.batch, game.fontBody,
                "RESTART",   BTN_X, RESTART_Y, BTN_W, BTN_H);
        UiFactory.drawButton(game.shapeRenderer, game.batch, game.fontBody,
                "MAIN MENU", BTN_X, MENU_Y,    BTN_W, BTN_H);
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

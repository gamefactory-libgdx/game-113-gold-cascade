package com.goldcascade601180.app.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.goldcascade601180.app.Constants;
import com.goldcascade601180.app.MainGame;
import com.goldcascade601180.app.UiFactory;

public class GameOverScreen implements Screen {

    private final MainGame          game;
    private final int               score;
    private final int               extra;      // nuggets collected this round
    private final int               bestScore;

    private final OrthographicCamera camera;
    private final StretchViewport   viewport;
    private final Stage             stage;
    private final GlyphLayout       layout = new GlyphLayout();

    private final float btnW   = Constants.BUTTON_WIDTH_MAIN;
    private final float btnH   = Constants.BUTTON_HEIGHT;
    private final float btnX;
    private final float retryY = 240f;
    private final float menuY  = retryY - Constants.BUTTON_HEIGHT - Constants.GAP_BUTTON;

    public GameOverScreen(MainGame game, int score, int extra) {
        this.game  = game;
        this.score = score;
        this.extra = extra;

        camera   = new OrthographicCamera();
        viewport = new StretchViewport(Constants.WORLD_WIDTH, Constants.WORLD_HEIGHT, camera);
        stage    = new Stage(viewport, game.batch);

        btnX = (Constants.WORLD_WIDTH - btnW) / 2f;

        // Persist high score
        Preferences prefs = Gdx.app.getPreferences(Constants.PREFS_NAME);
        int prev  = prefs.getInteger(Constants.PREF_HIGH_SCORE, 0);
        bestScore = Math.max(prev, score);
        if (score > prev) {
            prefs.putInteger(Constants.PREF_HIGH_SCORE, score);
            prefs.flush();
        }

        // Add to leaderboard
        LeaderboardScreen.addScore(score);

        // Game-over music plays once, then stops
        game.playMusicOnce("sounds/music/music_game_over.ogg");
        game.playSound("sounds/sfx/sfx_game_over.ogg");
    }

    @Override
    public void show() {
        Gdx.input.setInputProcessor(new InputMultiplexer(stage, new InputAdapter() {
            @Override
            public boolean touchDown(int screenX, int screenY, int pointer, int button) {
                Vector3 t = camera.unproject(new Vector3(screenX, screenY, 0));
                if (hit(t, btnX, retryY, btnW, btnH)) {
                    game.playSound("sounds/sfx/sfx_button_click.ogg");
                    game.setScreen(new GameScreen(game)); // fresh instance every retry
                    return true;
                }
                if (hit(t, btnX, menuY, btnW, btnH)) {
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
        }));
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(
                UiFactory.COLOR_BACKGROUND.r,
                UiFactory.COLOR_BACKGROUND.g,
                UiFactory.COLOR_BACKGROUND.b, 1f);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        camera.update();
        game.batch.setProjectionMatrix(camera.combined);
        game.shapeRenderer.setProjectionMatrix(camera.combined);

        // Panel background
        float panelW = Constants.PANEL_WIDTH;
        float panelH = Constants.PANEL_HEIGHT_GAMEOVER;
        float panelX = (Constants.WORLD_WIDTH  - panelW) / 2f;
        float panelY = (Constants.WORLD_HEIGHT - panelH) / 2f + 20f;

        Gdx.gl.glEnable(GL20.GL_BLEND);
        Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);
        game.shapeRenderer.begin(com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType.Filled);
        game.shapeRenderer.setColor(0f, 0f, 0f, 0.6f);
        game.shapeRenderer.rect(panelX, panelY, panelW, panelH);
        game.shapeRenderer.end();

        // Text
        game.batch.begin();

        // "GAME OVER" title
        game.fontTitle.setColor(UiFactory.COLOR_PRIMARY);
        layout.setText(game.fontTitle, "GAME OVER");
        game.fontTitle.draw(game.batch, "GAME OVER",
                (Constants.WORLD_WIDTH - layout.width) / 2f, 690f);

        // Score
        game.fontScore.setColor(UiFactory.COLOR_TEXT);
        String scoreStr = "Score: " + score;
        layout.setText(game.fontScore, scoreStr);
        game.fontScore.draw(game.batch, scoreStr,
                (Constants.WORLD_WIDTH - layout.width) / 2f, 610f);

        // Personal best
        String bestStr = "Best: " + bestScore;
        layout.setText(game.fontScore, bestStr);
        game.fontScore.draw(game.batch, bestStr,
                (Constants.WORLD_WIDTH - layout.width) / 2f, 550f);

        // Nuggets collected
        game.fontBody.setColor(UiFactory.COLOR_ACCENT);
        String nuggetsStr = "Nuggets: " + extra;
        layout.setText(game.fontBody, nuggetsStr);
        game.fontBody.draw(game.batch, nuggetsStr,
                (Constants.WORLD_WIDTH - layout.width) / 2f, 490f);

        game.batch.end();

        // Buttons
        UiFactory.drawButton(game.shapeRenderer, game.batch, game.fontBody,
                "RETRY",     btnX, retryY, btnW, btnH);
        UiFactory.drawButton(game.shapeRenderer, game.batch, game.fontBody,
                "MAIN MENU", btnX, menuY,  btnW, btnH);

        stage.act(delta);
    }

    @Override public void resize(int w, int h) { viewport.update(w, h, true); }
    @Override public void pause()  {}
    @Override public void resume() {}
    @Override public void hide()   {}
    @Override public void dispose() { stage.dispose(); }

    private boolean hit(Vector3 t, float x, float y, float w, float h) {
        return t.x >= x && t.x <= x + w && t.y >= y && t.y <= y + h;
    }
}

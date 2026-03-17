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

public class LeaderboardScreen implements Screen {

    private final MainGame          game;
    private final OrthographicCamera camera;
    private final StretchViewport   viewport;
    private final Stage             stage;
    private final GlyphLayout       layout = new GlyphLayout();

    private final int[] scores = new int[Constants.LEADERBOARD_SIZE];

    private final float btnW  = Constants.BUTTON_WIDTH_MAIN;
    private final float btnH  = Constants.BUTTON_HEIGHT;
    private final float btnX;
    private final float menuY = Constants.PAD_BOTTOM;

    // -----------------------------------------------------------------------
    // Static helper — call from GameOverScreen after every round
    // -----------------------------------------------------------------------
    public static void addScore(int score) {
        if (score <= 0) return;
        Preferences prefs = Gdx.app.getPreferences(Constants.PREFS_NAME);
        int[] s = new int[Constants.LEADERBOARD_SIZE];
        for (int i = 0; i < Constants.LEADERBOARD_SIZE; i++) {
            s[i] = prefs.getInteger(Constants.PREF_LB_SCORE_PREFIX + i, 0);
        }
        // Insert in descending order
        for (int i = 0; i < Constants.LEADERBOARD_SIZE; i++) {
            if (score > s[i]) {
                for (int j = Constants.LEADERBOARD_SIZE - 1; j > i; j--) {
                    s[j] = s[j - 1];
                }
                s[i] = score;
                break;
            }
        }
        for (int i = 0; i < Constants.LEADERBOARD_SIZE; i++) {
            prefs.putInteger(Constants.PREF_LB_SCORE_PREFIX + i, s[i]);
        }
        prefs.flush();
    }

    // -----------------------------------------------------------------------

    public LeaderboardScreen(MainGame game) {
        this.game = game;

        camera   = new OrthographicCamera();
        viewport = new StretchViewport(Constants.WORLD_WIDTH, Constants.WORLD_HEIGHT, camera);
        stage    = new Stage(viewport, game.batch);

        btnX = (Constants.WORLD_WIDTH - btnW) / 2f;

        // Load scores
        Preferences prefs = Gdx.app.getPreferences(Constants.PREFS_NAME);
        for (int i = 0; i < Constants.LEADERBOARD_SIZE; i++) {
            scores[i] = prefs.getInteger(Constants.PREF_LB_SCORE_PREFIX + i, 0);
        }
    }

    @Override
    public void show() {
        Gdx.input.setInputProcessor(new InputMultiplexer(stage, new InputAdapter() {
            @Override
            public boolean touchDown(int screenX, int screenY, int pointer, int button) {
                Vector3 t = camera.unproject(new Vector3(screenX, screenY, 0));
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

        game.batch.begin();

        // Title
        game.fontTitle.setColor(UiFactory.COLOR_TEXT);
        layout.setText(game.fontTitle, "LEADERBOARD");
        game.fontTitle.draw(game.batch, "LEADERBOARD",
                (Constants.WORLD_WIDTH - layout.width) / 2f, 800f);

        // Score rows
        float rowStartY = 710f;
        float rowStep   = 52f;

        for (int i = 0; i < Constants.LEADERBOARD_SIZE; i++) {
            float rowY = rowStartY - i * rowStep;
            String rankStr  = (i + 1) + ".";
            String scoreStr = scores[i] > 0 ? String.valueOf(scores[i]) : "---";

            // Rank number
            game.fontBody.setColor(UiFactory.COLOR_ACCENT);
            game.fontBody.draw(game.batch, rankStr, Constants.PAD_SIDE + 40f, rowY);

            // Score value — right-aligned
            game.fontScore.setColor(i == 0 ? UiFactory.COLOR_PRIMARY : UiFactory.COLOR_TEXT);
            layout.setText(game.fontScore, scoreStr);
            game.fontScore.draw(game.batch, scoreStr,
                    Constants.WORLD_WIDTH - Constants.PAD_SIDE - 40f - layout.width, rowY);
        }

        game.batch.end();

        // Main Menu button
        UiFactory.drawButton(game.shapeRenderer, game.batch, game.fontBody,
                "MAIN MENU", btnX, menuY, btnW, btnH);

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

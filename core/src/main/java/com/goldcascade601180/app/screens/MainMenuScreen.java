package com.goldcascade601180.app.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.goldcascade601180.app.Constants;
import com.goldcascade601180.app.MainGame;
import com.goldcascade601180.app.UiFactory;

public class MainMenuScreen implements Screen {

    private static final String BG = "backgrounds/menu/background-1_1.png";

    private final MainGame         game;
    private final OrthographicCamera camera;
    private final StretchViewport  viewport;
    private final Stage            stage;
    private final GlyphLayout      layout = new GlyphLayout();

    // Button geometry
    private final float btnW = Constants.BUTTON_WIDTH_MAIN;
    private final float btnH = Constants.BUTTON_HEIGHT;
    private final float btnX;
    private final float playY;
    private final float settingsY;
    private final float leaderY;

    public MainMenuScreen(MainGame game) {
        this.game = game;

        camera   = new OrthographicCamera();
        viewport = new StretchViewport(Constants.WORLD_WIDTH, Constants.WORLD_HEIGHT, camera);
        stage    = new Stage(viewport, game.batch);

        btnX      = (Constants.WORLD_WIDTH - btnW) / 2f;
        playY     = 460f;
        settingsY = playY     - btnH - Constants.GAP_BUTTON;
        leaderY   = settingsY - btnH - Constants.GAP_BUTTON;

        if (!game.manager.isLoaded(BG)) {
            game.manager.load(BG, Texture.class);
            game.manager.finishLoading();
        }

        game.playMusic("sounds/music/music_menu.ogg");
    }

    @Override
    public void show() {
        Gdx.input.setInputProcessor(new InputMultiplexer(stage, new InputAdapter() {
            @Override
            public boolean touchDown(int screenX, int screenY, int pointer, int button) {
                Vector3 t = camera.unproject(new Vector3(screenX, screenY, 0));
                if (hit(t, btnX, playY, btnW, btnH)) {
                    game.playSound("sounds/sfx/sfx_button_click.ogg");
                    game.setScreen(new GameScreen(game));
                    return true;
                }
                if (hit(t, btnX, settingsY, btnW, btnH)) {
                    game.playSound("sounds/sfx/sfx_button_click.ogg");
                    game.setScreen(new SettingsScreen(game));
                    return true;
                }
                if (hit(t, btnX, leaderY, btnW, btnH)) {
                    game.playSound("sounds/sfx/sfx_button_click.ogg");
                    game.setScreen(new LeaderboardScreen(game));
                    return true;
                }
                return false;
            }

            @Override
            public boolean keyDown(int keycode) {
                if (keycode == Input.Keys.BACK) return true; // swallow — this IS the main menu
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

        // Background
        game.batch.begin();
        game.batch.draw(game.manager.get(BG, Texture.class),
                0, 0, Constants.WORLD_WIDTH, Constants.WORLD_HEIGHT);
        game.batch.end();

        // Title text
        game.batch.begin();
        game.fontTitle.setColor(UiFactory.COLOR_TEXT);
        layout.setText(game.fontTitle, "GOLD CASCADE");
        game.fontTitle.draw(game.batch, "GOLD CASCADE",
                (Constants.WORLD_WIDTH - layout.width) / 2f, 730f);

        game.fontBody.setColor(UiFactory.COLOR_ACCENT);
        layout.setText(game.fontBody, "Plinko Adventure");
        game.fontBody.draw(game.batch, "Plinko Adventure",
                (Constants.WORLD_WIDTH - layout.width) / 2f, 678f);
        game.batch.end();

        // Buttons
        UiFactory.drawButton(game.shapeRenderer, game.batch, game.fontBody,
                "PLAY",        btnX, playY,     btnW, btnH);
        UiFactory.drawButton(game.shapeRenderer, game.batch, game.fontBody,
                "SETTINGS",    btnX, settingsY, btnW, btnH);
        UiFactory.drawButton(game.shapeRenderer, game.batch, game.fontBody,
                "LEADERBOARD", btnX, leaderY,   btnW, btnH);

        stage.act(delta);
    }

    @Override public void resize(int w, int h) { viewport.update(w, h, true); }
    @Override public void pause()  {}
    @Override public void resume() {}
    @Override public void hide()   {}

    @Override
    public void dispose() {
        stage.dispose();
        if (game.manager.isLoaded(BG)) game.manager.unload(BG);
    }

    private boolean hit(Vector3 t, float x, float y, float w, float h) {
        return t.x >= x && t.x <= x + w && t.y >= y && t.y <= y + h;
    }
}

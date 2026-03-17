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

public class SettingsScreen implements Screen {

    private final MainGame          game;
    private final OrthographicCamera camera;
    private final StretchViewport   viewport;
    private final Stage             stage;
    private final GlyphLayout       layout = new GlyphLayout();

    private boolean musicOn;
    private boolean sfxOn;

    private final float btnW     = Constants.BUTTON_WIDTH_MAIN;
    private final float btnH     = Constants.BUTTON_HEIGHT;
    private final float btnX;
    private final float musicY   = 520f;
    private final float sfxY     = 440f;
    private final float menuY    = 300f;

    public SettingsScreen(MainGame game) {
        this.game = game;

        camera   = new OrthographicCamera();
        viewport = new StretchViewport(Constants.WORLD_WIDTH, Constants.WORLD_HEIGHT, camera);
        stage    = new Stage(viewport, game.batch);

        btnX = (Constants.WORLD_WIDTH - btnW) / 2f;

        Preferences prefs = Gdx.app.getPreferences(Constants.PREFS_NAME);
        musicOn = prefs.getBoolean(Constants.PREF_MUSIC, true);
        sfxOn   = prefs.getBoolean(Constants.PREF_SFX,   true);

        game.musicEnabled = musicOn;
        game.sfxEnabled   = sfxOn;
    }

    @Override
    public void show() {
        Gdx.input.setInputProcessor(new InputMultiplexer(stage, new InputAdapter() {
            @Override
            public boolean touchDown(int screenX, int screenY, int pointer, int button) {
                Vector3 t = camera.unproject(new Vector3(screenX, screenY, 0));
                Preferences prefs = Gdx.app.getPreferences(Constants.PREFS_NAME);

                if (hit(t, btnX, musicY, btnW, btnH)) {
                    musicOn           = !musicOn;
                    game.musicEnabled = musicOn;
                    prefs.putBoolean(Constants.PREF_MUSIC, musicOn);
                    prefs.flush();
                    if (game.currentMusic != null) {
                        if (musicOn) game.currentMusic.play();
                        else         game.currentMusic.pause();
                    }
                    game.playSound("sounds/sfx/sfx_toggle.ogg");
                    return true;
                }
                if (hit(t, btnX, sfxY, btnW, btnH)) {
                    sfxOn           = !sfxOn;
                    game.sfxEnabled = sfxOn;
                    prefs.putBoolean(Constants.PREF_SFX, sfxOn);
                    prefs.flush();
                    // Play the toggle sound using the NEW state so feedback is immediate
                    if (sfxOn) game.manager.get("sounds/sfx/sfx_toggle.ogg",
                            com.badlogic.gdx.audio.Sound.class).play(0.5f);
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

        // Title
        game.batch.begin();
        game.fontTitle.setColor(UiFactory.COLOR_TEXT);
        layout.setText(game.fontTitle, "SETTINGS");
        game.fontTitle.draw(game.batch, "SETTINGS",
                (Constants.WORLD_WIDTH - layout.width) / 2f, 720f);
        game.batch.end();

        // Toggle buttons — label reflects current state
        String musicLabel = musicOn ? "MUSIC: ON"  : "MUSIC: OFF";
        String sfxLabel   = sfxOn   ? "SFX:   ON"  : "SFX:   OFF";

        UiFactory.drawButton(game.shapeRenderer, game.batch, game.fontBody,
                musicLabel, btnX, musicY, btnW, btnH);
        UiFactory.drawButton(game.shapeRenderer, game.batch, game.fontBody,
                sfxLabel,   btnX, sfxY,   btnW, btnH);
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

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
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.goldcascade601180.app.Constants;
import com.goldcascade601180.app.MainGame;
import com.goldcascade601180.app.UiFactory;

/**
 * Mine Select screen — PLAY button from MainMenuScreen leads here.
 * Player chooses Shallow Seam, Deep Vein, or Mother Lode.
 */
public class GameScreen implements Screen {

    private static final String BG = "backgrounds/menu/background-2_1.png";

    private final MainGame game;
    private final OrthographicCamera camera;
    private final StretchViewport viewport;
    private final GlyphLayout layout = new GlyphLayout();

    // Card geometry
    private static final float CARD_W = Constants.CARD_WIDTH;
    private static final float CARD_H = Constants.CARD_HEIGHT;
    private static final float CARD_X = (Constants.WORLD_WIDTH - CARD_W) / 2f;
    private static final float CARD_SHALLOW_Y = 380f;
    private static final float CARD_DEEP_Y    = CARD_SHALLOW_Y + CARD_H + Constants.GAP_CARD;
    private static final float CARD_MOTHER_Y  = CARD_DEEP_Y    + CARD_H + Constants.GAP_CARD;

    // HowToPlay + Back buttons
    private static final float BTN_W      = Constants.BUTTON_WIDTH_MAIN;
    private static final float BTN_H      = Constants.BUTTON_HEIGHT;
    private static final float BTN_X      = (Constants.WORLD_WIDTH - BTN_W) / 2f;
    private static final float HOWTO_Y    = 220f;
    private static final float BACK_Y     = HOWTO_Y - BTN_H - Constants.GAP_BUTTON;

    public GameScreen(MainGame game) {
        this.game = game;
        camera   = new OrthographicCamera();
        viewport = new StretchViewport(Constants.WORLD_WIDTH, Constants.WORLD_HEIGHT, camera);

        if (!game.manager.isLoaded(BG)) {
            game.manager.load(BG, Texture.class);
            game.manager.finishLoading();
        }
        game.playMusic("sounds/music/music_menu.ogg");
    }

    @Override
    public void show() {
        Gdx.input.setInputProcessor(new InputAdapter() {
            @Override
            public boolean touchDown(int sx, int sy, int pointer, int button) {
                Vector3 t = camera.unproject(new Vector3(sx, sy, 0));
                if (hit(t, CARD_X, CARD_SHALLOW_Y, CARD_W, CARD_H)) {
                    game.playSound("sounds/sfx/sfx_button_click.ogg");
                    game.setScreen(new ShallowSeamScreen(game));
                    return true;
                }
                if (hit(t, CARD_X, CARD_DEEP_Y, CARD_W, CARD_H)) {
                    game.playSound("sounds/sfx/sfx_button_click.ogg");
                    game.setScreen(new DeepVeinScreen(game));
                    return true;
                }
                if (hit(t, CARD_X, CARD_MOTHER_Y, CARD_W, CARD_H)) {
                    game.playSound("sounds/sfx/sfx_button_click.ogg");
                    game.setScreen(new MotherLodeScreen(game));
                    return true;
                }
                if (hit(t, BTN_X, HOWTO_Y, BTN_W, BTN_H)) {
                    game.playSound("sounds/sfx/sfx_button_click.ogg");
                    game.setScreen(new HowToPlayScreen(game));
                    return true;
                }
                if (hit(t, BTN_X, BACK_Y, BTN_W, BTN_H)) {
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
        Gdx.gl.glClearColor(UiFactory.COLOR_BACKGROUND.r, UiFactory.COLOR_BACKGROUND.g,
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

        // Header
        game.batch.begin();
        game.fontTitle.setColor(UiFactory.COLOR_PRIMARY);
        layout.setText(game.fontTitle, "SELECT YOUR MINE");
        game.fontTitle.draw(game.batch, "SELECT YOUR MINE",
                (Constants.WORLD_WIDTH - layout.width) / 2f, 790f);
        game.batch.end();

        // Mine cards
        drawCard(CARD_SHALLOW_Y, "SHALLOW SEAM", "Beginner Friendly",      "*",   0.18f, 0.40f, 0.55f);
        drawCard(CARD_DEEP_Y,    "DEEP VEIN",    "Intermediate Challenge",  "**",  0.24f, 0.42f, 0.56f);
        drawCard(CARD_MOTHER_Y,  "MOTHER LODE",  "Expert Only",             "***", 0.30f, 0.48f, 0.62f);

        // Buttons
        UiFactory.drawButton(game.shapeRenderer, game.batch, game.fontBody, "HOW TO PLAY", BTN_X, HOWTO_Y, BTN_W, BTN_H);
        UiFactory.drawButton(game.shapeRenderer, game.batch, game.fontBody, "BACK",        BTN_X, BACK_Y,  BTN_W, BTN_H);
    }

    private void drawCard(float y, String title, String sub, String stars, float r, float g, float b) {
        Gdx.gl.glEnable(GL20.GL_BLEND);
        Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);

        game.shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        game.shapeRenderer.setColor(r, g, b, 0.88f);
        game.shapeRenderer.rect(CARD_X, y, CARD_W, CARD_H);
        game.shapeRenderer.end();

        game.batch.begin();
        game.fontBody.setColor(UiFactory.COLOR_PRIMARY);
        game.fontBody.draw(game.batch, title, CARD_X + 16f, y + CARD_H - 16f);

        game.fontSmall.setColor(UiFactory.COLOR_ACCENT);
        game.fontSmall.draw(game.batch, sub, CARD_X + 16f, y + 26f);

        game.fontBody.setColor(UiFactory.COLOR_PRIMARY);
        layout.setText(game.fontBody, stars);
        game.fontBody.draw(game.batch, stars, CARD_X + CARD_W - layout.width - 16f, y + CARD_H - 16f);
        game.batch.end();
    }

    @Override public void resize(int w, int h) { viewport.update(w, h, true); }
    @Override public void pause()  {}
    @Override public void resume() {}
    @Override public void hide()   {}

    @Override
    public void dispose() {
        if (game.manager.isLoaded(BG)) game.manager.unload(BG);
    }

    private boolean hit(Vector3 t, float x, float y, float w, float h) {
        return t.x >= x && t.x <= x + w && t.y >= y && t.y <= y + h;
    }
}

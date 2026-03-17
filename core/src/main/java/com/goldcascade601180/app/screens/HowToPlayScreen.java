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
 * Tutorial screen explaining the core game mechanics.
 * Reachable from the Mine Select (GameScreen) "HOW TO PLAY" button.
 */
public class HowToPlayScreen implements Screen {

    private final MainGame game;
    private final OrthographicCamera camera;
    private final StretchViewport    viewport;
    private final GlyphLayout        layout = new GlyphLayout();

    private static final float BTN_W  = Constants.BUTTON_WIDTH_MAIN;
    private static final float BTN_H  = Constants.BUTTON_HEIGHT;
    private static final float BTN_X  = (Constants.WORLD_WIDTH - BTN_W) / 2f;
    private static final float BTN_Y  = Constants.PAD_BOTTOM + 20f;

    // Content area
    private static final float CONTENT_X = 40f;
    private static final float CONTENT_W = Constants.WORLD_WIDTH - 80f;

    public HowToPlayScreen(MainGame game) {
        this.game = game;
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
                if (hit(t, BTN_X, BTN_Y, BTN_W, BTN_H)) {
                    game.playSound("sounds/sfx/sfx_button_click.ogg");
                    game.setScreen(new GameScreen(game));
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

        // Content panel
        float panelX = CONTENT_X - 10f;
        float panelY = BTN_Y + BTN_H + 10f;
        float panelH = Constants.WORLD_HEIGHT - panelY - 90f;

        Gdx.gl.glEnable(GL20.GL_BLEND);
        Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);
        game.shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        game.shapeRenderer.setColor(0.18f, 0.35f, 0.48f, 0.80f);
        game.shapeRenderer.rect(panelX, panelY, CONTENT_W + 20f, panelH);
        game.shapeRenderer.end();

        game.batch.begin();

        // Header
        game.fontTitle.setColor(UiFactory.COLOR_PRIMARY);
        layout.setText(game.fontTitle, "HOW TO PLAY");
        game.fontTitle.draw(game.batch, "HOW TO PLAY",
                (Constants.WORLD_WIDTH - layout.width) / 2f, 820f);

        float y = 760f;
        float lineH = 22f;

        // Section 1
        y = drawSection(y, "THE GOAL",
                new String[]{
                    "Drop 10 gold nuggets through the pegboard.",
                    "Guide them into ore carts to score points."
                }, lineH);

        y -= 10f;

        // Section 2
        y = drawSection(y, "HOW TO DROP",
                new String[]{
                    "Tap anywhere on the upper screen",
                    "to release the next nugget.",
                    "Physics will bounce it left and right."
                }, lineH);

        y -= 10f;

        // Section 3
        y = drawSection(y, "SCORING",
                new String[]{
                    "Each cart has a point value shown inside.",
                    "Carts toward the edges score higher.",
                    "Premium carts glow gold for big points!"
                }, lineH);

        y -= 10f;

        // Section 4
        y = drawSection(y, "DIFFICULTY",
                new String[]{
                    "SHALLOW SEAM - Beginner (4 carts)",
                    "DEEP VEIN    - Medium   (6 carts)",
                    "MOTHER LODE  - Expert   (8 carts)"
                }, lineH);

        y -= 10f;

        // Section 5 — Pro tips
        y = drawSection(y, "PRO TIPS",
                new String[]{
                    "Tap early to aim left, later for right.",
                    "Pegs in the centre slow the nugget down.",
                    "Land 3+ in the same cart for a combo!"
                }, lineH);

        game.batch.end();

        // Got It button
        UiFactory.drawButton(game.shapeRenderer, game.batch, game.fontBody,
                "GOT IT!", BTN_X, BTN_Y, BTN_W, BTN_H);
    }

    /** Draws a section heading + body lines. Returns new Y after drawing. */
    private float drawSection(float y, String heading, String[] lines, float lineH) {
        game.fontBody.setColor(UiFactory.COLOR_PRIMARY);
        game.fontBody.draw(game.batch, heading, CONTENT_X, y);
        y -= lineH + 4f;

        game.fontSmall.setColor(UiFactory.COLOR_TEXT);
        for (String line : lines) {
            game.fontSmall.draw(game.batch, "  " + line, CONTENT_X, y);
            y -= lineH;
        }
        return y;
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

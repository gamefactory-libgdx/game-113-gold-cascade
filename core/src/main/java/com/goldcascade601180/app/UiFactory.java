package com.goldcascade601180.app;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

public class UiFactory {

    public static final Color COLOR_PRIMARY    = new Color(1.000f, 0.702f, 0.000f, 1f); // #FFB300
    public static final Color COLOR_ACCENT     = new Color(0.553f, 0.431f, 0.388f, 1f); // #8D6E63
    public static final Color COLOR_BACKGROUND = new Color(0.243f, 0.153f, 0.137f, 1f); // #3E2723
    public static final Color COLOR_TEXT       = Color.WHITE;

    private static final GlyphLayout LAYOUT = new GlyphLayout();

    /**
     * Draws a rounded-rectangle button: shadow offset (+3,-3) at 40% alpha, then
     * primary-colour fill. No border. Text centred in white.
     */
    public static void drawButton(ShapeRenderer sr, SpriteBatch batch, BitmapFont font,
                                  String label, float x, float y, float w, float h) {
        float r = h * 0.35f;

        Gdx.gl.glEnable(GL20.GL_BLEND);
        Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);

        sr.begin(ShapeRenderer.ShapeType.Filled);

        // Drop shadow
        sr.setColor(0f, 0f, 0f, 0.4f);
        fillRoundedRect(sr, x + 3, y - 3, w, h, r);

        // Button fill
        sr.setColor(COLOR_PRIMARY);
        fillRoundedRect(sr, x, y, w, h, r);

        sr.end();

        // Centred label
        LAYOUT.setText(font, label);
        batch.begin();
        font.setColor(COLOR_TEXT);
        font.draw(batch, label,
                x + (w - LAYOUT.width)  / 2f,
                y + (h + LAYOUT.height) / 2f);
        batch.end();
    }

    // ---------------------------------------------------------------------------

    private static void fillRoundedRect(ShapeRenderer sr, float x, float y, float w, float h, float r) {
        sr.rect(x + r, y,         w - 2 * r, h);           // horizontal band
        sr.rect(x,     y + r,     r,         h - 2 * r);   // left cap
        sr.rect(x + w - r, y + r, r,         h - 2 * r);   // right cap
        sr.circle(x + r,     y + r,     r, 16);
        sr.circle(x + w - r, y + r,     r, 16);
        sr.circle(x + r,     y + h - r, r, 16);
        sr.circle(x + w - r, y + h - r, r, 16);
    }
}

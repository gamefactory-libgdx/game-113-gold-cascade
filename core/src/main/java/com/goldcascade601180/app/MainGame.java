package com.goldcascade601180.app;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.goldcascade601180.app.screens.MainMenuScreen;

public class MainGame extends Game {

    public SpriteBatch    batch;
    public ShapeRenderer  shapeRenderer;
    public AssetManager   manager;

    public BitmapFont fontTitle;
    public BitmapFont fontBody;
    public BitmapFont fontSmall;
    public BitmapFont fontScore;

    public boolean musicEnabled = true;
    public boolean sfxEnabled   = true;
    public Music   currentMusic = null;

    @Override
    public void create() {
        batch         = new SpriteBatch();
        shapeRenderer = new ShapeRenderer();
        manager       = new AssetManager();

        generateFonts();
        loadAssets();
        manager.finishLoading();

        setScreen(new MainMenuScreen(this));
    }

    private void generateFonts() {
        FreeTypeFontGenerator titleGen = new FreeTypeFontGenerator(Gdx.files.internal("fonts/Crackman.otf"));
        FreeTypeFontGenerator bodyGen  = new FreeTypeFontGenerator(Gdx.files.internal("fonts/GasaltRegular.ttf"));

        FreeTypeFontGenerator.FreeTypeFontParameter param = new FreeTypeFontGenerator.FreeTypeFontParameter();

        param.size = Constants.FONT_SIZE_TITLE;
        fontTitle  = titleGen.generateFont(param);

        param.size = Constants.FONT_SIZE_HEADER;
        fontScore  = bodyGen.generateFont(param);

        param.size = Constants.FONT_SIZE_BUTTON;
        fontBody   = bodyGen.generateFont(param);

        param.size = Constants.FONT_SIZE_SMALL;
        fontSmall  = bodyGen.generateFont(param);

        titleGen.dispose();
        bodyGen.dispose();
    }

    private void loadAssets() {
        // Music
        manager.load("sounds/music/music_menu.ogg",      Music.class);
        manager.load("sounds/music/music_gameplay.ogg",  Music.class);
        manager.load("sounds/music/music_game_over.ogg", Music.class);

        // SFX
        manager.load("sounds/sfx/sfx_button_click.ogg",   Sound.class);
        manager.load("sounds/sfx/sfx_button_back.ogg",    Sound.class);
        manager.load("sounds/sfx/sfx_toggle.ogg",         Sound.class);
        manager.load("sounds/sfx/sfx_coin.ogg",           Sound.class);
        manager.load("sounds/sfx/sfx_hit.ogg",            Sound.class);
        manager.load("sounds/sfx/sfx_game_over.ogg",      Sound.class);
        manager.load("sounds/sfx/sfx_level_complete.ogg", Sound.class);
    }

    public void playMusic(String path) {
        Music requested = manager.get(path, Music.class);
        if (requested == currentMusic && currentMusic.isPlaying()) return;
        if (currentMusic != null) currentMusic.stop();
        currentMusic = requested;
        currentMusic.setLooping(true);
        currentMusic.setVolume(0.7f);
        if (musicEnabled) currentMusic.play();
    }

    public void playMusicOnce(String path) {
        if (currentMusic != null) currentMusic.stop();
        currentMusic = manager.get(path, Music.class);
        currentMusic.setLooping(false);
        currentMusic.setVolume(0.7f);
        if (musicEnabled) currentMusic.play();
    }

    public void playSound(String path) {
        if (sfxEnabled) manager.get(path, Sound.class).play(1.0f);
    }

    @Override
    public void dispose() {
        super.dispose();
        batch.dispose();
        shapeRenderer.dispose();
        manager.dispose();
        fontTitle.dispose();
        fontBody.dispose();
        fontSmall.dispose();
        fontScore.dispose();
    }
}

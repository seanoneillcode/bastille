package org.lovely.games;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Array;

import java.util.HashMap;
import java.util.Map;

public class LoadingManager {

    public static final String PLAYER_IDLE = "player-idle.png";
    public static final String PLAYER_SHADOW = "player-shadow.png";
    public static final String PLAYER_FALL = "player-fall.png";
    public static final String PLAYER_JUMP = "player-jump.png";
    public static final String PLAYER_RUN = "player-run.png";
    public static final String GRASS_TILE = "grass-tile.png";
    public static final String GRASS_TILE_2 = "grass-tile-2.png";
    public static final String GRASS_TILE_3 = "grass-tile-3.png";
    public static final String GRASS_TILE_4 = "grass-tile-4.png";
    public static final String GRASS_TILE_5 = "grass-tile-5.png";
    public static final String GRASS_TILE_6 = "grass-tile-6.png";
    public static final String GRASS_TILE_7 = "grass-tile-7.png";
    public static final String GRASS_TILE_8 = "grass-tile-8.png";
    public static final String GRASS_TILE_9 = "grass-tile-9.png";
    public static final String GRASS_TILE_BREAK = "grass-tile-break.png";
    public static final String LIGHTHOUSE = "lighthouse.png";
    public static final String BOTTOM_TILE = "bottom-tile.png";
    public static final String CLOUD_0 = "cloud-0.png";
    public static final String CLOUD_1 = "cloud-1.png";
    public static final String CLOUD_2 = "cloud-2.png";
    public static final String CLOUD_3 = "cloud-3.png";
    public static final String GOAL = "goal.png";
    public static final String BOMB = "bomb.png";
    public static final String EXPLODE = "explode.png";
    public static final String LAND_EFFECT = "land-effect.png";
    public static final String SOUND_HUP = "sound/hup.ogg";
    public static final String SOUND_PAH = "sound/pah.ogg";
    public static final String SOUND_FALL_SCREAM = "sound/fall-scream.ogg";
    public static final String SOUND_EXPLOSION = "sound/explosion.ogg";
    public static final String SOUND_SWISH = "sound/swish.ogg";
    public static final String SOUND_MUSIC_CLOUDS = "sound/music-clouds.ogg";

    private Map<String, Animation<TextureRegion>> anims;
    AssetManager assetManager;

    LoadingManager(AssetManager assetManager) {
        anims = new HashMap<>();
        this.assetManager = assetManager;
    }

    public void load() {
        assetManager.load(PLAYER_IDLE, Texture.class);
        assetManager.load(PLAYER_FALL, Texture.class);
        assetManager.load(PLAYER_RUN, Texture.class);
        assetManager.load(PLAYER_JUMP, Texture.class);
        assetManager.load(GRASS_TILE, Texture.class);
        assetManager.load(GRASS_TILE_2, Texture.class);
        assetManager.load(GRASS_TILE_3, Texture.class);
        assetManager.load(GRASS_TILE_4, Texture.class);
        assetManager.load(GRASS_TILE_5, Texture.class);
        assetManager.load(GRASS_TILE_6, Texture.class);
        assetManager.load(GRASS_TILE_7, Texture.class);
        assetManager.load(GRASS_TILE_8, Texture.class);
        assetManager.load(GRASS_TILE_9, Texture.class);
        assetManager.load(GRASS_TILE_BREAK, Texture.class);
        assetManager.load(LIGHTHOUSE, Texture.class);
        assetManager.load(BOTTOM_TILE, Texture.class);
        assetManager.load(PLAYER_SHADOW, Texture.class);
        assetManager.load(LAND_EFFECT, Texture.class);
        assetManager.load(CLOUD_0, Texture.class);
        assetManager.load(CLOUD_1, Texture.class);
        assetManager.load(CLOUD_2, Texture.class);
        assetManager.load(CLOUD_3, Texture.class);
        assetManager.load(GOAL, Texture.class);
        assetManager.load(BOMB, Texture.class);
        assetManager.load(EXPLODE, Texture.class);

        assetManager.load(SOUND_HUP, Sound.class);
        assetManager.load(SOUND_PAH, Sound.class);
        assetManager.load(SOUND_FALL_SCREAM, Sound.class);
        assetManager.load(SOUND_EXPLOSION, Sound.class);
        assetManager.load(SOUND_SWISH, Sound.class);
        assetManager.load(SOUND_MUSIC_CLOUDS, Sound.class);

        assetManager.finishLoading();

        anims.put(PLAYER_IDLE, loadAnimation(PLAYER_IDLE, 4, 0.25f));
        anims.put(PLAYER_FALL, loadAnimation(PLAYER_FALL, 6, 0.05f));
        anims.put(PLAYER_RUN, loadAnimation(PLAYER_RUN, 4, 0.15f));
        anims.put(PLAYER_JUMP, loadAnimation(PLAYER_JUMP, 2, 0.25f));
        anims.put(GOAL, loadAnimation(GOAL, 2, 0.25f));
        anims.put(BOMB, loadAnimation(BOMB, 4, 0.15f));
        anims.put(EXPLODE, loadAnimation(EXPLODE, 8, 0.1f));
        anims.put(GRASS_TILE, loadAnimation(GRASS_TILE, 4, 0.5f));
        anims.put(GRASS_TILE_2, loadAnimation(GRASS_TILE_2, 4, 0.5f));
        anims.put(GRASS_TILE_3, loadAnimation(GRASS_TILE_3, 4, 0.5f));
        anims.put(GRASS_TILE_4, loadAnimation(GRASS_TILE_4, 4, 0.5f));
        anims.put(GRASS_TILE_5, loadAnimation(GRASS_TILE_5, 4, 0.5f));
        anims.put(GRASS_TILE_6, loadAnimation(GRASS_TILE_6, 4, 0.5f));
        anims.put(GRASS_TILE_7, loadAnimation(GRASS_TILE_7, 4, 0.5f));
        anims.put(GRASS_TILE_8, loadAnimation(GRASS_TILE_8, 4, 0.5f));
        anims.put(GRASS_TILE_9, loadAnimation(GRASS_TILE_9, 4, 0.5f));
        anims.put(GRASS_TILE_BREAK, loadAnimation(GRASS_TILE_BREAK, 4, 0.2f));
        anims.put(LIGHTHOUSE, loadAnimation(LIGHTHOUSE, 8, 0.25f));
        anims.put(BOTTOM_TILE, loadAnimation(BOTTOM_TILE, 1, 1f));
        anims.put(PLAYER_SHADOW, loadAnimation(PLAYER_SHADOW, 1, 1f));
        anims.put(CLOUD_0, loadAnimation(CLOUD_0, 1, 1f));
        anims.put(CLOUD_1, loadAnimation(CLOUD_1, 1, 1f));
        anims.put(CLOUD_2, loadAnimation(CLOUD_2, 1, 1f));
        anims.put(CLOUD_3, loadAnimation(CLOUD_3, 1, 1f));
        anims.put(LAND_EFFECT, loadAnimation(LAND_EFFECT, 6, 0.05f));
    }

    public Sound getSound(String name) {
        return assetManager.get(name, Sound.class);
    }

    public Animation<TextureRegion> getAnim(String name) {
        return anims.get(name);
    }

    private Animation<TextureRegion> loadAnimation(String fileName, int numberOfFrames, float frameDelay) {
        Texture sheet = assetManager.get(fileName);
        TextureRegion[][] tmp = TextureRegion.split(sheet, sheet.getWidth() / numberOfFrames, sheet.getHeight());
        Array<TextureRegion> frames = new Array<>(numberOfFrames);
        for (int i = 0; i < numberOfFrames; i++) {
            frames.add(tmp[0][i]);
        }
        return new Animation<>(frameDelay, frames);
    }
}

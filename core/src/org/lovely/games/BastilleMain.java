package org.lovely.games;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.assets.loaders.FileHandleResolver;
import com.badlogic.gdx.assets.loaders.SoundLoader;
import com.badlogic.gdx.assets.loaders.TextureLoader;
import com.badlogic.gdx.assets.loaders.resolvers.InternalFileHandleResolver;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

import java.util.ArrayList;
import java.util.List;

import static org.lovely.games.LoadingManager.*;

public class BastilleMain extends ApplicationAdapter {

    SpriteBatch batch;

    private CameraManager cameraManager;
    private InputManager inputManager;
    private AssetManager assetManager;
    private LoadingManager loadingManager;
    LevelManager levelManager;
    private EffectManager effectManager;
    private PlayerManager playerManager;
    EntityManager entityManager;
    private TextManager textManager;
    private SoundManager soundManager;
    private ScreenShaker screenShaker;
    private float animationDelta = 0f;
    Color background = new Color(0 / 256f, 149 / 256f, 233 / 256f, 1);
    Player player;
    private List<Cloud> cloudShadows;

    @Override
	public void create () {
		batch = new SpriteBatch();
        assetManager = new AssetManager();
        FileHandleResolver fileHandleResolver = new InternalFileHandleResolver();
        assetManager.setLoader(Texture.class, new TextureLoader(fileHandleResolver));
        assetManager.setLoader(Sound.class, new SoundLoader(fileHandleResolver));
        loadingManager = new LoadingManager(assetManager);
        loadingManager.load();
        levelManager = new LevelManager();
		cameraManager = new CameraManager();
		effectManager = new EffectManager();
        soundManager = new SoundManager(loadingManager);
		inputManager = new InputManager();
		entityManager = new EntityManager();
		playerManager = new PlayerManager();
		textManager = new TextManager();
		screenShaker = new ScreenShaker();
        startLevel();
	}

	@Override
	public void render () {
        inputManager.update(this);
        cameraManager.update(player.pos, inputManager, screenShaker);
        levelManager.update(this);
        playerManager.update(levelManager, this, effectManager, soundManager, entityManager);
        effectManager.update();
        entityManager.update(this, soundManager);
		Gdx.gl.glClearColor(background.r, background.g, background.b, background.a);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        batch.setProjectionMatrix(cameraManager.camera.combined);
        animationDelta = animationDelta + Gdx.graphics.getDeltaTime();
		batch.begin();
        drawLevel(levelManager);
        drawEffects(effectManager);
        drawEntity(entityManager);
        drawPlayer(playerManager);
        drawCloudShadows();
        if (levelManager.getGroundLeft() < 30) {
            textManager.drawYouWin(batch, new Vector2(cameraManager.camera.position.x - 100, cameraManager.camera.position.y + 10));
        } else {
            textManager.drawText(batch, "GROUND LEFT " + levelManager.getGroundLeft() + "%", new Vector2(cameraManager.camera.position.x - 150, cameraManager.camera.position.y + 100));
        }
		batch.end();
	}

    private void drawEffects(EffectManager effectManager) {
        Sprite sprite = new Sprite();
        for (Effect effect : effectManager.effects) {
            TextureRegion frame = loadingManager.getAnim(effect.image).getKeyFrame(effect.anim, true);
            sprite.setRegion(frame);
            float width = frame.getRegionWidth();
            float height = frame.getRegionHeight();
            sprite.setSize(width, height);
            sprite.setPosition(effect.pos.x - (width / 2.0f), effect.pos.y - (height / 2.0f));
            sprite.draw(batch);
        }
    }

    private void drawEntity(EntityManager entityManager) {
        Sprite sprite = new Sprite();
        for (Entity entity : entityManager.entities) {
            TextureRegion frame = loadingManager.getAnim(entity.getImage()).getKeyFrame(entity.anim, entity.shouldLoop());
            sprite.setRegion(frame);
            float width = frame.getRegionWidth();
            float height = frame.getRegionHeight();
            sprite.setSize(width, height);
            sprite.setPosition(entity.pos.x - (width / 2.0f), entity.pos.y - (height / 2.0f));
            sprite.draw(batch);
        }
    }

    private void drawPlayer(PlayerManager playerManager) {
        Sprite sprite = new Sprite();
        sprite.setScale(2);
        for (Player player : playerManager.players) {
            sprite.setSize(player.size.x, player.size.y);
            float actualy = player.pos.y + player.z;


            if (player.state != Player.PlayerState.DEAD) {
                TextureRegion frame = null;
                if (player.state == Player.PlayerState.FALLING) {
                    frame = loadingManager.getAnim(PLAYER_FALL).getKeyFrame(player.delta, false);
                }
                if (player.state == Player.PlayerState.JUMPING) {
                    frame = loadingManager.getAnim(PLAYER_JUMP).getKeyFrame(player.delta, true);
                }
                if (player.state == Player.PlayerState.ALIVE) {
                    if (inputManager.isMoving()) {
                        frame = loadingManager.getAnim(PLAYER_RUN).getKeyFrame(player.delta, true);
                    } else {
                        frame = loadingManager.getAnim(PLAYER_IDLE).getKeyFrame(player.delta, true);
                    }
                }
                sprite.setRegion(frame);
                float width = frame.getRegionWidth();
                float height = frame.getRegionHeight();
                sprite.setPosition(player.pos.x - (width / 2.0f), actualy - (height / 2.0f));
                if (!inputManager.isRight) {
                    sprite.flip(true, false);
                }
                if (player.state != Player.PlayerState.DEAD && player.state != Player.PlayerState.FALLING) {
                    batch.draw(loadingManager.getAnim(PLAYER_SHADOW).getKeyFrame(player.delta, false), player.pos.x - (width / 2.0f), player.pos.y - 4 - (height / 2.0f));
                }
                sprite.draw(batch);
            }
        }
    }

    private void drawCloudShadows() {
        Sprite sprite = new Sprite();
        sprite.setSize(128, 128);
        for (Cloud cloud : cloudShadows) {
            sprite.setPosition(cloud.pos.x, cloud.pos.y);
            TextureRegion frame = loadingManager.getAnim(cloud.img).getKeyFrame(animationDelta, true);
            sprite.setRegion(frame);
            sprite.draw(batch);
        }
    }

    private void drawLevel(LevelManager levelManager) {
        cloudShadows = new ArrayList<>();
        Sprite sprite = new Sprite();
        sprite.setSize(128, 128);
        for (Cloud cloud : levelManager.clouds) {
            if (cloud.img == CLOUD_3) {
                cloudShadows.add(cloud);
                continue;
            }
            sprite.setPosition(cloud.pos.x, cloud.pos.y);
            TextureRegion frame = loadingManager.getAnim(cloud.img).getKeyFrame(animationDelta, true);
            sprite.setRegion(frame);
            sprite.setScale(cloud.scale);
            sprite.draw(batch);
        }

        Sprite tileSprite = new Sprite();

        for (Tile tile : levelManager.tiles) {
            TextureRegion frame = loadingManager.getAnim(tile.image).getKeyFrame(animationDelta + tile.animationOffset, true);
            float width = frame.getRegionWidth();
            float height = frame.getRegionHeight();
            tileSprite.setPosition(tile.pos.x - (width / 2.0f), tile.pos.y - (height / 2.0f));
            tileSprite.setSize(tile.size.x, tile.size.y);
            tileSprite.setRegion(frame);
            tileSprite.setColor(tile.color);
            tileSprite.draw(batch);
        }
    }

	@Override
	public void dispose () {
		batch.dispose();
		assetManager.dispose();
	}

    public void movePlayer(Vector2 inputVector) {
        player.move(inputVector);
    }

    void startLevel() {
        playerManager.start();
        levelManager.start(entityManager);
        effectManager.start();
        soundManager.playMusic(SOUND_MUSIC_CLOUDS);
        Vector2 startPos = levelManager.getStartPos();
        player = playerManager.addPlayer(startPos, new Vector2(8, 8), new Vector2(0, 0), true);
    }

    public void jumpPlayer() {
        player.jump(soundManager);
//        inputManager.throwBomb(this);
    }

    public void throwBomb() {
        Vector2 pos = player.pos.cpy();
        Entity bomb = new BombEntity(pos, new Vector2(96, 96), BOMB, EXPLODE);
        entityManager.entities.add(bomb);
    }

    public void destroyTile(Vector2 pos, Vector2 size) {
        for (Tile tile : levelManager.tiles) {
            if (PlayerManager.isOverlap(tile.pos, tile.size, pos, size)) {
                tile.isDead = true;
                effectManager.addEffect(tile.pos, GRASS_TILE_BREAK, 0.8f, new Vector2(0.1f, -0.05f));
            }
        }
    }

    public void shakeScreen(float amount) {
        screenShaker.shake(amount);
    }
}

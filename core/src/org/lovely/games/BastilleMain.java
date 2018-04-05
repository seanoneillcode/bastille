package org.lovely.games;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.assets.loaders.FileHandleResolver;
import com.badlogic.gdx.assets.loaders.TextureLoader;
import com.badlogic.gdx.assets.loaders.resolvers.InternalFileHandleResolver;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

import java.util.ArrayList;
import java.util.List;

import static org.lovely.games.LevelManager.TILE_SIZE;
import static org.lovely.games.LoadingManager.*;

public class BastilleMain extends ApplicationAdapter {

    SpriteBatch batch;

    private CameraManager cameraManager;
    private InputManager inputManager;
    private float PLAYER_SPEED = 1.8f;
    private AssetManager assetManager;
    private LoadingManager loadingManager;
    private LevelManager levelManager;
    private EffectManager effectManager;
    private PlayerManager playerManager;
    private EntityManager entityManager;
    private TextManager textManager;
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
        loadingManager = new LoadingManager(assetManager);
        loadingManager.load();
        levelManager = new LevelManager();
		cameraManager = new CameraManager();
		effectManager = new EffectManager();
		inputManager = new InputManager();
		entityManager = new EntityManager();
		playerManager = new PlayerManager();
		textManager = new TextManager();
        startLevel();
	}

	@Override
	public void render () {
        inputManager.update(this);
        cameraManager.update(player.pos, inputManager);
        levelManager.update();
        playerManager.update(levelManager, this, effectManager);
        effectManager.update();
        entityManager.update(this);
        updatePlayer();
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
        Sprite goalSprite = new Sprite();
        goalSprite.setSize(TILE_SIZE * 2, TILE_SIZE * 2);
        Tile goalTile = levelManager.goalTile;
        TextureRegion frame = loadingManager.getAnim(GOAL).getKeyFrame(animationDelta, true);
        goalSprite.setPosition(goalTile.pos.x, goalTile.pos.y);
        goalSprite.setRegion(frame);
        goalSprite.draw(batch);
    }

	@Override
	public void dispose () {
		batch.dispose();
		assetManager.dispose();
	}

    public void movePlayer(Vector2 inputVector) {
        if (player.state == Player.PlayerState.ALIVE) {
            Vector2 change = inputVector.cpy().scl(PLAYER_SPEED);
            player.pos.add(change);
        }
        if (player.state == Player.PlayerState.JUMPING) {
            Vector2 change = inputVector.cpy().scl(PLAYER_SPEED).scl(0.8f);
            player.pos.add(change);
        }
    }

    private void startLevel() {
        playerManager.start();
        levelManager.start();
        effectManager.start();
        Vector2 startPos = levelManager.getStartPos();
        player = playerManager.addPlayer(startPos, new Vector2(8, 8), new Vector2(0, 0), true);
    }

    public void jumpPlayer() {
        player.jump();
//        inputManager.throwBomb(this);
    }

    private void updatePlayer() {
        if (PlayerManager.isOverlap(player.pos, player.size, levelManager.goalTile.pos, levelManager.goalTile.size)) {
            startLevel();
        }
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
}

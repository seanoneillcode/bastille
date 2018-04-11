package org.lovely.games;

import static org.lovely.games.LoadingManager.BOMB;
import static org.lovely.games.LoadingManager.EXPLODE;
import static org.lovely.games.LoadingManager.SOUND_SWISH;
import static org.lovely.games.PlayerManager.isOverlap;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;

public class SnailEntity extends Entity {

    private static final float ACTUALLY_MOVING = 0.4f;
    private static final float MOVE_TIMER_TOTAL = 0.8f;
    private static final float BOMB_TIMER = 12.0f;
    float moveTimer;
    float bombTimer;

    public SnailEntity(Vector2 pos, Vector2 size, String image, String dyingImage, Vector2 move) {
        super(pos, size, image, dyingImage, move);
        this.move = move;
        this.bombTimer = MathUtils.random(0, BOMB_TIMER);
    }

    public void update(BastilleMain bastilleMain, SoundManager soundManager) {
        if (state == EntityState.ALIVE) {
            moveTimer = moveTimer - Gdx.graphics.getDeltaTime();
            bombTimer = bombTimer - Gdx.graphics.getDeltaTime();
            if (moveTimer < ACTUALLY_MOVING) {
                move(soundManager, bastilleMain);
            }
            if (moveTimer < 0) {
                moveTimer = MOVE_TIMER_TOTAL;
            }
            if (bombTimer < 0) {
                Entity bomb = new BombEntity(pos.cpy(), new Vector2(96, 96), BOMB, EXPLODE);
                bastilleMain.entityManager.addEntity(bomb);
                bombTimer = BOMB_TIMER;
            }
        }
    }
    
    private void move(SoundManager soundManager, BastilleMain bastilleMain) {
        soundManager.playSound(SOUND_SWISH);
        pos.x = pos.x + move.x;
        pos.y = pos.y + move.y;
        Vector2 next = new Vector2(pos.x + move.x, pos.y + move.y);
        if (!isOnGround(bastilleMain.levelManager, next, new Vector2(16, 16))) {
            move.x = move.x * -1;
            move.y = move.y * -1;
        }
    }
    
    private boolean isOnGround(LevelManager levelManager, Vector2 pos, Vector2 size) {
        for (Tile tile : levelManager.tiles) {
            if (tile.isGround && isOverlap(tile.pos, tile.size, pos.cpy().sub(size.cpy().scl(0.5f)), size)) {
                return true;
            }
        }
        return false;
    }

    public void handleCollision(Player player) {

    }
}

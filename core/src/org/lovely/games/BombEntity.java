package org.lovely.games;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;

import static org.lovely.games.LoadingManager.SOUND_EXPLOSION;

public class BombEntity extends Entity {

    private static final float BOMB_TIMER = 0.6f;
    private static final float DYING_TIMER = 0.8f;
    float timer;

    public BombEntity(Vector2 pos, Vector2 size, String image, String dyingImage) {
        super(pos, size, image, dyingImage);
        this.timer = BOMB_TIMER;
    }

    public void update(BastilleMain bastilleMain, SoundManager soundManager) {
        timer -= Gdx.graphics.getDeltaTime();
        if (state == EntityState.ALIVE) {
            if (timer < 0) {
                this.state = EntityState.DYING;
                this.anim = 0;
                timer = DYING_TIMER;
                bastilleMain.shakeScreen(10);
                soundManager.playSound(SOUND_EXPLOSION);
            }
        }
        if (state == EntityState.DYING) {
            if (timer < 0) {
                this.state = EntityState.DEAD;
            }
            if (timer < 0.6f) {
                bastilleMain.destroyTile(pos.cpy().sub(24,24), new Vector2(48, 48));
            }
        }
    }
}

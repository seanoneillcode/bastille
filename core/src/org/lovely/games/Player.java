package org.lovely.games;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;

import static org.lovely.games.LoadingManager.SOUND_FALL_SCREAM;
import static org.lovely.games.LoadingManager.SOUND_HUP;
import static org.lovely.games.LoadingManager.SOUND_SWISH;

public class Player {

    private float PLAYER_SPEED = 1.8f;

    private static final float JUMP_TOTAL_TIME = 1.0f;
    private static final float JUMP_HALF_TIME = 0.5f;
    private static final float STEP_TIMER = 0.3f;
    Vector2 pos;
    Vector2 size;
    Vector2 offset;
    Vector2 physics;
    public boolean needsGround;
    public PlayerState state;
    float fallTimer = 0;
    float delta = 0;
    float jumpTimer = 0;
    float z;
    float impulse = 0;
    public boolean isMoving;
    private float stepTimer = 0;

    public Player(Vector2 pos, Vector2 size, Vector2 offset, boolean needsGround) {
        this.pos = pos;
        this.size = size;
        this.offset = offset;
        this.needsGround = needsGround;
        this.state = PlayerState.ALIVE;
        this.physics = new Vector2();
        this.z = 0;
        isMoving = false;
    }

    public void fall(SoundManager soundManager) {
        state = PlayerState.FALLING;
        fallTimer = 2.0f;
        delta = 0;
        soundManager.playSound(SOUND_FALL_SCREAM);
    }

    public void jump(SoundManager soundManager) {
        // first jump
        if (state == PlayerState.ALIVE) {
            soundManager.playSound(SOUND_HUP);
            jumpTimer = JUMP_TOTAL_TIME;
            state = PlayerState.JUMPING;
            delta = 0;
            impulse = 2.0f;
        }
        // continue to jump
        if (state == PlayerState.JUMPING && jumpTimer > JUMP_HALF_TIME) {
            impulse = impulse + 0.05f;
        }
    }

    public void update(SoundManager soundManager) {
        if (isMoving) {
            stepTimer = stepTimer - Gdx.graphics.getDeltaTime();
            if (stepTimer < 0) {
                soundManager.playSound(SOUND_SWISH);
                stepTimer = STEP_TIMER;
            }
        } else {
            stepTimer = 0;
        }
        isMoving = false;
    }

    public void move(Vector2 inputVector) {
        if (state == Player.PlayerState.ALIVE) {
            Vector2 change = inputVector.cpy().scl(PLAYER_SPEED);
            if (change.x != 0 || change.y != 0) {
                isMoving = true;
                pos.add(change);
            }
        }
        if (state == Player.PlayerState.JUMPING) {
            Vector2 change = inputVector.cpy().scl(PLAYER_SPEED).scl(0.8f);
            pos.add(change);
        }
    }

    enum PlayerState {
        DEAD,
        FALLING,
        ALIVE,
        JUMPING
    }

}

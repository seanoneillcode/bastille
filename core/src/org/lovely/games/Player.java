package org.lovely.games;

import com.badlogic.gdx.math.Vector2;

public class Player {

    private static final float JUMP_TOTAL_TIME = 1.0f;
    private static final float JUMP_HALF_TIME = 0.5f;
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

    public Player(Vector2 pos, Vector2 size, Vector2 offset, boolean needsGround) {
        this.pos = pos;
        this.size = size;
        this.offset = offset;
        this.needsGround = needsGround;
        this.state = PlayerState.ALIVE;
        this.physics = new Vector2();
        this.z = 0;
    }

    public void fall() {
        state = PlayerState.FALLING;
        fallTimer = 2.0f;
        delta = 0;
    }

    public void jump() {
        // first jump
        if (state == PlayerState.ALIVE) {
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

    enum PlayerState {
        DEAD,
        FALLING,
        ALIVE,
        JUMPING
    }

}

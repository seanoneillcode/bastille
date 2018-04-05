package org.lovely.games;

import com.badlogic.gdx.math.Vector2;

public class Effect {
    Vector2 pos;
    float timer;
    String image;
    float anim;
    Vector2 mov;

    public Effect(Vector2 pos, float timer, String image, Vector2 mov) {
        this.pos = pos;
        this.timer = timer;
        this.image = image;
        this.anim = 0;
        this.mov = mov;
    }
}

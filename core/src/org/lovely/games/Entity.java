package org.lovely.games;

import com.badlogic.gdx.math.Vector2;

public class Entity {

    String dyingImage;
    Vector2 pos;
    Vector2 size;
    EntityState state;
    private String image;
    float anim;

    public Entity(Vector2 pos, Vector2 size, String image, String dyingImage) {
        this.pos = pos;
        this.size = size;
        this.state = EntityState.ALIVE;
        this.anim = 0;
        this.image = image;
        this.dyingImage = dyingImage;
    }

    public void update(BastilleMain bastilleMain, SoundManager soundManager) {

    }

    public boolean shouldLoop() {
        return state == EntityState.ALIVE;
    }

    public String getImage() {
        return state == EntityState.ALIVE ? image : dyingImage;
    }

    enum EntityState {
        ALIVE,
        DEAD,
        DYING
    }
}

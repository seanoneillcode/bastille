package org.lovely.games;

import com.badlogic.gdx.math.Vector2;

public class Entity {

    String dyingImage;
    Vector2 pos;
    Vector2 size;
    EntityState state;
    private String image;
    float anim;
    Vector2 move;

    public Entity(Vector2 pos, Vector2 size, String image, String dyingImage, Vector2 move) {
        this.pos = pos;
        this.size = size;
        this.state = EntityState.ALIVE;
        this.anim = 0;
        this.image = image;
        this.dyingImage = dyingImage;
        this.move = move;
    }

    public void update(BastilleMain bastilleMain, SoundManager soundManager) {

    }

    public boolean shouldLoop() {
        return state == EntityState.ALIVE;
    }

    public String getImage() {
        return state == EntityState.ALIVE ? image : dyingImage;
    }

    public void handleCollision(Player player) {

    }

    enum EntityState {
        ALIVE,
        DEAD,
        DYING
    }
}

package org.lovely.games;

import com.badlogic.gdx.Gdx;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class EntityManager {

    List<Entity> entities;

    EntityManager() {
        entities = new ArrayList<>();
    }

    public void update(BastilleMain bastilleMain, SoundManager soundManager) {
        Iterator<Entity> entityIterator = entities.iterator();
        while (entityIterator.hasNext()) {
            Entity entity = entityIterator.next();
            entity.anim += Gdx.graphics.getDeltaTime();
            entity.update(bastilleMain, soundManager);
            if (entity.state == Entity.EntityState.DEAD) {
                entityIterator.remove();
            }
        }
    }


}

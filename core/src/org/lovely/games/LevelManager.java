package org.lovely.games;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

import static org.lovely.games.LoadingManager.*;

public class LevelManager {

    public static final int NUM_CLOUDS = 64;
    private static final int NUM_POINTS = 8;
    List<Tile> tiles = new ArrayList<>();
    private float animationDelta = 0f;
    public static float TILE_SIZE = 16;
    private int MAP_SIZE = 64;
    List<Cloud> clouds = new ArrayList<>();
    List<String> cloundImages = Arrays.asList(CLOUD_0, CLOUD_1, CLOUD_2, CLOUD_3);
    public Tile goalTile;
    LevelGenerator levelGenerator;

    LevelManager() {
        levelGenerator = new LevelGenerator();
    }

    public void start() {
        tiles.clear();
        for (int i = 0; i < NUM_CLOUDS; i++) {
            Vector2 pos = new Vector2(MathUtils.random(0, MAP_SIZE * TILE_SIZE), MathUtils.random(0, MAP_SIZE * TILE_SIZE));
            Vector2 mov = new Vector2(MathUtils.random(0.1f, 1.0f), 0);
            String img = cloundImages.get(MathUtils.random(cloundImages.size() - 1));
            float scale = mov.x / 1.0f;
            addCloud(pos, img, mov, scale);
        }
        tiles.addAll(levelGenerator.generate(NUM_POINTS, MAP_SIZE));
        int goalTileIndex = MathUtils.random(0, tiles.size() - 1);
        goalTile = tiles.get(goalTileIndex);
        Vector2 lPos = tiles.get(MathUtils.random(0, tiles.size() - 1)).pos;
        tiles.add(new Tile(lPos.cpy(), new Vector2(128, 128), LIGHTHOUSE, false, Color.WHITE));
    }

    private void addCloud(Vector2 pos, String image, Vector2 mov, float scale) {
        clouds.add(new Cloud(pos, image, mov, scale));
    }

    public void update() {
        animationDelta = animationDelta + Gdx.graphics.getDeltaTime();
        for (Cloud cloud : clouds) {
            cloud.pos.add(cloud.mov);
            if (cloud.pos.x > (MAP_SIZE * TILE_SIZE * 2)) {
                cloud.pos.x = -(MAP_SIZE * TILE_SIZE);
                cloud.pos.y = MathUtils.random(0, MAP_SIZE * TILE_SIZE);
            }
        }
        tiles.removeIf(tile -> tile.isDead);
    }

    public Vector2 getStartPos() {
        return tiles.get(tiles.size() - 1).pos.cpy();
    }
}

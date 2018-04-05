package org.lovely.games;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

import java.util.ArrayList;
import java.util.List;

import static org.lovely.games.LoadingManager.LAND_EFFECT;

public class PlayerManager {

    private static final float GRAVITY = 0.1f;
    List<Player> players;

    PlayerManager() {
        players = new ArrayList<>();
    }

    public Player addPlayer(Vector2 pos, Vector2 size, Vector2 offset, boolean needsGround) {
        Player player = new Player(pos, size, offset, needsGround);
        players.add(player);
        return player;
    }

    public void update(LevelManager levelManager, BastilleMain bastilleMain, EffectManager effectManager) {
        boolean hasDeadEnts = false;
        for (Player player : players) {
            if (player.state == Player.PlayerState.ALIVE && player.needsGround && !isOnGround(levelManager, player.pos.cpy().add(player.offset), player.size)) {
                player.fall();
            }
            if (player.state == Player.PlayerState.FALLING) {
                player.fallTimer = player.fallTimer - Gdx.graphics.getDeltaTime();
                if (player.fallTimer < 0) {
                    player.state = Player.PlayerState.DEAD;
                    hasDeadEnts = true;
                }
            }
            if (player.state == Player.PlayerState.JUMPING) {
                player.impulse = player.impulse - GRAVITY;
                player.z = player.z - GRAVITY;
                player.z = player.z + player.impulse;
                player.pos = player.pos.cpy().add(player.physics);
                if (player.z <= 0) {
                    player.state = Player.PlayerState.ALIVE;
                    player.jumpTimer = 0;
                    player.z = 0;
                    if (player.needsGround && !isOnGround(levelManager, player.pos.cpy().add(player.offset), player.size)) {
                        player.fall();
                    } else {
                        effectManager.addEffect(player.pos.cpy().add(-4, -10), LAND_EFFECT, 0.3f, new Vector2());
                    }

                }
            }
            player.jumpTimer = player.jumpTimer - Gdx.graphics.getDeltaTime();
            player.delta = player.delta + Gdx.graphics.getDeltaTime();
        }
        if (bastilleMain.player.state == Player.PlayerState.DEAD) {
            bastilleMain.player = addPlayer(levelManager.getStartPos(), new Vector2(8, 8), new Vector2(4, 4), true);
        }
        if (hasDeadEnts) {
            players.removeIf(player -> player.state == Player.PlayerState.DEAD);
        }
    }

    private boolean isOnGround(LevelManager levelManager, Vector2 pos, Vector2 size) {
        for (Tile tile : levelManager.tiles) {
            if (tile.isGround && isOverlap(tile.pos, tile.size, pos, size)) {
                return true;
            }
        }
        return false;
    }

    public static boolean isOverlap(Vector2 p1, Vector2 s1, Vector2 p2, Vector2 s2) {
        Rectangle rect1 = new Rectangle(p1.x, p1.y, s1.x, s1.y);
        Rectangle rect2 = new Rectangle(p2.x, p2.y, s2.x, s2.y);
        return rect1.overlaps(rect2);
    }

    public void start() {
        players.clear();
    }
}

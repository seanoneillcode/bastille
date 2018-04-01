package org.lovely.games;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;

public class CameraManager {


    private float VIEWPORT_WIDTH = 320;
    private float VIEWPORT_HEIGHT = 240f;

    private float CAMERA_CATCHUP_SPEED = 0.8f;
    public OrthographicCamera camera;


    public CameraManager() {
        camera = new OrthographicCamera();
        camera.setToOrtho(false, VIEWPORT_WIDTH, VIEWPORT_HEIGHT);
    }

    public void update(Vector2 playerPos, Vector2 inputPos) {
        camera.position.set(getCameraPosition(playerPos, inputPos));
        camera.update();
    }

    public Vector3 getCameraPosition(Vector2 playerPos, Vector2 inputPos) {
        Vector2 lookAhead = inputPos.scl(96);
        Vector3 target = new Vector3(playerPos.x, playerPos.y, 0);
        target.x = target.x + lookAhead.x;
        target.y = target.y + lookAhead.y;
        final float speed = CAMERA_CATCHUP_SPEED * Gdx.graphics.getDeltaTime();
        float ispeed = 1.0f - speed;
        Vector3 cameraPosition = camera.position.cpy();
        cameraPosition.scl(ispeed);
        target.scl(speed);
        cameraPosition.add(target);
        float cameraTrailLimit = 100.0f;
        cameraPosition.x = MathUtils.clamp(cameraPosition.x, -cameraTrailLimit + playerPos.x, cameraTrailLimit + playerPos.x);
        cameraPosition.y = MathUtils.clamp(cameraPosition.y, -cameraTrailLimit + playerPos.y, cameraTrailLimit + playerPos.y);
        return cameraPosition;
    }
}

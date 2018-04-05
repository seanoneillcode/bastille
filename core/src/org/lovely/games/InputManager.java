package org.lovely.games;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.math.Vector2;


public class InputManager {

    private static final float THROW_COOLDOWN = 0.5f;
    private Vector2 inputVector;
    public boolean isRight = true;
    private float zoom = 0;
    private float throwCoolDown = 0;

    public void update(BastilleMain bastilleMain) {
        Vector2 inputVector = getInput();
        bastilleMain.movePlayer(inputVector);
        zoom = 0;
        if (Gdx.input.isKeyPressed(Input.Keys.M)) {
            zoom = 0.02f;
        }
        if (Gdx.input.isKeyPressed(Input.Keys.N)) {
            zoom = -0.02f;
        }
        if (Gdx.input.isKeyPressed(Input.Keys.SPACE) || Gdx.input.isKeyPressed(Input.Keys.G)) {
            bastilleMain.jumpPlayer();
        }
        if (Gdx.input.isKeyPressed(Input.Keys.F)) {
            throwBomb(bastilleMain);
        }
        if (Gdx.input.isKeyPressed(Input.Keys.ESCAPE)) {
            Gdx.app.exit();
        }
        if (throwCoolDown > 0) {
            throwCoolDown = throwCoolDown - Gdx.graphics.getDeltaTime();
        }
    }

    public Vector2 getInput() {
        inputVector = new Vector2();
        boolean isLeftPressed = Gdx.input.isKeyPressed(Input.Keys.LEFT) || Gdx.input.isKeyPressed(Input.Keys.A);
        boolean isRightPressed = Gdx.input.isKeyPressed(Input.Keys.RIGHT) || Gdx.input.isKeyPressed(Input.Keys.D);
        boolean isUpPressed = Gdx.input.isKeyPressed(Input.Keys.UP) || Gdx.input.isKeyPressed(Input.Keys.W);
        boolean isDownPressed = Gdx.input.isKeyPressed(Input.Keys.DOWN) || Gdx.input.isKeyPressed(Input.Keys.S);

        if (isLeftPressed) {
            inputVector.x = inputVector.x - 1;
            isRight = false;
        }
        if (isRightPressed) {
            inputVector.x = inputVector.x + 1;
            isRight = true;
        }
        if (isUpPressed) {
            inputVector.y = inputVector.y + 1;
        }
        if (isDownPressed) {
            inputVector.y = inputVector.y - 1;
        }
        return inputVector;
    }

    public boolean isMoving() {
        return inputVector.x != 0 || inputVector.y != 0;
    }

    public float getZoom() {
        return zoom;
    }

    public void throwBomb(BastilleMain bastilleMain) {
        if (throwCoolDown <= 0) {
            throwCoolDown = THROW_COOLDOWN;
            bastilleMain.throwBomb();
        }
    }
}

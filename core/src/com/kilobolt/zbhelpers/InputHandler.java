package com.kilobolt.zbhelpers;

import com.badlogic.gdx.InputProcessor;
import com.kilobolt.gameobjects.Bird;
import com.kilobolt.gameworld.GameWorld;
import com.kilobolt.ui.SimpleButton;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Alex on 7/30/2015.
 */
public class InputHandler implements InputProcessor{

    private Bird myBird;
    private GameWorld myWorld;

    private List<SimpleButton> menuButtons;
    private SimpleButton playButton;

    private float scaleFactorX;
    private float scaleFactorY;

    // Ask for a reference to the Bird when InputHandler is created.
    public InputHandler(GameWorld myWorld, float scaleFactorX, float scaleFactorY) {
        this.myWorld = myWorld;
        // myBird now represents the gameWorld's bird.
        myBird = myWorld.getBird();

        int midPointY = myWorld.getMidPointY();

        menuButtons = new ArrayList<SimpleButton>();
        playButton = new SimpleButton(136/2 - (AssetLoader.playButtonUp.getRegionWidth() / 2),
                midPointY + 50, 29, 16, AssetLoader.playButtonUp, AssetLoader.playButtonDown);
        menuButtons.add(playButton);
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        screenX = scaleX(screenX);
        screenY = scaleY(screenY);

        System.out.println(screenX + " " + screenY);

        if(myWorld.isMenu()) {
            playButton.isTouchDown(screenX, screenY);
        }else if(myWorld.isReady()){
            myWorld.start();
        }

        myBird.onClick();

        if (myWorld.isGameOver() || myWorld.isHighScore()){
            // Reset all variables, go to GameState.READY
            myWorld.restart();
        }

        return true; // Return true to say we handled the touch.
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        screenX = scaleX(screenX);
        screenY = scaleY(screenY);

        if(myWorld.isMenu()){
            if(playButton.isTouchUp(screenX, screenY)){
                myWorld.ready();
                return true;
            }
        }
        return false;
    }




    @Override
    public boolean keyDown(int keycode) {
        return false;
    }

    @Override
    public boolean keyUp(int keycode) {
        return false;
    }

    @Override
    public boolean keyTyped(char character) {
        return false;
    }


    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        return false;
    }

    @Override
    public boolean mouseMoved(int screenX, int screenY) {
        return false;
    }

    @Override
    public boolean scrolled(int amount) {
        return false;
    }

    public int scaleX(int screenX) {
        return (int) (screenX / scaleFactorX);
    }

    public int scaleY(int screenY) {
        return (int) (screenY / scaleFactorY);
    }

    public List<SimpleButton> getMenuButtons() {
        return menuButtons;
    }
}

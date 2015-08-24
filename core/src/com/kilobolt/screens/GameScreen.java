package com.kilobolt.screens;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.kilobolt.gameobjects.Bird;
import com.kilobolt.gameworld.GameRenderer;
import com.kilobolt.gameworld.GameWorld;
import com.kilobolt.zbhelpers.InputHandler;

/**
 * Created by Alex on 7/29/2015.
 */
public class GameScreen implements Screen{

    private GameWorld world;
    private GameRenderer renderer;

    private float runTime = 0;

    public GameScreen(){

        /*Remember that our game will be 136 units wide. Our screen may be 1080 pixels wide, so we
        must scale everything down by about 1/8. To get the game height, we must take the screen
        height and scale that down by the same factor!*/

        float screenWidth = Gdx.graphics.getWidth();
        float screenHeight = Gdx.graphics.getHeight();
        float gameWidth = 136;
        float gameHeight = screenHeight / (screenWidth/gameWidth);

        int midPointY = (int) (gameHeight/2);

        world = new GameWorld(midPointY);


        Gdx.input.setInputProcessor(new InputHandler(world, screenWidth/gameWidth, screenHeight/gameHeight));

       /* shortened version of this:
        Bird bird = world.getBird();
        InputHandler handler = new InputHandler(bird);
        Gdx.input.setInputProcessor(handler);*/
        renderer = new GameRenderer(world, (int) gameHeight, midPointY);
    }


    @Override
    public void show() {
        Gdx.app.log("GameScreen", "show called");
    }

    @Override
    public void render(float delta) {
        runTime += delta;
        world.update(delta);
        renderer.render(delta, runTime);
    }

    @Override
    public void resize(int width, int height) {
    }

    @Override
    public void pause() {
    }

    @Override
    public void resume() {
    }

    @Override
    public void hide() {
    }

    @Override
    public void dispose() {
    }
}

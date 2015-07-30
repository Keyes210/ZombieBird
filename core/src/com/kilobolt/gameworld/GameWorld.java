package com.kilobolt.gameworld;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;

import com.badlogic.gdx.math.Rectangle;

/**
 * Created by Alex on 7/29/2015.
 */
public class GameWorld {

    //params: x,y,width, height?
    private Rectangle rect = new Rectangle(0,0,17,12);

    public void update(float delta){
        /*// Sets a Color to Fill the Screen with (RGB = 10, 15, 230), Opacity of 1 (100%)
        Gdx.gl.glClearColor(10/255.0f, 15/255.0f, 230/255.0f, 1f);
        // Fills the screen with the selected color
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        // Covert Frame rate to String, print it
        Gdx.app.log("GameScreen FPS", (1/delta) + "");*/

        Gdx.app.log("GameWorld", "update");
        rect.x++;
        if (rect.x > 137) rect.x = 0;
    }

    public Rectangle getRect(){
        return rect;
    }
}

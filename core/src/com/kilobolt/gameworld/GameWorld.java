package com.kilobolt.gameworld;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;

import com.badlogic.gdx.math.Rectangle;
import com.kilobolt.gameobjects.Bird;

/**
 * Created by Alex on 7/29/2015.
 */
public class GameWorld {
    private Bird bird;

    public GameWorld(int midPointY){
        //initialize bird
        bird = new Bird(33, midPointY - 5, 17, 12);
    }


    public void update(float delta){
        bird.update(delta);
    }

    public Bird getBird(){
        return bird;
    }

}

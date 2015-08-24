package com.kilobolt.gameworld;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.kilobolt.TweenAccessor.Value;
import com.kilobolt.TweenAccessor.ValueAccessor;
import com.kilobolt.gameobjects.Bird;
import com.kilobolt.gameobjects.Grass;
import com.kilobolt.gameobjects.Pipe;
import com.kilobolt.gameobjects.ScrollHandler;
import com.kilobolt.ui.SimpleButton;
import com.kilobolt.zbhelpers.AssetLoader;
import com.kilobolt.zbhelpers.InputHandler;

import java.util.List;

import aurelienribon.tweenengine.Tween;
import aurelienribon.tweenengine.TweenEquations;
import aurelienribon.tweenengine.TweenManager;

/**
 * Created by Alex on 7/29/2015.
 */
public class GameRenderer {

    private GameWorld myWorld;
    private OrthographicCamera cam;
    private ShapeRenderer shapeRenderer;

    private SpriteBatch batcher;

    private int midPointY;


    // Game Objects
    private Bird bird;
    private ScrollHandler scroller;
    private Grass frontGrass, backGrass;
    private Pipe pipe1, pipe2, pipe3;

    // Game Assets
    private TextureRegion bg, grass;
    private Animation birdAnimation;
    private TextureRegion birdMid;
    private TextureRegion skullUp, skullDown, bar;

    //Tween
    private TweenManager manager;
    private Value alpha = new Value();

    //buttons
    private List<SimpleButton> menuButtons;

    public GameRenderer(GameWorld world, int gameHeight, int midPointY) {
        this.myWorld = world;

        // The word "this" refers to this instance.
        // We are setting the instance variables' values to be that of the
        // parameters passed in from GameScreen.

        this.midPointY = midPointY;
        this.menuButtons = ((InputHandler) Gdx.input.getInputProcessor()).getMenuButtons();

        cam = new OrthographicCamera();
        //params: do we want orthographic projection?, width, height
        cam.setToOrtho(true, 136, gameHeight);

        batcher = new SpriteBatch();
        // Attach batcher to camera
        batcher.setProjectionMatrix(cam.combined);


        shapeRenderer = new ShapeRenderer();
        //attaches shape renderer to camera
        shapeRenderer.setProjectionMatrix(cam.combined);

        // Call helper methods to initialize instance variables
        initGameObjects();
        initAssets();
        setupTweens();
    }

    private void setupTweens() {
        Tween.registerAccessor(Value.class, new ValueAccessor());
        manager = new TweenManager();
        Tween.to(alpha, -1, .5f).target(0).ease(TweenEquations.easeOutQuad).start(manager);
    }

    private void initGameObjects() {
        bird = myWorld.getBird();
        scroller = myWorld.getScroller();
        frontGrass = scroller.getFrontGrass();
        backGrass = scroller.getBackGrass();
        pipe1 = scroller.getPipe1();
        pipe2 = scroller.getPipe2();
        pipe3 = scroller.getPipe3();
    }

    private void initAssets() {
        bg = AssetLoader.bg;
        grass = AssetLoader.grass;
        birdAnimation = AssetLoader.birdAnimation;
        birdMid = AssetLoader.bird;
        skullUp = AssetLoader.skullUp;
        skullDown = AssetLoader.skullDown;
        bar = AssetLoader.bar;
    }

    private void drawGrass() {
        batcher.draw(grass, frontGrass.getX(), frontGrass.getY(), frontGrass.getWidth(),
                frontGrass.getHeight());

        batcher.draw(grass, backGrass.getX(), backGrass.getY(), backGrass.getWidth(),
                backGrass.getHeight());
    }

    private void drawSkulls() {
        // Temporary code! Sorry about the mess :)
        // We will fix this when we finish the Pipe class.

        batcher.draw(skullUp, pipe1.getX() - 1,
                pipe1.getY() + pipe1.getHeight() - 14, 24, 14);
        batcher.draw(skullDown, pipe1.getX() - 1,
                pipe1.getY() + pipe1.getHeight() + 45, 24, 14);

        batcher.draw(skullUp, pipe2.getX() - 1,
                pipe2.getY() + pipe2.getHeight() - 14, 24, 14);
        batcher.draw(skullDown, pipe2.getX() - 1,
                pipe2.getY() + pipe2.getHeight() + 45, 24, 14);

        batcher.draw(skullUp, pipe3.getX() - 1,
                pipe3.getY() + pipe3.getHeight() - 14, 24, 14);
        batcher.draw(skullDown, pipe3.getX() - 1,
                pipe3.getY() + pipe3.getHeight() + 45, 24, 14);
    }

    private void drawPipes() {
        // Temporary code! Sorry about the mess :)
        // We will fix this when we finish the Pipe class.
        batcher.draw(bar, pipe1.getX(), pipe1.getY(), pipe1.getWidth(),
                pipe1.getHeight());
        batcher.draw(bar, pipe1.getX(), pipe1.getY() + pipe1.getHeight() + 45,
                pipe1.getWidth(), midPointY + 66 - (pipe1.getHeight() + 45));

        batcher.draw(bar, pipe2.getX(), pipe2.getY(), pipe2.getWidth(),
                pipe2.getHeight());
        batcher.draw(bar, pipe2.getX(), pipe2.getY() + pipe2.getHeight() + 45,
                pipe2.getWidth(), midPointY + 66 - (pipe2.getHeight() + 45));

        batcher.draw(bar, pipe3.getX(), pipe3.getY(), pipe3.getWidth(),
                pipe3.getHeight());
        batcher.draw(bar, pipe3.getX(), pipe3.getY() + pipe3.getHeight() + 45,
                pipe3.getWidth(), midPointY + 66 - (pipe3.getHeight() + 45));
    }

    private void drawBirdCentered(float runTime) {
        batcher.draw(birdAnimation.getKeyFrame(runTime), 59, bird.getY() - 15,
                bird.getWidth() / 2.0f, bird.getHeight() / 2.0f,
                bird.getWidth(), bird.getHeight(), 1, 1, bird.getRotation());
    }

    private void drawBird(float runTime){
        if(bird.shouldntFlap()){
            batcher.draw(birdMid, bird.getX(), bird.getY(), bird.getWidth()/ 2.0f, bird.getHeight() / 2.0f,
                    bird.getWidth(), bird.getHeight(), 1, 1, bird.getRotation());
        }else{
            batcher.draw(birdAnimation.getKeyFrame(runTime), bird.getX(),
                    bird.getY(), bird.getWidth() / 2.0f,
                    bird.getHeight() / 2.0f, bird.getWidth(), bird.getHeight(),
                    1, 1, bird.getRotation());
        }
    }

    private void drawMenuUI() {
        batcher.draw(AssetLoader.zbLogo, 136 / 2 - 56, midPointY - 50,
                AssetLoader.zbLogo.getRegionWidth() / 1.2f,
                AssetLoader.zbLogo.getRegionHeight() / 1.2f);

        for (SimpleButton button : menuButtons) {
            button.draw(batcher);
        }

    }

    private void drawScore() {
        int length = ("" + myWorld.getScore()).length();
        AssetLoader.shadow.draw(batcher, "" + myWorld.getScore(),
                68 - (3 * length), midPointY - 82);
        AssetLoader.font.draw(batcher, "" + myWorld.getScore(),
                68 - (3 * length), midPointY - 83);
    }


    public void render(float delta, float runTime) {
        // Fill the entire screen with black, to prevent potential flickering.
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        // Begin ShapeRenderer
        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);

        // Draw Background color
        shapeRenderer.setColor(55 / 255.0f, 80 / 255.0f, 100 / 255.0f, 1);
        shapeRenderer.rect(0, 0, 136, midPointY + 66);

        // Draw Grass
        shapeRenderer.setColor(111 / 255.0f, 186 / 255.0f, 45 / 255.0f, 1);
        shapeRenderer.rect(0, midPointY + 66, 136, 11);

        // Draw Dirt
        shapeRenderer.setColor(147 / 255.0f, 80 / 255.0f, 27 / 255.0f, 1);
        shapeRenderer.rect(0, midPointY + 77, 136, 52);

        // End ShapeRenderer
        shapeRenderer.end();

        // Begin SpriteBatch
        batcher.begin();
        // Disable transparency
        // This is good for performance when drawing images that do not require
        // transparency.
        batcher.disableBlending();
        batcher.draw(bg, 0, midPointY + 23, 136, 43);

        drawGrass();

        drawPipes();

        // The skulls need transparency, so we enable that again.
        batcher.enableBlending();

        drawSkulls();

        if(myWorld.isRunning()){
            drawBird(runTime);
            drawScore();
        }else if(myWorld.isReady()){
            drawBird(runTime);
            drawScore();
        }else if(myWorld.isMenu()){
            drawBirdCentered(runTime);
            drawMenuUI();
        }else if(myWorld.isGameOver()){
            drawBird(runTime);
            drawScore();
        }else if(myWorld.isHighScore()){
            drawBird(runTime);
            drawScore();
        }

        /*// Draw bird at its coordinates. Retrieve the Animation object from
        // AssetLoader
        // Pass in the runTime variable to get the current frame.
        if (bird.shouldntFlap()) {
            batcher.draw(birdMid, bird.getX(), bird.getY(), bird.getWidth() / 2.0f, bird.getHeight() / 2.0f,
                    bird.getWidth(), bird.getHeight(), 1, 1, bird.getRotation());
        } else {
            batcher.draw(birdAnimation.getKeyFrame(runTime), bird.getX(), bird.getY(), bird.getWidth() / 2.0f,
                    bird.getHeight() / 2.0f, bird.getWidth(), bird.getHeight(), 1, 1, bird.getRotation());
        }

        if (myWorld.isReady()){
            // Draw shadow first
            AssetLoader.shadow.draw(batcher, "Touch me", (136 / 2)
                    - (42), 76);
            // Draw text
            AssetLoader.font.draw(batcher, "Touch me", (136 / 2)
                    - (42 - 1), 75);
        }else {
               if(myWorld.isGameOver() || myWorld.isHighScore()) {

                   if (myWorld.isGameOver()) {
                       AssetLoader.shadow.draw(batcher, "Game Over", 25, 56);
                       AssetLoader.font.draw(batcher, "Game Over", 24, 55);

                       AssetLoader.shadow.draw(batcher, "High Score:", 23, 106);
                       AssetLoader.font.draw(batcher, "High Score:", 22, 105);

                       String highScore = AssetLoader.getHighScore() + "";

                       // Draw shadow first
                       AssetLoader.shadow.draw(batcher, highScore, (136 / 2)
                               - (3 * highScore.length()), 128);
                       // Draw text
                       AssetLoader.font.draw(batcher, highScore, (136 / 2)
                               - (3 * highScore.length() - 1), 127);
                   } else {
                       AssetLoader.shadow.draw(batcher, "High Score!", 19, 56);
                       AssetLoader.font.draw(batcher, "High Score!", 18, 55);
                   }
                   AssetLoader.shadow.draw(batcher, "Try again?", 23, 76);
                   AssetLoader.font.draw(batcher, "Try again?", 24, 75);

                   // Convert integer into String
                   String score = myWorld.getScore() + "";

                   // Draw shadow first
                   AssetLoader.shadow.draw(batcher, score,
                           (136 / 2) - (3 * score.length()), 12);
                   // Draw text
                   AssetLoader.font.draw(batcher, score,
                           (136 / 2) - (3 * score.length() - 1), 11);

               }
            // Convert integer into String
            String score = myWorld.getScore() + "";

            // Draw shadow first
            AssetLoader.shadow.draw(batcher, "" + myWorld.getScore(), (136 / 2) - (3 * score.length()), 12);
            // Draw text
            AssetLoader.font.draw(batcher, "" + myWorld.getScore(), (136 / 2) - (3 * score.length() - 1), 11);

        }*/

        // End SpriteBatch
        batcher.end();
        drawTransition(delta);
    }

    private void drawTransition(float delta) {
        if (alpha.getValue() > 0) {
            manager.update(delta);
            Gdx.gl.glEnable(GL20.GL_BLEND);
            Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);
            shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
            shapeRenderer.setColor(1, 1, 1, alpha.getValue());
            shapeRenderer.rect(0, 0, 136, 300);
            shapeRenderer.end();
            Gdx.gl.glDisable(GL20.GL_BLEND);
        }
    }

}

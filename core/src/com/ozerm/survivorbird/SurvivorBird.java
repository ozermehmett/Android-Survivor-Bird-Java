package com.ozerm.survivorbird;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.utils.ScreenUtils;

import java.util.Random;

public class SurvivorBird extends ApplicationAdapter {
	private SpriteBatch batch;
	private Texture background;
	private Texture bird;
	private Texture bee1;
	private Texture bee2;
	private Texture bee3;
	private float birdX, birdY;
	private int gameState = 0;
	private float velocity = 0;
	private float gravity = 0.5f;
	private float sizeW;
	private float sizeH;
	private int numberOfEnemies;
	private float[] enemyX;
	private float distance;
	private float enemyVelocity = 4;
	private float[] enemyOffSet1;
	private float[] enemyOffSet2;
	private float[] enemyOffSet3;
	private Random random;
	private float beeY;
	private Circle birdCircle;
	private Circle[] enemyCircle1;
	private Circle[] enemyCircle2;
	private Circle[] enemyCircle3;
	private int score;
	private int scoredEnemy;
	private BitmapFont font;
	private BitmapFont fontGameOver;

	@Override
	public void create () {
		beeY = (float) Gdx.graphics.getHeight()/2;

		sizeW = (float) Gdx.graphics.getWidth()/15;
		sizeH = (float) Gdx.graphics.getHeight()/10;

		birdX = (float) (Gdx.graphics.getWidth()/2 - Gdx.graphics.getWidth()/3);
		birdY = (float) (Gdx.graphics.getHeight() / 3);


		batch = new SpriteBatch();
		background = new Texture("background.png");
		bird = new Texture("bird.png");
		bee1  =new Texture("bee.png");
		bee2  =new Texture("bee.png");
		bee3  =new Texture("bee.png");

		distance = (float) Gdx.graphics.getWidth()/2;
		numberOfEnemies = 4;
		enemyX = new float[numberOfEnemies];
		enemyOffSet1 = new float[numberOfEnemies];
		enemyOffSet2 = new float[numberOfEnemies];
		enemyOffSet3 = new float[numberOfEnemies];

		random = new Random();
		font = new BitmapFont();
		fontGameOver = new BitmapFont();

		font.setColor(Color.WHITE);
		font.getData().setScale(8);

		fontGameOver.setColor(Color.WHITE);
		fontGameOver.getData().setScale(10);

		birdCircle = new Circle();
		enemyCircle1 = new Circle[numberOfEnemies];
		enemyCircle2 = new Circle[numberOfEnemies];
		enemyCircle3 = new Circle[numberOfEnemies];

		for (int i = 0; i < numberOfEnemies; i++){

			enemyOffSet1[i] = (random.nextFloat() - 0.5f) * (Gdx.graphics.getHeight() - 200);
			enemyOffSet2[i] = (random.nextFloat() - 0.5f) * (Gdx.graphics.getHeight() - 200);
			enemyOffSet3[i] = (random.nextFloat() - 0.5f) * (Gdx.graphics.getHeight() - 200);

			enemyX[i] = Gdx.graphics.getWidth() - (float) bee1.getWidth() / 2 + i * distance;

			enemyCircle1[i] = new Circle();
			enemyCircle2[i] = new Circle();
			enemyCircle3[i] = new Circle();
		}
		score = 0;
		scoredEnemy = 0;
	}

	@Override
	public void render () {
		batch.begin();
		batch.draw(background, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

		if (gameState == 1){

			if (enemyX[scoredEnemy] < birdX){
				score++;
				if (scoredEnemy < 3){
					scoredEnemy++;
				}else {
					scoredEnemy = 0;
				}
			}

			if (Gdx.input.justTouched()){
				//uçma
				velocity = 	-14;
			}

			for (int i = 0; i < numberOfEnemies; i++){
				if (enemyX[i] < sizeW ){
					enemyX[i] += numberOfEnemies * distance;

					enemyOffSet1[i] = (random.nextFloat() - 0.5f) * (Gdx.graphics.getHeight() - 200);
					enemyOffSet2[i] = (random.nextFloat() - 0.5f) * (Gdx.graphics.getHeight() - 200);
					enemyOffSet3[i] = (random.nextFloat() - 0.5f) * (Gdx.graphics.getHeight() - 200);

				}else {
					enemyX[i] -= enemyVelocity;
				}

				enemyX[i] -= enemyVelocity;
				batch.draw(bee1, enemyX[i], beeY + enemyOffSet1[i], sizeW, sizeH);
				batch.draw(bee2, enemyX[i], beeY + enemyOffSet2[i], sizeW, sizeH);
				batch.draw(bee3, enemyX[i], beeY + enemyOffSet3[i], sizeW, sizeH);

				enemyCircle1[i] = new Circle(enemyX[i] + sizeW/2 ,beeY + enemyOffSet1[i] + sizeH/2, sizeW/2);
				enemyCircle2[i] = new Circle(enemyX[i] + sizeW/2 ,beeY + enemyOffSet2[i] + sizeH/2, sizeW/2);
				enemyCircle3[i] = new Circle(enemyX[i] + sizeW/2 ,beeY + enemyOffSet3[i] + sizeH/2, sizeW/2);
			}


			if (birdY > 0){
				//yer çekimi, düşme
				velocity += gravity;
				birdY -= velocity;
			}else {
				gameState = 2;
			}

		}else if (gameState == 0){
			if (Gdx.input.justTouched()){
				//başlangıç tıklama
				gameState = 1;
			}
		} else if (gameState == 2) {

			fontGameOver.draw(batch, "Game Over!! Tap To Play Again", 100, beeY);

		if (Gdx.input.justTouched()){
				//başlangıç tıklama
				gameState = 1;
				birdY = (float) (Gdx.graphics.getHeight() / 3);

				for (int i = 0; i < numberOfEnemies; i++){

					enemyOffSet1[i] = (random.nextFloat() - 0.5f) * (Gdx.graphics.getHeight() - 200);
					enemyOffSet2[i] = (random.nextFloat() - 0.5f) * (Gdx.graphics.getHeight() - 200);
					enemyOffSet3[i] = (random.nextFloat() - 0.5f) * (Gdx.graphics.getHeight() - 200);

					enemyX[i] = Gdx.graphics.getWidth() - (float) bee1.getWidth() / 2 + i * distance;

					enemyCircle1[i] = new Circle();
					enemyCircle2[i] = new Circle();
					enemyCircle3[i] = new Circle();
				}
				velocity = 0;
				scoredEnemy = 0;
				score = 0;
			}

		}

		batch.draw(bird, birdX, birdY, sizeW, sizeH);
		font.draw(batch, String.valueOf(score), 100, 200);
		batch.end();

		birdCircle.set(birdX + sizeW/2, birdY + sizeH/2, sizeW/2);




		for (int i = 0; i < numberOfEnemies; i++){
			if (Intersector.overlaps(birdCircle, enemyCircle1[i]) || Intersector.overlaps(birdCircle, enemyCircle2[i]) || Intersector.overlaps(birdCircle, enemyCircle3[i])){
				gameState = 2;
			}
		}
	}
	
	@Override
	public void dispose () {

	}
}

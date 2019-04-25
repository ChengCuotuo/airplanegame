package com.nianzuochen;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.image.Image;
import javafx.scene.layout.Pane;
import javafx.util.Duration;

import java.util.ArrayList;
import java.util.EventListener;

/**
 * Created by lei02 on 2019/4/25.
 * 生成敌机的管理
 * 类
 */
public class EnemyBuilder extends Pane {
    private double posX;                //敌机移动的x坐标
    private double posY;                //敌机移动的y坐标
    private double speed;               //敌机移动的速度
    private long interval;              //产生敌机的时间间隔
    private ArrayList<Enemy> enemyArray;    //生成的敌机

    public EnemyBuilder (double posX, double posY, double speed) {
        this.posX = posX;
        this.posY = posY;
        this.speed = speed;
        this.interval = 1500;
        enemyArray = new ArrayList<>();
    }
    //生成敌机
    public void getEnemys () {
        EventHandler<ActionEvent> eventHandler = e -> {
            getAnEnemy();
        };
        Timeline bulletAnimation =
                new Timeline(new KeyFrame(Duration.millis(interval), eventHandler));
        bulletAnimation.setCycleCount(Timeline.INDEFINITE);
        bulletAnimation.play();
    }

    public void getAnEnemy () {
        //敌机的图片
        Image enemyBornImage = new Image("image/eplane1.png");
        Image[] enemyRuinImage =
                {new Image("image/eplane1.png"), new Image("image/eplane2.png"),
                        new Image("image/eplane3.png")};
        Enemy enemy = new Enemy(enemyBornImage, enemyRuinImage,posX, posY, speed);
        enemyArray.add(enemy);
        enemy.getAnEnemy();
        enemy.attack();
        super.getChildren().add(enemy);
    }
}

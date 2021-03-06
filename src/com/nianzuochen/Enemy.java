package com.nianzuochen;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Polyline;
import javafx.util.Duration;

import java.util.Random;

/**
 * Created by lei02 on 2019/4/24.
 * 敌机类，敌机的产生、移动、摧毁动画
 */
public class Enemy extends Pane {
    private double down;            //敌人移动的最低位置
    private double right;           //敌人移动的最右边位置
    private ImageView enemyImage;   //静态的敌机
    private ShowImages ruinImages;   //敌机被摧毁的动画
    private Polyline path;          //敌机移动的路线，使用 Polyline 折线，可以定义复杂的移动路线
    private double posX;            //敌机移动的x坐标
    private double posY;            //敌机移动的y坐标
    private double speed;           //敌机移动的速度
    private double enemyHeigh;      //敌机图片的高度
    private double enemyWidth;      //敌机图片的宽度
    private Random random = new Random();//随机数
    private  Timeline enemyAnimation;    //敌机移动的动画

    public Enemy(Image enemy, Image[] ruins, double down, double right, double speed) {
        this.down = down;
        this.right = right;
        this.enemyImage = new ImageView(enemy);
        this.ruinImages = new ShowImages(ruins);
        this.posX = 0.0;
        this.posY = 0.0;
        this.speed = speed;
        this.enemyHeigh = enemy.getHeight();
        this.enemyWidth = enemy.getWidth();
        //被摧毁的时候需要调用 ruinImages.ruin() 方法
    }
    // 获取一个敌机
    public void getAnEnemy() {
        posX = random.nextDouble() *
                (right - 2 * enemyImage.getImage().getWidth()) +
                enemyImage.getImage().getWidth();
        super.getChildren().add(enemyImage);
        enemyImage.setX(posX);
        enemyImage.setY(posY);
    }
    //敌机的出击
    public void attack () {
        EventHandler<ActionEvent> eventHandler = e -> {
           double positionY = enemyImage.getY() + speed;
           if (positionY <= down - enemyImage.getImage().getHeight()) {
               enemyImage.setY(positionY);
               this.posY = positionY;
           }
        };
        enemyAnimation =
                new Timeline(new KeyFrame(Duration.millis(100), eventHandler));
        enemyAnimation.setCycleCount((int) ((down - enemyHeigh) / speed));
        enemyAnimation.play();

        //当敌机动画停止的时候要将敌机移除面板，还需要即将存储该敌机的数组中删除它
        enemyAnimation.setOnFinished(e -> {
            super.getChildren().remove(enemyImage);
            //设置敌机的位置为 (0, 0) 标记为无效敌机
            this.posX = -1;
            this.posY = -1;
        });
    }
    //敌机摧毁的时候，返回敌机被摧毁的动画，停止飞机移动的动画，并将敌机移除
    public ImageView getRuinImages(double x, double y) {
        enemyAnimation.stop();
        //设置敌机的位置为 (0, 0) 标记为无效敌机
        this.posX = -1;
        this.posY = -1;
        if(super.getChildren().contains(ruinImages))  {
            return null;
        }
        super.getChildren().add(ruinImages);
        super.getChildren().remove(enemyImage);
        ruinImages.setX(x);
        ruinImages.setY(y);
        ruinImages.ruin();
        return ruinImages;
    }

    public void stopAnimation () {
        enemyAnimation.stop();
    }

    public double getEnemyWidth() {
        return enemyWidth;
    }

    public double getEnemyHeigh() {
        return enemyHeigh;
    }

    public double getPosX() {
        return posX;
    }

    public double getPosY() {
        return posY;
    }
}

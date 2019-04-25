package com.nianzuochen;

import javafx.animation.Interpolator;
import javafx.animation.KeyFrame;
import javafx.animation.PathTransition;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Point2D;
import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.shape.Line;
import javafx.util.Duration;

import java.util.ArrayList;

/**
 * Created by lei02 on 2019/4/22.
 * 玩家类，定义玩家的飞机移动、飞机动画、飞机摧毁动画、飞机的子弹发射动画
 */
public class Player extends Pane{
    private ImageView plane;        //玩家的飞机
    private double posX;            //飞机的位置坐标 x
    private double posY;            //飞机的位置坐标  y
    private double down;            //飞机移动的最低位置
    private double right;           //飞机移动的最右边位置
    private double speed;           //飞机的速度，其实就是每次触发事件飞机的移动距离
    private double width;           //飞机图片的宽度
    private double height;          //飞机图片的高度
    private ShowImages bornImages;  //飞机的动画效果
    private Image bullet;           //飞机发射的子弹
    private int bulletSpeed;        //子弹移动的速度
    private ArrayList<Bullet>  bullets;//飞机发射的子弹

    public Player(Image[] born, Image[] ruin, double posX, double posY,
                  double down, double right, double speed) {
        bornImages = new ShowImages(born, ruin);
        this.plane = bornImages;

        this.posX = posX;
        this.posY = posY;
        this.down = down;
        this.right = right;

        //这里不能使用 imageView 的 getImage().getWidth() 因为现在的 imageView 中的 image 是动态变化的
        this.width = born[0].getWidth();
        this.height = born[0].getHeight();
        this.speed = speed;

        //设置飞机位置，也就是玩家的初始位置
        super.setPrefSize(right + width, down + height);
        plane.setX(posX);
        plane.setY(posY);
        super.getChildren().add(plane);

        this.bulletSpeed = 20;   //子弹的默认速度
        bullets = new ArrayList<>();
    }

    //飞机向上移动
    public void moveUp() {
        if (posY <= 20) {
            posY = 20;
        } else {
            posY -= speed;
        }
    }

    //飞机向下移动
    public void moveDown() {
        if (posY >= down - height) {
            posY = down - height;
        } else {
            posY += speed;
        }
    }

    //飞机向左移动
    public void moveLeft() {
        if (posX <= 20) {
            posX = 20;
        } else {
            posX -= speed;
        }
    }

    //飞机向右移动
    public void moveRight() {
        if (posX >= right - width) {
            posX = right - width;
        } else {
            posX += speed;
        }
    }

    // 使用改变的坐标来重新定位 plane
    public void changePos() {
        plane.setX(posX);
        plane.setY(posY);
    }

    public void launch() {
        if (bullet == null) {
            return;
        } else {
            //获取子弹照片的大小，飞机此时的坐标
            double bulletWidth = bullet.getWidth();
            double bulletHeight = bullet.getHeight();
            //计算子弹初始位置，也就是在飞机的上方中央位置
            double bulletX = posX + (width - bulletWidth) / 2;
            double bulletY = posY + bulletHeight;
            Bullet bulletObj = new Bullet(bullet, bulletX, bulletY, bulletSpeed);
            super.getChildren().add(bulletObj);
            bullets.add(bulletObj);
            //发射子弹
            bulletObj.launch();
        }
    }

    //使用上下左右键控制飞机飞行方向
    //控制飞机子弹
    public void planeController() {
        super.setOnKeyPressed(e -> {
            switch (e.getCode()) {
                case W:
                    moveUp(); changePos();
                    break;
                case S:
                    moveDown(); changePos();
                    break;
                case A:
                    moveLeft(); changePos();
                    break;
                case D:
                    moveRight(); changePos();
                    break;
                case SPACE:
                    launch();
                    bulletMusic();
                    break;
            }
        });
        //当键盘释放的时候也触发移动事件体验更好，更流畅
        super.setOnKeyReleased(e -> {
            switch (e.getCode()) {
                case W:
                    moveUp(); changePos();
                    break;
                case S:
                    moveDown(); changePos();
                    break;
                case A:
                    moveLeft(); changePos();
                    break;
                case D:
                    moveRight(); changePos();
                    break;
            }
        });
    }

    //当点击发射子弹的时候也应该有音乐
    public void bulletMusic () {
        String musicPath =
                getClass().getResource("/sound/bullet.mp3").toString();
        Media media = new Media(musicPath);
        MediaPlayer bulletLaunch = new MediaPlayer(media);
        bulletLaunch.play();
    }
    public ArrayList<Bullet> getBullets() {
        return bullets;
    }

    public void setBullet(Image bullet) {
        this.bullet = bullet;
    }
}

package com.nianzuochen;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Point2D;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.util.Duration;

public class Bullet extends Pane {
    private ImageView bullet;       //子弹照片
    private double bulletX;         //子弹的 x 坐标
    private double bulletY;         //子弹的 y 坐标
    private int bulletSpeed;        //子弹的速度

    public Bullet (Image bullet, double bulletX, double bulletY, int bulletSpeed) {
        this.bullet = new ImageView(bullet);
        this.bulletX = bulletX;
        this.bulletY = bulletY;
        this.bulletSpeed = bulletSpeed;
    }

    //飞机发射子弹动画，如果没有指定的话，将不进行此动画
    public void launch() {
        //没有指定子弹照片
        if (bullet == null) {
            return ;
        }
        //指定子弹的照片
        //将子弹添加到面板中

        //子弹由初始位置移动值面板的最顶端
        // 产生多个子弹是不是需要使用多线程？先不使用试试
//        //动画使用的是路线动画， 路线就是从当前位置到面板的顶端
//        Line path = new Line(bulletX, bulletY, bulletX, 0);
//        PathTransition bulletAnimation =
//                new PathTransition(Duration.millis(3000), path, bulletImageView);

        //如何获取子弹的移动的坐标，如下方法不行，只能自己写 Timeline 动画
//        EventHandler<ActionEvent> printPosition = e-> {
//            Line line = (Line)bulletAnimation.getPath();
//            System.out.println(line.getLayoutY());
//        };
//        Timeline printAnimation = new Timeline(new KeyFrame(Duration.millis(100), printPosition));
//        printAnimation.setCycleCount(Timeline.INDEFINITE);
//
//        printAnimation.play();
//        bulletAnimation.play();
//        //当动画结束的时候需要将子弹照片移除
//        bulletAnimation.setOnFinished(e -> {
//            super.getChildren().remove(bulletImageView);
//        });
        super.getChildren().add(bullet);
        bullet.setX(bulletX);
        bullet.setY(bulletY);
        //使用  PathTransition 并不能获取子弹的移动位置，也就无法计算摧毁敌机的时间
        EventHandler<ActionEvent> eventHandler = e -> {
            double positionY = bullet.getY() - bulletSpeed;
            if (positionY > 0) {
                bullet.setY(positionY);
                this.bulletY = positionY;
            }
        };
        Timeline bulletAnimation =
                new Timeline(new KeyFrame(Duration.millis(100), eventHandler));
        bulletAnimation.setCycleCount((int) (bulletY / bulletSpeed));
        bulletAnimation.play();

        bulletAnimation.setOnFinished(e -> {
            super.getChildren().remove(bullet);
        });
    }

    public double getBulletX() {
        return bulletX;
    }

    public double getBulletY() {
        return bulletY;
    }
}

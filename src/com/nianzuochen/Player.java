package com.nianzuochen;

import javafx.animation.PathTransition;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Line;
import javafx.util.Duration;

/**
 * Created by lei02 on 2019/4/22.
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

    public Player(Image[] born, Image[] ruin, double posX, double posY, double down, double right, double speed) {
        bornImages = new ShowImages(born, ruin);
        this.plane = bornImages;
        //测试摧毁的动画
        //bornImages.ruin();

        //System.out.println(456);
        this.posX = posX;
        this.posY = posY;
        this.down = down;
        this.right = right;
        //这里不能使用 imageView 的 getImage().getWidth() 因为现在的 imageView 中的 image 是动态变化的
        this.width = born[0].getWidth();
        this.height = born[0].getHeight();
        this.speed = speed;
        //设置图片，也就是玩家的初始位置
        super.setPrefSize(right + width, down + height);
        plane.setX(posX);
        plane.setY(posY);
        super.getChildren().add(plane);
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

    //飞机发射子弹动画，如果没有指定的话，将不进行此动画
    public void launch() {
        //没有指定子弹照片
        if (bullet == null) {
            return ;
        }
        //指定子弹的照片
        //获取子弹照片的大小，飞机此时的坐标
        double bulletWidth = bullet.getWidth();
        double bulletHeight = bullet.getHeight();
        //计算子弹初始位置，也就是在飞机的上方中央位置
        double bulletX = posX + (bulletWidth - width) / 2;
        double bulletY = posY + bulletHeight;
        //子弹由初始位置移动值面板的最顶端
        // 产生多个子弹是不是需要使用多线程？先不使用试试
        //动画使用的是路线动画， 路线就是从当前位置到面板的顶端
        Line path = new Line(bulletX, bulletY, 0, bulletY);
        PathTransition bulletAnimation = new PathTransition(Duration.millis(1000), path, new ImageView(bullet));

    }

    public ImageView getPlane() {
        return plane;
    }

    public void setPlane(ImageView plane) {
        this.plane = plane;
    }

    public double getPosX() {
        return posX;
    }

    public void setPosX(double posX) {
        this.posX = posX;
    }

    public double getPosY() {
        return posY;
    }

    public void setPosY(double posY) {
        this.posY = posY;
    }

    public double getDown() {
        return down;
    }

    public void setDown(double down) {
        this.down = down;
    }

    public double getRight() {
        return right;
    }

    public void setRight(double right) {
        this.right = right;
    }

    public double getSpeed() {
        return speed;
    }

    public void setSpeed(double speed) {
        this.speed = speed;
    }

    public Image getBullet() {
        return bullet;
    }

    public void setBullet(Image bullet) {
        this.bullet = bullet;
    }
}

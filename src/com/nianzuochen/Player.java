package com.nianzuochen;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;

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
    private double width;           //图片的宽度
    private double height;          //图片的高度
    private ShowImages bornImages;  //飞机的动画效果

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

}

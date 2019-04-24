package com.nianzuochen;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Polyline;

/**
 * Created by lei02 on 2019/4/24.
 * 敌机类，敌机的产生、移动、摧毁动画
 */
public class Enemy extends Pane {
    private double down;            //敌人移动的最低位置
    private double right;           //敌人移动的最右边位置
    private ImageView enemyImage;   //静态的敌机
    private ImageView ruinImages;   //敌机被摧毁的动画
    private Polyline path;          //敌机移动的路线，使用 Polyline 折线，可以定义复杂的移动路线
    private double posX;            //敌机移动的x坐标
    private double posY;            //敌机移动的y坐标

    public Enemy(int down, int right, Image enemy, Image[] ruins) {
        this.down = down;
        this.right = right;
        this.enemyImage = new ImageView(enemy);
        this.ruinImages = new ShowImages(ruins);
        //被摧毁的时候需要调用 ruinImages.ruin() 方法
    }

    // 每次调用 getEnemy() 方法，产生一个敌机添加到面板中
    // 生成
}

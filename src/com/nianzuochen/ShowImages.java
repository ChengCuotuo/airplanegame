package com.nianzuochen;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.util.Duration;

/**
 * Created by lei02 on 2019/4/22.
 * 用来生成动态的 ImageView，也就是根据给定的 Image 在相同的时间间隔内交换
 */
public class ShowImages extends ImageView{
    private Image[] images;
    private int index;

    public ShowImages (Image[] images) {
        System.out.print(123);
        this.images = images;
        this.index = 0;
        //setImage(images[index++]);
        exchange();
    }

    public void exchange () {
        int length = images.length;
        //事件的功能是不断的改变 imageView 的图片
        EventHandler<ActionEvent> eventHandler = e -> {
            setImage(images[index++]);
            if (index == length) {
                index = 0;
            }
        };
        //创建东环
        //实现每隔一秒钟改变一次图片中的内容
        Timeline animation = new Timeline(new KeyFrame(Duration.millis(500), eventHandler));
        //动画执行的次数是无限次
        animation.setCycleCount(Timeline.INDEFINITE);
        // 开始动画
        animation.play();
    }
}

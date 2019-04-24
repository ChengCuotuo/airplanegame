package com.nianzuochen;

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
    private Image[] bornImages;
    private Image[] ruinImages;
    private int bornIndex;
    private int ruinIndex;
    private Timeline startAnimation;
    private Timeline ruinAnimation;
    //初始状态和摧毁都是动画
    public ShowImages (Image[] bornImages, Image[] ruinImages) {
        //System.out.print(123);
        this.bornImages = bornImages;
        this.ruinImages = ruinImages;
        this.bornIndex = 0;
        this.ruinIndex = 0;
        born ();
    }
    //摧毁是动画
    public ShowImages (Image[] ruinImages) {
        this.ruinImages = ruinImages;
        this.bornIndex = 0;
        this.ruinIndex = 0;
    }

    //初始化的动态的飞机
    private void born () {
        int length = bornImages.length;
        //事件的功能是不断的改变 imageView 的图片
        EventHandler<ActionEvent> eventHandler = e -> {
            setImage(bornImages[bornIndex++]);
            if (bornIndex == length) {
                bornIndex = 0;
            }
        };
        //创建动画
        //实现每隔一秒钟改变一次图片中的内容
        startAnimation = new Timeline(new KeyFrame(Duration.millis(500), eventHandler));
        //动画执行的次数是无限次
        startAnimation.setCycleCount(Timeline.INDEFINITE);
        // 开始动画
        startAnimation.play();
    }

    //飞机摧毁时候的动画
    public void ruin() {
        //初始动画停止
        startAnimation.stop();
        int length = ruinImages.length;
        //事件的功能是不断的改变 ruinImages 的图片
        EventHandler<ActionEvent> eventHandler = e -> {
            setImage(ruinImages[ruinIndex++]);
            if (ruinIndex == length) {
                ruinIndex = 0;
            }
        };
        //创建动画
        //实现每隔一秒钟改变一次图片中的内容
        ruinAnimation = new Timeline(new KeyFrame(Duration.millis(500), eventHandler));
        //动画执行的次数是无限次
        ruinAnimation.setCycleCount(length);
        // 开始动画
        ruinAnimation.play();
    }
}

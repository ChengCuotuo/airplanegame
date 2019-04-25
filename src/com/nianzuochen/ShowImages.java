package com.nianzuochen;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
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
        this.ruinAnimation = null;
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
        if (startAnimation != null) {
           startAnimation.stop();
        }
        //被摧毁的背景音乐
        String musicPath =
                getClass().getResource("/sound/enemy3_down.mp3").toString();
        Media media = new Media(musicPath);
        MediaPlayer mediaPlayer = new MediaPlayer(media);
        mediaPlayer.setCycleCount(1);
        mediaPlayer.play();

        int length = ruinImages.length;
        //事件的功能是不断的改变 ruinImages 的图片
        EventHandler<ActionEvent> eventHandler = e -> {
            if (ruinIndex >= length) {
                setImage(null);
            } else {
                setImage(ruinImages[ruinIndex++]);
            }
        };
        //创建动画
        //实现每隔一秒钟改变一次图片中的内容
        ruinAnimation = new Timeline(new KeyFrame(Duration.millis(300), eventHandler));
        //动画执行的次数是无限次
        ruinAnimation.setCycleCount(length + 1);
        // 开始动画
        ruinAnimation.play();

    }

    public Timeline getRuinAnimation() {
        return ruinAnimation;
    }
}

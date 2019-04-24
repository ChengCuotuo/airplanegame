package com.nianzuochen;

import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.Pane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.stage.Stage;

/**
 * Created by lei02 on 2019/4/22.
 */
public class MainPane extends Application{
    private Pane mainPane = new Pane();
    //玩家
    Player player;

    @Override
    public void start(Stage primaryStage) {
        //设置主面板的背景
        //背景照片
        Image backgroundImage= new Image("image/universe.jpg");
        mainPane.setBackground(
                new Background(
                        new BackgroundImage(backgroundImage, null, null, null, null)));
        //设置背景音乐
        String musicPath =
                getClass().getResource("/sound/game_music.mp3").toString();
        Media media = new Media(musicPath);
        MediaPlayer mediaPlayer = new MediaPlayer(media);
        //无限循环背景音乐
        mediaPlayer.setCycleCount(Timeline.INDEFINITE);
        mediaPlayer.play();

        //玩家的图片，传递多个图片将会将所有以间隔1秒交换显示
        Image[] bornImages = {new Image("image/player1.png"), new Image("image/player2.png")};
        Image[] ruinImages = {new Image("image/playerdown1.png"),
                new Image("image/playerdown2.png"), new Image("image/playerdown3.png")};
        //生成玩家对象
        player = new Player(bornImages, ruinImages, 200, 500,
                500 + bornImages[0].getHeight(), 450 + bornImages[0].getWidth(), 8);
        //为玩家的飞机设置子弹
        player.setBullet(new Image("image/playerbullet.png"));

        //将玩家添加到主面板中
        mainPane.getChildren().add(player);

        //飞机的飞行控制和发射子弹控制无法在同一个线程中实现，因为一次只能相应一种按键出发的事件
        //使用不同的线程控制，非主程序线程需要使用 Platform.runLater()
        //实现飞机移动
        //测试发射子弹
        //发射子弹应该还有背景音乐，也是有点击触发的
        //player.launch();
        //添加点击时间，当点击空格的时候发射子弹
        planeController();


        //添加在幕布中，同时指定幕布的大小
        Scene scene = new Scene(mainPane, 450 + bornImages[0].getWidth(),
                500 + bornImages[0].getHeight());
        //设置标题
        primaryStage.setTitle("飞机大战");
        primaryStage.setScene(scene);
        //禁止改动窗体大小
        primaryStage.setResizable(false);
        //舞台（窗体）显示
        primaryStage.show();
        //主面板获得焦点，这样按键才会有效
        mainPane.requestFocus();
    }
    //使用上下左右键控制飞机飞行方向
    //控制飞机子弹
    public void planeController() {
        mainPane.setOnKeyPressed(e -> {
            switch (e.getCode()) {
                case W:
                    player.moveUp(); player.changePos();
                    break;
                case S:
                    player.moveDown(); player.changePos();
                    break;
                case A:
                    player.moveLeft(); player.changePos();
                    break;
                case D:
                    player.moveRight(); player.changePos();
                    break;
                case SPACE:
                    player.launch();
                    bulletMusic();
                    break;
            }
        });
        //当键盘释放的时候也触发移动事件体验更好，更流畅
        mainPane.setOnKeyReleased(e -> {
            switch (e.getCode()) {
                case W:
                    player.moveUp(); player.changePos();
                    break;
                case S:
                    player.moveDown(); player.changePos();
                    break;
                case A:
                    player.moveLeft(); player.changePos();
                    break;
                case D:
                    player.moveRight(); player.changePos();
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
}

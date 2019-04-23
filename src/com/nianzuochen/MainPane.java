package com.nianzuochen;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.image.Image;
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
        //mediaPlayer.play();

        //玩家的图片，传递多个图片将会将所有以间隔1秒交换显示
        Image[] bornImages = {new Image("image/player1.png"), new Image("image/player2.png")};
        Image[] ruinImages = {new Image("image/playerdown1.png"),
                new Image("image/playerdown2.png"), new Image("image/playerdown3.png")};
        //生成玩家独享
        player = new Player(bornImages, ruinImages, 200, 500,
                500 + bornImages[0].getHeight(), 450 + bornImages[0].getWidth(), 8);

        //将玩家添加到主面板中
        mainPane.getChildren().add(player);

        //实现飞机移动
        planeMove();
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

    public void planeMove() {
        mainPane.setOnKeyPressed(e -> {
            switch (e.getCode()) {
                case UP:
                    player.moveUp(); player.changePos();
                    break;
                case DOWN:
                    player.moveDown(); player.changePos();
                    break;
                case LEFT:
                    player.moveLeft(); player.changePos();
                    break;
                case RIGHT:
                    player.moveRight(); player.changePos();
                    break;
            }
        });
    }
}

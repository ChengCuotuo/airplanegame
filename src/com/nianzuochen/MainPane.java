package com.nianzuochen;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.Pane;
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
        mainPane.setBackground(
                new Background(
                        new BackgroundImage(new Image("image/background.png"), null, null, null, null)));
        Image image = new Image("image/player1.png");
        player = new Player(new ImageView(image), 200, 500, 500 + image.getHeight(), 450 + image.getWidth(), 8);
        mainPane.getChildren().add(player);

        //实现飞机移动
        planeMove();

        Scene scene = new Scene(mainPane, 450 + image.getWidth(), 500 + image.getHeight());
        primaryStage.setTitle("飞机大战");
        primaryStage.setScene(scene);
        primaryStage.setResizable(false);
        primaryStage.show();
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

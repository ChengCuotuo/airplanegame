package com.nianzuochen;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

/**
 * Created by lei02 on 2019/4/22.
 * 用来测试继承子 ImageView 的动态图片是否可用
 */
public class TestExchangeImageView extends Application{

    @Override
    public void start(Stage primaryStage) {
        Pane pane = new Pane();
        Image[] images = {new Image("image/player1.png"), new Image("image/player2.png")};
        pane.getChildren().add(new ShowImages(images));

        Scene scene = new Scene(pane);
        primaryStage.setScene(scene);
        primaryStage.setTitle("变换的图片");
        primaryStage.show();
    }
}

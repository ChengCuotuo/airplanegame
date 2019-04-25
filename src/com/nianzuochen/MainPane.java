package com.nianzuochen;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.Pane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.util.ArrayList;

/**
 * Created by lei02 on 2019/4/22.
 */
public class MainPane extends Application{
    private Pane mainPane = new Pane();
    //玩家
    private Player player;
    private EnemyBuilder enemyBuilder;

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
        Image[] playerBornImages = {new Image("image/player1.png"), new Image("image/player2.png")};
        Image[] playerRuinImages = {new Image("image/playerdown1.png"),
                new Image("image/playerdown2.png"), new Image("image/playerdown3.png")};
        //生成玩家对象
        player = new Player(playerBornImages, playerRuinImages, 200, 500,
                500 + playerBornImages[0].getHeight(), 450 + playerBornImages[0].getWidth(), 8);
        //为玩家的飞机设置子弹
        player.setBullet(new Image("image/playerbullet.png"));
        //将玩家添加到主面板中
        mainPane.getChildren().add(player);

        //创建敌机生成类对象
        enemyBuilder = new EnemyBuilder( 500 + playerBornImages[0].getHeight(),
                450 + playerBornImages[0].getWidth(), 10);
        //添加到面板中
        mainPane.getChildren().add(enemyBuilder);
        //生成敌机
        enemyBuilder.getEnemys();

        //需要通过计算根据玩家飞机的位置、敌机的位置和子弹的位置实现玩家飞机或敌机被摧毁
        ArrayList<Enemy> enemys = enemyBuilder.getEnemyArray();
        ArrayList<Bullet> bullets = player.getBullets();
        computeRuin(enemys, bullets, player);


        //飞机的飞行控制和发射子弹控制无法在同一个线程中实现，因为一次只能相应一种按键出发的事件
        //使用不同的线程控制，非主程序线程需要使用 Platform.runLater()
        //实现飞机移动
        //测试发射子弹
        //发射子弹应该还有背景音乐，也是有点击触发的
        //player.launch();
        //添加点击时间，当点击空格的时候发射子弹
        player.planeController();
        player.bulletMusic();


        //添加在幕布中，同时指定幕布的大小
        Scene scene = new Scene(mainPane, 450 + playerBornImages[0].getWidth(),
                500 + playerBornImages[0].getHeight());
        //设置标题
        primaryStage.setTitle("飞机大战");
        primaryStage.setScene(scene);
        //禁止改动窗体大小
        //primaryStage.setResizable(false);
        //舞台（窗体）显示
        primaryStage.show();
        //主面板获得焦点，这样按键才会有效
        player.requestFocus();
    }

    //计算敌机和玩家被摧毁事件
    public void computeRuin(ArrayList<Enemy> ememys, ArrayList<Bullet> bullets, Player player) {
        //每0.5秒钟刷新一次各自的值，如果子弹在敌机的被攻击范围内，敌机被摧毁
        //如果敌机在玩家飞机的可被摧毁的范围内，玩家飞机被摧毁
        //敌机的可被攻击范围，应该是敌机子弹的 y 坐标小于等于敌机底端的 y 坐标同时子弹的左侧大于等于
        //敌机的左侧同时子弹的右侧小于等于敌机的右侧
        //玩家飞机被摧毁也是同样的
        EventHandler<ActionEvent> eventHandler = e -> {
            remove(ememys, bullets, player);
        };

        Timeline ruinAnimation = new Timeline(new KeyFrame(Duration.millis(500), eventHandler));
        ruinAnimation.setCycleCount(Timeline.INDEFINITE);
        ruinAnimation.play();
    }

    public void remove(ArrayList<Enemy> ememys, ArrayList<Bullet> bullets, Player player) {
        for (Bullet bullet : bullets) {
            double bulletX = bullet.getBulletX();
            double bulletY = bullet.getBulletY();
            double bulletWidth = bullet.getBulletWidth();
            for (Enemy enemy : ememys) {
                double enemyX = enemy.getPosX();
                double enemyY = enemy.getPosY();
                double enemyWidth = enemy.getEnemyWidth();
                double enemyHeight = enemy.getEnemyHeigh();
                if ((bulletY <= enemyY + enemyHeight) && (bulletX >= enemyX) &&
                        ((bulletX + bulletWidth) <= (enemyX + enemyWidth))) {
                    player.getChildren().remove(bullet);
                    enemyBuilder.getChildren().remove(enemy);
                    ememys.remove(enemy);
                    bullets.remove(bullet);
                    return ;
                }
            }
        }
    }
}

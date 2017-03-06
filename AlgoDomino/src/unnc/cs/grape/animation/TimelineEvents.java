package unnc.cs.grape.animation;

import javafx.application.Application;
import javafx.stage.Stage;
import javafx.animation.AnimationTimer;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.effect.Lighting;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.text.Text;
import javafx.util.Duration;

public class TimelineEvents extends Application {

    //主时间轴
    private Timeline timeline;
    private AnimationTimer timer;

    //用于指定实际帧的变量
    private Integer i=0;

    @Override public void start(Stage stage) {
        Group p = new Group();
        Scene scene = new Scene(p);
        stage.setScene(scene);
        stage.setWidth(500);
        stage.setHeight(500);
        p.setTranslateX(80);
        p.setTranslateY(80);

        //创建一个带有特效的圆
        final Circle circle = new Circle(20,  Color.rgb(156,216,255));
        circle.setEffect(new Lighting());
        //在圆内部创建一个文本
        final Text text = new Text (i.toString());
        text.setStroke(Color.BLACK);
        //为带有文本的圆创建一个布局
        final StackPane stack = new StackPane();
        stack.getChildren().addAll(circle, text);
        stack.setLayoutX(30);
        stack.setLayoutY(30);

        p.getChildren().add(stack);
        stage.show();

        //为了移动圆创建一个时间轴
        timeline = new Timeline();
        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.setAutoReverse(true);

        //在每个帧开始时你可以添加一个特定的动作
        timer = new AnimationTimer() {
            @Override
            public void handle(long l) {
                text.setText(i.toString());
                i++;
            }

        };

        //创建一个带有缩放因子的keyValue:将圆缩放2倍
        KeyValue keyValueX = new KeyValue(stack.scaleXProperty(), 2);
        KeyValue keyValueY = new KeyValue(stack.scaleYProperty(), 2);

        //创建一个KeyFrame, keyValue会在2秒钟时抵达
        Duration duration = Duration.millis(2000);
        //当抵达关键帧时可以指定一个特定的动作
        EventHandler onFinished = new EventHandler<ActionEvent>() {
            public void handle(ActionEvent t) {
                stack.setTranslateX(java.lang.Math.random()*200-100);
                //复位计数器 
                i = 0;
            }
        };

        KeyFrame keyFrame = new KeyFrame(duration, onFinished , keyValueX, keyValueY);

        //将关键帧添加到时间轴中
        timeline.getKeyFrames().add(keyFrame);

        timeline.play();
        timer.start();
    }


    public static void main(String[] args) {
        Application.launch(args);
    }
}
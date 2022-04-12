package pyroman.jigsaw;

import java.util.Random;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.geometry.Point2D;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.image.ImageView;
import javafx.scene.image.PixelWriter;
import javafx.scene.image.WritableImage;
import javafx.scene.paint.Color;
import javafx.scene.paint.CycleMethod;
import javafx.scene.paint.LinearGradient;
import javafx.scene.paint.Stop;
import javafx.scene.shape.Ellipse;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

public class ShowSmoke extends Application
{
    public final static int SCENE_WIDTH = 400;
    public final static int SCENE_HEIGHT = 300;

    public static void main(String[] args)
    {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception
    {
        Group root = new Group();
        Scene scene = new Scene(root, SCENE_WIDTH, SCENE_HEIGHT);
        scene.setFill(Color.BLACK);

        Rectangle landscape = new Rectangle(0, 200, SCENE_WIDTH, SCENE_HEIGHT - 200);

        Stop[] stops = new Stop[] {
                new Stop( 0, new Color(0.95, 0.95, 0.5, 1)),
                new Stop( 1, new Color(0.1, 0.1, 0, 1))
        };
        landscape.setFill(new LinearGradient(
                0, 1, 0, 0, true, CycleMethod.NO_CYCLE, stops));

        root.getChildren().add(landscape);

        Ellipse potBot = new Ellipse();
        potBot.setCenterX(200);
        potBot.setCenterY(262);
        potBot.setRadiusX(9);
        potBot.setRadiusY(5);
        potBot.setFill(Color.BROWN);

        Ellipse potTop = new Ellipse();
        potTop.setCenterX(200);
        potTop.setCenterY(260);
        potTop.setRadiusX(6);
        potTop.setRadiusY(2);
        potTop.setFill(Color.BLACK);

        root.getChildren().addAll(potBot, potTop);

        Smoke smoke = new Smoke(180, 221, Color.WHITESMOKE);
        smoke.setOpacity(1);

        root.getChildren().add(smoke);

        primaryStage.setScene(scene);
        primaryStage.show();

        AnimationTimer gameloop = new AnimationTimer()
        {
            @Override
            public void handle(long arg0)
            {
                smoke.update();
            }
        };
        gameloop.start();
    }
}

class Smoke extends Group
{
    private static WritableImage baseImg;
    private final ImageView[] lifeCycle;

    private final int FRAMES = 50;
    private final int FALLING_FRAMES = 32;
    private final int width = 40;
    private final int height = 40;
    private final double radius;
    private final Point2D midPoint;

    private double xThrow = -2 ;
    private int xWobble = 5;
    private double yThrow = -2.2;
    private int yWobble = 2;

    private Random random;
    private int throttle;
    private int throttleMax;

    public Smoke(int xLoc, int yLoc, Color color)
    {
        throttleMax = 4; // 4 ==> 15 fps animation rate
        random = new Random();

        midPoint = new Point2D(width/2, height/2);
        radius = width/2 - 1;
        lifeCycle = new ImageView[50];

        double topAlpha = 0.5;
        double fadeAmount = topAlpha/FALLING_FRAMES;
        double fadeInAmount =
                topAlpha/((FRAMES - FALLING_FRAMES) * 3);

        double pixR = color.getRed();
        double pixG = color.getGreen();
        double pixB = color.getBlue();
        double pixA = topAlpha;

        // Create the base image.
        // The color is constant, the alpha is a
        // computed radial gradient.
        baseImg = new WritableImage(width, height);
        PixelWriter baseRaster = baseImg.getPixelWriter();
        double distance;
        double gradientFactor; // between 0 and 1

        for (int jj = 0; jj < height; jj++)
        {
            for (int kk = 0; kk < width; kk++)
            {
                distance = midPoint.distance(jj, kk);
                if (distance > radius) gradientFactor = 0.0;
                else gradientFactor = (radius - distance) / radius;

                pixA =  topAlpha * gradientFactor;
                baseRaster.setColor(jj, kk, new Color(pixR, pixG, pixB, pixA));
            }
        }

        for (int i = 0; i < FRAMES; ++i)
        {
            lifeCycle[i]= new ImageView();
            lifeCycle[i].setImage(baseImg);
            lifeCycle[i].setX(xLoc);
            lifeCycle[i].setY(yLoc);
            this.getChildren().add(lifeCycle[i]);
        }

        // alpha will rise and fall over course of lifeCycle
        for (int i = 0; i < FALLING_FRAMES; ++i)
        {
            lifeCycle[i].setOpacity((i + 1) * fadeAmount);
        }

        for (int i = FALLING_FRAMES; i < FRAMES; i++)
        {
            lifeCycle[i].setOpacity(
                    topAlpha - (i - FALLING_FRAMES + 1) * fadeInAmount);
        }
    }

    public void update()
    {
        // Slow the animation by skipping cycles. 60 fps is overkill.
        if (throttle-- > 0) return;
        else throttle = throttleMax;

        // Propagate location of particles, while adding
        //     'throw' and 'wobble'
        // lifeCycle[n] is stationary (origin of stream, visually)
        int n = FRAMES - 1;
        for (int i = 0; i < n; i++)
        {
            lifeCycle[i].setX( lifeCycle[i+1].getX()
                    + xThrow + random.nextInt(xWobble));
            lifeCycle[i].setY( lifeCycle[i+1].getY()
                    + yThrow + random.nextInt(yWobble));
        }
    }
}

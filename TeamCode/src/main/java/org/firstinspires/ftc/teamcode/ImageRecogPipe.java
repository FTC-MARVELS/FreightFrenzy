package org.firstinspires.ftc.teamcode;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.Point;
import org.opencv.core.Rect;
import org.opencv.core.Scalar;
import org.opencv.imgproc.Imgproc;
import org.openftc.easyopencv.OpenCvPipeline;

import java.util.TreeMap;

public class ImageRecogPipe extends OpenCvPipeline {
    Telemetry telemetry;
    int correctlocation = 3;
    Mat mat = new Mat();
    public enum Location{
        RIGHT,
        MIDDLE,
        LEFT
    }
    private Location location;
    //Defining rectangles
    static final Rect Middle = new Rect(
            new Point(47, 45),
            new Point(120, 75));
    //Defining rectangles
    static final Rect Right = new Rect(
            new Point(140, 35),
            new Point(200, 75));
    static final double PERCENT_COLOR_THRESHOLD = 0.1;
    public ImageRecogPipe(Telemetry t) {telemetry = t;}

    @Override
    public Mat processFrame(Mat input) {
        Imgproc.cvtColor(input,mat,Imgproc.COLOR_RGB2HSV);
        Scalar lowHSV = new Scalar(10,100,20);
        Scalar highHSV = new Scalar(25,255,255);

        Core.inRange(mat,lowHSV,highHSV,mat);

        //Creating the rectangles defined above
        Mat middle = mat.submat(Middle);
        Mat right = mat.submat(Right);


        double middleValue = Core.sumElems(middle).val[0] / Middle.area() / 255;
        double rightValue = Core.sumElems(right).val[0] / Right.area() / 255;

        middle.release();
        right.release();

        telemetry.addData("Right raw value", (int) Core.sumElems(right).val[0]);
        telemetry.addData("Middle raw value", (int) Core.sumElems(middle).val[0]);
        telemetry.addData("Right percentage", Math.round(rightValue * 100) + "%");
        telemetry.addData("Middle percentage", Math.round(middleValue * 100) + "%");


        boolean onRight = rightValue >PERCENT_COLOR_THRESHOLD;
        boolean onMiddle = middleValue>PERCENT_COLOR_THRESHOLD;

        if (onRight){
            correctlocation = 1;
            telemetry.addData("LOCATION!:","RIGHT");

        }
        else if (onMiddle){
            correctlocation = 2;
            telemetry.addData("LOCATION!:","MIDDLE");
        }
        else{
            correctlocation = 3;
            telemetry.addData("LOCATION!:","LEFT");
        }
        telemetry.update();
        Scalar False = new Scalar(255, 0, 0);
        Scalar True = new Scalar(0, 0, 255);


        Imgproc.cvtColor(mat,mat,Imgproc.COLOR_GRAY2RGB);
        Imgproc.rectangle(mat,Right , location == Location.RIGHT? True:False);
        Imgproc.rectangle(mat,Middle, location == Location.MIDDLE? True :False);
        return mat;
    }
    public Location getLocation(){
        return location;
    }
}

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

public class redRightPipe extends OpenCvPipeline {
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
    static final Rect Left = new Rect(
            new Point(85, 45),
            new Point(128, 95));
    //Defining rectangles
    static final Rect Middle = new Rect(
            new Point(202, 40),
            new Point(250, 90));
    static final double PERCENT_COLOR_THRESHOLD = 0.1;
    public redRightPipe(Telemetry t) {telemetry = t;}

    @Override
    public Mat processFrame(Mat input) {
        Imgproc.cvtColor(input,mat,Imgproc.COLOR_RGB2HSV);
        Scalar lowHSV = new Scalar(10,100,20);
        Scalar highHSV = new Scalar(25,255,255);

        Core.inRange(mat,lowHSV,highHSV,mat);

        //Creating the rectangles defined above
        Mat middle = mat.submat(Middle);
        Mat left = mat.submat(Left);


        double middleValue = Core.sumElems(middle).val[0] / Middle.area() / 255;
        double LeftValue = Core.sumElems(left).val[0] / Left.area() / 255;

        middle.release();
        left.release();

        telemetry.addData("Right raw value", (int) Core.sumElems(left).val[0]);
        telemetry.addData("Middle raw value", (int) Core.sumElems(middle).val[0]);
        telemetry.addData("Left percentage", Math.round(LeftValue * 100) + "%");
        telemetry.addData("Middle percentage", Math.round(middleValue * 100) + "%");


        boolean onLeft = LeftValue >PERCENT_COLOR_THRESHOLD;
        boolean onMiddle = middleValue>PERCENT_COLOR_THRESHOLD;

        if (onLeft){
            correctlocation = 1;
            telemetry.addData("LOCATION!:","LEFT");

        }
        else if (onMiddle){
            correctlocation = 2;
            telemetry.addData("LOCATION!:","MIDDLE");
        }
        else{
            correctlocation = 3;
            telemetry.addData("LOCATION!:","RIGHT");
        }
        telemetry.update();
        Scalar False = new Scalar(255, 0, 0);
        Scalar True = new Scalar(0, 0, 255);


        Imgproc.cvtColor(mat,mat,Imgproc.COLOR_GRAY2RGB);
        Imgproc.rectangle(mat,Left , location == Location.LEFT? True:False);
        Imgproc.rectangle(mat,Middle, location == Location.MIDDLE? True :False);
        return mat;
    }
    public Location getLocation(){
        return location;
    }
}

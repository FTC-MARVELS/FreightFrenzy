package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName;
import org.firstinspires.ftc.teamcode.RobotObjects.EPIC.Claw;
import org.firstinspires.ftc.teamcode.RobotObjects.EPIC.Mecanum_Wheels;
import org.firstinspires.ftc.teamcode.RobotObjects.Spinner;
import org.firstinspires.ftc.teamcode.tfrec.Detector;
import org.firstinspires.ftc.teamcode.tfrec.classification.Classifier;
import org.openftc.easyopencv.OpenCvCamera;
import org.openftc.easyopencv.OpenCvCameraFactory;
import org.openftc.easyopencv.OpenCvCameraRotation;

import java.util.List;

@Autonomous(name="newAutonRedRight", group="Robot19587")
public class newAutonRedRight extends LinearOpMode {
    //Configuration used: 6wheelConfig
    OpenCvCamera webcam;
    @Override
    public void runOpMode() throws InterruptedException {
        int cameraMonitorViewId = hardwareMap.appContext.getResources().getIdentifier("cameraMonitorViewId", "id", hardwareMap.appContext.getPackageName());
        webcam = OpenCvCameraFactory.getInstance().createWebcam(hardwareMap.get(WebcamName.class, "Webcam 1"), cameraMonitorViewId);
        redRightPipe detector = new redRightPipe(telemetry);
        webcam.setPipeline(detector);
        webcam.openCameraDeviceAsync(new OpenCvCamera.AsyncCameraOpenListener()
        {
            @Override
            public void onOpened()
            {
                webcam.startStreaming(320, 240, OpenCvCameraRotation.UPRIGHT);
            }

            @Override
            public void onError(int errorCode)
            {
                telemetry.addData("Error:","Camera cant stream");
            }
        });

        double distance = 0;
        double correctionFactor = 1;
        boolean tfdActivated = false;
        double speed = 0.6;
        Mecanum_Wheels mecanum = new Mecanum_Wheels(hardwareMap);
        mecanum.leftErrorAdjustment = 0.991;
        Spinner spinner = new Spinner(hardwareMap);
        double spinnerPower = 0.58;
        double levelDistance = 11;
        double backDistance = 5;
        mecanum.parent = this;
        Claw claw = new Claw(hardwareMap);
        claw.parent = this;
        claw.telemetry = this.telemetry;
        //sleep(5000);
        mecanum.IsAutonomous = true;
        mecanum.velocity = 400;
        mecanum.telemetry = this.telemetry;
        mecanum.initialize();
        telemetry.addData("LOCATION", detector.correctlocation);
        telemetry.update();
        waitForStart();
        // run until the end of the match (driver presses STOP)
        int i =0;
        int level = 3;
        distance = 27;
        sleep(500);
        claw.lift(detector.correctlocation);
        sleep(1000);
        correctionFactor = 1.444;//.4;
        distance = 13.5;
        distance = distance * correctionFactor;
        //Left
        mecanum.encoderDrive(speed,-distance,distance,distance,-distance,2);
        distance = levelDistance * correctionFactor;
        //forward
        distance = 20;
        mecanum.encoderDrive(0.4,distance,distance,distance,distance,2);
        sleep(1000);
        claw.release();
        sleep(1000);
//                //sleep(2000);
       // distance = levelDistance * correctionFactor;
//      //back
        distance = 21;
        mecanum.encoderDrive(speed,-distance,-distance,-distance,-distance,2);
        claw.lift(0);
        distance = 11.23 * correctionFactor;
        //Right turn
        mecanum.encoderDrive(speed,distance,distance,-distance,-distance,2);
        distance = 37 * correctionFactor;
        //Forward
        mecanum.encoderDrive(0.6,distance,distance,distance,distance,2);
        sleep(100);

    }

}


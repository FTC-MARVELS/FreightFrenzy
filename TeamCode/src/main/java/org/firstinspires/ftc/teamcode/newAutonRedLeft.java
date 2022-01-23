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

@Autonomous(name="newAuton_RedLeft", group="Robot19587")
public class newAutonRedLeft extends LinearOpMode {
    //Configuration used: 6wheelConfig
    OpenCvCamera webcam;
    @Override
    public void runOpMode() throws InterruptedException {
        int cameraMonitorViewId = hardwareMap.appContext.getResources().getIdentifier("cameraMonitorViewId", "id", hardwareMap.appContext.getPackageName());
        webcam = OpenCvCameraFactory.getInstance().createWebcam(hardwareMap.get(WebcamName.class, "Webcam 1"), cameraMonitorViewId);
        ImageRecogPipe detector = new ImageRecogPipe(telemetry);
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
            sleep(2000);
            if (detector.correctlocation==3){
                claw.lift(1);
            }
            else if (detector.correctlocation==2){
                claw.lift(2);
            }
            else if (detector.correctlocation==1){
                claw.lift(3);
            }

//        claw.lift(3);
            sleep(500);
            correctionFactor = 1.444;//.4;
            distance = distance * correctionFactor;
            distance = 7.6;
            //Forward
            mecanum.encoderDrive(0.4,distance,distance,distance,distance,2);
            distance = levelDistance * correctionFactor;
            distance = 33;
            //Left
            mecanum.encoderDrive(0.4,-distance,distance,distance,-distance,2);
            spinner.setPower(-spinnerPower);
            sleep(3000);
            spinner.setPower(0);
            //Right
            distance = 1;
            mecanum.encoderDrive(0.4,distance,distance,-distance,-distance,1);
            //Forward
            distance = 35;
            mecanum.encoderDrive(0.4,distance,distance,distance,distance,2);
            //Right turn
            distance = 14.9;
            mecanum.encoderDrive(0.4,distance,distance,-distance,-distance,2);
            //Forward
            distance = 22.8;
            mecanum.encoderDrive(0.4,distance,distance,distance,distance,2);
            claw.release();
            sleep(100);
            //Backwards
            distance = 31;
            mecanum.encoderDrive(0.3,-distance,-distance,-distance,-distance,2);
            claw.lift(0);
            //Left
            distance = 19.6;
            mecanum.encoderDrive(0.4,distance,-distance,-distance,distance,2);

        }

    }


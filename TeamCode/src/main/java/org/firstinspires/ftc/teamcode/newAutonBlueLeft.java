package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName;
import org.firstinspires.ftc.teamcode.RobotObjects.EPIC.Claw;
import org.firstinspires.ftc.teamcode.RobotObjects.EPIC.Mecanum_Wheels;
import org.firstinspires.ftc.teamcode.RobotObjects.Spinner;
import org.openftc.easyopencv.OpenCvCamera;
import org.openftc.easyopencv.OpenCvCameraFactory;
import org.openftc.easyopencv.OpenCvCameraRotation;
@Autonomous(name = "newAutonBlueLeft")
public class newAutonBlueLeft extends LinearOpMode {
    OpenCvCamera webcam;
    @Override
    public void runOpMode() throws InterruptedException {
        double distance = 0;
        double correctionFactor = 1;
        int cameraMonitorViewId = hardwareMap.appContext.getResources().getIdentifier("cameraMonitorViewId", "id", hardwareMap.appContext.getPackageName());
        webcam = OpenCvCameraFactory.getInstance().createWebcam(hardwareMap.get(WebcamName.class, "Webcam 1"), cameraMonitorViewId);
        blueLeftPipe detector = new blueLeftPipe(telemetry);
        webcam.setPipeline(detector);
        Mecanum_Wheels wheels = new Mecanum_Wheels(hardwareMap);
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
        String id = "";
        telemetry.addData("Opmode Active", "No");
        telemetry.update();
        waitForStart();
        claw.lift(detector.correctlocation);
        sleep(500);
        correctionFactor = 1.444;//.4;
        distance = 15;
        distance = distance * correctionFactor;
        //claw.lift(3);
        //Right
        mecanum.encoderDrive(0.5,distance,-distance,-distance,distance,2);
        distance = levelDistance * correctionFactor;
        distance = 17;
        //Forward
        mecanum.encoderDrive(0.5,distance,distance,distance,distance,2);
        claw.release();
        sleep(700);
        //Backward
        distance = 18;
        mecanum.encoderDrive(0.5,-distance,-distance,-distance,-distance,1);
        claw.lift(0);
        //Left turn
        distance = 17.3;
        mecanum.encoderDrive(0.5,-distance,-distance,distance,distance,2);
        //Left
        distance = 3;
        mecanum.encoderDrive(0.5,-distance,distance,distance,-distance,1);
        //Forward
        distance = 57;
        mecanum.encoderDrive(0.5,distance,distance,distance,distance,4);
    }}
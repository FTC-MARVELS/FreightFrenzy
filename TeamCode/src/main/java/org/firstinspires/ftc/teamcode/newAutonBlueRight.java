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
@Autonomous(name = "newAutonBlueRight")
public class newAutonBlueRight extends LinearOpMode {
    OpenCvCamera webcam;
    @Override
    public void runOpMode() throws InterruptedException {
        double distance = 0;
        double correctionFactor = 1;
        int cameraMonitorViewId = hardwareMap.appContext.getResources().getIdentifier("cameraMonitorViewId", "id", hardwareMap.appContext.getPackageName());
        webcam = OpenCvCameraFactory.getInstance().createWebcam(hardwareMap.get(WebcamName.class, "Webcam 1"), cameraMonitorViewId);
        BlueRightPipe detector = new BlueRightPipe(telemetry);
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
        double level;
        mecanum.parent = this;
        Claw claw = new Claw(hardwareMap);
        claw.parent = this;
        claw.telemetry = this.telemetry;
        //sleep(5000);
        mecanum.IsAutonomous = true;
        mecanum.velocity = 400;
        mecanum.telemetry = this.telemetry;
        mecanum.initialize();

        waitForStart();
        sleep(2000);
        claw.lift(detector.correctlocation);
        sleep(700);
        correctionFactor = 1.444;//.4;
        distance = 3.5;
        distance = distance * correctionFactor;
        //Forward
        mecanum.encoderDrive(0.4,distance,distance,distance,distance,2);
        distance = levelDistance * correctionFactor;
        distance = 35.5;
        sleep(200);
        //Right
        mecanum.encoderDrive(0.4,distance,-distance,-distance,distance,2);
        distance = 7.9;
        //Left turn
        mecanum.encoderDrive(0.3,-distance,-distance,distance,distance,2);
        //Backward
        distance = 1.86;
        mecanum.encoderDrive(0.3,-distance,-distance,-distance,-distance,2);
        spinner.setPower(spinnerPower);
        sleep(3700);
        spinner.setPower(0);
        //Forward
        distance = 5;
        mecanum.encoderDrive(0.4,distance,distance,distance,distance,1);
        //Right turn
        distance = 7.6;
        mecanum.encoderDrive(0.4,distance,distance,-distance,-distance,2);
        //Forward
        distance = 29.4;
        mecanum.encoderDrive(0.4,distance,distance,distance,distance,2);
        //Left turn
        distance = 16;
        mecanum.encoderDrive(0.4,-distance,-distance,distance,distance,2);
        //Forward
        distance = 22.75;
        mecanum.encoderDrive(0.4,distance,distance,distance,distance,2);
        claw.release();
        sleep(1000);
        //Backwards
        distance = 30;
        mecanum.encoderDrive(0.3,-distance,-distance,-distance,-distance,2);
        claw.lift(0);
        claw.grab();
        //Left
        distance = 12;
        mecanum.encoderDrive(0.4,-distance,distance,distance,-distance,2);
        correctionFactor = 1.444;//.4;
        distance = 5;
        distance = distance * correctionFactor;



    }
}

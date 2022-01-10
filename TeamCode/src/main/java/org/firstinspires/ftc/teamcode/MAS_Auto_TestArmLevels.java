package org.firstinspires.ftc.teamcode;

import com.qualcomm.hardware.bosch.BNO055IMU;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.robotcore.external.navigation.Orientation;
import org.firstinspires.ftc.teamcode.RobotObjects.MAS.Claw;
import org.firstinspires.ftc.teamcode.RobotObjects.MAS.Mecanum_Wheels;
import org.firstinspires.ftc.teamcode.RobotObjects.MAS.Scanner;
import org.firstinspires.ftc.teamcode.tfrec.Detector;
import org.firstinspires.ftc.teamcode.tfrec.classification.Classifier;

@Autonomous(name = "MAS_Auto_TestArmLevels")
@Disabled
public class MAS_Auto_TestArmLevels extends LinearOpMode {

    private Detector tfDetector = null;
    private ElapsedTime runtime = new ElapsedTime();

    //Configuration used: 6wheelConfig
    @Override
    public void runOpMode() throws InterruptedException {
        Mecanum_Wheels mecanum = new Mecanum_Wheels(hardwareMap);
        Scanner scanner = new Scanner();
        Claw claw = new Claw(hardwareMap);
        mecanum.IsMASAutonomous = true;
        mecanum.velocity = 400;
        mecanum.telemetry = this.telemetry;
        mecanum.parent = this;
        mecanum.initialize();
        mecanum.rightErrorAdjustment = 0.5;//1;


        waitForStart();


        mecanum.move_backward_auto(0.6, 10, 10.0);
        int level = 0;
        String alliance = "Blue";
        if(level == 0) {
            mecanum.moveArmSideways(level, 0, alliance);
            sleep(2000);
            if(alliance.equalsIgnoreCase("Red")) {
                claw.moveSwing(0.1);
                sleep(500);
                mecanum.move_left_auto(0.8, 22, 10.0); //Red Level 0
                claw.moveFloor(1.0);
                sleep(1000);

            } else {
                claw.moveSwing(-0.1);
                sleep(500);
                mecanum.move_right_auto(0.8, 22, 10.0); //Blue Level 0
                claw.moveFloor(0.0);
                sleep(1000);
            }
        } else if (level == 1) {
            mecanum.moveArmSideways(level, 0, alliance);
            sleep(2000);
            if (alliance.equalsIgnoreCase("Red")) {
                mecanum.move_left_auto(0.8, 21, 10.0);//Red Level 1
                claw.moveFloor(1.0);
                sleep(1000);
            } else {
                mecanum.move_right_auto(0.8, 21, 10.0);//Blue Level 1
                claw.moveFloor(1.0);
                sleep(1000);
            }
        } else if (level == 2) {
            mecanum.moveArmSideways(level, 0, alliance);
            sleep(2000);
            if (alliance.equalsIgnoreCase("Red")) {
                mecanum.move_left_auto(0.8, 24, 10.0); //Red Level 2
                claw.moveFloor(1.0);
                sleep(1000);
            } else {
                mecanum.move_right_auto(0.8, 24, 10.0); //Blue Level 2
                claw.moveFloor(1.0);
                sleep(1000);
            }
        }

        claw.dropObject();
        sleep(500);
        claw.stopIntake();
        claw.moveFloor(0.5);
        //mecanum.arm.setTargetPosition(200);
        //mecanum.arm.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        //sleep(2000);
        if(alliance.equalsIgnoreCase("Red")) {
            mecanum.move_right_auto(0.3, 10, 10.0);
        } else {
            mecanum.move_left_auto(0.3, 10, 10.0);
        }
       // mecanum.arm.setPower(0.0);
        //mecanum.arm.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        //mecanum.moveArmSideways(2, 0);

    //labels are reversed here
      /*  if(position == 0 || position == 3 || position == 9) {
            mecanum.moveArm(2,0);
            sleep(3000);
            claw.moveBucket(0.0);
            sleep(2000);
        } else if(position == 1) {
            mecanum.moveArm(1,0);
            sleep(3000);
            claw.moveBucket(0.0);
            sleep(2000);
        } else if(position == 2) {
            claw.moveBucket(0.2);
            sleep(200);
        }*/

        /*mecanum.moveArm(2,0);
        sleep(3000);
        claw.moveBucket(0.0);
        sleep(2000);

        mecanum.moveArm(0,2);
        sleep(3000);
        claw.moveBucket(0.0);
        sleep(2000);

        mecanum.moveArm(1,0);
        sleep(3000);
        claw.moveBucket(0.0);
        sleep(2000);


        mecanum.moveArm(0,1);
        sleep(3000);
        claw.moveBucket(0.0);
        sleep(2000);
*/

    }

}


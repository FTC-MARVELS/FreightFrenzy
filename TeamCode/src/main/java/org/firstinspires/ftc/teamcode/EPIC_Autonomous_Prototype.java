package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.teamcode.RobotObjects.EPIC.Claw;

@Autonomous(name="EPIC_Autonomous_Prototype")

@Disabled
public class EPIC_Autonomous_Prototype extends LinearOpMode {
    //Configuration used: 6wheelConfig
    private ElapsedTime runtime = new ElapsedTime();
    @Override
    public void runOpMode() throws InterruptedException {

        double speed = 0.2;
        //Mecanum_Wheels mecanum = new Mecanum_Wheels(hardwareMap);
        Claw claw = new Claw(hardwareMap);
        claw.parent = this;
        claw.telemetry = this.telemetry;
        //sleep(5000);
        //mecanum.IsAutonomous = true;
        //mecanum.velocity = 400;
        //mecanum.telemetry = this.telemetry;
        //mecanum.parent = this;
        //mecanum.initialize();
        waitForStart();
        //forward
        //mecanum.encoderDrive(speed,12,12,12,12,12,12, 1.0);
        //backward
        //mecanum.encoderDrive(speed,-12,-12,-12,-12,-12,-12, 1.0);
        //left
        //mecanum.encoderDrive(speed,-12,0,12,12,0,-12, 1.0);
        //right
        //mecanum.encoderDrive(speed,12,0,-12,-12,0,12, 1.0);
        //left turn
        //mecanum.encoderDrive(speed,-12,0,-12,12,0,12, 1.0);
        //right turn
        //mecanum.encoderDrive(speed,12,0,12,-12,0,-12, 1.0);
        //contract
        //mecanum.encoderDrive(speed,3.15,0,-3.15,3.1,0,-3.1, 1.0);
        //expand
        //mecanum.encoderDrive(speed,-3.15,0,3.15,-3.1,0,3.1, 1.0);
        claw.grab();
        sleep(2000);
        claw.release();
        sleep(5000);

//        mecanum.moveForward();
//        //mecanum.TestMechanumWheels(0.5);
//        //mecanum.TestOmniWheels(0.5);
//        //mecanum.TestOmniWheelsBackward(1);
//        //while (opModeIsActive())
//        //{
//        //    if(runtime.seconds() <3.0) {
//                mecanum.moveY(12);
//                //mecanum.moveY(-1000);
//                telemetry.addData("Done", runtime.seconds());
//                telemetry.addData("middleright", mecanum.middleright.getPower());
//                telemetry.addData("middleleft", mecanum.middleleft.getPower());
//                //telemetry.addData
//                telemetry.update();
//        //    }
//        //}
//        while (mecanum.frontleft.isBusy()) {
//            //mecanum.moveY(1000);
//        //    else if(runtime.seconds() >3.0 && runtime.seconds()<6.0) {
//        //        mecanum.moveY(-1000);
//                telemetry.addData("Done", runtime.seconds());
//                telemetry.addData("middleright", mecanum.middleright.getPower());
//                telemetry.addData("middleleft", mecanum.middleleft.getPower());
//                //telemetry.addData
//                telemetry.update();
//        //    }
//        }
//        mecanum.moveY(-12);
//        while (mecanum.frontleft.isBusy()) {
//            //mecanum.moveY(1000);
//            //    else if(runtime.seconds() >3.0 && runtime.seconds()<6.0) {
//            //        mecanum.moveY(-1000);
//            telemetry.addData("Done", runtime.seconds());
//            telemetry.addData("middleright", mecanum.middleright.getPower());
//            telemetry.addData("middleleft", mecanum.middleleft.getPower());
//            //telemetry.addData
//            telemetry.update();
//            //    }
//        }
//
//        //mecanum.TestOmniWheels(0.5);
//        //mecanum.TestOmniWheelsBackward(0);
//        //mecanum.TestMechanumWheels(0);

    }

}
package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.teamcode.RobotObjects.Mecanum_Wheels;

@Autonomous(name="EPIC_Autonomous_Testing")

@Disabled
public class EPIC_Autonomous_Testing extends LinearOpMode {
    //Configuration used: 6wheelConfig
    private ElapsedTime runtime = new ElapsedTime();
    @Override
    public void runOpMode() throws InterruptedException {
        waitForStart();

        Mecanum_Wheels mecanum = new Mecanum_Wheels(hardwareMap);
        //mecanum.moveForward();
        mecanum.TestMechanumWheels(1);
        //mecanum.TestOmniWheels(0.5);
        while (opModeIsActive() && (runtime.seconds() <3.0)) {
            telemetry.addData("Done", runtime.seconds());
            telemetry.addData("frontright", mecanum.frontright.getPower());
            telemetry.addData("frontleft", mecanum.frontleft.getPower());
            telemetry.addData("backright", mecanum.backright.getPower());
            telemetry.addData("backleft", mecanum.backleft.getPower());
            telemetry.update();
        }
        //mecanum.TestOmniWheels(0.5);
        mecanum.TestMechanumWheels(0);

    }

}
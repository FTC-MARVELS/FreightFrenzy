package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.teamcode.RobotObjects.Mecanum_Wheels;
import org.firstinspires.ftc.teamcode.RobotObjects.Spinner;

@Autonomous(name="EPIC_Autonomous")
public class EPIC_Autonomous extends LinearOpMode {
    //Configuration used: 6wheelConfig
    private ElapsedTime runtime = new ElapsedTime();
    @Override
    public void runOpMode() throws InterruptedException {
        waitForStart();

        Mecanum_Wheels mecanum = new Mecanum_Wheels(hardwareMap);
        //mecanum.moveForward();
        //mecanum.TestMechanumWheels(0.5);
        //mecanum.TestOmniWheels(0.5);
        mecanum.TestOmniWheelsBackward(1);
        while (opModeIsActive() && (runtime.seconds() <3.0)) {
            telemetry.addData("Done", runtime.seconds());
            telemetry.addData("middleright", mecanum.middleright.getPower());
            telemetry.addData("middleleft", mecanum.middleleft.getPower());
            //telemetry.addData
            telemetry.update();
        }

        //mecanum.TestOmniWheels(0.5);
        mecanum.TestOmniWheelsBackward(0);
        //mecanum.TestMechanumWheels(0);

    }

}
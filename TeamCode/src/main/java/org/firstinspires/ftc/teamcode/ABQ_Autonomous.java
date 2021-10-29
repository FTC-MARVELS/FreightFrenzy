package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.RobotObjects.Mecanum_Wheels;

@Autonomous(name="ABQ_Autonomous")
public class ABQ_Autonomous extends LinearOpMode {
    //Configuration used: 6wheelConfig
    @Override
    public void runOpMode() throws InterruptedException {
        waitForStart();

        Mecanum_Wheels mecanum = new Mecanum_Wheels(hardwareMap);
        mecanum.moveForward();

    }

}
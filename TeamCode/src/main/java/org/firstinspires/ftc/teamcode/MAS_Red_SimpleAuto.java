package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.teamcode.RobotObjects.MAS.Mecanum_Wheels;
import org.firstinspires.ftc.teamcode.RobotObjects.MAS.Scanner;
import org.firstinspires.ftc.teamcode.tfrec.Detector;
import org.firstinspires.ftc.teamcode.tfrec.classification.Classifier;

@Autonomous(name = "MAS_Red_SimpleAuto")
@Disabled

public class MAS_Red_SimpleAuto extends LinearOpMode {

    @Override
    public void runOpMode() throws InterruptedException {

        double speed = 0.75;
        double rotationSpeed = 0.2;
        Mecanum_Wheels mecanum = new Mecanum_Wheels(hardwareMap);
        Scanner scanner = new Scanner();

        mecanum.IsMASAutonomous = true;
        mecanum.velocity = 400;
        mecanum.telemetry = this.telemetry;
        mecanum.parent = this;
        mecanum.initialize();
        mecanum.rightErrorAdjustment = 0.5;//1;

        waitForStart();

        double forwardDistance = 38;
        double parkingDistance = 33;
        mecanum.move_forward_auto(speed,forwardDistance, 10.0 );
        mecanum.move_left_auto(speed*0.75, parkingDistance, 20.0);
    }
}

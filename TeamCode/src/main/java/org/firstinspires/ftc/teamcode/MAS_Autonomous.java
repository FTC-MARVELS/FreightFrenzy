package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotorEx;

import org.firstinspires.ftc.teamcode.RobotObjects.MAS.Claw;
import org.firstinspires.ftc.teamcode.RobotObjects.Mecanum_Wheels;

@Autonomous(name="MAS_Autonomous")
@Disabled
public class MAS_Autonomous extends LinearOpMode {
    //Configuration used: 6wheelConfig
    @Override
    public void runOpMode() throws InterruptedException {
        double speed = 0.2;
        Mecanum_Wheels mecanum = new Mecanum_Wheels(hardwareMap);
        Claw claw = new Claw(hardwareMap);
        mecanum.IsAutonomous = true;
        mecanum.velocity = 400;
        mecanum.telemetry = this.telemetry;
        mecanum.parent = this;
        mecanum.initialize();
        waitForStart();



    }

    /*
    Near Carousel
    =============
    1. Spin Carousel
    2. Scan
    3. Drop preload
    4. Go to Warehouse and Park

    Near Warehouse
    =============
    1. Scan
    2. Drop preload
    3. Spin (if we decide to do this)
    4. Go to Warehouse and Park

     */


    public void startBlueNearCarousel() {

    }

    public void startBlueNearWarehouse() {

    }

    public void startRedNearCarousel() {

    }

    public void startRedNearWarehouse() {

    }

}
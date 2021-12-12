package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.RobotObjects.MAS.Claw;
import org.firstinspires.ftc.teamcode.RobotObjects.MAS.Mecanum_Wheels;
/*

    Near Warehouse
    =============
    1. Scan
    2. Drop preload
    3. Spin (if we decide to do this)
    4. Go to Warehouse and Park

     */

@Autonomous(name="MAS_Auto_BlueWarehouse")
@Disabled
public class MAS_Auto_BlueWarehouse extends LinearOpMode {
    //Configuration used: 6wheelConfig
    @Override
    public void runOpMode() throws InterruptedException {
        double speed = 0.2;
        Mecanum_Wheels mecanum = new Mecanum_Wheels(hardwareMap);
        Claw claw = new Claw(hardwareMap);
        mecanum.IsMASAutonomous = true;
        mecanum.velocity = 400;
        mecanum.telemetry = this.telemetry;
        mecanum.parent = this;
        mecanum.initialize();
        waitForStart();
        double spinnerDistance = 23;
        double spinnerRotate = 20;
        double shippingHubDistance = 14;
        double rotateNinety = 21;

        //SCAN CODE- EISHA AND HAMZA

        //Scan for position of the element- eisha and hamza!

        //move right a bit then forward a bit to drop
        mecanum.move_right_auto(speed, shippingHubDistance/1.05, 20.0);
        mecanum.move_forward_auto(speed, shippingHubDistance*1.5,20.0);

        //Raise Arm and wrist to drop
        /*mecanum.liftArm(-0.4);
        //claw.raiseWrist(0.5);
        sleep(4200);
        mecanum.arm.setPower(0.0);
        sleep(100);
        //claw.openClaws();
        sleep(500);
        //claw.closeClaws();
        mecanum.liftArm(0.3);
        //claw.restWrist();
        sleep(2100);
        */

        mecanum.moveArm(2, 0);
        claw.dropObject();
        mecanum.moveArm(0, 2);

        mecanum.move_backward_auto(speed,shippingHubDistance/1.25, 20.0 );

        mecanum.rotate_clock_auto(speed, rotateNinety, 10.0);

        double ParkDistance = 40;//going forward into warehouse
        //Increase the speed if we are going over the obstacle
        mecanum.move_forward_auto(speed*2.5,ParkDistance, 25.0 );
        //else will need logic to collapse and then move right to park



    }


}
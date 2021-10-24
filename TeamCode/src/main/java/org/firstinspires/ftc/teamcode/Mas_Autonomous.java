package org.firstinspires.ftc.teamcode;

@Autonomous(name="MAS_Autonomous")
public class MAS_Autonomous extends LinearOpMode {
    @Override
    public void runOpMode() throws InterruptedException {
        Mecanum_Wheels mecanum = new Mecanum_Wheels(hardwareMap);
        mecanum.moveForward();

    }

}
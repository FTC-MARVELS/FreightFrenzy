/* AutonomousWorkerMethods class is a collection of methods called by
 *  the various autonomous OpModes.
 */

package org.firstinspires.ftc.teamcode.Research;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
@Disabled
public class AutonomousWorkerMethods extends LinearOpMode {

    /*
     * Portions of this code have been copied from the now obsolite OpMode
     * 'Autonomous_Blue', which was originally written by Hamza (in Blocks).
     */

    @Override
    public void runOpMode() {
    }

//d    DcMotor leftMotor = hardwareMap.dcMotor.get("LeftDrive");
//d    DcMotor rightMotor = hardwareMap.dcMotor.get("RightDrive");
//d    DcMotor middleMotor = hardwareMap.dcMotor.get("MiddleDrive");

    public void telemeterEncoderPositions() {
        // Display via telemetry all current encoder positions and busy statuses
//d        telemetry.addData("encoder-left", leftMotor.getCurrentPosition() + "  busy=" + leftMotor.isBusy());
//d        telemetry.addData("encoder-right", rightMotor.getCurrentPosition() + "  busy=" + rightMotor.isBusy());
//d        telemetry.addData("encoder-middle", middleMotor.getCurrentPosition() + "  busy=" + middleMotor.isBusy());
        telemetry.update();
        idle();
    }
}
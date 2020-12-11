/* AutonomousWorkerMethods class is a collection of methods called by
 *  the various autonomous OpModes.
 */

package org.firstinspires.ftc.teamcode.WorkerClasses;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;

public class AutonomousWorkerMethods extends LinearOpMode {

    /*
     * Portions of this code have been copied from the now obsolite OpMode
     * 'Autonomous_Blue', which was originally written by Hamza (in Blocks).
     */

    @Override
    public void runOpMode() {
    }

    DcMotor leftMotor = hardwareMap.dcMotor.get("LeftDrive");
    DcMotor rightMotor = hardwareMap.dcMotor.get("RightDrive");
    DcMotor middleMotor = hardwareMap.dcMotor.get("MiddleDrive");

    public void telemeterEncoderPositions() {
        // Display via telemetry all current encoder positions and busy statuses
        telemetry.addData("encoder-left", leftMotor.getCurrentPosition() + "  busy=" + leftMotor.isBusy());
        telemetry.addData("encoder-right", rightMotor.getCurrentPosition() + "  busy=" + rightMotor.isBusy());
        telemetry.addData("encoder-middle", middleMotor.getCurrentPosition() + "  busy=" + middleMotor.isBusy());
        telemetry.update();
        idle();
    }
}
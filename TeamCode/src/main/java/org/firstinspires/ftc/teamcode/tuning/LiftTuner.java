package org.firstinspires.ftc.teamcode.tuning;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;

import java.util.List;

public class LiftTuner extends LinearOpMode {
    DcMotor leftLift, rightLift;

    DcMotor[] lift = new DcMotor[2];

    @Override
    public void runOpMode() throws InterruptedException {
        leftLift = hardwareMap.get(DcMotor.class, "leftLift");
        rightLift = hardwareMap.get(DcMotor.class, "rightLift");

        rightLift.setDirection(DcMotorSimple.Direction.REVERSE);

        lift[0] = (leftLift);
        lift[1] = (rightLift);

        for(DcMotor motor : lift) {
            motor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
            motor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        }

        telemetry.addData("L", leftLift.getCurrentPosition());
        telemetry.addData("R", rightLift.getCurrentPosition());

        telemetry.update();

        while (opModeIsActive()) {
            telemetry.addData("L", leftLift.getCurrentPosition());
            telemetry.addData("R", rightLift.getCurrentPosition());

            telemetry.update();
        }
    }
}

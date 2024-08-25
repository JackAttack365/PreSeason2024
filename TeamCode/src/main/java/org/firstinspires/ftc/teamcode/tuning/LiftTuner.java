package org.firstinspires.ftc.teamcode.tuning;

import com.acmerobotics.dashboard.config.Config;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;


@Config
public class LiftTuner extends LinearOpMode {
    DcMotor leftLift, rightLift;

    DcMotor[] lift = new DcMotor[2];

    public static volatile double Kp = 1;
    public static volatile double Ki = 0;
    public static volatile double Kd = 0;

    double targetPosition = 0;
    double currentPosition = 0;
    double error = 0;

    double integral = 0;
    double previousError = 0;

    public final double inPerTick = 81.3798387812;

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

        telemetry.addData("Height Estimate", leftLift.getCurrentPosition()*inPerTick);

        telemetry.update();

        while (opModeIsActive()) {
            targetPosition += (gamepad2.right_trigger) * 10;

            currentPosition = leftLift.getCurrentPosition();
            error = targetPosition - currentPosition;

            integral += error;  // Accumulate error over time
            double derivative = error - previousError;  // Change in error

            // Calculate PID output
            double power = (Kp * error) + (Ki * integral) + (Kd * derivative);

            // Set motor power
            leftLift.setPower(power);
            rightLift.setPower(power);

            previousError = error;

            telemetry.addData("L", leftLift.getCurrentPosition());
            telemetry.addData("R", rightLift.getCurrentPosition());

            telemetry.addData("Height Estimate", leftLift.getCurrentPosition()*inPerTick);

            telemetry.update();
        }
    }
}

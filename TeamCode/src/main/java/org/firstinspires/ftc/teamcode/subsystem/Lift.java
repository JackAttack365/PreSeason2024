package org.firstinspires.ftc.teamcode.subsystem;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;

import org.firstinspires.ftc.teamcode.Config;

public class Lift extends SubSystem {
    private DcMotor leftLift, rightLift;

    double Kp = 1;
    double Ki = 0;
    double Kd = 0;

    double targetPosition = 0;
    double currentPosition = 0;
    double error = 0;

    double integral = 0;
    double previousError = 0;

    private final int LIFT_TOP_STOP = 1000; // TODO tune using LiftTuner

    private final int LIFT_BOTTOM_STOP = 100;

    public Lift(Config config, boolean isOneController) {
        super(config, isOneController);
    }

    public Lift(Config config) {
        super(config);
    }

    @Override
    public void init() {
        leftLift = config.hardwareMap.get(DcMotor.class, Config.LEFT_LIFT_MOTOR);
        rightLift = config.hardwareMap.get(DcMotor.class, Config.RIGHT_LIFT_MOTOR);

        leftLift.setDirection(DcMotor.Direction.FORWARD);
        rightLift.setDirection(DcMotor.Direction.REVERSE);

        leftLift.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        rightLift.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        leftLift.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        rightLift.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
    }

    @Override
    public void update() {
        targetPosition += (config.gamePad2.right_trigger) * 10;

        targetPosition = Math.max(LIFT_BOTTOM_STOP, Math.min(targetPosition, LIFT_TOP_STOP));

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

        config.telemetry.addData("Lift Height", leftLift.getCurrentPosition());
    }

    private boolean isNotNearToStop() {
        return ((LIFT_BOTTOM_STOP + 300) <= leftLift.getCurrentPosition()) && (leftLift.getCurrentPosition() <= (LIFT_TOP_STOP -  300));
    }
}

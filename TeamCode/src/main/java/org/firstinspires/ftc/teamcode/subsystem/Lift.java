package org.firstinspires.ftc.teamcode.subsystem;

import androidx.annotation.NonNull;

import com.acmerobotics.dashboard.telemetry.TelemetryPacket;
import com.acmerobotics.roadrunner.Action;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;

import org.firstinspires.ftc.teamcode.Config;

public class Lift extends SubSystem {
    private DcMotor leftLift, rightLift;

    private boolean atBottom;

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

        rightLift.setDirection(DcMotor.Direction.FORWARD);
        leftLift.setDirection(DcMotor.Direction.REVERSE);

        leftLift.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        rightLift.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        leftLift.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        rightLift.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        atBottom = true;
    }

    @Override
    public void update() {

        if (config.gamePad1.right_trigger >= 0.1) {
            if (atBottom) {
                leftLift.setTargetPosition(1400);
                rightLift.setTargetPosition(1400);

                leftLift.setPower(1);
                rightLift.setPower(1);

                leftLift.setMode(DcMotor.RunMode.RUN_TO_POSITION);
                rightLift.setMode(DcMotor.RunMode.RUN_TO_POSITION);

                atBottom = true;
            } else {
                leftLift.setTargetPosition(100);
                rightLift.setTargetPosition(100);

                leftLift.setPower(1);
                rightLift.setPower(1);

                leftLift.setMode(DcMotor.RunMode.RUN_TO_POSITION);
                rightLift.setMode(DcMotor.RunMode.RUN_TO_POSITION);

                atBottom = true;
            }
        }

        if (config.gamePad1.a) {
            leftLift.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
            rightLift.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        }


        config.telemetry.addData("liftL", leftLift.getCurrentPosition());
        config.telemetry.addData("liftR", rightLift.getCurrentPosition());
    }
}

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

        leftLift.setDirection(DcMotor.Direction.FORWARD);
        rightLift.setDirection(DcMotor.Direction.REVERSE);

        leftLift.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        rightLift.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        leftLift.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        rightLift.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        atBottom = true;
    }

    @Override
    public void update() {
        if (config.gamePad1.right_trigger >= 0.1 && !leftLift.isBusy()) {
            if (atBottom) {
                leftLift.setTargetPosition(400);
                rightLift.setTargetPosition(400);

                leftLift.setMode(DcMotor.RunMode.RUN_TO_POSITION);
                rightLift.setMode(DcMotor.RunMode.RUN_TO_POSITION);

                atBottom = true;
            } else {
                leftLift.setTargetPosition(20);
                rightLift.setTargetPosition(20);

                leftLift.setMode(DcMotor.RunMode.RUN_TO_POSITION);
                rightLift.setMode(DcMotor.RunMode.RUN_TO_POSITION);

                atBottom = true;
            }
        }

        if (config.gamePad1.a) {
            leftLift.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
            rightLift.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        }
    }
}

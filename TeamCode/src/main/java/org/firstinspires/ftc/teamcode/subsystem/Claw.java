package org.firstinspires.ftc.teamcode.subsystem;

import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.teamcode.Config;

public class Claw extends SubSystem {
    private Servo claw;

    private boolean clawClosed;

    public Claw(Config config) {
        super(config);
    }

    public Claw(Config config, boolean isOneController) {
        super(config, isOneController);
    }

    @Override
    public void init() {
        claw = config.hardwareMap.get(Servo.class, "claw");

        clawClosed = false;
    }

    @Override
    public void update() {
        if (config.gamePad1.right_bumper) {
            if (clawClosed) {
                claw.setPosition(0);

                clawClosed = false;
            } else {
                claw.setPosition(1);

                clawClosed = true;
            }
        }
    }
}

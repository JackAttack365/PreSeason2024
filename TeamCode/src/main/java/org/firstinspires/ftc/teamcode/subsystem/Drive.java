package org.firstinspires.ftc.teamcode.subsystem;

import com.qualcomm.robotcore.hardware.DcMotor;

import org.firstinspires.ftc.teamcode.Config;


public class Drive extends SubSystem {
    private DcMotor left, right;

    public Drive(Config cfg) {
        super(cfg);
    }

    public Drive(Config cfg, boolean isOneController) {
        super(cfg, isOneController);
    }

    public void init() {

        // Initialize the hardware variables. Note that the strings used here must correspond
        // to the names assigned during the robot configuration step on the DS or RC devices.
        left = config.hardwareMap.get(DcMotor.class, Config.LEFT_DRIVE);
        right = config.hardwareMap.get(DcMotor.class, Config.RIGHT_DRIVE);

        // Most robots need the motors on one side to be reversed to drive forward.
        // When you first test your robot, push the left joystick forward
        // and flip the direction ( FORWARD <-> REVERSE ) of any wheel that runs backwards
        left.setDirection(DcMotor.Direction.REVERSE); // DO NOT CHANGE
        right.setDirection(DcMotor.Direction.FORWARD); // TODO make sure this shit works please

        left.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE); // DO NOT CHANGE
        right.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE); // DO NOT CHANGE
    }

    public void update() {

        // POV Mode uses left joystick to go forward & strafe, and right joystick to rotate.
        double axial = -config.gamePad1.left_stick_y;  // Note: pushing stick forward gives negative value
        // MUST BE INVERTED!
        double yaw = -config.gamePad1.right_stick_x;
        // Take the average of the 2 triggers
        double speed = 1 - (config.gamePad1.right_trigger + config.gamePad1.left_trigger) / 2;

        // Combine the joystick requests for each axis-motion to determine each wheel's power.
        // Set up a variable for each drive wheel to save the power level for telemetry.
        double leftPower = (axial - yaw) * speed; // DO NOT CHANGE
        double rightPower = (axial + yaw) * speed; // DO NOT CHANGE=

        // Normalize the values so no wheel power exceeds 100%
        // This ensures that the robot maintains the desired motion.
        double max;
        max = Math.max(Math.abs(leftPower), Math.abs(rightPower));

        if (max > 1.0) {
            leftPower /= max;
            rightPower /= max;
        }

        // Send calculated power to wheels`
        left.setPower(leftPower);
        right.setPower(rightPower);

        // Show the elapsed game time and wheel power.
        config.telemetry.addData("Wheels left/Right", "%4.2f, %4.2f", leftPower, rightPower);
        config.telemetry.addData("Right Stick x Position", "%4.2f", yaw);
    }
}
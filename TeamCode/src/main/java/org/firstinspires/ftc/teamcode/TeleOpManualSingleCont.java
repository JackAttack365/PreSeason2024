package org.firstinspires.ftc.teamcode;


import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

@TeleOp(name = "Single Controller", group = "LinearOpmode")
public class TeleOpManualSingleCont extends LinearOpMode {
    // Config
    Config config;
    ManualRobot manualRobot;

    @Override
    public void runOpMode() {

        // Create the config used in all subsystems
        config = new Config(telemetry, hardwareMap, gamepad1, gamepad2);
        // Create the Manual Robot and register the subsystems
        manualRobot = new ManualRobot(config, true);
        // Initialize all subsystems
        manualRobot.init();
        waitForStart();

        // Main Loop
        while (opModeIsActive()) {
            // Update everything
            config.updateTelemetry();

            // runs each sub-system once
            manualRobot.update();

            // Show the elapsed game time and wheel power.
            telemetry.update();
        }

    }


}

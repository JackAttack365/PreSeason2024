package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.robotcore.external.Telemetry;

import org.firstinspires.ftc.teamcode.enums.GameStage;

// Config stores everything any of our SubSystems need to function, stores GamePads, Telemetry, HardwareMap,
// and names of each motor
public class Config {

    public Telemetry telemetry;
    public HardwareMap hardwareMap;
    public Gamepad gamePad1;
    public Gamepad gamePad2;

    public GameStage stage;

    // Stores the hardwareMap names as constants
    // Drive system
    public static final String RIGHT_FRONT_DRIVE = "rightFront";
    public static final String RIGHT_BACK_DRIVE = "rightBack";
    public static final String LEFT_FRONT_DRIVE = "leftFront";
    public static final String LEFT_BACK_DRIVE = "leftBack";

    // Current game runtime
    private ElapsedTime runtime = new ElapsedTime();

    // Constructor
    public Config(Telemetry tlm, HardwareMap hwm, Gamepad gmp1, Gamepad gmp2, GameStage stage) {
        this.telemetry = tlm;
        this.hardwareMap = hwm;
        this.gamePad1 = gmp1;
        this.gamePad2 = gmp2;
    }

    // Telemetry is similar to logging. Appears in Driver Station
    public void updateTelemetry() {
        telemetry.addData("Status", "Run Time: " + runtime.toString());

        telemetry.addData("G1: bumper", "L: %b R: %b", gamePad1.left_bumper, gamePad1.right_bumper);
        telemetry.addData("G1: trigger", "L: %4.2f, R: %4.2f", gamePad1.left_trigger, gamePad1.right_trigger);
    }

    // Alerts driver when endgame starts
    public void checkTime() {
        if (runtime.seconds() >= 90.0 && stage != GameStage.DrivePractice && !gamePad1.isRumbling()) {
            // Endgame period begins
            if (stage == GameStage.TeleOp) {
                gamePad1.rumble(1000);
                gamePad2.rumble(1000);
                stage = GameStage.EndGame;
            }
            // 5 seconds left in endgame
            else if (runtime.seconds() >= 115.0) {
                gamePad1.rumble(1000);
                gamePad2.rumble(1000);

            }

        }
    }

}

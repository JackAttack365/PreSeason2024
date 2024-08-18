package org.firstinspires.ftc.teamcode.subsystem;

import static org.firstinspires.ftc.teamcode.Config.ROBOT_WIDTH;

import com.acmerobotics.dashboard.config.Config;
import com.acmerobotics.roadrunner.DualNum;
import com.acmerobotics.roadrunner.Time;
import com.acmerobotics.roadrunner.Twist2dDual;
import com.acmerobotics.roadrunner.Vector2d;
import com.acmerobotics.roadrunner.Vector2dDual;
import com.acmerobotics.roadrunner.ftc.Encoder;
import com.acmerobotics.roadrunner.ftc.FlightRecorder;
import com.acmerobotics.roadrunner.ftc.OverflowEncoder;
import com.acmerobotics.roadrunner.ftc.PositionVelocityPair;
import com.acmerobotics.roadrunner.ftc.RawEncoder;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.teamcode.messages.ThreeDeadWheelInputsMessage;

@Config
public class Localizer extends SubSystem {
    public Localizer(org.firstinspires.ftc.teamcode.Config cfg) {
        super(cfg);
    }

    public Localizer(org.firstinspires.ftc.teamcode.Config cfg, boolean isOneController) {
        super(cfg, isOneController);
    }

    public DcMotorEx par0, par1, perp;

    public double inPerTick;

    public int lastPar0, lastPar1, lastPerp;

    public double xPos, yPos, heading;

    @Override
    public void init() {
        // TODO: make sure your config has **motors** with these names (or change them)
        //   the encoders should be plugged into the slot matching the named motor
        //   see https://ftc-docs.firstinspires.org/en/latest/hardware_and_software_configuration/configuring/index.html
        par0 = config.hardwareMap.get(DcMotorEx.class,"leftFront");
        par1 = config.hardwareMap.get(DcMotorEx.class,"rightFront");
        perp = config.hardwareMap.get(DcMotorEx.class,"rightBack");

        // TODO: reverse encoder directions if needed
        //   par0.setDirection(DcMotorSimple.Direction.REVERSE);

        this.inPerTick = 2000;

        xPos = 0.0;
        yPos = 0.0;
        heading = 0.0;
    }

    @Override
    public void update() {
        int par0Pos = par0.getCurrentPosition();
        int par1Pos = par1.getCurrentPosition();
        int perpPos = perp.getCurrentPosition();

        int deltaPar0 = par0Pos - lastPar0;
        int deltaPar1 = par1Pos - lastPar1;
        int deltaPerp = perpPos - lastPerp;

        double deltaTheta = (double) (deltaPar0 - deltaPar1) / ROBOT_WIDTH;

        heading += deltaTheta;

        double deltaX = (deltaPar1 - deltaPar0) / 2.0 * Math.cos(heading) - deltaPerp * Math.sin(heading);
        double deltaY = (deltaPar1 - deltaPar0) / 2.0 * Math.sin(heading) - deltaPerp * Math.cos(heading);

        xPos += deltaX;
        yPos += deltaY;

        config.telemetry.addData("Localizer Pose Estimate","x: " + xPos + ", y: " + yPos + ", Θ: " + heading);

        //config.dashboard.

        lastPar0 = par0Pos;
        lastPar1 = par1Pos;
        lastPerp = perpPos;
    }
}

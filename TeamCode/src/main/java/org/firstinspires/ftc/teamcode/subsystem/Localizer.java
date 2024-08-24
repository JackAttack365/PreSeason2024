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

    @Override
    public void init() {
        par0 = config.hardwareMap.get(DcMotorEx.class,"leftFront");
        par1 = config.hardwareMap.get(DcMotorEx.class,"rightFront");
        perp = config.hardwareMap.get(DcMotorEx.class,"rightBack");

        // TODO: reverse encoder directions if needed
        //   par0.setDirection(DcMotorSimple.Direction.REVERSE);

        this.inPerTick = 1058.3345;

        config.robotX = 0.0;
        config.robotY = 0.0;
        config.robotHeading = 0.0;
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

        config.robotHeading += deltaTheta;

        double deltaX = (deltaPar1 - deltaPar0) / 2.0 * Math.cos(config.robotHeading) - deltaPerp * Math.sin(config.robotHeading);
        double deltaY = (deltaPar1 - deltaPar0) / 2.0 * Math.sin(config.robotHeading) - deltaPerp * Math.cos(config.robotHeading);

        config.robotX += deltaX;
        config.robotY += deltaY;

        config.telemetry.addData("Localizer Pose Estimate","x: " + config.robotX + ", y: " + config.robotY + ", Θ: " + config.robotHeading);

        lastPar0 = par0Pos;
        lastPar1 = par1Pos;
        lastPerp = perpPos;
    }
}

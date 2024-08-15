package com.piedmontpioneers.meepmeeptesting.autos.paths.test;



// RR-specific imports
import com.acmerobotics.roadrunner.Action;
import com.acmerobotics.roadrunner.Vector2d;
import com.noahbres.meepmeep.roadrunner.DriveShim;



public class Near {

    public static Action square(DriveShim drive) {
        return drive.actionBuilder(drive.getPoseEstimate())
                .strafeToLinearHeading(new Vector2d(0,40), Math.toRadians(90))
                .strafeToLinearHeading(new Vector2d(40,40), Math.toRadians(0))
                .strafeToLinearHeading(new Vector2d(40,0), Math.toRadians(270))
                .strafeToLinearHeading(new Vector2d(0,0), Math.toRadians(180))
                .build();
    }
}

package org.firstinspires.ftc.teamcode.subsystem;

import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName;
import org.firstinspires.ftc.teamcode.Config;
import org.openftc.easyopencv.OpenCvCameraFactory;
import org.openftc.easyopencv.OpenCvWebcam;

public class CameraStream extends SubSystem{
    public CameraStream(Config config) {
        super(config);
    }

    public CameraStream(Config config, boolean isOneController) {
        super(config, isOneController);
    }

    int cameraMonitorViewId;

    OpenCvWebcam camera;

    @Override
    public void init() {
        cameraMonitorViewId = config.hardwareMap.appContext.getResources().getIdentifier("cameraMonitorViewId", "id", config.hardwareMap.appContext.getPackageName());

        camera = OpenCvCameraFactory.getInstance().createWebcam(config.hardwareMap.get(WebcamName.class, "Webcam 1"), cameraMonitorViewId);
    }

    @Override
    public void update() {
        config.dashboard.startCameraStream(camera, 0);
    }
}

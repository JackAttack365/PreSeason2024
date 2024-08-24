package org.firstinspires.ftc.teamcode.vision;


import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.Point;
import org.opencv.core.Rect;
import org.opencv.core.Scalar;
import org.opencv.imgproc.Imgproc;
import org.openftc.easyopencv.OpenCvPipeline;

public class ExamplePipeline extends OpenCvPipeline {
    public Rect topRect = new Rect(new Point(137, 23), new Point(156, 45));
    public Rect bottomRect = new Rect(new Point(155, 11), new Point(159, 39));

    // Coordinates of the box that checks for the top three rings. Find coordinates in same way as bottom rectangle
    public double thresholdLow = 7;
    public double thresholdHigh = 16;

    // The two values that the hsv mean of the rings fall between. You can see the hsv mean by running visiontester.
    public int stack;
    public boolean checkThreshold(double checkThreshold) {
        return checkThreshold > thresholdLow && checkThreshold < thresholdHigh;
    }

    public Mat processFrame (Mat input) {
        Mat convertedMat = new Mat();
        Imgproc.cvtColor(input, convertedMat, Imgproc.COLOR_RGB2HSV);
        Scalar meanTopRect = Core.mean(convertedMat.submat(bottomRect));
        Scalar meanBottomRect = Core.mean(convertedMat.submat(topRect));
        // gets average hsv value for rectangle
        if(checkThreshold(meanTopRect.val[0])){
            stack = 4;
        }else if(checkThreshold(meanBottomRect.val[0])) {
            stack = 1;
        }else{
            stack = 0;
        }
        Imgproc.cvtColor(input, convertedMat, Imgproc.COLOR_RGB2YCrCb);
        return input;

    }
}
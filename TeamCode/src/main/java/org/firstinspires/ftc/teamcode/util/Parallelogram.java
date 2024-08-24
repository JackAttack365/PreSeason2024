package org.firstinspires.ftc.teamcode.util;

/*
    This class converts raw camera data into a more readable and usable form.

    The points are stored as doubles and correspond to this diagram

    For example:

    A              B
      +------------+
       \            \
        \            \
         \            \
          +------------+
          C             D
 */

public class Parallelogram {
    Coordinate A;
    Coordinate B;
    Coordinate C;
    Coordinate D;


    public Parallelogram(Coordinate a, Coordinate b, Coordinate c, Coordinate d) {
        this.A = a;
        this.B = b;
        this.C = c;
        this.D = d;
    }

    public double getAB() {
        return Math.sqrt(Math.pow(A.x - B.x, 2) + Math.pow(A.y - B.y, 2));
    }

    public double getBD() {
        return Math.sqrt(Math.pow(B.x - D.x, 2) + Math.pow(B.y - D.y, 2));
    }

    public double getCD() {
        return Math.sqrt(Math.pow(C.x - D.x, 2) + Math.pow(C.y - D.y, 2));
    }

    public double getAC() {
        return Math.sqrt(Math.pow(A.x - C.x, 2) + Math.pow(A.y - C.y, 2));
    }

    public double getArea() {
        return getAB() * getAC();
    }
}

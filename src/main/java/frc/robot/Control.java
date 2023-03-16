package frc.robot;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.VictorSPX;

import edu.wpi.first.wpilibj.Joystick;

import java.lang.Math;

public class Control {

    public static VictorSPX DriveTrainLeft0;
    public static VictorSPX DriveTrainLeft1;
    public static VictorSPX DriveTrainRight0;
    public static VictorSPX DriveTrainRight1;
  
    public static Joystick Controller;

    public static void controlLeft(double percent) {
        DriveTrainLeft0.set(ControlMode.PercentOutput, percent);
        DriveTrainLeft1.set(ControlMode.PercentOutput, percent);
    }

    public static void controlRight(double percent) {
        DriveTrainRight0.set(ControlMode.PercentOutput, percent);
        DriveTrainRight1.set(ControlMode.PercentOutput, percent);
    }

    public static void turn(double xRight, double yLeft) {
        //check for forward or backward
        double rotationMod = 0.5;
        double yLeftModifier = 0;
        int direction = 1;
        if (yLeft >= 0.1) {yLeftModifier = 1 - yLeft; direction = 1;}
        else if (yLeft <= -0.1) {yLeftModifier = 1 + yLeft; direction = -1;}
        System.out.print(yLeftModifier);

        //check for curved turn
        boolean curve = false;
        if (yLeft >= 0.1 || yLeft <= -0.1) {curve = true;}
        else {curve = false;}

        //turn right
        if (xRight > 0.1) {
            //right curve
            if (curve) {
                controlRight(Math.abs(yLeft) * (1 - Math.abs(xRight)) * direction);
                controlLeft(Math.abs(yLeft) * direction);
                System.out.println(direction);
            }
            //spin right
            else {
                controlRight(xRight * rotationMod);
                controlLeft(xRight * -1 * rotationMod);
            }
        }
        //turn left
        else if (xRight < 0.1) {
            //left curve
            if (curve) {
                controlRight(Math.abs(yLeft) * direction);
                controlLeft(Math.abs(yLeft) * (1 - Math.abs(xRight)) * direction);
                System.out.println(direction);
            }
            //spin left
            else {
                controlRight(xRight * rotationMod);
                controlLeft(xRight * -1 * rotationMod);
            }
        }
    }

    public static void move(double yLeft) {
        controlLeft(yLeft);
        controlRight(yLeft);
    }

    public static void control(double yLeft, double xRight) {
        if (xRight <= -0.1 || xRight >= 0.1) {
            turn(xRight, yLeft);
        } 
        else if (yLeft <= -0.1 || yLeft >= 0.1){
            move(yLeft);
        }
        else {
            move(0.0);
        }
    }
}

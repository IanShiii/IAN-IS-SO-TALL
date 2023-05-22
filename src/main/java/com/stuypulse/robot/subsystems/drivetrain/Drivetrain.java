package com.stuypulse.robot.subsystems.drivetrain;

import com.stuypulse.robot.subsystems.drivetrain.DrivetrainImpl.Gear;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public abstract class Drivetrain extends SubsystemBase {
    
    //singleton
    private static final Drivetrain instance;

    static {
        instance = new DrivetrainImpl();
    }

    public static Drivetrain getInstance() {
        return instance;
    }

    public abstract void setGear(Gear gear);

    public abstract void setVoltages(double leftVoltages, double rightVoltages);
    public abstract double getLeftDistance();
    public abstract double getRightDistance();

}
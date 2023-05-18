package com.stuypulse.robot.subsystems.drivetrain;
import com.stuypulse.robot.subsystems.drivetrain.DrivetrainImpl.Gear;

import edu.wpi.first.wpilibj2.command.SubsystemBase;

public abstract class Drivetrain extends SubsystemBase{
    
    private static Drivetrain instance; 

    static {
        instance = new DrivetrainImpl();
    }

    public static Drivetrain getInstance() {
        return instance;
    }

    public abstract void setGear(Gear gear);
    public abstract void arcadeDrive(double speed, double rotation);
    
}

package com.stuypulse.robot.commands.drivetrain;

import com.stuypulse.robot.subsystems.drivetrain.Drivetrain;
import com.stuypulse.robot.subsystems.drivetrain.DrivetrainImpl.Gear;

import edu.wpi.first.wpilibj2.command.CommandBase;

public class DrivetrainDriveDistance extends CommandBase{
    
    private final Drivetrain drivetrain;
    private final double targetDistance;

    public DrivetrainDriveDistance(double targetDistance) {
        drivetrain = Drivetrain.getInstance();
        this.targetDistance = targetDistance;

        addRequirements(drivetrain);
    }
    
    
}
    
    
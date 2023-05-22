package com.stuypulse.robot.commands.drivetrain;

import com.stuypulse.robot.constants.Settings;
import com.stuypulse.robot.subsystems.drivetrain.Drivetrain;
import com.stuypulse.robot.subsystems.drivetrain.DrivetrainImpl.Gear;
import com.stuypulse.stuylib.control.feedback.PIDController;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.CommandBase;

public class DrivetrainDriveDistance extends CommandBase{
    
    private final Drivetrain drivetrain;
    
    private final double targetDistance;

    private final PIDController leftController;
    private final PIDController rightController;

    public DrivetrainDriveDistance(double targetDistance) {
        drivetrain = Drivetrain.getInstance();
        this.targetDistance = targetDistance;

        leftController = new PIDController(Settings.Drivetrain.Motion.PID.kP, Settings.Drivetrain.Motion.PID.kI, Settings.Drivetrain.Motion.PID.kD);
        rightController = new PIDController(Settings.Drivetrain.Motion.PID.kP, Settings.Drivetrain.Motion.PID.kI, Settings.Drivetrain.Motion.PID.kD);

        addRequirements(drivetrain);
    }

    @Override
    public void initialize() {
        drivetrain.setTargetDistance(targetDistance);
        drivetrain.setGear(Gear.LOW);
    }

    @Override 
    public void execute() {
        drivetrain.setVoltages(leftController.update(drivetrain.getTargetDistance(), drivetrain.getLeftDistance()), rightController.update(drivetrain.getTargetDistance(), drivetrain.getRightDistance()));

        SmartDashboard.putNumber("Target Distance", drivetrain.getTargetDistance());
    }
}

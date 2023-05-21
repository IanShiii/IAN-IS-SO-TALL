package com.stuypulse.robot.subsystems.drivetrain;

import com.stuypulse.robot.constants.Settings;
import com.stuypulse.robot.subsystems.drivetrain.DrivetrainImpl.Gear;
import com.stuypulse.stuylib.control.feedback.PIDController;
import com.stuypulse.stuylib.network.SmartNumber;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
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

    private final SmartNumber targetDistance;
    private final PIDController leftController;
    private final PIDController rightController;

    protected Drivetrain() {
        targetDistance = new SmartNumber("Target Distance", 0);
        leftController = new PIDController(Settings.Drivetrain.Motion.PID.kP, Settings.Drivetrain.Motion.PID.kI, Settings.Drivetrain.Motion.PID.kD);
        rightController = new PIDController(Settings.Drivetrain.Motion.PID.kP, Settings.Drivetrain.Motion.PID.kI, Settings.Drivetrain.Motion.PID.kD);
    }

    public void setTargetDistance(double targetDistance) {
        this.targetDistance.set(targetDistance);
    }

    public double getTargetDistance() {
        return targetDistance.get();
    }

    public abstract void setGear(Gear gear);

    protected abstract void setVoltages(double leftVoltages, double rightVoltages);
    protected abstract double getLeftDistance();
    protected abstract double getRightDistance();

    @Override
    public final void periodic() {
        
        setVoltages(leftController.update(getTargetDistance(), getLeftDistance()), rightController.update(getTargetDistance(), getRightDistance()));

        SmartDashboard.putNumber("Target Distance", getTargetDistance());
    }
}
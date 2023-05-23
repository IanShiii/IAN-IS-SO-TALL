package com.stuypulse.robot.commands.drivetrain;

import com.stuypulse.robot.subsystems.drivetrain.Drivetrain;
import com.stuypulse.robot.subsystems.drivetrain.DrivetrainImpl.Gear;
import com.stuypulse.stuylib.input.Gamepad;
import com.stuypulse.stuylib.streams.IStream;

import edu.wpi.first.wpilibj2.command.CommandBase;

public class DrivetrainDrive extends CommandBase {
    
    private final Drivetrain drivetrain;
    private final Gamepad driver;

    private final IStream speed, angle;

    public DrivetrainDrive(Gamepad driver) {
        drivetrain = Drivetrain.getInstance();
        this.driver = driver;

        this.speed = IStream.create(() -> driver.getRightTrigger() - driver.getLeftTrigger());
        this.angle = IStream.create(() -> driver.getLeftX());

        addRequirements(drivetrain);
    }

    public void execute() {
        if (driver.getRawLeftButton()) {
            drivetrain.setGear(Gear.LOW);
            drivetrain.arcadeDrive(speed.get() - 0.1, angle.get());
        } else {
            drivetrain.setGear(Gear.HIGH);
            drivetrain.arcadeDrive(speed.get(), angle.get());
        }
    }
}
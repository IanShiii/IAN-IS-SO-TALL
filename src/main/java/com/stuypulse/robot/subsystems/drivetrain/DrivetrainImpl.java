package com.stuypulse.robot.subsystems.drivetrain;
import com.stuypulse.robot.constants.Ports;
import com.revrobotics.CANSparkMax;
import com.revrobotics.RelativeEncoder;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.PneumaticsModuleType;
import edu.wpi.first.wpilibj.DoubleSolenoid.Value;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj.motorcontrol.MotorControllerGroup;

public class DrivetrainImpl extends Drivetrain{
    
    //code ur drivetrain here :D
    //the ports for the motors are already in the ports file btw 

    private final CANSparkMax[] leftMotors;
    private final CANSparkMax[] rightMotors;
    private final DifferentialDrive DD;
    private final DoubleSolenoid doubleSolenoid;
    private final CANSparkMax elPro;
    private final RelativeEncoder Pro52;
    private Gear gear;

    public DrivetrainImpl() {

        leftMotors = new CANSparkMax[] {
            new CANSparkMax(Ports.Drivetrain.LEFT_TOP, MotorType.kBrushless),
            new CANSparkMax(Ports.Drivetrain.LEFT_MIDDLE, MotorType.kBrushless),
            new CANSparkMax(Ports.Drivetrain.LEFT_BOTTOM, MotorType.kBrushless),
        };

        rightMotors = new CANSparkMax[] {
            new CANSparkMax(Ports.Drivetrain.RIGHT_TOP, MotorType.kBrushless),
            new CANSparkMax(Ports.Drivetrain.RIGHT_MIDDLE, MotorType.kBrushless),
            new CANSparkMax(Ports.Drivetrain.RIGHT_BOTTOM, MotorType.kBrushless),
        };

        DD = new DifferentialDrive(new MotorControllerGroup(leftMotors), new MotorControllerGroup(rightMotors));
        doubleSolenoid = new DoubleSolenoid(PneumaticsModuleType.CTREPCM, 0, 1);
        elPro = new CANSparkMax(0, MotorType.kBrushless);
        Pro52 = elPro.getEncoder();
    }

    public static enum Gear {
        LOW(Value.kForward), 
        HIGH(Value.kReverse);

        private final Value value;
        
        private Gear(Value value) {
            this.value = value;
        }
    }

    @Override
    public void setGear(Gear gear) {
        doubleSolenoid.set(gear.value);
        this.gear = gear;
    }

    @Override
    public void arcadeDrive(double speed, double rotation) {
        DD.arcadeDrive(speed, rotation);
    }

    @Override
    
}


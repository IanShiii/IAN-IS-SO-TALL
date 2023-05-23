package com.stuypulse.robot.subsystems.drivetrain;
import com.stuypulse.robot.constants.Motors;
import com.stuypulse.robot.constants.Ports;
import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.PneumaticsModuleType;
import edu.wpi.first.wpilibj.DoubleSolenoid.Value;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj.motorcontrol.MotorControllerGroup;

import com.stuypulse.robot.constants.Settings;

public class DrivetrainImpl extends Drivetrain{

    private final CANSparkMax[] leftMotors;
    private final CANSparkMax[] rightMotors;

    private final DifferentialDrive drivetrain;

    private final DoubleSolenoid doubleSolenoid;

    private final Encoder leftGrayhill;
    private final Encoder rightGrayhill;
    
    private Gear gear;

    public DrivetrainImpl() {

        leftMotors = new CANSparkMax[] {
            new CANSparkMax(Ports.Drivetrain.LEFT_TOP, MotorType.kBrushless),
            new CANSparkMax(Ports.Drivetrain.LEFT_MIDDLE, MotorType.kBrushless),
            new CANSparkMax(Ports.Drivetrain.LEFT_BOTTOM, MotorType.kBrushless),
        };

        for (CANSparkMax motor : leftMotors) {
            Motors.Drivetrain.LEFT.configure(motor);
        }

        rightMotors = new CANSparkMax[] {
            new CANSparkMax(Ports.Drivetrain.RIGHT_TOP, MotorType.kBrushless),
            new CANSparkMax(Ports.Drivetrain.RIGHT_MIDDLE, MotorType.kBrushless),
            new CANSparkMax(Ports.Drivetrain.RIGHT_BOTTOM, MotorType.kBrushless),
        };

        for (CANSparkMax motor : rightMotors) {
            Motors.Drivetrain.RIGHT.configure(motor);
        }

        drivetrain = new DifferentialDrive(new MotorControllerGroup(leftMotors), new MotorControllerGroup(rightMotors));

        doubleSolenoid = new DoubleSolenoid(PneumaticsModuleType.CTREPCM, 0, 1);

        leftGrayhill = new Encoder(Ports.Grayhill.LEFT_A, Ports.Grayhill.LEFT_B);
        rightGrayhill = new Encoder(Ports.Grayhill.RIGHT_A, Ports.Grayhill.RIGHT_B);

        leftGrayhill.setReverseDirection(
                Settings.Drivetrain.Encoders.GRAYHILL_INVERTED);
                
        rightGrayhill.setReverseDirection(
                Settings.Drivetrain.Encoders.GRAYHILL_INVERTED);
        
        rightGrayhill.setDistancePerPulse(Settings.Drivetrain.Encoders.GRAYHILL_DISTANCE_PER_PULSE);
        rightGrayhill.reset();
        
        leftGrayhill.setDistancePerPulse(Settings.Drivetrain.Encoders.GRAYHILL_DISTANCE_PER_PULSE);
        leftGrayhill.reset();
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
    public double getLeftDistance() {
        return leftGrayhill.getDistance();
    }

    public double getRightDistance() {
        return rightGrayhill.getDistance();
    }

    @Override
    public void arcadeDrive(double speed, double rotation) {
        drivetrain.arcadeDrive(speed, rotation, true);
    }

    @Override
    public void setVoltages(double leftVoltages, double rightVoltages) {
        for (CANSparkMax motor : leftMotors) {
            motor.setVoltage(leftVoltages);
        }

        for (CANSparkMax motor : rightMotors) {
            motor.setVoltage(rightVoltages);
        }
    }

    @Override
    public void setGear(Gear gear) {
        doubleSolenoid.set(gear.value);
        this.gear = gear;
    }
}


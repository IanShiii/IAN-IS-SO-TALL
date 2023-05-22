package com.stuypulse.robot.subsystems.drivetrain;
import com.stuypulse.robot.constants.Ports;
import com.stuypulse.stuylib.control.feedback.PIDController;
import com.stuypulse.stuylib.network.SmartNumber;
import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.PneumaticsModuleType;
import edu.wpi.first.wpilibj.DoubleSolenoid.Value;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj.motorcontrol.MotorControllerGroup;
import com.stuypulse.robot.constants.Settings;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;


public class DrivetrainImpl extends Drivetrain{
    
    //code ur drivetrain here :D
    //the ports for the motors are already in the ports file btw 

    private final CANSparkMax[] leftMotors;
    private final CANSparkMax[] rightMotors;
    
    private final DifferentialDrive differentialDrive;
    private final DoubleSolenoid doubleSolenoid;
    
    private final Encoder leftGrayhill;  
    private final Encoder rightGrayhill;

    private final SmartNumber targetDistance;
    private final PIDController leftController;
    private final PIDController rightController;

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

        differentialDrive = new DifferentialDrive(new MotorControllerGroup(leftMotors), new MotorControllerGroup(rightMotors));
        doubleSolenoid = new DoubleSolenoid(PneumaticsModuleType.CTREPCM, 0, 1);

        leftController = new PIDController(Settings.Drivetrain.Motion.PID.kP, Settings.Drivetrain.Motion.PID.kI, Settings.Drivetrain.Motion.PID.kD);
        rightController = new PIDController(Settings.Drivetrain.Motion.PID.kP, Settings.Drivetrain.Motion.PID.kI, Settings.Drivetrain.Motion.PID.kD);
        

        leftGrayhill = new Encoder(Ports.Grayhill.LEFT_A, Ports.Grayhill.LEFT_B);
        rightGrayhill = new Encoder(Ports.Grayhill.RIGHT_A, Ports.Grayhill.RIGHT_B);

        leftGrayhill.setReverseDirection(
                Settings.Drivetrain.Encoders.GRAYHILL_INVERTED);
                
        rightGrayhill.setReverseDirection(
                Settings.Drivetrain.Encoders.GRAYHILL_INVERTED);
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

    private double getLeftDistance() {
        return leftGrayhill.getDistance();
    }
    
    private double getRightDistance() {
        return rightGrayhill.getDistance();
    } 

    @Override
    public void setVoltages(double leftVoltages, double rightVoltages) {
        for (CANSparkMax motor : leftMotors) {
            motor.set(leftVoltages);
        }
        for (CANSparkMax motor : rightMotors) {
            motor.set(rightVoltages);
        }
    }

    public void setTargetDistance(double targetDistance) {
        this.targetDistance.set(targetDistance);
    }

    public double getTargetDistance() {
        return targetDistance.get();
    }

    @Override
    public final void periodic() {
        
        setVoltages(leftController.update(getTargetDistance(), getLeftDistance()), rightController.update(getTargetDistance(), getRightDistance()));

        SmartDashboard.putNumber("Target Distance", getLeftDistance());
    }
     
}


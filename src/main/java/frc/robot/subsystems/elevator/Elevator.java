package frc.robot.subsystems.elevator;

import frc.robot.utils.Config;
import frc.robot.utils.Constants.DriveConstants;

import com.revrobotics.spark.SparkClosedLoopController;
import com.revrobotics.spark.SparkMax;
import com.revrobotics.spark.SparkBase.ControlType;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import com.revrobotics.spark.SparkBase.PersistMode;
import com.revrobotics.spark.SparkBase.ResetMode;
import com.revrobotics.spark.SparkLowLevel.MotorType;
import com.revrobotics.AbsoluteEncoder;
import com.revrobotics.RelativeEncoder;

import edu.wpi.first.math.controller.ProfiledPIDController;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.DutyCycle;

public class Elevator extends SubsystemBase {
    // SparkMax Declaration
    public final SparkMax elevatorSparkMax1;
    public final SparkMax elevatorSparkMax2;
    
    // Encoder Declaration
    private final RelativeEncoder elevatorEncoder;
    
    public Elevator () { // Object Constructor
        // SparkMax Definitions
        elevatorSparkMax1 = new SparkMax(DriveConstants.elevator1CanId, MotorType.kBrushless);
        elevatorSparkMax2 = new SparkMax(DriveConstants.elevator2CanId, MotorType.kBrushless);
        
        // Encoder Definition
        elevatorEncoder = elevatorSparkMax1.getEncoder();
    }

    public void moveToPos (double targetPosInches) { // Moves the Elevator to the desired position.
        // Conversion between Rotations and Inches
        double rotationsPerInch = 1; // Conversion Constant
        double targetPos = targetPosInches / rotationsPerInch; // Conversion Equation
        
        // PID Controller
        double kp = 1; // Proportional
        double ki = 1; // Integral
        double kd = 1; // Derivative

        ProfiledPIDController elevatorPidController = new ProfiledPIDController(kp, ki, kd, null);
        
        // Uses above PID Controller to Run Motors so Elevator Ends up in the Desired Position
        elevatorSparkMax1.set(elevatorPidController.calculate(elevatorEncoder.getPosition(), targetPos));
        elevatorSparkMax2.set(-(elevatorPidController.calculate(elevatorEncoder.getPosition(), targetPos)));
    }

    public double getPos () { // Returns the current position of the elevator.
        double rotationsPerInch = 1; // Conversion Constant
        double currentPos = elevatorEncoder.getPosition() * rotationsPerInch; // Converts the Elevator's Position from Rotations to Inches
        return currentPos;
    }
}
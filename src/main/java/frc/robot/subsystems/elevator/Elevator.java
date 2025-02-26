package frc.robot.subsystems.elevator;

import frc.robot.utils.Config;
import frc.robot.utils.Constants.ElevatorConstants;

import com.revrobotics.spark.SparkBase;
import com.revrobotics.spark.SparkClosedLoopController;
import com.revrobotics.spark.SparkLowLevel;
import com.revrobotics.spark.SparkMax;
import com.revrobotics.spark.SparkBase.ControlType;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import com.revrobotics.spark.SparkBase.PersistMode;
import com.revrobotics.spark.SparkBase.ResetMode;
import com.revrobotics.spark.SparkLowLevel.MotorType;
import com.revrobotics.spark.config.SparkBaseConfig;
import com.revrobotics.spark.config.SparkMaxConfig;
import com.revrobotics.AbsoluteEncoder;
import com.revrobotics.RelativeEncoder;

import edu.wpi.first.math.controller.ProfiledPIDController;
import edu.wpi.first.networktables.GenericEntry;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.DutyCycle;
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.wpilibj.shuffleboard.ShuffleboardTab;

public class Elevator extends SubsystemBase {
    // SparkMax Declaration
    public final SparkMax elevatorSparkMax1;
    public final SparkMax elevatorSparkMax2;
    
    // Encoder Declaration
    private final RelativeEncoder elevatorEncoder;

    // Movement Variables so they can be edited by methods.
    public double targetPos = 0;
    public double manualSpeed = 1;

    public boolean manual = false;

    // Elevator States
    public ElevatorState currentState = ElevatorState.IDLE;

    public enum ElevatorState { // Target Position Presets
        IDLE,
        MOVING_TO_POS,
        MANUALLY_CONTROLLED,
        HOLD_POS,
        ERROR;
    }
    
    public Elevator () { // Object Constructor
        // SparkMax Definitions
        elevatorSparkMax1 = new SparkMax(ElevatorConstants.elevator1CanId, MotorType.kBrushless);
        elevatorSparkMax2 = new SparkMax(ElevatorConstants.elevator2CanId, MotorType.kBrushless);
        
        SparkMaxConfig elevatorConfig1 = new SparkMaxConfig();
        SparkMaxConfig elevatorConfig2 = new SparkMaxConfig();

        elevatorConfig1.encoder
            .positionConversionFactor(1 /*Placeholder*/);
        // elevatorConfig1.closedLoop
        //     .pid(0.1, 0, 0);
        elevatorConfig2
            .follow(ElevatorConstants.elevator1CanId)
            .inverted(true);

        elevatorSparkMax1.configure (
            elevatorConfig1, 
            ResetMode.kResetSafeParameters, 
            PersistMode.kPersistParameters);
        elevatorSparkMax2.configure (
            elevatorConfig2, 
            ResetMode.kResetSafeParameters,
            PersistMode.kPersistParameters);

        // Encoder Definition
        elevatorEncoder = elevatorSparkMax1.getEncoder();
    }

    @Override
    public void periodic () {
        if (currentState == ElevatorState.ERROR || currentState == ElevatorState.IDLE) {
            elevatorSparkMax1.stopMotor();
        }

        if (currentState == ElevatorState.MOVING_TO_POS || currentState == ElevatorState.HOLD_POS) {
            this.moveToPos(targetPos);
        }

        if (currentState == ElevatorState.MANUALLY_CONTROLLED) {
            this.manualMove(manualSpeed);
        }
    }
    
    public void requestElevatorState (ElevatorState requestedState, double... inputValue) {
        if (currentState != ElevatorState.ERROR) {
            switch (requestedState) {
                case IDLE:
                    currentState = ElevatorState.IDLE;
                case MOVING_TO_POS:
                    targetPos = inputValue[0];
                    currentState = ElevatorState.MOVING_TO_POS;
                case MANUALLY_CONTROLLED:
                    manualSpeed = inputValue[0];
                    currentState = ElevatorState.MANUALLY_CONTROLLED;
                case HOLD_POS:
                    targetPos = this.getPos();
                    currentState = ElevatorState.HOLD_POS;
                case ERROR:
                    currentState = ElevatorState.ERROR;
            }
        }
    }
    
    public void moveToPos (double targetPos) { // Moves the Elevator to the desired position.
        // PID Controller
        double kp = 0.1; // Proportional
        double ki = 0; // Integral
        double kd = 0; // Derivative

        ProfiledPIDController elevatorPidController = new ProfiledPIDController(kp, ki, kd, null);
        
        // Uses above PID Controller to Run Motors so Elevator Ends up in the Desired Position
        elevatorSparkMax1.set(elevatorPidController.calculate(elevatorEncoder.getPosition(), targetPos));

        if (Math.abs(targetPos - this.getPos()) < 0.5) {
            currentState = ElevatorState.HOLD_POS;
        }
    }

    public void manualMove (double speed) {
        // PID Controller
        double kp = 0.1; // Proportional
        double ki = 0; // Integral
        double kd = 0; // Derivative
        
        ProfiledPIDController elevatorPidController = new ProfiledPIDController(kp, ki, kd, null);
        
        elevatorSparkMax1.set(elevatorPidController.calculate(elevatorEncoder.getVelocity(), speed));
    }

    public double getPos () { // Returns the current position of the elevator.
        return elevatorEncoder.getPosition();
    }
}
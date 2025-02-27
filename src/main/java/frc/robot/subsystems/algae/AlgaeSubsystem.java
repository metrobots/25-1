package frc.robot.subsystems.algae;

import edu.wpi.first.math.controller.ProfiledPIDController;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

import com.revrobotics.RelativeEncoder;
import com.revrobotics.spark.SparkMax;
import com.revrobotics.spark.SparkBase.PersistMode;
import com.revrobotics.spark.SparkBase.ResetMode;

import com.revrobotics.spark.SparkLowLevel.MotorType;
import com.revrobotics.spark.config.SparkMaxConfig;

import frc.robot.utils.Constants.DriveConstants;

public class AlgaeSubsystem extends SubsystemBase {
    private final SparkMax intakeMotor;
    private final RelativeEncoder intakeEncoder;
    private final ProfiledPIDController pidController = new ProfiledPIDController(1.0, 0, 0, null);
    private AlgaeState currentState;
    private AlgaeState previousPositionState;

    public enum AlgaeState {
        IDLE,
        PICK_UP,
        SHOOT,
        ERROR,
    }

    public AlgaeSubsystem() {
        this.currentState = AlgaeState.IDLE;
        SparkMaxConfig intakeConfig = new SparkMaxConfig();
        intakeConfig.inverted(false); /* change in case of wrong direction */
        this.intakeMotor = new SparkMax(DriveConstants.topAlgaeCanId, MotorType.kBrushless);
        this.intakeMotor.configure(intakeConfig, ResetMode.kResetSafeParameters, PersistMode.kPersistParameters);
        this.intakeEncoder = intakeMotor.getAlternateEncoder();
    }

    public void setCurrentState(AlgaeState currentState) {
        this.currentState = currentState;
    }

    public AlgaeState getCurrentState() {
        return currentState;
    }

    public AlgaeState getPreviousPositionState() {
        return previousPositionState;
    }

    public void driveIntake(double speed) {
        final double MAX_RPM = 8912; // randomly chosen number
        double targetRPM = 3600; // randomly chosen number
        double targetSpeed = this.pidController.calculate(this.intakeEncoder.getVelocity(), targetRPM);
        targetSpeed = targetSpeed / (MAX_RPM + pidController.getVelocityError());
        this.intakeMotor.set(targetSpeed);
    }

    public void stopIntake() {
        this.intakeMotor.stopMotor();
    }

    @Override
    public void periodic() {
        switch (this.currentState) {
            case IDLE:
                this.intakeMotor.stopMotor();
                break;
            case PICK_UP:
                this.driveIntake(-1);
                break;
            case SHOOT:
                this.driveIntake(1);
                break;
            case ERROR:
                this.intakeMotor.stopMotor();
                break;
        }
    }
}

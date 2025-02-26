package frc.robot.subsystems.algae;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import com.revrobotics.spark.SparkMax;
import com.revrobotics.spark.SparkBase.PersistMode;
import com.revrobotics.spark.SparkBase.ResetMode;

import com.revrobotics.AbsoluteEncoder;
import com.revrobotics.spark.SparkLowLevel.MotorType;
import com.revrobotics.spark.config.SparkMaxConfig;

import frc.robot.utils.Constants.DriveConstants;

public class AlgaeSubsystem extends SubsystemBase {
    private final SparkMax intakeMotor;
    private final SparkMax pivotMotor;
    private final AbsoluteEncoder pivotEncoder;
    private AlgaeState currentState;
    private AlgaeState previousPositionState;

    public enum AlgaeState {
        IDLE,
        MOVE_DOWN,
        PICK_UP,
        MOVE_UP,
        SHOOT,
    }

    public AlgaeSubsystem() {
        this.currentState = AlgaeState.IDLE;
        this.previousPositionState = AlgaeState.MOVE_UP;
        SparkMaxConfig intakeConfig = new SparkMaxConfig();
        intakeConfig.inverted(false); /* change in case of wrong direction */
        this.intakeMotor = new SparkMax(DriveConstants.topAlgaeCanId, MotorType.kBrushless);
        this.intakeMotor.configure(intakeConfig, ResetMode.kResetSafeParameters, PersistMode.kPersistParameters);
        this.pivotMotor = new SparkMax(DriveConstants.algaePivotCanId, MotorType.kBrushless);
        this.pivotEncoder = pivotMotor.getAbsoluteEncoder();
    }

    public void setCurrentState(AlgaeState currentState) {
        this.currentState = currentState;
        if (currentState == AlgaeState.MOVE_DOWN || currentState == AlgaeState.MOVE_UP) {
            this.previousPositionState = currentState;
        }
    }

    public AlgaeState getCurrentState() {
        return currentState;
    }

    public AlgaeState getPreviousPositionState() {
        return previousPositionState;
    }

    public void driveIntake(double speed) {
        this.intakeMotor.set(speed);
    }

    public void drivePivot(double speed) {
        this.pivotMotor.set(speed);
    }

    public void stopIntake() {
        this.intakeMotor.stopMotor();
    }

    public void stopPivot() {
        this.pivotMotor.stopMotor();
    }

    public double getPivotPosition() {
        return this.pivotEncoder.getPosition();
    }

    @Override
    public void periodic() {
        switch (this.currentState) {
            case IDLE:
                this.pivotMotor.stopMotor();
                this.intakeMotor.stopMotor();
                break;
            case MOVE_DOWN:
                this.pivotMotor.set(0.5);
                break;
            case MOVE_UP:
                this.pivotMotor.set(-0.5);
                break;
            case PICK_UP:
                this.intakeMotor.set(-1);
                break;
            case SHOOT:
                this.intakeMotor.set(1);
                break;
        }
    }
}

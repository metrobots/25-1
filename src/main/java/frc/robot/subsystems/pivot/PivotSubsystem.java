package main.java.frc.robot.subsystems.pivot;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import com.revrobotics.spark.SparkMax;
import com.revrobotics.spark.SparkBase.PersistMode;
import com.revrobotics.spark.SparkBase.ResetMode;
import com.revrobotics.AbsoluteEncoder;
import com.revrobotics.spark.SparkLowLevel.MotorType;
import com.revrobotics.spark.config.SparkMaxConfig;

import frc.robot.subsystems.algae.AlgaeSubsystem.AlgaeState;
import frc.robot.utils.Constants.DriveConstants;

public class PivotSubsystem extends SubsystemBase{
    
    private final SparkMax pivotMotor;
    private final AbsoluteEncoder pivotEncoder;
    private PivotState currentState;

    public enum PivotState {
        IDLE,
        MOVE_DOWN,
        MOVE_UP,
        PICK_UP,
        SHOOT,
    }

    public PivotSubsystem() {
        currentState = PivotState.IDLE;
        this.pivotMotor = new SparkMax(DriveConstants.algaePivotCanId, MotorType.kBrushless);
        this.pivotEncoder = pivotMotor.getAbsoluteEncoder();
    }

    public void drivePivot(double speed) {
        this.pivotMotor.set(speed);
    }

    public void setCurrentState(PivotState currentState) {
        this.currentState = currentState;
    }

    public PivotState getCurrentState() {
        return currentState;
    }

    public void stopPivot() {
        this.pivotMotor.stopMotor();
    }

    public double getPivotPosition() {
        return this.pivotEncoder.getPosition();
    }

    public void periodic() {
        switch (this.getCurrentState()) {
            case IDLE:
                break;
            case MOVE_DOWN:
                /* Should probably add a stop when this hits the lowest point */
                this.drivePivot(0.5);
                break;
            case MOVE_UP:
                /* Should probably add a stop when this hits the lowest point */
                this.drivePivot(-0.5);
                break;
            case PICK_UP:
                this.driveIntake(0.5);
                break;
            case SHOOT:
                this.driveIntake(-0.5);
                break;
        }
    }
}
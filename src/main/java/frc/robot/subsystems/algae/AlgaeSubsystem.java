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
    private AlgaeState currentState;

    public enum AlgaeState {
        IDLE,
        PICK_UP,
        SHOOT,
    }

    public AlgaeSubsystem() {
        currentState = AlgaeState.IDLE;
        SparkMaxConfig intakeConfig = new SparkMaxConfig();
        intakeConfig.inverted(false); /* change in case of wrong direction */
        this.intakeMotor = new SparkMax(DriveConstants.topAlgaeCanId, MotorType.kBrushless);
        this.intakeMotor.configure(intakeConfig, ResetMode.kResetSafeParameters, PersistMode.kPersistParameters);
    }

    public void setCurrentState(AlgaeState currentState) {
        this.currentState = currentState;
    }

    public AlgaeState getCurrentState() {
        return currentState;
    }

    public void driveIntake(double speed) {
        this.intakeMotor.set(speed);
    }

    public void stopIntake() {
        this.intakeMotor.stopMotor();
    }

    @Override
    public void periodic() {
        switch (this.getCurrentState()) {
            case IDLE:
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

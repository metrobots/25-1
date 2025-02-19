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

    public enum AlgaeState {
        PICK_UP,
        SHOOT,
        NONE,
    }

    public AlgaeSubsystem() {
        currentState = AlgaeState.NONE;
        SparkMaxConfig intakeConfig = new SparkMaxConfig();
        intakeConfig.inverted(false); /* change in case of wrong direction */
        this.intakeMotor = new SparkMax(DriveConstants.topAlgaeCanId, MotorType.kBrushless);
        this.intakeMotor.configure(intakeConfig, ResetMode.kResetSafeParameters, PersistMode.kPersistParameters);
        this.pivotMotor = new SparkMax(DriveConstants.algaePivotCanId, MotorType.kBrushless);
        this.pivotEncoder = pivotMotor.getAbsoluteEncoder();
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
}

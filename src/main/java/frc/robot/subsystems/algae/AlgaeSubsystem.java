package frc.robot.subsystems.algae;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import com.revrobotics.spark.SparkMax;
import com.revrobotics.spark.SparkBase.ControlType;
import com.revrobotics.spark.SparkBase.PersistMode;
import com.revrobotics.spark.SparkBase.ResetMode;
import com.revrobotics.spark.SparkClosedLoopController;
import com.revrobotics.spark.SparkLowLevel.MotorType;
import com.revrobotics.spark.config.SparkMaxConfig;

import frc.robot.utils.Constants.DriveConstants;

public class AlgaeSubsystem extends SubsystemBase {

    private final SparkMax intakeMotor;
    private final SparkMax pivotMotor;

    public AlgaeSubsystem() {
        SparkMaxConfig intakeConfig = new SparkMaxConfig();
        intakeConfig.inverted(false); /* change in case of wrong direction */

        this.intakeMotor = new SparkMax(DriveConstants.topAlgaeCanId, MotorType.kBrushless);
        this.intakeMotor.configure(intakeConfig, ResetMode.kResetSafeParameters, PersistMode.kPersistParameters);

        this.pivotMotor = new SparkMax(DriveConstants.algaePivotCanId, MotorType.kBrushless);
    }

    public void driveInward() {
        this.intakeMotor.set(1.0);
    }

    public void driveOutward() {
        this.intakeMotor.set(-1.0);
    }

    public void stopDrive() {
        this.intakeMotor.stopMotor();
    }

}

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

    private final SparkMax topMotor;
    private final SparkMax bottomMotor;
    private final SparkMax pivotMotor;

    private final SparkClosedLoopController topMotorController;
    private final SparkClosedLoopController bottomMotorController;
    private final SparkClosedLoopController pivotMotorController;

    private final SparkMaxConfig motorConfig = new SparkMaxConfig();

    public AlgaeSubsystem(double pivotSpeed, double topMotorSpeed, double bottomMotorSpeed) {
        motorConfig.inverted(false);

        this.topMotor = new SparkMax(DriveConstants.topAlgaeCanId, MotorType.kBrushless);
        this.bottomMotor = new SparkMax(DriveConstants.bottomAlgaeCanId, MotorType.kBrushless);
        this.pivotMotor = new SparkMax(DriveConstants.algaePivotCanId, MotorType.kBrushless);

        this.topMotor.configure(motorConfig, ResetMode.kResetSafeParameters, PersistMode.kPersistParameters);

        this.topMotorController = topMotor.getClosedLoopController();
        this.bottomMotorController = bottomMotor.getClosedLoopController();
        this.pivotMotorController = pivotMotor.getClosedLoopController();

        this.topMotor.set(topMotorSpeed);
        this.bottomMotor.set(bottomMotorSpeed);
        this.pivotMotor.set(pivotSpeed);
    }

    public void driveOutward(double speed) {
        this.topMotorController.setReference(speed, ControlType.kVelocity);
        this.bottomMotorController.setReference(-speed, ControlType.kVelocity);
    }

    public void driveInward(double speed) {
        this.topMotorController.setReference(-speed, ControlType.kVelocity);
        this.bottomMotorController.setReference(speed, ControlType.kVelocity);
    }

    public void stopDrive() {
        this.topMotorController.setReference(0, ControlType.kVelocity);
        this.topMotorController.setReference(0, ControlType.kVelocity);
    }
}

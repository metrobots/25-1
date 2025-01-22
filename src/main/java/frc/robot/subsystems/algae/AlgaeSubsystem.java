package frc.robot.subsystems.algae;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import com.revrobotics.AbsoluteEncoder;
import com.revrobotics.spark.SparkMax;
import com.revrobotics.spark.SparkBase.ControlType;
import com.revrobotics.spark.SparkClosedLoopController;
import com.revrobotics.spark.SparkLowLevel.MotorType;

public class AlgaeSubsystem extends SubsystemBase {

    private final SparkMax topMotor;
    private final SparkMax bottomMotor;
    private final SparkMax pivotMotor;

    private final SparkClosedLoopController topMotorController;
    private final SparkClosedLoopController bottomMotorController;
    private final SparkClosedLoopController pivotMotorController;

    // Completely randomly chosen number. Will change later.
    private static final double motorSpeed = 20.0;

    public AlgaeSubsystem(int topMotorCanID, int bottomMotorCanID, int pivotCanID,
            double pivotSpeed, double topMotorSpeed, double bottomMotorSpeed) {

        // TODO: check if brushless.
        this.topMotor = new SparkMax(topMotorCanID, MotorType.kBrushless);
        this.bottomMotor = new SparkMax(bottomMotorCanID, MotorType.kBrushless);
        this.pivotMotor = new SparkMax(pivotCanID, MotorType.kBrushless);

        this.topMotorController = topMotor.getClosedLoopController();
        this.bottomMotorController = bottomMotor.getClosedLoopController();
        this.pivotMotorController = pivotMotor.getClosedLoopController();

        this.topMotor.set(topMotorSpeed);
        this.bottomMotor.set(bottomMotorSpeed);
        this.pivotMotor.set(pivotSpeed);
    }

    // TODO: name less weird.
    public void driveOutward() {
        this.topMotorController.setReference(motorSpeed, ControlType.kVelocity);
        this.bottomMotorController.setReference(motorSpeed, ControlType.kVelocity);
    }

    // TODO: name less weird.
    public void driveInward() {
        this.topMotorController.setReference(-motorSpeed, ControlType.kVelocity);
        this.bottomMotorController.setReference(-motorSpeed, ControlType.kVelocity);
    }
}

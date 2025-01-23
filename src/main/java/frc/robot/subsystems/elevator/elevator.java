package frc.robot.subsystems.elevator;

import frc.robot.utils.Config;

import com.revrobotics.spark.SparkClosedLoopController;
import com.revrobotics.spark.SparkMax;
import com.revrobotics.spark.SparkBase.ControlType;
import com.revrobotics.spark.SparkBase.PersistMode;
import com.revrobotics.spark.SparkBase.ResetMode;
import com.revrobotics.spark.SparkLowLevel.MotorType;
import com.revrobotics.AbsoluteEncoder;
import com.revrobotics.RelativeEncoder;

import edu.wpi.first.math.controller.ProfiledPIDController;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.DutyCycle;

public class Elevator {
    public final SparkMax elevatorSparkMax1;
    public final SparkMax elevatorSparkMax2;

    private final RelativeEncoder elevatorEncoder;

    public Elevator (int motor1CanId, int motor2CanId) {
        elevatorSparkMax1 = new SparkMax(motor1CanId, MotorType.kBrushless);
        elevatorSparkMax2 = new SparkMax(motor2CanId, MotorType.kBrushless);

        elevatorSparkMax2.setInverted(true);

        elevatorEncoder = elevatorSparkMax1.getEncoder();
    }

    public void moveToPos (double targetPosInches) {
        double rotationsPerInch = 1;
        // PID Constants YAYYYYYYYYYY
        double kp = 1;
        double ki = 1;
        double kd = 1;
        
        double targetPos = targetPosInches / rotationsPerInch;

        ProfiledPIDController elevatorPidController = new ProfiledPIDController(kp, ki, kd, null);

        elevatorSparkMax1.set(elevatorPidController.calculate(elevatorEncoder.getPosition(), targetPos));
        elevatorSparkMax2.set(elevatorPidController.calculate(elevatorEncoder.getPosition(), targetPos));
    }

    public double getPos () {
        double rotationsPerInch = 1; // Placeholder until we can test
        double currentPos = elevatorEncoder.getPosition() * rotationsPerInch;
        return currentPos;
    }
}
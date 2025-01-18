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
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.DutyCycle;

public class Elevator {
    public final SparkMax elevatorSparkMax1;
    public final SparkMax elevatorSparkMax2;

    private final RelativeEncoder elevatorEncoder;

    public Elevator (int motor1CanId, int motor2CanId, double targetPos) {
        elevatorSparkMax1 = new SparkMax(motor1CanId, MotorType.kBrushless);
        elevatorSparkMax2 = new SparkMax(motor2CanId, MotorType.kBrushless);

        elevatorEncoder = elevatorSparkMax1.getEncoder();
    }

    public double posConvert (double targetPosInches, double encoderValue, double conversionConstant) {
        double targetPosDegrees = targetPosInches * conversionConstant;
        double posDifference = targetPosDegrees - encoderValue;
        return posDifference;
    }

    public static void moveToPos () {
        double realTargetPos = Elevator.posConvert (Elevator.targetPos, elevatorEncoder, 1);
        
    }
}
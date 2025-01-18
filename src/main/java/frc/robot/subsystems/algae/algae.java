package frc.robot.subsystems.algae;

import com.revrobotics.AbsoluteEncoder;
import com.revrobotics.spark.SparkMax;
import com.revrobotics.spark.SparkLowLevel.MotorType;

public class Algae {

    /* The motor IDs are not currently defined in Constants.java, so... */
    private static final int MOTOR_ONE_ID = 0;

    private static SparkMax motor1;
    private static SparkMax motor2;
    private static SparkMax motor3;
    private static AbsoluteEncoder encoder;

    public Algae(int topMotorCanID, int bottomMotorCanID, int pivotCanID, double pivotSpeed, double topMotorSpeed, double bottomMotorSpeed) {
       
        motor1 = new SparkMax(topMotorCanID, MotorType.kBrushless);
        motor2 = new SparkMqx(bottomMotorCanID, MotorType.kBrushless);
        motor3 = new SparkMax(pivotCanID, MotorType.kBrushless);

        motor1.set(topMotorSpeed);
        motor2.set(bottomMotorSpeed);
        motor3.set(pivotSpeed);
    }
} 

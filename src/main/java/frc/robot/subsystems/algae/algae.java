package frc.robot.subsystems.algae;

import com.revrobotics.AbsoluteEncoder;
import com.revrobotics.spark.SparkMax;
import com.revrobotics.spark.SparkLowLevel.MotorType;

public class Algae {

    /* The motor IDs are not currently defined in Constants.java, so... */
    private static final int MOTOR_ONE_ID = 0;

    private static SparkMax motor1;
    private static AbsoluteEncoder encoder;

    public Algae() {
        motor1 = new SparkMax(MOTOR_ONE_ID, MotorType.kBrushless);
    }

    /**
     * ! UNIMPLEMENTED
     */
    public static void pickUpAlgae() {
    }

    /**
     * ! UNIMPLEMENTED
     */
    public static void shootAlgae() {

    }
}

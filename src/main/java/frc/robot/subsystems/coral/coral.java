package frc.robot.subsystems.coral;

import frc.robot.utils.Config;
import frc.robot.utils.Constants.DriveConstants;

import com.revrobotics.spark.SparkClosedLoopController;
import com.revrobotics.spark.SparkMax;
import com.revrobotics.spark.SparkBase.ControlType;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import com.revrobotics.spark.SparkBase.PersistMode;
import com.revrobotics.spark.SparkBase.ResetMode;
import com.revrobotics.spark.SparkLowLevel.MotorType;
import com.revrobotics.AbsoluteEncoder;
import com.revrobotics.RelativeEncoder;

import edu.wpi.first.math.controller.ProfiledPIDController;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.DutyCycle;

public class Coral extends SubsystemBase {
    // SparkMax Declarations
    public final SparkMax lCoral;
    public final SparkMax rCoral;

    public Coral () { // Object Constructor
        // SparkMax Definitions
        lCoral = new SparkMax (DriveConstants.lCoralCanId, MotorType.kBrushless);
        rCoral = new SparkMax (DriveConstants.rCoralCanId, MotorType.kBrushless);
    }

    public void run (double speed) { // Runs the motors in a direction at a specified speed
        lCoral.set(speed); // Sets the speed of the motor to a specified value
        rCoral.set(-speed); // Negative because motors need to run in opposite directions
    }
}

package frc.robot.subsystems.drivetrain;

import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.kinematics.SwerveModulePosition;
import edu.wpi.first.math.kinematics.SwerveModuleState;
import frc.robot.utils.Configs;

import com.revrobotics.spark.SparkClosedLoopController;
import com.revrobotics.spark.SparkMax;
import com.revrobotics.spark.SparkBase.ControlType;
import com.revrobotics.spark.SparkBase.PersistMode;
import com.revrobotics.spark.SparkBase.ResetMode;
import com.revrobotics.spark.SparkLowLevel.MotorType;
import com.revrobotics.RelativeEncoder;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.DutyCycle;

public class Module {
  private final SparkMax drivingSpark;
  private final SparkMax turningSpark;

  private final RelativeEncoder drivingEncoder;
  
  //Thrifty encoder setup
  private final DigitalInput encoderInput;
  private final DutyCycle dutyCycleEncoder;
  
  private final SparkClosedLoopController drivingClosedLoopController;
  private final SparkClosedLoopController turningClosedLoopController;

  private double m_chassisAngularOffset = 0;
  private SwerveModuleState m_desiredState = new SwerveModuleState(0.0, new Rotation2d());

  /**
   * Constructs a MK4i Swerve Module and configures the driving and turning motor,
   * encoder, and PID controller. This version is configured for L3 gear ratio and
   * Thrifty encoders.
   */
  public Module(int drivingCANId, int turningCANId, int encoderChannel, double chassisAngularOffset) {
    drivingSpark = new SparkMax(drivingCANId, MotorType.kBrushless);
    turningSpark = new SparkMax(turningCANId, MotorType.kBrushless);

    //Configure driving motor and encoder
    drivingEncoder = drivingSpark.getEncoder();
    
    //Configure Thrifty encoder for absolute position
    encoderInput = new DigitalInput(encoderChannel);
    dutyCycleEncoder = new DutyCycle(encoderInput);

    drivingClosedLoopController = drivingSpark.getClosedLoopController();
    turningClosedLoopController = turningSpark.getClosedLoopController();

    //Apply configurations to the SPARKs
    drivingSpark.configure(Configs.MK4iSwerveModule.drivingConfig, ResetMode.kResetSafeParameters,
        PersistMode.kPersistParameters);
    turningSpark.configure(Configs.MK4iSwerveModule.turningConfig, ResetMode.kResetSafeParameters,
        PersistMode.kPersistParameters);

    m_chassisAngularOffset = chassisAngularOffset;
    m_desiredState.angle = new Rotation2d(getAbsoluteEncoder());
    drivingEncoder.setPosition(0);
  }

  /**
   * Gets the absolute encoder angle in radians from the Thrifty encoder
   */
  private double getAbsoluteEncoder() {
    double angle = dutyCycleEncoder.getOutput() * 2.0 * Math.PI;
    angle -= m_chassisAngularOffset;
    return angle;
  }

  /**
   * Returns the current state of the module.
   *
   * @return The current state of the module.
   */
  public SwerveModuleState getState() {
    return new SwerveModuleState(
        drivingEncoder.getVelocity(),
        new Rotation2d(getAbsoluteEncoder())
    );
  }

  /**
   * Returns the current position of the module.
   *
   * @return The current position of the module.
   */
  public SwerveModulePosition getPosition() {
    return new SwerveModulePosition(
        drivingEncoder.getPosition(),
        new Rotation2d(getAbsoluteEncoder())
    );
  }

  /**
   * Sets the desired state for the module.
   *
   * @param desiredState Desired state with speed and angle.
   */
  public void setDesiredState(SwerveModuleState desiredState) {
    //Optimize the reference state to avoid spinning further than 90 degrees
    @SuppressWarnings("deprecation")
    SwerveModuleState optimizedState = SwerveModuleState.optimize(desiredState, 
        new Rotation2d(getAbsoluteEncoder()));

    //Command driving and turning motors
    drivingClosedLoopController.setReference(
        optimizedState.speedMetersPerSecond, 
        ControlType.kVelocity
    );
    
    turningClosedLoopController.setReference(
        optimizedState.angle.getRadians(), 
        ControlType.kPosition
    );

    m_desiredState = optimizedState;
  }

  /** Zeroes all the SwerveModule encoders. */
  public void resetEncoders() {
    drivingEncoder.setPosition(0);
  }
}
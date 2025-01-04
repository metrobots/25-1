// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

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
  private final SparkMax m_drivingSpark;
  private final SparkMax m_turningSpark;

  private final RelativeEncoder m_drivingEncoder;
  
  // Thrifty encoder setup
  private final DigitalInput m_encoderInput;
  private final DutyCycle m_dutyCycleEncoder;
  
  private final SparkClosedLoopController m_drivingClosedLoopController;
  private final SparkClosedLoopController m_turningClosedLoopController;

  private double m_chassisAngularOffset = 0;
  private SwerveModuleState m_desiredState = new SwerveModuleState(0.0, new Rotation2d());

  /**
   * Constructs a MK4i Swerve Module and configures the driving and turning motor,
   * encoder, and PID controller. This version is configured for L3 gear ratio and
   * Thrifty encoders.
   */
  public Module(int drivingCANId, int turningCANId, int encoderChannel, double chassisAngularOffset) {
    m_drivingSpark = new SparkMax(drivingCANId, MotorType.kBrushless);
    m_turningSpark = new SparkMax(turningCANId, MotorType.kBrushless);

    // Configure driving motor and encoder
    m_drivingEncoder = m_drivingSpark.getEncoder();
    
    // Configure Thrifty encoder for absolute position
    m_encoderInput = new DigitalInput(encoderChannel);
    m_dutyCycleEncoder = new DutyCycle(m_encoderInput);

    m_drivingClosedLoopController = m_drivingSpark.getClosedLoopController();
    m_turningClosedLoopController = m_turningSpark.getClosedLoopController();

    // Apply configurations to the SPARKs
    m_drivingSpark.configure(Configs.MK4iSwerveModule.drivingConfig, ResetMode.kResetSafeParameters,
        PersistMode.kPersistParameters);
    m_turningSpark.configure(Configs.MK4iSwerveModule.turningConfig, ResetMode.kResetSafeParameters,
        PersistMode.kPersistParameters);

    m_chassisAngularOffset = chassisAngularOffset;
    m_desiredState.angle = new Rotation2d(getAbsoluteEncoder());
    m_drivingEncoder.setPosition(0);
  }

  /**
   * Gets the absolute encoder angle in radians from the Thrifty encoder
   */
  private double getAbsoluteEncoder() {
    double angle = m_dutyCycleEncoder.getOutput() * 2.0 * Math.PI;
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
        m_drivingEncoder.getVelocity(),
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
        m_drivingEncoder.getPosition(),
        new Rotation2d(getAbsoluteEncoder())
    );
  }

  /**
   * Sets the desired state for the module.
   *
   * @param desiredState Desired state with speed and angle.
   */
  public void setDesiredState(SwerveModuleState desiredState) {
    // Optimize the reference state to avoid spinning further than 90 degrees
    @SuppressWarnings("deprecation")
    SwerveModuleState optimizedState = SwerveModuleState.optimize(desiredState, 
        new Rotation2d(getAbsoluteEncoder()));

    // Command driving and turning motors
    m_drivingClosedLoopController.setReference(
        optimizedState.speedMetersPerSecond, 
        ControlType.kVelocity
    );
    
    m_turningClosedLoopController.setReference(
        optimizedState.angle.getRadians(), 
        ControlType.kPosition
    );

    m_desiredState = optimizedState;
  }

  /** Zeroes all the SwerveModule encoders. */
  public void resetEncoders() {
    m_drivingEncoder.setPosition(0);
  }
}
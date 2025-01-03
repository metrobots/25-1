package frc.robot.utils;

import edu.wpi.first.math.geometry.Translation2d;
import edu.wpi.first.math.kinematics.SwerveDriveKinematics;
import edu.wpi.first.math.trajectory.TrapezoidProfile;
import edu.wpi.first.math.util.Units;

public final class Constants {
  public static final class DriveConstants {
    // Driving Parameters - Note that these are the allowed maximum speeds
    public static final double maxSpeedMetersPerSecond = 4.8; // Adjust based on preference
    public static final double maxAngularSpeed = 2 * Math.PI; // radians per second

    // Chassis configuration
    public static final double trackWidth = Units.inchesToMeters(26.5);
    public static final double wheelBase = Units.inchesToMeters(26.5);
    public static final SwerveDriveKinematics kDriveKinematics = new SwerveDriveKinematics(
        new Translation2d(wheelBase / 2, trackWidth / 2),
        new Translation2d(wheelBase / 2, -trackWidth / 2),
        new Translation2d(-wheelBase / 2, trackWidth / 2),
        new Translation2d(-wheelBase / 2, -trackWidth / 2));

    // Angular offsets of the modules relative to the chassis in radians
    // These values should be adjusted based on the Thrifty encoder readings
    public static final double frontLeftChassisAngularOffset = -Math.PI / 2;
    public static final double frontRightChassisAngularOffset = 0;
    public static final double backLeftChassisAngularOffset = Math.PI;
    public static final double backRightChassisAngularOffset = Math.PI / 2;

    // SPARK MAX CAN IDs
    public static final int frontLeftDrivingCanId = 11;
    public static final int rearLeftDrivingCanId = 13;
    public static final int frontRightDrivingCanId = 15;
    public static final int rearRightDrivingCanId = 17;

    public static final int frontLeftTurningCanId = 10;
    public static final int rearLeftTurningCanId = 12;
    public static final int frontRightTurningCanId = 14;
    public static final int rearRightTurningCanId = 16;

    // Thrifty Encoder DIO ports
    public static final int kFrontLeftEncoderPort = 0;
    public static final int kFrontRightEncoderPort = 1;
    public static final int kBackLeftEncoderPort = 2;
    public static final int kBackRightEncoderPort = 3;

    public static final boolean gyroReversed = false;
  }

  public static final class ModuleConstants {
    // MK4i Configuration - L3 Gear Ratio
    public static final double wheelDiameterMeters = 0.10033; // MK4i wheel diameter
    public static final double drivingMotorReduction = 6.12; // L3 ratio
    
    // Calculations required for driving motor conversion factors and feed forward
    public static final double drivingMotorFreeSpeedRps = NeoMotorConstants.kFreeSpeedRpm / 60;
    public static final double wheelCircumferenceMeters = wheelDiameterMeters * Math.PI;
    public static final double driveWheelFreeSpeedRps = (drivingMotorFreeSpeedRps * wheelCircumferenceMeters)
        / drivingMotorReduction;

    // Turn motor encoder resolution
    public static final double kTurningMotorReduction = 150.0/7.0; // MK4i turning reduction
    
    // Thrifty encoder constants
    public static final double kEncoderResolution = 1.0; // Full rotation
    public static final double kMinEncoderFrequency = 100.0; // Hz - for detecting disconnection
    public static final double kMaxEncoderFrequency = 1000.0; // Hz - typical working range
  }

  public static final class OIConstants {
    public static final int kDriverControllerPort = 0;
    public static final double kDriveDeadband = 0.05;
  }

  public static final class AutoConstants {
    public static final double kMaxSpeedMetersPerSecond = 4.75;
    public static final double kMaxAccelerationMetersPerSecondSquared = 3;
    public static final double kMaxAngularSpeedRadiansPerSecond = Math.PI;
    public static final double kMaxAngularSpeedRadiansPerSecondSquared = Math.PI;

    public static final double kPXController = 1;
    public static final double kPYController = 1;
    public static final double kPThetaController = 1;

    public static final TrapezoidProfile.Constraints kThetaControllerConstraints = new TrapezoidProfile.Constraints(
        kMaxAngularSpeedRadiansPerSecond, kMaxAngularSpeedRadiansPerSecondSquared);
  }

  public static final class NeoMotorConstants {
    public static final double kFreeSpeedRpm = 5676;
  }
}
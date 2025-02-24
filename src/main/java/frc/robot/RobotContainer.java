package frc.robot;

import edu.wpi.first.math.MathUtil;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.subsystems.algae.AlgaeSubsystem;
import frc.robot.subsystems.drivetrain.Drivetrain;
import frc.robot.subsystems.algae.AlgaeSubsystem;
import frc.robot.subsystems.algae.commands.ShootAlgae;
import frc.robot.subsystems.coral.Coral;
import frc.robot.subsystems.coral.commands.Intake;
import frc.robot.subsystems.coral.commands.Place;
import frc.robot.subsystems.elevator.Elevator;
import frc.robot.subsystems.elevator.commands.Ascend;
import frc.robot.subsystems.elevator.commands.Descend;
import frc.robot.subsystems.elevator.commands.Elevate;
import frc.robot.subsystems.climb.climb;
import frc.robot.utils.Constants.OIConstants;
import frc.robot.utils.Constants.DriveConstants;
import frc.robot.utils.ControllerKit;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.RunCommand;
import edu.wpi.first.wpilibj2.command.button.JoystickButton;
import com.pathplanner.lib.auto.AutoBuilder;

/*
 * This class is where the bulk of the robot should be declared.  Since Command-based is a
 * "declarative" paradigm, very little robot logic should actually be handled in the {@link Robot}
 * periodic methods (other than the scheduler calls).  Instead, the structure of the robot
 * (including subsystems, commands, and button mappings) should be declared here.
 */
public class RobotContainer {
  private final SendableChooser<Command> autoChooser;

  // The robot's subsystems
  private final Drivetrain drivetrain = new Drivetrain();
  private final AlgaeSubsystem algae = new AlgaeSubsystem();
  private final Coral coral = new Coral();
  private final Elevator elevator = new Elevator();

  // Make sure to add namedcommands to pathplanner here smthn like this ↓
  // NamedCommands.registerCommand("autoBalance", swerve.autoBalanceCommand());

  // The driver's controller
  ControllerKit primary = new ControllerKit(OIConstants.primaryPort);

  /**
   * The container for the robot. Contains subsystems, OI devices, and commands.
   */
  public RobotContainer() {

    autoChooser = AutoBuilder.buildAutoChooser();
    SmartDashboard.putData("Auto Chooser", autoChooser);

    // //Configure the button bindings
    // private void configureButtonBindings() {

    // }

    // Configure default commands
    drivetrain.setDefaultCommand(
        // The left stick controls translation of the robot.
        // Turning is controlled by the X axis of the right stick.
        new RunCommand(
            () -> drivetrain.drive(
                -MathUtil.applyDeadband(primary.getLeftY(), OIConstants.driveDeadband),
                -MathUtil.applyDeadband(primary.getLeftX(), OIConstants.driveDeadband),
                -MathUtil.applyDeadband(primary.getRightX(), OIConstants.driveDeadband),
                true),
            drivetrain));
  }

  /**
   * Use this method to define your button->command mappings. Buttons can be
   * created by
   * instantiating a {@link edu.wpi.first.wpilibj.GenericHID} or one of its
   * subclasses ({@link
   * edu.wpi.first.wpilibj.Joystick} or {@link XboxController}), and then calling
   * passing it to a
   * {@link JoystickButton}.
   */
  private void configureButtonBindings() {
    // Elevator
    primary.povLeft().whileTrue(new Elevate(1)); // Feed Station
    primary.povUp().whileTrue(new Elevate(2)); // Reef Level 2
    primary.povRight().whileTrue(new Elevate(3)); // Reef Level 3
    primary.povDown().whileTrue(new Elevate(4)); // Reef Level 4

    primary.rightBumper().whileTrue(new Ascend());
    primary.leftBumper().whileTrue(new Descend());
  }

  /**
   * Use this to pass the autonomous command to the main {@link Robot} class.
   *
   * @return the command to run in autonomous
   */
  public Command getAutonomousCommand() {
    return autoChooser.getSelected();
  }
}

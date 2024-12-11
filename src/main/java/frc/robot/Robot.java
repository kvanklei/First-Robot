// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkBase.IdleMode;
import com.revrobotics.CANSparkLowLevel.MotorType;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.button.JoystickButton;

/**
 * The VM is configured to automatically run this class, and to call the functions corresponding to
 * each mode, as described in the TimedRobot documentation. If you change the name of this class or
 * the package after creating this project, you must also update the build.gradle file in the
 * project.
 */
public class Robot extends TimedRobot {
  private static final String kDefaultAuto = "Default";
  private static final String kCustomAuto = "My Auto";
  private String m_autoSelected;
  private final SendableChooser<String> m_chooser = new SendableChooser<>();

  //defining drivetrain motors
  //front right motor
  private CANSparkMax r_motor1 = new CANSparkMax(0, MotorType.kBrushless);
  // back right motor 
  private CANSparkMax r_motor2 = new CANSparkMax(1, MotorType.kBrushless);
  //front left motor
  private CANSparkMax l_motor1 = new CANSparkMax(2, MotorType.kBrushless);
  //back left motor
  private CANSparkMax l_motor2 = new CANSparkMax(3,MotorType.kBrushless);

  //pivot motor definiton 
  private CANSparkMax pivot_motor = new CANSparkMax(4, MotorType.kBrushless);


  //Joysticks 
  // button to make the pivot go up 
  private Joystick r_joystick = new Joystick (1);
  // button to make the pivot go down 
  private Joystick l_joystick = new Joystick (0);

  // pivot joystick buttons 
  private JoystickButton pivot_up = new JoystickButton(r_joystick,2);
  private JoystickButton pivot_down = new JoystickButton(r_joystick,3);

  /**
   * This function is run when the robot is first started up and should be used for any
   * initialization code.
   */
  @Override
  public void robotInit() {
    m_chooser.setDefaultOption("Default Auto", kDefaultAuto);
    m_chooser.addOption("My Auto", kCustomAuto);
    SmartDashboard.putData("Auto choices", m_chooser);

    //setting idlemode
    r_motor1.setIdleMode(IdleMode.kBrake);
    r_motor2.setIdleMode(IdleMode.kBrake);
    l_motor1.setIdleMode(IdleMode.kBrake);
    l_motor2.setIdleMode(IdleMode.kBrake);
    //pivot motor idlemode
    pivot_motor.setIdleMode(IdleMode.kBrake);

    //inverting motors 
    //r_motor1.setInverted(true);
   // l_motor1.setInverted(true);
   //pivot_motor.setInverted(true);


    //make back right motor follow front right motor 
    r_motor2.follow(r_motor1);
    // make back left motor follow front right motor 
    l_motor2.follow(l_motor1); 
  }


  /**
   * This function is called every 20 ms, no matter the mode. Use this for items like diagnostics
   * that you want ran during disabled, autonomous, teleoperated and test.
   *
   * <p>This runs after the mode specific periodic functions, but before LiveWindow and
   * SmartDashboard integrated updating.
   */
  @Override
  public void robotPeriodic() {}

  /**
   * This autonomous (along with the chooser code above) shows how to select between different
   * autonomous modes using the dashboard. The sendable chooser code works with the Java
   * SmartDashboard. If you prefer the LabVIEW Dashboard, remove all of the chooser code and
   * uncomment the getString line to get the auto name from the text box below the Gyro
   *
   * <p>You can add additional auto modes by adding additional comparisons to the switch structure
   * below with additional strings. If using the SendableChooser make sure to add them to the
   * chooser code above as well.
   */
  @Override
  public void autonomousInit() {
    m_autoSelected = m_chooser.getSelected();
    // m_autoSelected = SmartDashboard.getString("Auto Selector", kDefaultAuto);
    System.out.println("Auto selected: " + m_autoSelected);
  }

  /** This function is called periodically during autonomous. */
  @Override
  public void autonomousPeriodic() {
    switch (m_autoSelected) {
      case kCustomAuto:
        // Put custom auto code here
        break;
      case kDefaultAuto:
      default:
        // Put default auto code here
        break;
    }
  }

  /** This function is called once when teleop is enabled. */
  @Override
  public void teleopInit() {}

  /** This function is called periodically during operator control. */
  @Override
  public void teleopPeriodic() {
    // tank drive 
    // set motor speeds
   // r_motor1.set(r_joystick.getY());
    //l_motor1.set(l_joystick.getY());

    //arcade
    r_motor1.set(l_joystick.getY()-r_joystick.getX());
    l_motor1.set(l_joystick.getY()+r_joystick.getX());

    if (pivot_up.getAsBoolean()) {
      pivot_motor.set(0.5);
    } else if (pivot_down.getAsBoolean()) {
        pivot_motor.set(-0.5);
    } else {
          pivot_motor.set(0);
      }
    }
  /** This function is called once when the robot is disabled. */
  @Override
  public void disabledInit() {}

  /** This function is called periodically when disabled. */
  @Override
  public void disabledPeriodic() {}

  /** This function is called once when test mode is enabled. */
  @Override
  public void testInit() {}

  /** This function is called periodically during test mode. */
  @Override
  public void testPeriodic() {}

  /** This function is called once when the robot is first started up. */
  @Override
  public void simulationInit() {}

  /** This function is called periodically whilst in simulation. */
  @Override
  public void simulationPeriodic() {}
}

/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

import com.ctre.phoenix.sensors.PigeonIMU;

import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj.Timer;
import frc.robot.Auton;

/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the TimedRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the build.gradle file in the
 * project.
 */
public class Robot extends TimedRobot {
  private static final String kDefaultAuto = "Default";
  private static final String kCustomAuto1 = "AutoPos1";
  private static final String kCustomAuto2 = "AutoPos2";
  private static final String kCustomAuto3 = "AutoPos3";
  private String m_autoSelected;
  private final SendableChooser<String> m_chooser = new SendableChooser<>();
  private static DriveTrain driveTrain;
  private static Ingester ingester;
  private static ColorWheel colorWheel;
  private static Climber climber;
  private static PID pid;
  private static SkyWalker skywalker;
  private static Auton auton;
  private PigeonIMU m_gyro;
  public static final Timer m_timer = new Timer();

  /**
   * This function is run when the robot is first started up and should be
   * used for any initialization code.
   */
  @Override
  public void robotInit() {
    m_chooser.setDefaultOption("Default Auto", kDefaultAuto);
    m_chooser.addOption("AutoPos1", kCustomAuto1);
    m_chooser.addOption("AutoPos2", kCustomAuto2);
    m_chooser.addOption("AutoPos3", kCustomAuto3);
    SmartDashboard.putData("Auto choices", m_chooser);

    driveTrain = new DriveTrain();
    this.m_gyro = new PigeonIMU(driveTrain.m_leftRrMotor);
    ingester = new Ingester();
    colorWheel = new ColorWheel();
    climber = new Climber();
    pid = new PID();
    skywalker = new SkyWalker();
    auton = new Auton();
  }

  /**
   * This function is called every robot packet, no matter the mode. Use
   * this for items like diagnostics that you want ran during disabled,
   * autonomous, teleoperated and test.
   *
   * <p>This runs after the mode specific periodic functions, but before
   * LiveWindow and SmartDashboard integrated updating.
   */
  @Override
  public void robotPeriodic() {
  }

  /**
   * This autonomous (along with the chooser code above) shows how to select
   * between different autonomous modes using the dashboard. The sendable
   * chooser code works with the Java SmartDashboard. If you prefer the
   * LabVIEW Dashboard, remove all of the chooser code and uncomment the
   * getString line to get the auto name from the text box below the Gyro
   *
   * <p>You can add additional auto modes by adding additional comparisons to
   * the switch structure below with additional strings. If using the
   * SendableChooser make sure to add them to the chooser code above as well.
   */
  @Override
  public void autonomousInit() {
    m_autoSelected = m_chooser.getSelected();
    System.out.println("Auto selected: " + m_autoSelected);

    m_timer.reset();
    m_timer.start();
    m_gyro.setFusedHeading(0);
  }

  /**
   * This function is called periodically during autonomous.
   */
  @Override
  public void autonomousPeriodic() {
    System.out.println("In autonomousPeriodic: " + m_autoSelected);
    switch (m_autoSelected) {
      case kCustomAuto1:
        // Put custom auto code here
        System.out.println("In autonomousPeriodic-1: " + m_autoSelected);
        auton.AutonPos1Run();
        break;
      case kCustomAuto2:
        // Put custom auto code here
        System.out.println("In autonomousPeriodic-2: " + m_autoSelected);
        auton.AutonPos2Run();
        
        break;
      case kDefaultAuto:
      default:
        // Put default auto code here
        break;
    }
    
  }

  @Override
  public void teleopInit() {
    
      climber.climberInit();
      pid.pidControl();
      colorWheel.colorInit();
  }

  /**
   * This function is called periodically during operator control.
   */
  @Override
  public void teleopPeriodic() {

      driveTrain.driveTank();
      colorWheel.colorWheelSpin();
      climber.climber();
      ingester.ingesterSweep();
      pid.commonLoop();
      skywalker.SkyWalk();

  }

  @Override
  public void testPeriodic() {
    
  }

}

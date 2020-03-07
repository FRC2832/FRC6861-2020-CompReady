/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

import edu.wpi.cscore.UsbCamera;
import edu.wpi.first.cameraserver.CameraServer;

//import com.ctre.phoenix.sensors.PigeonIMU;

import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the TimedRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the build.gradle file in the
 * project.
 */
public class Robot extends TimedRobot {
  private static final String kDefaultAuto = "Default - Move 2s";
  private static final String kCustomAuto1 = "Front of Goal";
  private static final String kCustomAuto2 = "Left of Goal";
  private static final String kCustomAuto3 = "Right of Goal";
  private String m_autoSelected;
  private final SendableChooser<String> m_chooser = new SendableChooser<>();
  private static DriveTrain driveTrain;
  private static Ingester ingester;
  private static ColorWheel colorWheel;
  private static Climber climber;
  private static PID pid;
  private static SkyWalker skywalker;
  private static Auton auton;
  private static UsbCamera piCam1;
  private static UsbCamera piCam2;
  //private PigeonIMU m_gyro;

  /**
   * This function is run when the robot is first started up and should be
   * used for any initialization code.
   */
  @Override
  public void robotInit() {
    m_chooser.setDefaultOption("Default 2s Move", kDefaultAuto);
    m_chooser.addOption("Front of Goal", kCustomAuto1);
    m_chooser.addOption("Left of Goal", kCustomAuto2);
    m_chooser.addOption("Right of Goal", kCustomAuto3);
    SmartDashboard.putData("Auto Choices", m_chooser);


    driveTrain = new DriveTrain();
    //this.m_gyro = new PigeonIMU(driveTrain.m_leftRrMotor);
    ingester = new Ingester();
    colorWheel = new ColorWheel();
    climber = new Climber();
    pid = new PID();
    skywalker = new SkyWalker();
    auton = new Auton(driveTrain);
    piCam1 = CameraServer.getInstance().startAutomaticCapture(0);
    piCam2 = CameraServer.getInstance().startAutomaticCapture(1);
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
    auton.autonInit();
    //m_gyro.setFusedHeading(0);
  }

  /**
   * This function is called periodically during autonomous.
   */
  @Override
  public void autonomousPeriodic() {
    System.out.println("Autonomous Periodic: " + m_autoSelected);
    switch (m_autoSelected) {
      case kCustomAuto1:
        // Put custom auto code here
        System.out.println("Front of Goal: " + m_autoSelected);
        auton.autonFrontGoal();

        break;

      case kCustomAuto2:
        // Put custom auto code here
        System.out.println("Left of Goal: " + m_autoSelected);
        auton.autonLeftGoal();
        
        break;

        case kCustomAuto3:
        // Put custom auto code here
        System.out.println("Right of Goal: " + m_autoSelected);
        auton.autonRightGoal();
        
        break;

      case kDefaultAuto:
      default:
        // Put default auto code here
        System.out.println("Move 2 Seconds: " + m_autoSelected);
        auton.autonMove2Sec();
        
        break;
    }
    
  }

  /**
   * This function is called once each time the robot enters teleoperated mode.
   */
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

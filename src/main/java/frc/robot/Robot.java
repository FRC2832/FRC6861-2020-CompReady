
package frc.robot;

import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj.Timer;


public class Robot extends TimedRobot {
  private static final String kDefaultAuto = "Default";
  private static final String kCustomAuto = "My Auto";
  private String m_autoSelected;
  private final SendableChooser<String> m_chooser = new SendableChooser<>();
  private static DriveTrain driveTrain;
  private static Ingester ingester;
  private static ColorWheel colorWheel;
  private static Climber climber;
  private static PID pid;
  private static SkyWalker skywalker;

  private final Timer m_timer = new Timer();

  private int count = 1;

  private double stepTime1 = 4.0;
  private double stepTime2 = 1.0;
  private double stepTime3 = 4.0;

  @Override
  public void robotInit() {
    m_chooser.setDefaultOption("Default Auto", kDefaultAuto);
    m_chooser.addOption("My Auto", kCustomAuto);
    SmartDashboard.putData("Auto choices", m_chooser);
    driveTrain = new DriveTrain();
    ingester = new Ingester();
    colorWheel = new ColorWheel();
    climber = new Climber();
    pid = new PID();
    skywalker = new SkyWalker();
  }
  
  @Override
  public void robotPeriodic() {
  }

  @Override
  public void autonomousInit() {
    m_autoSelected = m_chooser.getSelected();
    m_autoSelected = SmartDashboard.getString("Auto Selector", kDefaultAuto);
    System.out.println("Auto selected: " + m_autoSelected);
    m_timer.reset();
    m_timer.start();
  }

  @Override
  public void autonomousPeriodic() {
    
   /* switch (m_autoSelected) {
      case kCustomAuto:*/
      if ((m_timer.get() < stepTime1) && (count==1)) {
        driveTrain.driveArcade(0.7, 0.0);
      } else if ((m_timer.get() > stepTime1) && (count==1)) {
        driveTrain.driveArcade(0, 0);
        count = 2;
        m_timer.reset();
        m_timer.start(); 
      }
  
      if ((m_timer.get() < stepTime2) && (count==2)) {
        ingester.ingesterAuton(-1.0);
      } else if ((m_timer.get() > stepTime2) && (count==2)) {
        ingester.ingesterAuton(0.0);
        count = 3;
        m_timer.reset();
        m_timer.start();
      }
  
      if ((m_timer.get() < stepTime3) && (count==3)) {
        driveTrain.driveArcade(-0.6, 0.3);
      } else if ((m_timer.get() > stepTime3) && (count==3)) {
        driveTrain.driveArcade(0, 0);
        count = 4;
        m_timer.reset();
        m_timer.start();
      }
        /*break;
      case kDefaultAuto:
      default:

        break;
    }*/
    
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


  @Override
  public void teleopPeriodic() {

      driveTrain.driveTank();
      colorWheel.colorWheelSpin();
      climber.climber();
      ingester.ingesterSweep();
      pid.commonLoop();
      skywalker.SkyWalk();
  }

    /**
   * This function is called periodically during test mode.
   */
  @Override
  public void testPeriodic() {
  }
}

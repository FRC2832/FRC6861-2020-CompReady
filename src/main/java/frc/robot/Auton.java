package frc.robot;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.DriveTrain;
import frc.robot.Ingester;

import com.ctre.phoenix.sensors.PigeonIMU;

public class Auton {
    private static Timer m_timer = new Timer();
    private DriveTrain driveTrain;
    private Ingester ingester = new Ingester();

    private PigeonIMU m_gyro;

    private double stepTimeA1 = 4.0;
    private double stepTimeA2 = 1.0;
    private double stepTimeA3 = 3.0;

    private double stepTimeB1 = 1.5;
    private double stepTimeB3 = 2.0;
    private double stepTimeB5 = 2.5;
    private double stepTimeB6 = 1.0;
    private double stepTimeB7 = 3.0;

    private double stepTimeC1 = 2.0; // Default Move Time
    private double stepTimeC2 = 1.5;
    private double stepTimeC3 = 2.0;

    private static boolean move1SecDone = true;
    private static boolean moveHalfSecDone = true;
    private static boolean isAutonDone;
    private static boolean isScoreReady;
    private static boolean putIngesterDown = true;

    private int driveStep = 1;
    private static int autonStep = 1;

    public Auton(DriveTrain driveTrain) {
        this.driveTrain = driveTrain;
        this.m_gyro = new PigeonIMU(driveTrain.m_leftRrMotor);
    }

    public void autonInit() {
        m_gyro.setFusedHeading(0);
        driveStep = 1;
        m_timer.reset();
        m_timer.start();
        SmartDashboard.putNumber("Gyro Fused Heading", m_gyro.getFusedHeading());
        isAutonDone = false;
    }

    // Autonomous mode: Robot positioned anywhere on the line
    // Move forward for a set amount of time
    public void autonMove2Sec() {
        double timerValue = m_timer.get();
        SmartDashboard.putString("Auton Mode", "autonMove2Sec");
        SmartDashboard.putNumber("Auton Step", driveStep);
        SmartDashboard.putNumber("Gyro Fused Heading", m_gyro.getFusedHeading());
        SmartDashboard.putNumber("Timer", timerValue);
        if ((timerValue < stepTimeC1) && (driveStep == 1)) {
            driveTrain.driveArcade(0.6, 0.0);
        } else if ((timerValue > stepTimeC1) && (driveStep == 1)) {
            driveTrain.driveArcade(0, 0);
            driveStep = 2; // increment driveStep counter, move to next driveStep
            m_timer.reset();
            m_timer.start();
        } else {
            driveTrain.driveArcade(0, 0);
        }
    }

    public void move1Sec() {
        double timerValue = m_timer.get();
        SmartDashboard.putString("Auton Mode", "move1Sec");
        SmartDashboard.putNumber("Auton Step", driveStep);
        SmartDashboard.putNumber("Gyro Fused Heading", m_gyro.getFusedHeading());
        SmartDashboard.putNumber("Timer", timerValue);
        if ((timerValue < stepTimeC2) && (driveStep == 1)) {
            driveTrain.driveArcade(0.5, 0.0);
        } else if ((timerValue > stepTimeC2) && (driveStep == 1)) {
            driveTrain.driveArcade(0, 0);
            driveStep = 2; // increment driveStep counter, move to next driveStep
            // Auton.setMove1SecDone(true); // TODO: is this right?
            m_timer.reset();
            m_timer.start();
        } else {
            driveTrain.driveArcade(0, 0);
            System.out.println("move1SecDone = true");
            Auton.setMove1SecDone(true);
            driveStep = 1;
            m_timer.reset();
            m_timer.start();
        }
    }

    public void moveHalfSec() {
        double timerValue = m_timer.get();
        SmartDashboard.putString("Auton Mode", "move1Sec");
        SmartDashboard.putNumber("Auton Step", driveStep);
        SmartDashboard.putNumber("Gyro Fused Heading", m_gyro.getFusedHeading());
        SmartDashboard.putNumber("Timer", timerValue);
        if ((timerValue < stepTimeC3) && (driveStep == 1)) {
            driveTrain.driveArcade(0.6, 0.0);
        } else if ((timerValue > stepTimeC3) && (driveStep == 1)) {
            driveTrain.driveArcade(0, 0);
            driveStep = 2; // increment driveStep counter, move to next driveStep
            // Auton.setMove1SecDone(true); // TODO: is this right?
            m_timer.reset();
            m_timer.start();
        } else {
            driveTrain.driveArcade(0, 0);
            System.out.println("moveHalfSecDone = true");
            Auton.setMoveHalfSecDone(true);
            Auton.setIsScoreReady(true);
            driveStep = 1;
            m_timer.reset();
            m_timer.start();
        }
    }

    public void score() {
        double timerValue = m_timer.get();
        if ((timerValue < stepTimeC3) && (driveStep == 1)) {
            ingester.ingesterAuton(-1.0);
        } else if ((timerValue > stepTimeC3) && (driveStep == 1)) {
            ingester.ingesterAuton(0.0);
            driveStep = 2; // increment driveStep counter, move to next driveStep
            // Auton.setMove1SecDone(true); // TODO: is this right?
            m_timer.reset();
            m_timer.start();
        } else {
            ingester.ingesterAuton(0.0);
            Auton.setIsScoreReady(false);
            isAutonDone = true;
            driveStep = 1;
            m_timer.reset();
            m_timer.start();
        }
    }

    public static void setMove1SecDone(boolean done) {
        move1SecDone = done;
    }

    public static boolean getMove1SecDone() {
        return move1SecDone;
    }

    public static void setMoveHalfSecDone(boolean done) {
        moveHalfSecDone = done;
    }

    public static boolean getMoveHalfSecDone() {
        return moveHalfSecDone;
    }

    public static boolean getIsAutonDone() {
        return isAutonDone;
    }

    public static boolean getIsScoreReady() {
        return isScoreReady;
    }

    public static void setIsScoreReady(boolean done) {
        isScoreReady = done;
    }

    public static int getAutonStep() {
        return autonStep;
    }

    public static boolean getPutIngesterDown() {
        return putIngesterDown;
    }

    // Autonomous mode: Robot positioned directly in front of the scoring goal
    // Move fwd to goal, score, get out of the way
    public void autonFrontGoal() {
        SmartDashboard.putString("Auton Mode", "autonFrontGoal");
        SmartDashboard.putNumber("Auton Step", driveStep);
        SmartDashboard.putNumber("Gyro Fused Heading", m_gyro.getFusedHeading());
        SmartDashboard.putNumber("Timer", m_timer.get());
        if ((m_timer.get() < stepTimeA1) && (driveStep == 1)) {
            driveTrain.driveArcade(0.6, 0.0);
        } else if ((m_timer.get() > stepTimeA1) && (driveStep == 1)) {
            driveTrain.driveArcade(0, 0);
            driveStep = 2; // increment driveStep counter, move to next driveStep
            m_timer.reset();
            m_timer.start();
        } else {
            driveTrain.driveArcade(0, 0);
        }

        if ((m_timer.get() < stepTimeA2) && (driveStep == 2)) {
            ingester.ingesterAuton(-1.0);
        } else if ((m_timer.get() > stepTimeA2) && (driveStep == 2)) {
            ingester.ingesterAuton(0.0);
            driveStep = 3; // increment driveStep counter, move to next driveStep
            m_timer.reset();
            m_timer.start();
        }

        if ((m_timer.get() < stepTimeA3) && (driveStep == 3)) {
            driveTrain.driveArcade(-0.6, 0.45);
        } else if ((m_timer.get() > stepTimeA3) && (driveStep == 3)) {
            driveTrain.driveArcade(0, 0);
            driveStep = 4; // increment driveStep counter, move to next driveStep
            m_timer.reset();
            m_timer.start();
        } else {
            driveTrain.driveArcade(0, 0);
        }
    }

    // Autonomous mode: Robot positioned just left of the scoring goal
    // Move forward, turn right, move forward, move left, move fwd to goal, score,
    // get out of the way
    public void autonLeftGoal() {
        SmartDashboard.putString("Auton Mode", "autonLeftGoal");
        SmartDashboard.putNumber("Auton Step", driveStep);
        SmartDashboard.putNumber("Gyro Fused Heading", m_gyro.getFusedHeading() % 360);
        SmartDashboard.putNumber("Timer", m_timer.get());
        if ((m_timer.get() < stepTimeB1) && (driveStep == 1)) {
            driveTrain.driveArcade(0.6, 0.0);
        } else if ((m_timer.get() > stepTimeB1) && (driveStep == 1)) {
            driveTrain.driveArcade(0, 0);
            driveStep = 2; // increment driveStep counter, move to next driveStep
            m_timer.reset();
            m_timer.start();
        } else {
            driveTrain.driveArcade(0, 0);
        }

        if (driveStep == 2) {
            driveTrain.driveArcade(0.3, 0.6);
        }

        if (((m_gyro.getFusedHeading() % 360) < -85) && (driveStep == 2)) {
            driveTrain.driveArcade(0, 0);
            driveStep = 3; // increment driveStep counter, move to next driveStep
            m_timer.reset();
            m_timer.start();
        } else {
            driveTrain.driveArcade(0, 0);
        }

        if ((m_timer.get() < stepTimeB3) && (driveStep == 3)) {
            driveTrain.driveArcade(0.5, 0.0);
        } else if ((m_timer.get() > stepTimeB3) && (driveStep == 3)) {
            ingester.ingesterAuton(0.0);
            driveStep = 4; // increment driveStep counter, move to next driveStep
            m_timer.reset();
            m_timer.start();
        }

        if (driveStep == 4) {
            driveTrain.driveArcade(0.3, -0.6);
        }

        if (((m_gyro.getFusedHeading() % 360) > -5) && (driveStep == 4)) {
            driveTrain.driveArcade(0, 0);
            driveStep = 5; // increment driveStep counter, move to next driveStep
            m_timer.reset();
            m_timer.start();
        } else {
            driveTrain.driveArcade(0, 0);
        }

        if ((m_timer.get() < stepTimeB5) && (driveStep == 5)) {
            driveTrain.driveArcade(0.6, 0);
        } else if ((m_timer.get() > stepTimeB5) && (driveStep == 5)) {
            driveTrain.driveArcade(0, 0);
            driveStep = 6; // increment driveStep counter, move to next driveStep
            m_timer.reset();
            m_timer.start();
        } else {
            driveTrain.driveArcade(0, 0);
        }

        if ((m_timer.get() < stepTimeB6) && (driveStep == 6)) {
            ingester.ingesterAuton(-1.0);
        } else if ((m_timer.get() > stepTimeB6) && (driveStep == 6)) {
            ingester.ingesterAuton(0.0);
            driveStep = 7; // increment driveStep counter, move to next driveStep
            m_timer.reset();
            m_timer.start();
        }

        if ((m_timer.get() < stepTimeB7) && (driveStep == 7)) {
            driveTrain.driveArcade(-0.6, 0.3);
        } else if ((m_timer.get() > stepTimeB7) && (driveStep == 7)) {
            driveTrain.driveArcade(0, 0);
            driveStep = 8; // increment driveStep counter, move to next driveStep
            m_timer.reset();
            m_timer.start();
        } else {
            driveTrain.driveArcade(0, 0);
        }
    }

    // Autonomous mode: Robot positioned just right of the scoring goal
    // Move forward, turn left, move forward, move right, move fwd to goal, score,
    // get out of the way
    public void autonRightGoal() {
        SmartDashboard.putString("Auton Mode", "autonRightGoal");
        SmartDashboard.putNumber("Auton Step", driveStep);
        SmartDashboard.putNumber("Gyro Fused Heading", m_gyro.getFusedHeading() % 360);
        SmartDashboard.putNumber("Timer", m_timer.get());
        if ((m_timer.get() < stepTimeB1) && (driveStep == 1)) {
            driveTrain.driveArcade(0.6, 0.0);
        } else if ((m_timer.get() > stepTimeB1) && (driveStep == 1)) {
            driveTrain.driveArcade(0, 0);
            driveStep = 2; // increment driveStep counter, move to next driveStep
            m_timer.reset();
            m_timer.start();
        } else {
            driveTrain.driveArcade(0, 0);
        }

        if (driveStep == 2) {
            driveTrain.driveArcade(0.3, -0.6);
        }

        if (((m_gyro.getFusedHeading() % 360) > 85) && (driveStep == 2)) {
            driveTrain.driveArcade(0, 0);
            driveStep = 3; // increment driveStep counter, move to next driveStep
            m_timer.reset();
            m_timer.start();
        } else {
            driveTrain.driveArcade(0, 0);
        }

        if ((m_timer.get() < stepTimeB3) && (driveStep == 3)) {
            driveTrain.driveArcade(0.5, 0.0);
        } else if ((m_timer.get() > stepTimeB3) && (driveStep == 3)) {
            ingester.ingesterAuton(0.0);
            driveStep = 4; // increment driveStep counter, move to next driveStep
            m_timer.reset();
            m_timer.start();
        }

        if (driveStep == 4) {
            driveTrain.driveArcade(0.3, 0.6);
        }

        if (((m_gyro.getFusedHeading() % 360) < 5) && (driveStep == 4)) {
            driveTrain.driveArcade(0, 0);
            driveStep = 5; // increment driveStep counter, move to next driveStep
            m_timer.reset();
            m_timer.start();
        } else {
            driveTrain.driveArcade(0, 0);
        }

        if ((m_timer.get() < stepTimeB5) && (driveStep == 5)) {
            driveTrain.driveArcade(0.6, 0);
        } else if ((m_timer.get() > stepTimeB5) && (driveStep == 5)) {
            driveTrain.driveArcade(0, 0);
            driveStep = 6; // increment driveStep counter, move to next driveStep
            m_timer.reset();
            m_timer.start();
        } else {
            driveTrain.driveArcade(0, 0);
        }

        if ((m_timer.get() < stepTimeB6) && (driveStep == 6)) {
            ingester.ingesterAuton(-1.0);
        } else if ((m_timer.get() > stepTimeB6) && (driveStep == 6)) {
            ingester.ingesterAuton(0.0);
            driveStep = 7; // increment driveStep counter, move to next driveStep
            m_timer.reset();
            m_timer.start();
        }

        if ((m_timer.get() < stepTimeB7) && (driveStep == 7)) {
            driveTrain.driveArcade(-0.6, -0.3);
        } else if ((m_timer.get() > stepTimeB7) && (driveStep == 7)) {
            driveTrain.driveArcade(0, 0);
            driveStep = 8; // increment driveStep counter, move to next driveStep
            m_timer.reset();
            m_timer.start();
        } else {
            driveTrain.driveArcade(0, 0);
        }
    }
    
    public void centerRobot(boolean isFindingPowerCells) {
        // System.out.println("centering robot");
        if (Pi.getMoveLeft()) {
            driveTrain.driveTank(-0.2, 0.2);
            System.out.println("turning left");
        } else if (Pi.getMoveRight()) {
            driveTrain.driveTank(0.2, -0.2);
            System.out.println("turning right");
        } else {
            driveTrain.driveTank(0, 0);
            System.out.println("not turning");
            // System.out.println("move1SecDone = false");
            if (!isFindingPowerCells)
                Auton.setMove1SecDone(false);
        }
    }

    public void findPowerCells() {
        switch(autonStep) {
            case 1:
                centerRobot(true);
                // autonStep++;
                break;

            default:
                break;
            
        }
    }

}
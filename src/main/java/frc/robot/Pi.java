package frc.robot;

import edu.wpi.first.cameraserver.CameraServer;
import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.wpilibj.XboxController;

public class Pi {
    private NetworkTableInstance netTableInstance;
    private NetworkTable table;
    // private NetworkTableEntry camSelect;
    private boolean isButtonHeld;
    private XboxController m_driverController1;
    private NetworkTableEntry targetCenterX;
    private final double CAM_X_RES = 1280;
    private final double CAM_Y_RES = 720;
    private static boolean moveRight;
    private static boolean moveLeft;


    public Pi() {
        m_driverController1 = new XboxController(0);
        netTableInstance = NetworkTableInstance.getDefault();
        table = netTableInstance.getTable("data table");
        // camSelect = netTableInstance.getTable("SmartDashboard").getEntry("camNumber");
        targetCenterX = netTableInstance.getTable("datatable").getEntry("x");
        // targetCenterX.getNumberArray(new Number[0]);
        CameraServer.getInstance().addServer("10.68.61.62");
    }

    public void processTargets() {
        Number[] targetArray = targetCenterX.getNumberArray(new Number[0]);
        if (targetArray.length == 0) 
            return;
        // for (Number n : targetArray) {
        //     double targetX = (double) n;
        // }
        double targetX = (double) targetArray[0];
        if (targetX < (CAM_X_RES / 2) - (CAM_X_RES * 0.025)) {
            moveRight = false;
            moveLeft = true;
        } else if (targetX > (CAM_X_RES / 2) + (CAM_X_RES * 0.025)) {
            moveLeft = false;
            moveRight = true;
        } else {
            moveRight = false;
            moveLeft = false;
        }
    }

    public static boolean getMoveRight() {
        return moveRight;
    }

    public static boolean getMoveLeft() {
        return moveLeft;
    }

    public boolean getCentered() {
        return !moveRight && !moveLeft;
    }

    public void switchCameras() {
        // if (m_driverController1.getStartButtonPressed()) {
        //     if (!isButtonHeld) {
        //         int currentCam = (int) camSelect.getNumber(0);
        //         camSelect.setNumber((currentCam + 1) % 2);
        //         isButtonHeld = true;
        //     }
        // }
        // else {
        //     isButtonHeld = false;
        // }
    }
}
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
    private NetworkTableEntry targetWidth;
    private final double CAM_X_RES = 1280;
    private final double CAM_Y_RES = 720;
    private static boolean moveRight;
    private static boolean moveLeft;


    public Pi() {
        m_driverController1 = new XboxController(0);
        netTableInstance = NetworkTableInstance.getDefault();
        table = netTableInstance.getTable("datatable");
        // camSelect = netTableInstance.getTable("SmartDashboard").getEntry("camNumber");
        targetCenterX = table.getEntry("x");
        targetWidth = table.getEntry("w");
        // targetCenterX.getNumberArray(new Number[0]);
        CameraServer.getInstance().addServer("10.68.61.62");
    }

    public void processTargets() {
        Number[] targetCenterArray = targetCenterX.getNumberArray(new Number[0]);
        Number[] targetWidthArray = targetWidth.getNumberArray(new Number[0]);
        if (targetCenterArray.length == 0) {
            moveRight = false;
            moveLeft = false;
            return;
        }
        // for (Number n : targetCenterArray) {
        //     double targetX = (double) n;
        // }
        double targetX = (double) targetCenterArray[targetCenterArray.length - 1]; //rightmost target
        double targetW = (double) targetWidthArray[targetWidthArray.length - 1];
        System.out.println("width: " + targetW);
        System.out.println("target x value: " + targetX);
        if (targetX < (CAM_X_RES / 2) - (CAM_X_RES * 0.05)) {
            moveRight = false;
            moveLeft = true;
            // System.out.println(moveRight + "\n" + moveLeft);
        } else if (targetX > (CAM_X_RES / 2) + (CAM_X_RES * 0.05)) {
            moveLeft = false;
            moveRight = true;
            // System.out.println(moveRight + "\n" + moveLeft);
        } else {
            moveRight = false;
            moveLeft = false;
            // System.out.println(moveRight + "\n" + moveLeft);
        }
    }

    public static boolean getMoveRight() {
        return moveRight;
    }

    public static boolean getMoveLeft() {
        return moveLeft;
    }

    public static boolean getCentered() {
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
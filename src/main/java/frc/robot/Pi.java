package frc.robot;

import edu.wpi.first.cameraserver.CameraServer;
import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.wpilibj.XboxController;

public class Pi {
    private NetworkTableInstance netTableInstance;
    private NetworkTable table;
    private NetworkTableEntry camSelect;
    private boolean isButtonHeld;
    private XboxController m_driverController1;


    public Pi() {
        m_driverController1 = new XboxController(0);
        netTableInstance = NetworkTableInstance.getDefault();
        table = netTableInstance.getTable("data table");
        camSelect = netTableInstance.getTable("SmartDashboard").getEntry("camNumber");
        CameraServer.getInstance().addServer("10.68.61.62");
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
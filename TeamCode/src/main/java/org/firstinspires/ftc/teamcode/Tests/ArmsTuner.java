package org.firstinspires.ftc.teamcode.Tests;

import com.acmerobotics.dashboard.config.Config;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.ServoImplEx;

// This TEST FILE: tunes the servo positions of the arms (++ FTCDashboard Config Variables)

@Disabled // TEST FILE: It is Disabled the OpMode so it doesnt show up in the driver station
@Config
@TeleOp(name="ArmsTuner", group="Tests")
public class ArmsTuner extends LinearOpMode {
    public static double posL, posR;
    private ServoImplEx leftArm, rightArm;

    @Override
    public void runOpMode() {
        leftArm = hardwareMap.get(ServoImplEx.class, "left_arm");
        rightArm = hardwareMap.get(ServoImplEx.class, "right_arm");

        waitForStart();

        while (opModeIsActive()) {
            leftArm.setPosition(posL);
            rightArm.setPosition(posR);
        }
    }
}

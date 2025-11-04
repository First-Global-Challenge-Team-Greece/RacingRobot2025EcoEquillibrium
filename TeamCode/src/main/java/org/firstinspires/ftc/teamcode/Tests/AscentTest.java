package org.firstinspires.ftc.teamcode.Tests;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;

@Disabled
@TeleOp(name="AscentTest", group="")
public class AscentTest extends LinearOpMode {
    private DcMotorEx leftMotor, rightMotor, ascentMotor, slideMotor, intakeMotor;

    @Override
    public void runOpMode() {
        leftMotor = hardwareMap.get(DcMotorEx.class, "lm");
        rightMotor = hardwareMap.get(DcMotorEx.class, "rm");
        leftMotor.setDirection(DcMotorEx.Direction.REVERSE);
        leftMotor.setZeroPowerBehavior(DcMotorEx.ZeroPowerBehavior.BRAKE);
        rightMotor.setZeroPowerBehavior(DcMotorEx.ZeroPowerBehavior.BRAKE);
        ascentMotor = hardwareMap.get(DcMotorEx.class, "ascent");
        ascentMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        slideMotor = hardwareMap.get(DcMotorEx.class, "slide");
        slideMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        intakeMotor = hardwareMap.get(DcMotorEx.class, "intake");
        intakeMotor.setZeroPowerBehavior(DcMotorEx.ZeroPowerBehavior.BRAKE);


        waitForStart();

        while (opModeIsActive() && !isStopRequested()) {
            leftMotor.setPower(gamepad1.left_stick_y+gamepad1.right_stick_x);
            rightMotor.setPower(gamepad1.left_stick_y-gamepad1.right_stick_x);

            if(gamepad1.a) ascentMotor.setPower(1.0);
            else if(gamepad1.b) ascentMotor.setPower(-1.0);
            else ascentMotor.setPower(0.0);

            if(gamepad1.dpad_up) slideMotor.setPower(0.3);
            else if(gamepad1.dpad_down) slideMotor.setPower(-0.3);
            else slideMotor.setPower(0.0);

            if(gamepad1.right_bumper) intakeMotor.setPower(1.0);
            else if(gamepad1.left_bumper) intakeMotor.setPower(-1.0);
            else intakeMotor.setPower(0.0);
        }
    }
}

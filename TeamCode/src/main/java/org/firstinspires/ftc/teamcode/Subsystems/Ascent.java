package org.firstinspires.ftc.teamcode.Subsystems;

import com.acmerobotics.dashboard.config.Config;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.ServoImplEx;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.Constants;

import java.util.function.BooleanSupplier;

@Config
public class Ascent {
    // ---------------------------------------- Hardware ---------------------------------------- //
    private DcMotorEx ascentMotor, slideMotor;
    private BooleanSupplier raiseBtn, lowerBtn, slideBtn1, slideBtn2;

    private Constants.AscentState state = Constants.AscentState.STOPPED,
            last_state = Constants.AscentState.STOPPED;

    private Telemetry telemetry;

    // ------------------------------------------------------------------------------------------ //

    public Ascent(HardwareMap hm,
                  Telemetry telemetry,
                  BooleanSupplier raiseBtn,
                  BooleanSupplier lowerBtn,
                  BooleanSupplier slideBtn1,
                    BooleanSupplier slideBtn2
                  ) {
        ascentMotor = hm.get(DcMotorEx.class, Constants.ASCENT_MOTOR_NAME);
        ascentMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        ascentMotor.setDirection(DcMotorSimple.Direction.REVERSE);
//        slideMotor = hm.get(DcMotorEx.class, Constants.ASCENT_SLIDER_MOTOR_NAME);
//        slideMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        this.raiseBtn = raiseBtn;
        this.lowerBtn = lowerBtn;
//        this.slideBtn1 = slideBtn1;
//        this.slideBtn2 = slideBtn2;

        this.telemetry = telemetry;
    }

    public void update() {
        // If no button is pressed, do not set power (Less Variable Writing Optimization)
//        if(!raiseBtn.getAsBoolean() && !guideBtn.getAsBoolean()) return;

        if(raiseBtn.getAsBoolean()) {
            if(state != Constants.AscentState.ASCENDING) setState(Constants.AscentState.ASCENDING);
            else setState(Constants.AscentState.STOPPED);
        }

//        if(slideBtn1.getAsBoolean()) slideMotor.setPower(0.3);
//        else if(slideBtn2.getAsBoolean()) slideMotor.setPower(-0.3);
//        else slideMotor.setPower(0.0);

        // ------------------------------------- Telemetry -------------------------------------- //
        telemetry.addData("[Ascent] State: ", state);
    }

    public void setState(Constants.AscentState state) {
        if(this.state == state) return; // No Change, (Less Variable Writing Optimization)
        this.last_state = this.state;
        this.state = state;
        ascentMotor.setPower(state.getVelocity());
    }

    public Constants.AscentState getState() {
        return state;
    }

    public boolean justStartedAscending() {
        return state == Constants.AscentState.ASCENDING &&
                last_state != Constants.AscentState.ASCENDING;
    }
}

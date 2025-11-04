package org.firstinspires.ftc.teamcode.Subsystems;

import com.acmerobotics.dashboard.config.Config;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.ServoImplEx;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.Constants;

import java.util.function.BooleanSupplier;

@Config
public class BarrierDrive {
    // ----------------------------------------- Hardware --------------------------------------- //
    private ServoImplEx leftServo, rightServo;
    private BooleanSupplier toggleBtn;

    private Constants.BarrierServoState state = Constants.BarrierServoState.UP;

    // ------------------------------------------------------------------------------------------ //
    private Telemetry telemetry;

    public BarrierDrive(HardwareMap hm,
                        Telemetry telemetry,
                        BooleanSupplier toggleBtn) {
        leftServo = hm.get(ServoImplEx.class, Constants.BARRIER_LEFT_SERVO_NAME);
        rightServo = hm.get(ServoImplEx.class, Constants.BARRIER_RIGHT_SERVO_NAME);

        leftServo.setPosition(state.getPosition(Constants.BarrierServo.LEFT));
        rightServo.setPosition(state.getPosition(Constants.BarrierServo.RIGHT));

        this.toggleBtn = toggleBtn;

        this.telemetry = telemetry;
    }

    public void update() {
        // toggle barrier state
        if(toggleBtn.getAsBoolean()) {
            if(state == Constants.BarrierServoState.UP) setState(Constants.BarrierServoState.DOWN);
            else setState(Constants.BarrierServoState.UP);
        }

        // -------------------------------------- Telemetry ------------------------------------- //
        telemetry.addData("[Barrier Drive] State: ", state);
    }

    public void setState(Constants.BarrierServoState state) {
        if(this.state == state) return; // No change
        this.state = state;
        leftServo.setPosition(state.getPosition(Constants.BarrierServo.LEFT));
        rightServo.setPosition(state.getPosition(Constants.BarrierServo.RIGHT));
    }

    public Constants.BarrierServoState getState() {
        return state;
    }
}

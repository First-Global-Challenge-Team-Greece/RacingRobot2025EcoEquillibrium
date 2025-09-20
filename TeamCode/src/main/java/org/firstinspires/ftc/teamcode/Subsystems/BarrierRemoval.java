package org.firstinspires.ftc.teamcode.Subsystems;

import com.acmerobotics.dashboard.config.Config;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.Constants;

import java.util.function.BooleanSupplier;

@Config
public class BarrierRemoval {
    // ----------------------------------------- Hardware --------------------------------------- //
    private DcMotorEx motor1, motor2;
    private int cur_pos = 0, error = 0;
    private double given_power = 0.0;
    private BooleanSupplier toggleBtn;

    private Constants.BarrierRemovalState state = Constants.BarrierRemovalState.UP;

    // ------------------------------------------------------------------------------------------ //
    private Telemetry telemetry;

    public BarrierRemoval(HardwareMap hm,
                          Telemetry telemetry,
                          BooleanSupplier toggleBtn) {
        motor1 = hm.get(DcMotorEx.class, Constants.BARRIER_REMOVAL_MOTOR1_NAME);
        motor2 = hm.get(DcMotorEx.class, Constants.BARRIER_REMOVAL_MOTOR2_NAME);
        motor1.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        motor1.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        motor2.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        motor2.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        motor2.setDirection(DcMotor.Direction.REVERSE);

        this.toggleBtn = toggleBtn;

        this.telemetry = telemetry;
    }

    public void update() {
        // toggle barrier state
        if (toggleBtn.getAsBoolean()) state = (state == Constants.BarrierRemovalState.DOWN)
                ? Constants.BarrierRemovalState.UP
                :
                Constants.BarrierRemovalState.DOWN;

        cur_pos = (motor1.getCurrentPosition() + motor2.getCurrentPosition())/2;
        error = state.getPos() - cur_pos;

        if(Math.abs(error) > 10) {
            given_power = (state == Constants.BarrierRemovalState.UP) ? Constants.BARRIER_RUN_POWER :
                    -Constants.BARRIER_RUN_POWER/3;
        } else {
            given_power = (state == Constants.BarrierRemovalState.UP) ? Constants.BARRIER_HOLD_POWER :
                    -Constants.BARRIER_HOLD_POWER/1.5;
        }

        // Set power ++ Feedforward
        motor1.setPower(given_power);
        motor2.setPower(given_power);

        // -------------------------------------- Telemetry ------------------------------------- //
        telemetry.addData("[Barrier Removal] State: ", state);
        telemetry.addData("[Barrier Removal] Position: ", cur_pos);
        telemetry.addData("[Barrier Removal] Power: ", given_power);
    }

    public Constants.BarrierRemovalState getState() {
        return state;
    }
}

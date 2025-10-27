package org.firstinspires.ftc.teamcode.Subsystems;

import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.ServoImplEx;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.Constants;

import java.util.function.BooleanSupplier;

public class Compartment {
    private ServoImplEx wheel1, wheel2;
    public Constants.CompartmentWheelState state = Constants.CompartmentWheelState.CLOSED;
    private boolean firstTime = true;

    private BooleanSupplier btn, just_hanging;
    private Telemetry telemetry;

    public Compartment(HardwareMap hm, Telemetry telemetry, BooleanSupplier btn,
                       BooleanSupplier just_hanging) {
        this.btn = btn;
        this.wheel1 = hm.get(ServoImplEx.class, Constants.COMPARTMENT_WHEEL_1_NAME);
        this.wheel2 = hm.get(ServoImplEx.class, Constants.COMPARTMENT_WHEEL_2_NAME);
        this.telemetry = telemetry;
        this.just_hanging = just_hanging;
    }

    public void update(Constants.IntakeState intakeState) {
        if(btn.getAsBoolean()) {
            if(state == Constants.CompartmentWheelState.OPEN) setState(Constants.CompartmentWheelState.CLOSED);
            else setState(Constants.CompartmentWheelState.OPEN);
        }

        telemetry.addData("[Compartment] State: ", state);
        telemetry.addData("[Compartment] Pos: ", state.getPosition(Constants.CompartmentWheel.WHEEL1));

        if(firstTime) {
            // First Update Loop
            firstTime = false;
            setState(Constants.CompartmentWheelState.OPEN);
        }

        if(just_hanging.getAsBoolean()) setState(Constants.CompartmentWheelState.CLOSED);

        if(intakeState != Constants.IntakeState.STOPPED) setState(Constants.CompartmentWheelState.OPEN);
    }

    public void setState(Constants.CompartmentWheelState state) {
        if(this.state == state) return; // No Change, (Less Variable Writing Optimization)
        this.state = state;
        wheel1.setPosition(state.getPosition(Constants.CompartmentWheel.WHEEL1));
        wheel2.setPosition(state.getPosition(Constants.CompartmentWheel.WHEEL2));
    }

    public Constants.CompartmentWheelState getState() {
        return state;
    }
}

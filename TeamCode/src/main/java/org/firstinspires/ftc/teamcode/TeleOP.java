package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.Subsystems.Accelerator;
import org.firstinspires.ftc.teamcode.Subsystems.Ascent;
import org.firstinspires.ftc.teamcode.Subsystems.BarrierDrive;
import org.firstinspires.ftc.teamcode.Subsystems.BarrierRemoval;
import org.firstinspires.ftc.teamcode.Subsystems.Compartment;
import org.firstinspires.ftc.teamcode.Subsystems.Drivetrain;
import org.firstinspires.ftc.teamcode.Subsystems.Intake;
import org.firstinspires.ftc.teamcode.utils.GamepadEx;

import java.util.function.BooleanSupplier;

@TeleOp(name="TeleOP", group="")
public class TeleOP extends LinearOpMode {
    // --------------------------------------- Subsystems --------------------------------------- //
    private GamepadEx controller, controller2;
    private Drivetrain drivetrain;
    private Intake intake;
    private Accelerator accelerator;
    private Ascent ascent;
    private BarrierRemoval barrierRemoval;
    private BarrierDrive barrierDrive;
    private Compartment compartment;

    private boolean robotHasInit = false;
    private BooleanSupplier hanging = () -> false;

    // ------------------------------------------------------------------------------------------ //

    @Override
    public void runOpMode() {
        // ------------------------------ Subsystem Initialization ------------------------------ //
        controller = new GamepadEx(gamepad1);
        controller2 = new GamepadEx(gamepad2);

        drivetrain = new Drivetrain(
                hardwareMap,
                telemetry,
                controller::getLeftStickY,
                controller::getRightStickX,
                controller::getRightTrigger,
//                () -> barrierRemoval.getState() == Constants.BarrierRemovalState.DOWN,
                () -> false,
                hanging
        );

        accelerator = new Accelerator(
                hardwareMap,
                telemetry,
                () -> controller2.justPressed(GamepadEx.Button.A),
                hanging
        );

        ascent = new Ascent(
                hardwareMap,
                telemetry,
                () -> controller2.justPressed(GamepadEx.Button.RIGHT_BUMPER),
                () -> false,
                () -> controller2.getLeftTrigger() > 0.4,
                () -> controller2.getRightTrigger() > 0.4
        );

        hanging = ascent::justStartedAscending;

        barrierRemoval = new BarrierRemoval(
                hardwareMap,
                telemetry,
                () -> controller2.justPressed(GamepadEx.Button.X),
                () -> controller2.justPressed(GamepadEx.Button.LEFT_BUMPER)
        );

        barrierDrive = new BarrierDrive(
                hardwareMap,
                telemetry,
                () -> controller2.justPressed(GamepadEx.Button.Y)
        );

        compartment = new Compartment(
                hardwareMap,
                telemetry,
                () -> controller2.justPressed(GamepadEx.Button.B),
                hanging
        );

        intake = new Intake(
                hardwareMap,
                telemetry,
                () -> controller2.justPressed(GamepadEx.Button.DPAD_UP),
                () -> controller2.justPressed(GamepadEx.Button.DPAD_LEFT),
                () -> controller2.justPressed(GamepadEx.Button.DPAD_DOWN),
                hanging
        );

        waitForStart();

        while (opModeIsActive() && !isStopRequested()) {
            controller.update();
            controller2.update();

            // Wait for the robot to Move a little to init the subsystems -> avoid penalties b4
            // match timer starts
            if (!robotHasInit) {
                if(Math.abs(controller.getRightStickX()) > 0.1
                        || Math.abs(controller.getLeftStickY()) > 0.1) {
                    robotHasInit = true;
                }
                continue;
            }

            // ------------------------------- Subsystem Updates -------------------------------- //
            drivetrain.update();
            intake.update(compartment.getState());
            accelerator.update();
            ascent.update();
            barrierRemoval.update();
            barrierDrive.update();
            compartment.update(intake.getState());

            telemetry.update();
        }
    }
}

package org.firstinspires.ftc.teamcode;

public class Constants {
    public static final String ROBOT_NAME = "Racing1.0";

    // ----------------------------- Drivebase Motors Configuration ----------------------------- //
    public static final String LEFT_MOTOR_NAME = "lm";
    public static final String RIGHT_MOTOR_NAME = "rm";

    // -------------------------- DriveBase Constants Configuration ----------------------------- //
    public static final double KS_theta = 0.0; // Static gain for turning
    public static final double DEFAULT_POWER = 0.55; // Default power for driving
    public static final double MAX_POWER = 1.0; // Maximum power for driving

    public enum Motor {
        LEFT(0),
        RIGHT(1);

        // Feedforward constants
        // KS: is the static gain -> for Static Friction
        // KV: is the velocity gain -> Fixes Motor Inaccuracies Linearly
        public static final double[] KS = {0.05, 0.11};
        public static final double[] KV = {0.96, 1.086};

        private final int idx;

        Motor(int idx) {
            this.idx = idx;
        }

        public double getKS() {
            return KS[idx];
        }
        public double getKV() {
            return KV[idx];
        }
    }

    //// ------------------------------------- Mechanisms ------------------------------------- ////
    // ----------------------------------------- Intake ----------------------------------------- //
    public static final String INTAKE_MOTOR_NAME = "intake";
    public static final double INTAKE_MAX_POWER = 1.0; // Maximum power for intake motor
    public enum IntakeState {
        STOPPED(0),
        FORWARD(1),
        REVERSE(2);

        private final int idx;
        public static final double[] VELOCITIES = {0.0, 1.0, -1.0};

        IntakeState(int idx) {
            this.idx = idx;
        }

        public double getVelocity() {
            return VELOCITIES[idx];
        }
    }

    //----------------------------------------- Barrier------------------------------------------ //
    public static final String BARRIER_REMOVAL_MOTOR1_NAME = "barrier1";
    public static final String BARRIER_REMOVAL_MOTOR2_NAME = "barrier2";

    public static final double BARRIER_RUN_POWER = 0.7;
    public static final double BARRIER_HOLD_POWER = 0.1;


    public enum BarrierRemovalState {
        DOWN(0),
        UP(1);

        public final int idx;
        BarrierRemovalState(int idx) {
            this.idx = idx;
        }

        int[] pos = {-75, 0};

        public int getPos() {
            return pos[idx];
        }
    }

    public static String BARRIER_LEFT_SERVO_NAME = "barrierServoL";
    public static String BARRIER_RIGHT_SERVO_NAME = "barrierServoR";

    public enum BarrierServoState {
        DOWN(0),
        UP(1);

        public final int idx;
        BarrierServoState(int idx) {
            this.idx = idx;
        }

        double[][] positions = {{0.0, 0.63}, {0.88, 0.1}};

        public double getPosition(BarrierServo servo) {
            return positions[servo.getIdx()][idx];
        }
    }

    public enum BarrierServo {
        LEFT(0),
        RIGHT(1);

        public final int idx;
        BarrierServo(int idx) {
            this.idx = idx;
        }

        public int getIdx() {
            return idx;
        }
    }


    // -------------------------------------- Accelerator --------------------------------------- //
    public static final String ACCELERATOR_MOTOR_NAME = "accel";
    public enum AcceleratorState {
        STOPPED(0),
        RUNNING(1);

        private final int idx;
        public static final double[] VELOCITIES = {0.0, 1.0};

        AcceleratorState(int idx) {
            this.idx = idx;
        }

        public double getVelocity() {
            return VELOCITIES[idx];
        }
    }
    // ----------------------------------------- Ascent ----------------------------------------- //
    public static final String ASCENT_MOTOR_NAME = "ascent";
    public static double ACCEL_ENABLE_TIMESTAMP = 10.0;
    public enum AscentState {
        STOPPED(0),
        ASCENDING(1),
        DESCENDING(2);

        private final int idx;
        public static final double[] VELOCITIES = {0.0, 1.0, -1.0};

        AscentState(int idx) {
            this.idx = idx;
        }

        public double getVelocity() {
            return VELOCITIES[idx];
        }
    }
    // -------------------------------------- Compartment --------------------------------------- //
    public static final String COMPARTMENT_WHEEL_1_NAME = "wheel1";
    public static final String COMPARTMENT_WHEEL_2_NAME = "wheel2";

    public enum CompartmentWheel {
        WHEEL1(0),
        WHEEL2(1);

        private final int idx;

        CompartmentWheel(int idx) {
            this.idx = idx;
        }

        public int getIdx() {
            return idx;
        }
    }

    public enum CompartmentWheelState {
        OPEN(0),
        CLOSED(1);

        private final int idx;
        public static final double[][] POSITIONS = {
                {0.0, 1.0},
                {0.0, 1.0}
        };

        CompartmentWheelState(int idx) {
            this.idx = idx;
        }

        public double getPosition(CompartmentWheel wheel) {
            return POSITIONS[wheel.getIdx()][idx];
        }
    }
}

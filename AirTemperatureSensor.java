package responsipbo_iot;

public class AirTemperatureSensor extends IoTSensor implements Calibratable {
    private double temperatureCelsius;
    private String fanMode;
    private boolean isCalibrated;

    public AirTemperatureSensor(String sensorId, String location, double batteryLevel, double initialTemp) {
        super(sensorId, location, batteryLevel);
        this.temperatureCelsius = initialTemp;
        this.fanMode = "OFF";
        this.isCalibrated = false;
    }

    public double getTemperatureCelsius() {
        return this.temperatureCelsius;
    }

    public void setTemperatureCelsius(double temperatureCelsius) {
        this.temperatureCelsius = temperatureCelsius;
    }

    public String getFanMode() {
        return this.fanMode;
    }

    public void setFanMode(String fanMode) {
        this.fanMode = fanMode;
    }

    public boolean isCalibrated() {
        return this.isCalibrated;
    }

    public void setCalibrated(boolean isCalibrated) {
        this.isCalibrated = isCalibrated;
    }

    @Override
    public void connectNetwork() {
        super.connectNetwork();
        System.out.println("[TEMP-SENSOR] Siap memantau suhu udara di " + this.getLocation());
    }

    @Override
    public void evaluateEnvironment(double readingValue) {
        this.temperatureCelsius = readingValue;
        if (this.temperatureCelsius >= 32.0) {
            this.fanMode = "HIGH_COOLING";
            this.setBatteryLevel(this.getBatteryLevel() - 3.0);
            System.out.println("[ACTION] Suhu tinggi (" + this.temperatureCelsius + "°C). Kipas pendingin diset ke: " + this.fanMode);
        } else if (this.temperatureCelsius <= 18.0) {
            this.fanMode = "HEATER_ON";
            this.setBatteryLevel(this.getBatteryLevel() - 3.0);
            System.out.println("[ACTION] Suhu rendah (" + this.temperatureCelsius + "°C). Pemanas diaktifkan: " + this.fanMode);
        } else {
            this.fanMode = "IDLE";
            System.out.println("[INFO] Suhu ruangan stabil (" + this.temperatureCelsius + "°C). Mode kipas: " + this.fanMode);
        }
    }

    @Override
    public void calibrate() {
        this.isCalibrated = true;
        System.out.println("[CALIBRATION] Kalibrasi termistor pada " + this.getSensorId() + " selesai.");
    }

    @Override
    public String getCalibrationStatus() {
        return this.isCalibrated ? "TERKALIBRASI" : "BELUM_TERKALIBRASI";
    }
}
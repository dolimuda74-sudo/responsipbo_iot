package responsipbo_iot;

public class SoilMoistureSensor extends IoTSensor implements Connectable, Calibratable {
    private double moisturePercentage;
    private String irrigationStatus;
    private boolean isCalibrated;

    public SoilMoistureSensor(String sensorId, String location, double initialMoisture) {
        super(sensorId, location);
        this.moisturePercentage = initialMoisture;
        this.irrigationStatus = "STANDBY";
        this.isCalibrated = false;
    }

    public double getMoisturePercentage() {
        return this.moisturePercentage;
    }

    public void setMoisturePercentage(double moisturePercentage) {
        this.moisturePercentage = moisturePercentage;
    }

    public String getIrrigationStatus() {
        return this.irrigationStatus;
    }

    public void setIrrigationStatus(String irrigationStatus) {
        this.irrigationStatus = irrigationStatus;
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
        System.out.println("[SOIL-SENSOR] Sinkronisasi data kelembapan tanah awal: " + this.moisturePercentage + "%");
    }

    @Override
    public void evaluateEnvironment(double readingValue) {
        this.moisturePercentage = readingValue;
        if (!this.isConnected()) {
            System.out.println("[WARNING] Sensor offline! Evaluasi kelembapan dibatalkan.");
            return;
        }

        if (this.moisturePercentage < 30.0) {
            this.irrigationStatus = "POMPA_AKTIF";
            this.setBatteryLevel(this.getBatteryLevel() - 2.0);
            System.out.println("[ACTION] Tanah kering (" + this.moisturePercentage + "%). Status irigasi diubah ke: " + this.irrigationStatus);
        } else if (this.moisturePercentage > 75.0) {
            this.irrigationStatus = "DRAINASE_AKTIF";
            this.setBatteryLevel(this.getBatteryLevel() - 1.5);
            System.out.println("[ACTION] Tanah terlalu basah (" + this.moisturePercentage + "%). Status irigasi diubah ke: " + this.irrigationStatus);
        } else {
            this.irrigationStatus = "OPTIMAL_STANDBY";
            System.out.println("[INFO] Kelembapan tanah normal (" + this.moisturePercentage + "%). Status: " + this.irrigationStatus);
        }
    }

    @Override
    public void calibrate() {
        this.isCalibrated = true;
        System.out.println("[CALIBRATION] Kalibrasi probe kelembapan pada " + this.getSensorId() + " berhasil.");
    }

    @Override
    public String getCalibrationStatus() {
        return this.isCalibrated ? "TERKALIBRASI" : "BELUM_TERKALIBRASI";
    }
}
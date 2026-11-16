package responsipbo_iot;

public abstract class IoTSensor implements Connectable {
    public static final String NETWORK_PROTOCOL = "LoRaWAN-v1.0.4";
    private final String sensorId;

    private String location;
    private boolean isConnected;
    private double batteryLevel;

    public IoTSensor(String sensorId, String location) {
        this.sensorId = sensorId;
        this.location = location;
        this.isConnected = false;
        this.batteryLevel = 100.0;
    }

    public IoTSensor(String sensorId, String location, double batteryLevel) {
        this.sensorId = sensorId;
        this.location = location;
        this.isConnected = false;
        this.batteryLevel = batteryLevel;
    }

    public String getSensorId() {
        return this.sensorId;
    }

    public String getLocation() {
        return this.location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public boolean isConnected() {
        return this.isConnected;
    }

    public void setConnected(boolean isConnected) {
        this.isConnected = isConnected;
    }

    public double getBatteryLevel() {
        return this.batteryLevel;
    }

    public void setBatteryLevel(double batteryLevel) {
        this.batteryLevel = batteryLevel;
    }

    @Override
    public void connectNetwork() {
        this.isConnected = true;
        this.batteryLevel -= 1.5;
        System.out.println("[NETWORK] Sensor " + this.sensorId + " terhubung ke " + NETWORK_PROTOCOL);
    }

    @Override
    public void disconnectNetwork() {
        this.isConnected = false;
        System.out.println("[NETWORK] Sensor " + this.sensorId + " terputus dari jaringan.");
    }

    public String getDeviceSummary() {
        return "ID: " + this.sensorId + " | Lokasi: " + this.location + " | Baterai: " + this.batteryLevel + "%";
    }

    public abstract void evaluateEnvironment(double readingValue);
}

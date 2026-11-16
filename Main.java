package responsipbo_iot;

public class Main {
    public static void main(String[] args) {
        System.out.println("=========================================================");
        System.out.println("  SMART GREENHOUSE IoT MONITORING SYSTEM (" + IoTSensor.NETWORK_PROTOCOL + ")");
        System.out.println("=========================================================\n");

        SoilMoistureSensor soilSensor = new SoilMoistureSensor("IOT-SOIL-01", "Blok A - Hidroponik", 45.0);
        AirTemperatureSensor tempSensor = new AirTemperatureSensor("IOT-TEMP-02", "Blok B - Pembibitan", 88.5, 27.0);

        System.out.println("--- [UJI POLIMORFISME INTERFACE & MULTIPLE INTERFACE] ---");
        Connectable polyConnectSoil = soilSensor;
        Calibratable polyCalibrateSoil = soilSensor;

        polyCalibrateSoil.calibrate();
        System.out.println("Status Kalibrasi Tanah: " + polyCalibrateSoil.getCalibrationStatus());
        polyConnectSoil.connectNetwork(); // Memanggil override + super.connectNetwork()

        System.out.println();

        Connectable polyConnectTemp = tempSensor;
        Calibratable polyCalibrateTemp = tempSensor;

        polyCalibrateTemp.calibrate();
        polyConnectTemp.connectNetwork();

        System.out.println("\n--- [UJI LOGIKA STATE PERCABANGAN (IF/ELSE)] ---");
        System.out.println("Sebelum evaluasi -> " + soilSensor.getDeviceSummary());
        soilSensor.evaluateEnvironment(22.5); // Kondisi kering -> mengubah state irrigationStatus & baterai
        System.out.println("Setelah evaluasi -> " + soilSensor.getDeviceSummary());
        System.out.println("State Irigasi Saat Ini: " + soilSensor.getIrrigationStatus());

        System.out.println();

        System.out.println("Sebelum evaluasi -> " + tempSensor.getDeviceSummary());
        tempSensor.evaluateEnvironment(34.8); // Kondisi panas -> mengubah state fanMode & baterai
        System.out.println("Setelah evaluasi -> " + tempSensor.getDeviceSummary());
        System.out.println("State Kipas Saat Ini: " + tempSensor.getFanMode());

        System.out.println("\n--- [UJI IMMUTABILITY & PEMUTUSAN KONEKSI] ---");
        System.out.println("Konstanta Protokol (public static final): " + IoTSensor.NETWORK_PROTOCOL);
        System.out.println("ID Sensor Kekal (private final): " + soilSensor.getSensorId());

        polyConnectSoil.disconnectNetwork();
        polyConnectTemp.disconnectNetwork();
        System.out.println("\n=========================================================");
    }
}
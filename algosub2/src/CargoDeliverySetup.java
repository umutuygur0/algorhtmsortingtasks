//-----------------------------------------------------
// Title: My Cargodelivery class
// Author: umut uygur
// ID: 13474078970
// Section: 1
// Assignment: 1
// Description: this class make all the work (main) this class include
//all city,Package,Vehicle,mission clases also reading files input each of them
//execute the code with correct mission and display the data create result.txt
//-----------------------------------------------------

import java.io.*;
import java.util.*;

// Şehir sınıfı, sadece şehir adı tutacak
class City {
    String name;

    public City(String name) {
        this.name = name;
    }
    public boolean equals(Object obj) {
        if (this == obj) return true; // Check for reference equality
        if (obj == null || getClass() != obj.getClass()) return false; // Ensure the object is of the same class
        City city = (City) obj;
        return name.equals(city.name); // Compare city names for equality
    }

    @Override
    public int hashCode() {
        return name.hashCode(); // Use the name's hash code
    }

    @Override
    public String toString() {
        return name; // Use name for string representation
    }
}

// Paket sınıfı, paket kimliği (ID) tutacak
class Package {
    String id;

    public Package(String id) {
        this.id = id;
    }
    public String toString() {
        return  id; // Format it as you desire
    }
}

// Araç sınıfı, araç kimliği (ID) tutacak
class Vehicle {
    String id;

    public Vehicle(String id) {
        this.id = id;
    }
    public String toString() {
        return id; // Format it as you desire
    }
}

class Mission {
    String sourceCity;
    String middleCity;
    String destinationCity;
    int loadFromSource;
    int loadFromMiddle;
    List<Integer> dropIndices;

    public Mission(String sourceCity, String middleCity, String destinationCity, int loadFromSource, int loadFromMiddle, List<Integer> dropIndices) {
        this.sourceCity = sourceCity;
        this.middleCity = middleCity;
        this.destinationCity = destinationCity;
        this.loadFromSource = loadFromSource;
        this.loadFromMiddle = loadFromMiddle;
        this.dropIndices = dropIndices;
    }
}

public class CargoDeliverySetup {
    static List<City> cities = new ArrayList<>();                       // Şehirleri tutmak için liste
    static List<Stack<Package>> cityPackages = new ArrayList<>();       // Şehirlerin paket stack'lerini tutmak için liste
    static List<Queue<Vehicle>> cityVehicles = new ArrayList<>();       // Şehirlerin araç queue'larını tutmak için liste

    public static void main(String[] args) throws IOException {
        // Şehirleri oku ve kaydet
        readCities("cities.txt");

        // Paketleri oku ve ilgili şehre göre stacklere kaydet
        readPackages("packages.txt");

        // Araçları oku ve ilgili şehre göre queue'lara kaydet
        readVehicles("vehicles.txt");

        // Görevleri oku ve işle
        List<Mission> missions = readMissions("missions.txt");
        for (Mission mission : missions) {
            executeMission(mission);
        }

        // Son durumu ekrana yazdır
        displayData();
        writeResultsToFile("result.txt");
    }

    // Şehirleri okuyan ve kaydeden method
    public static void readCities(String fileName) throws IOException {
        try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
            String line;
            while ((line = reader.readLine()) != null) {
                City city = new City(line.trim());
                cities.add(city);                           // Şehir adını listeye ekle
                cityPackages.add(new Stack<>());            // Her şehir için bir paket stack'i oluştur
                cityVehicles.add(new Queue<>());            // Her şehir için bir araç queue'su oluştur
            }
        }
    }

    // Paketleri okuyan ve şehir bazında stacklere kaydeden method
    public static void readPackages(String fileName) throws IOException {
        try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(" ");
                String packageId = parts[0];
                String cityName = parts[1];

                int cityIndex = findCityIndex(cityName);
                if (cityIndex != -1) {
                    cityPackages.get(cityIndex).push(new Package(packageId));
                }
            }
        }
    }

    // Araçları okuyan ve şehir bazında queue'lara kaydeden method
    public static void readVehicles(String fileName) throws IOException {
        try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(" ");
                String vehicleId = parts[0];
                String cityName = parts[1];

                int cityIndex = findCityIndex(cityName);
                if (cityIndex != -1) {
                    cityVehicles.get(cityIndex).enqueue(new Vehicle(vehicleId));
                }
            }
        }
    }

    // Misyonları okuyan method
    public static List<Mission> readMissions(String filename) {
        List<Mission> missions = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split("-");
                if (parts.length != 6) {
                    System.out.println("Invalid load/drop format: " + line);
                    continue;
                }

                String sourceCity = parts[0];
                String middleCity = parts[1];
                String destinationCity = parts[2];
                int loadFromSource = Integer.parseInt(parts[3]);
                int loadFromMiddle = Integer.parseInt(parts[4]);

                // Parse drop indices
                List<Integer> dropIndices = new ArrayList<>();
                String[] dropParts = parts[5].split(",");
                for (String drop : dropParts) {
                    dropIndices.add(Integer.parseInt(drop.trim())); // Ensure any spaces are removed
                }

                Mission mission = new Mission(sourceCity, middleCity, destinationCity, loadFromSource, loadFromMiddle, dropIndices);
                missions.add(mission);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (NumberFormatException e) {
            System.out.println("Error parsing a number: " + e.getMessage());
        }
        return missions;
    }





    // Misyonları işleyen method
    public static void executeMission(Mission mission) {
        // Find the city indices
        int sourceIndex = findCityIndex(mission.sourceCity);
        int middleIndex = findCityIndex(mission.middleCity);
        int destinationIndex = findCityIndex(mission.destinationCity);

        if (sourceIndex == -1 || middleIndex == -1 || destinationIndex == -1) {
            System.out.println("Invalid mission cities.");
            return;
        }

        // Step 1: Assemble a vehicle from the source city
        Queue<Vehicle> sourceVehicles = cityVehicles.get(sourceIndex);
        Stack<Package> sourcePackages = cityPackages.get(sourceIndex);
        Vehicle vehicle = sourceVehicles.isEmpty() ? null : sourceVehicles.dequeue();

        if (vehicle == null) {
            System.out.println("No available vehicles at " + mission.sourceCity);
            return;
        }

        // Create a list to act as the vehicle's doubly linked list of cargo
        List<Package> vehicleCargo = new LinkedList<>();

        // Step 2: Load `a` packages from the source city
        for (int i = 0; i < mission.loadFromSource && !sourcePackages.isEmpty(); i++) {
            vehicleCargo.add(sourcePackages.pop()); // Add to the end of the list
        }

        // Step 3: Load `b` packages from the middle city
        Stack<Package> middlePackages = cityPackages.get(middleIndex);
        for (int i = 0; i < mission.loadFromMiddle && !middlePackages.isEmpty(); i++) {
            vehicleCargo.add(middlePackages.pop()); // Add to the end of the list
        }

        // Step 4: Drop off packages at the middle city based on indices
        Collections.sort(mission.dropIndices); // Sort the indices in ascending order
        for (int index : mission.dropIndices) {
            if (index < vehicleCargo.size()) {
                middlePackages.push(vehicleCargo.get(index)); // Push to the middle city's stack
            }
        }

        // Remove the dropped packages from the vehicle cargo
        for (int i = mission.dropIndices.size() - 1; i >= 0; i--) {
            int index = mission.dropIndices.get(i);
            if (index < vehicleCargo.size()) {
                vehicleCargo.remove(index); // Remove from vehicle cargo
            }
        }

        // Step 5: Move remaining vehicle cargo to the destination city
        Stack<Package> destinationPackages = cityPackages.get(destinationIndex);
        for (Package pkg : vehicleCargo) {
            destinationPackages.push(pkg); // Disband cargo upon arrival
        }

        // Step 6: Return the vehicle to the destination city's vehicle queue
        cityVehicles.get(destinationIndex).enqueue(vehicle);
    }




    // Şehir ismine göre indeks bulan yardımcı method
    public static int findCityIndex(String cityName) {
        for (int i = 0; i < cities.size(); i++) {
            if (cities.get(i).name.equals(cityName)) {
                return i;
            }
        }
        return -1;
    }

    // Veri yapılarındaki bilgileri ekrana yazdıran method
    public static void displayData() {
        System.out.println("Cities:");
        for (City city : cities) {
            System.out.println("- " + city.name);
        }

        System.out.println("\nCity Packages:");
        for (int i = 0; i < cityPackages.size(); i++) {
            System.out.print(cities.get(i).name + ": ");
            Stack<Package> stack = cityPackages.get(i);
            List<Package> tempStack = new ArrayList<>();

            // Stack'i geçici bir listeye ekleyerek ters sırayla yazdır
            while (!stack.isEmpty()) {
                Package pkg = stack.pop();
                System.out.print(pkg.id + " ");
                tempStack.add(pkg);  // Paketi geçici listeye ekle
            }
            System.out.println();

            // Paketleri orijinal stack'e geri yükle
            for (int j = tempStack.size() - 1; j >= 0; j--) {
                stack.push(tempStack.get(j));
            }
        }

        System.out.println("\nCity Vehicles:");
        for (int i = 0; i < cityVehicles.size(); i++) {
            System.out.print(cities.get(i).name + ": ");
            Queue<Vehicle> queue = cityVehicles.get(i);
            List<Vehicle> tempQueue = new ArrayList<>();

            // Queue'dan elemanları geçici listeye taşıyarak yazdır
            while (!queue.isEmpty()) {
                Vehicle vehicle = queue.dequeue();
                System.out.print(vehicle.id + " ");
                tempQueue.add(vehicle);  // Aracı geçici listeye ekle
            }
            System.out.println();

            // Araçları orijinal queue'ya geri yükle
            for (Vehicle vehicle : tempQueue) {
                queue.enqueue(vehicle);
            }

        }
    }
    private static void writeResultsToFile(String fileName) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName))) {
            for (City city : cities) {
                int index = findCityIndex(city.toString());
                if (index == -1) {
                    writer.write("City not found: " + city.toString());
                    writer.newLine();
                    continue; // Skip this city
                }

                writer.write(city.toString());
                writer.newLine();
                writer.write("Packages:");
                writer.newLine();

                Stack<Package> packages = cityPackages.get(index);
                if (packages.isEmpty()) {
                    writer.write("No packages\n");
                } else {
                    while (!packages.isEmpty()) {
                        Package pkg = packages.pop();
                        writer.write(pkg.toString());
                        writer.newLine();
                    }
                }

                writer.write("Vehicles:");
                writer.newLine();

                Queue<Vehicle> vehicles = cityVehicles.get(index);
                if (vehicles.isEmpty()) {
                    writer.write("No vehicles\n");
                } else {
                    while (!vehicles.isEmpty()) {
                        Vehicle vehicle = vehicles.dequeue();
                        writer.write(vehicle.toString());
                        writer.newLine();
                    }
                }
                writer.write("-------------");
                writer.newLine();
            }
        } catch (IOException e) {
            System.out.println("Error writing to file: " + e.getMessage());
        }
    }




}

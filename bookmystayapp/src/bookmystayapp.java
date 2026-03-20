import java.io.*;
import java.util.*;

// Inventory class (Serializable)
class Inventory implements Serializable {
    private static final long serialVersionUID = 1L;

    private Map<String, Integer> rooms;

    public Inventory() {
        rooms = new HashMap<>();
        rooms.put("Single", 5);
        rooms.put("Double", 3);
        rooms.put("Suite", 2);
    }

    public Map<String, Integer> getRooms() {
        return rooms;
    }

    public void displayInventory() {
        System.out.println("\nCurrent Inventory:");
        for (String type : rooms.keySet()) {
            System.out.println(type + ": " + rooms.get(type));
        }
    }
}

// Persistence Service
class PersistenceService {

    private static final String FILE_NAME = "inventory.dat";

    // Save inventory to file
    public void saveInventory(Inventory inventory) {
        try (ObjectOutputStream oos =
                     new ObjectOutputStream(new FileOutputStream(FILE_NAME))) {

            oos.writeObject(inventory);
            System.out.println("Inventory saved successfully.");

        } catch (IOException e) {
            System.out.println("Error saving inventory data.");
        }
    }

    // Load inventory from file
    public Inventory loadInventory() {
        try (ObjectInputStream ois =
                     new ObjectInputStream(new FileInputStream(FILE_NAME))) {

            return (Inventory) ois.readObject();

        } catch (FileNotFoundException e) {
            System.out.println("No valid inventory data found. Starting fresh.");
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("Error reading inventory data. Starting fresh.");
        }

        return new Inventory(); // fallback
    }
}

// Main Class
public class bookmystayapp{

    public static void main(String[] args) {

        System.out.println("System Recovery");

        PersistenceService persistenceService = new PersistenceService();

        // Load existing inventory or create new one
        Inventory inventory = persistenceService.loadInventory();

        // Display recovered inventory
        inventory.displayInventory();

        // Simulate shutdown: save state
        persistenceService.saveInventory(inventory);
    }
}
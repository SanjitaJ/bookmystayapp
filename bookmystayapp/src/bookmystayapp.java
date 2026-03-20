import java.util.HashMap;
import java.util.Map;

/**
 * UseCase3InventorySetup
 *
 * Demonstrates centralized room inventory management using HashMap.
 *
 * @author Sanjita
 * @version 3.1
 */

// Abstract Room class
abstract class Room {
    protected int beds;
    protected int size;
    protected double price;

    public Room(int beds, int size, double price) {
        this.beds = beds;
        this.size = size;
        this.price = price;
    }

    public abstract String getRoomType();

    public void displayDetails(int availability) {
        System.out.println(getRoomType() + " Room:");
        System.out.println("Beds: " + beds);
        System.out.println("Size: " + size + " sqft");
        System.out.println("Price per night: " + price);
        System.out.println("Available Rooms: " + availability);
        System.out.println();
    }
}

// Room Types
class SingleRoom extends Room {
    public SingleRoom() {
        super(1, 250, 1500.0);
    }

    public String getRoomType() {
        return "Single";
    }
}

class DoubleRoom extends Room {
    public DoubleRoom() {
        super(2, 400, 2500.0);
    }

    public String getRoomType() {
        return "Double";
    }
}

class SuiteRoom extends Room {
    public SuiteRoom() {
        super(3, 750, 5000.0);
    }

    public String getRoomType() {
        return "Suite";
    }
}

// Inventory Class (NEW)
class RoomInventory {

    private Map<String, Integer> inventory;

    // Constructor initializes inventory
    public RoomInventory() {
        inventory = new HashMap<>();
        inventory.put("Single", 5);
        inventory.put("Double", 3);
        inventory.put("Suite", 2);
    }

    // Get availability
    public int getAvailability(String roomType) {
        return inventory.getOrDefault(roomType, 0);
    }

    // Update availability
    public void updateAvailability(String roomType, int count) {
        inventory.put(roomType, count);
    }

    // Display all inventory
    public void displayInventory(Room[] rooms) {
        System.out.println("Hotel Room Inventory Status\n");

        for (Room room : rooms) {
            int available = getAvailability(room.getRoomType());
            room.displayDetails(available);
        }
    }
}

// Main Class
public class bookmystayapp{

    public static void main(String[] args) {

        // Create room objects
        Room single = new SingleRoom();
        Room doubleRoom = new DoubleRoom();
        Room suite = new SuiteRoom();

        Room[] rooms = {single, doubleRoom, suite};

        // Initialize inventory
        RoomInventory inventory = new RoomInventory();

        // Display inventory
        inventory.displayInventory(rooms);
    }
}
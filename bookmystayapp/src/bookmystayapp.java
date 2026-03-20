/**
 * BookMyStayApp
 *
 * Demonstrates object-oriented design using abstraction,
 * inheritance, and polymorphism for room types.
 *
 * @author Sanjita
 * @version 2.1
 */

// Abstract class
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
        System.out.println("Available: " + availability);
        System.out.println();
    }
}

// Single Room
class SingleRoom extends Room {
    public SingleRoom() {
        super(1, 250, 1500.0);
    }

    public String getRoomType() {
        return "Single";
    }
}

// Double Room
class DoubleRoom extends Room {
    public DoubleRoom() {
        super(2, 400, 2500.0);
    }

    public String getRoomType() {
        return "Double";
    }
}

// Suite Room
class SuiteRoom extends Room {
    public SuiteRoom() {
        super(3, 750, 5000.0);
    }

    public String getRoomType() {
        return "Suite";
    }
}

// Main class (UPDATED NAME)
public class bookmystayapp{

    public static void main(String[] args) {

        System.out.println("Hotel Room Initialization\n");

        Room single = new SingleRoom();
        Room doubleRoom = new DoubleRoom();
        Room suite = new SuiteRoom();

        int singleAvailable = 5;
        int doubleAvailable = 3;
        int suiteAvailable = 2;

        single.displayDetails(singleAvailable);
        doubleRoom.displayDetails(doubleAvailable);
        suite.displayDetails(suiteAvailable);
    }
}
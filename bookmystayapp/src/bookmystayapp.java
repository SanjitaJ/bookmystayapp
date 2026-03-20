import java.util.*;

/**
 * UseCase6RoomAllocationService
 *
 * Room allocation with unique incremental IDs.
 *
 * @author Sanjita
 * @version 6.1
 */

// Reservation
class Reservation {
    private String guestName;
    private String roomType;

    public Reservation(String guestName, String roomType) {
        this.guestName = guestName;
        this.roomType = roomType;
    }

    public String getGuestName() {
        return guestName;
    }

    public String getRoomType() {
        return roomType;
    }
}

// Inventory
class RoomInventory {
    private Map<String, Integer> inventory = new HashMap<>();

    public RoomInventory() {
        inventory.put("Single", 2);
        inventory.put("Double", 1);
        inventory.put("Suite", 1);
    }

    public int getAvailability(String type) {
        return inventory.getOrDefault(type, 0);
    }

    public void decreaseAvailability(String type) {
        inventory.put(type, getAvailability(type) - 1);
    }
}

// Queue
class BookingQueue {
    private Queue<Reservation> queue = new LinkedList<>();

    public void addRequest(Reservation r) {
        queue.offer(r);
    }

    public Reservation getNext() {
        return queue.poll();
    }

    public boolean isEmpty() {
        return queue.isEmpty();
    }
}

// Booking Service
class BookingService {

    private RoomInventory inventory;

    // Track allocated room IDs
    private Map<String, Set<String>> allocatedRooms = new HashMap<>();

    // Counter for each room type
    private Map<String, Integer> counters = new HashMap<>();

    public BookingService(RoomInventory inventory) {
        this.inventory = inventory;
    }

    public void processBookings(BookingQueue queue) {

        System.out.println("Room Allocation Processing:");

        while (!queue.isEmpty()) {

            Reservation r = queue.getNext();
            String type = r.getRoomType();

            if (inventory.getAvailability(type) > 0) {

                // Initialize structures if not present
                allocatedRooms.putIfAbsent(type, new HashSet<>());
                counters.putIfAbsent(type, 0);

                // Generate ID like Single-1
                int count = counters.get(type) + 1;
                counters.put(type, count);

                String roomId = type + "-" + count;

                // Add to set (ensures uniqueness)
                allocatedRooms.get(type).add(roomId);

                // Decrease inventory
                inventory.decreaseAvailability(type);

                // Output format (MATCHED)
                System.out.println(
                        "Booking confirmed for Guest: " +
                                r.getGuestName() +
                                ", Room ID: " +
                                roomId
                );

            } else {
                System.out.println(
                        "Booking failed for Guest: " +
                                r.getGuestName() +
                                " (No availability)"
                );
            }
        }
    }
}

// Main
public class bookmystayapp{

    public static void main(String[] args) {

        RoomInventory inventory = new RoomInventory();

        BookingQueue queue = new BookingQueue();

        queue.addRequest(new Reservation("Abhi", "Single"));
        queue.addRequest(new Reservation("Subha", "Single"));
        queue.addRequest(new Reservation("Vanmathi", "Suite"));

        BookingService service = new BookingService(inventory);

        service.processBookings(queue);
    }
}
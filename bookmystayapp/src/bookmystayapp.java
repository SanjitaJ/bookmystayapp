import java.util.*;

// Booking Request Class
class BookingRequest {
    String guestName;
    String roomType;

    public BookingRequest(String guestName, String roomType) {
        this.guestName = guestName;
        this.roomType = roomType;
    }
}

// Inventory Class (Thread-Safe)
class Inventory {
    private Map<String, Integer> rooms = new HashMap<>();

    public Inventory() {
        rooms.put("Single", 5);
        rooms.put("Double", 3);
        rooms.put("Suite", 2);
    }

    // Synchronized method to ensure thread safety
    public synchronized boolean allocateRoom(String roomType) {
        int count = rooms.getOrDefault(roomType, 0);
        if (count > 0) {
            rooms.put(roomType, count - 1);
            return true;
        }
        return false;
    }

    public void displayInventory() {
        System.out.println("\nRemaining Inventory:");
        for (String type : rooms.keySet()) {
            System.out.println(type + ": " + rooms.get(type));
        }
    }
}

// Allocation Service
class AllocationService {
    private Map<String, Integer> roomCounter = new HashMap<>();

    public AllocationService() {
        roomCounter.put("Single", 0);
        roomCounter.put("Double", 0);
        roomCounter.put("Suite", 0);
    }

    public synchronized String generateRoomId(String roomType) {
        int count = roomCounter.get(roomType) + 1;
        roomCounter.put(roomType, count);
        return roomType + "-" + count;
    }
}

// Concurrent Booking Processor (Runnable)
class ConcurrentBookingProcessor implements Runnable {
    private Queue<BookingRequest> bookingQueue;
    private Inventory inventory;
    private AllocationService allocationService;

    public ConcurrentBookingProcessor(Queue<BookingRequest> bookingQueue,
                                      Inventory inventory,
                                      AllocationService allocationService) {
        this.bookingQueue = bookingQueue;
        this.inventory = inventory;
        this.allocationService = allocationService;
    }

    @Override
    public void run() {
        while (true) {
            BookingRequest request;

            // Critical Section: Access shared queue
            synchronized (bookingQueue) {
                if (bookingQueue.isEmpty()) {
                    break;
                }
                request = bookingQueue.poll();
            }

            // Process booking
            if (inventory.allocateRoom(request.roomType)) {
                String roomId = allocationService.generateRoomId(request.roomType);
                System.out.println("Booking confirmed for Guest: "
                        + request.guestName + ", Room ID: " + roomId);
            } else {
                System.out.println("Booking failed for Guest: "
                        + request.guestName + " (No rooms available)");
            }
        }
    }
}

// Main Class
public class bookmystayapp{

    public static void main(String[] args) {

        System.out.println("Concurrent Booking Simulation");

        Queue<BookingRequest> bookingQueue = new LinkedList<>();

        // Add booking requests
        bookingQueue.add(new BookingRequest("Abhi", "Single"));
        bookingQueue.add(new BookingRequest("Vanmathi", "Double"));
        bookingQueue.add(new BookingRequest("Kural", "Suite"));
        bookingQueue.add(new BookingRequest("Subha", "Single"));

        Inventory inventory = new Inventory();
        AllocationService allocationService = new AllocationService();

        // Create threads
        Thread t1 = new Thread(
                new ConcurrentBookingProcessor(bookingQueue, inventory, allocationService)
        );

        Thread t2 = new Thread(
                new ConcurrentBookingProcessor(bookingQueue, inventory, allocationService)
        );

        // Start threads
        t1.start();
        t2.start();

        // Wait for threads to finish
        try {
            t1.join();
            t2.join();
        } catch (InterruptedException e) {
            System.out.println("Thread execution interrupted.");
        }

        // Display remaining inventory
        inventory.displayInventory();
    }
}
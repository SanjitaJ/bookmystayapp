import java.util.*;

// Reservation Entity
class Reservation {
    private String reservationId;
    private String roomType;

    public Reservation(String reservationId, String roomType) {
        this.reservationId = reservationId;
        this.roomType = roomType;
    }

    public String getReservationId() {
        return reservationId;
    }

    public String getRoomType() {
        return roomType;
    }
}

// Booking Service (Manages Active Reservations)
class BookingService {
    private Map<String, Reservation> activeBookings = new HashMap<>();

    public void addReservation(Reservation reservation) {
        activeBookings.put(reservation.getReservationId(), reservation);
    }

    public Reservation getReservation(String reservationId) {
        return activeBookings.get(reservationId);
    }

    public void removeReservation(String reservationId) {
        activeBookings.remove(reservationId);
    }
}

// Cancellation Service with Rollback (Stack)
class CancellationService {

    private Map<String, Integer> inventory;
    private BookingService bookingService;
    private Stack<String> rollbackStack = new Stack<>();

    public CancellationService(Map<String, Integer> inventory,
                               BookingService bookingService) {
        this.inventory = inventory;
        this.bookingService = bookingService;
    }

    public void cancelBooking(String reservationId) {

        Reservation reservation = bookingService.getReservation(reservationId);

        // Validate existence
        if (reservation == null) {
            System.out.println("Cancellation failed: Reservation not found.");
            return;
        }

        String roomType = reservation.getRoomType();

        // Push to rollback stack (LIFO)
        rollbackStack.push(reservationId);

        // Restore inventory
        inventory.put(roomType, inventory.get(roomType) + 1);

        // Remove booking
        bookingService.removeReservation(reservationId);

        System.out.println("Booking cancelled successfully. Inventory restored for room type: " + roomType);

        // Print rollback history
        printRollbackHistory();

        // Show updated availability
        System.out.println("\nUpdated " + roomType + " Room Availability: " + inventory.get(roomType));
    }

    private void printRollbackHistory() {
        System.out.println("\nRollback History (Most Recent First):");
        for (int i = rollbackStack.size() - 1; i >= 0; i--) {
            System.out.println("Released Reservation ID: " + rollbackStack.get(i));
        }
    }
}

// Main Class
public class bookmystayapp{

    public static void main(String[] args) {

        System.out.println("Booking Cancellation");

        // Initial inventory
        Map<String, Integer> inventory = new HashMap<>();
        inventory.put("Single", 5);
        inventory.put("Double", 3);
        inventory.put("Suite", 2);

        // Booking service
        BookingService bookingService = new BookingService();

        // Simulate an existing booking
        Reservation r1 = new Reservation("Single-1", "Single");
        bookingService.addReservation(r1);

        // Assume it was previously allocated → inventory reduced
        inventory.put("Single", 5); // After cancellation should become 6

        // Cancellation service
        CancellationService cancellationService =
                new CancellationService(inventory, bookingService);

        // Perform cancellation
        cancellationService.cancelBooking("Single-1");
    }
}
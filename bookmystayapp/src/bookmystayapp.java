import java.util.*;

// Reservation Entity
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

    @Override
    public String toString() {
        return "Guest: " + guestName + ", Room Type: " + roomType;
    }
}

// Booking History (Storage Layer)
class BookingHistory {
    private List<Reservation> reservations;

    public BookingHistory() {
        reservations = new ArrayList<>();
    }

    // Add confirmed reservation
    public void addReservation(Reservation reservation) {
        reservations.add(reservation);
    }

    // Retrieve all reservations (read-only copy)
    public List<Reservation> getAllReservations() {
        return new ArrayList<>(reservations);
    }
}

// Reporting Service (Business Logic Layer)
class BookingReportService {

    public void printBookingReport(List<Reservation> reservations) {
        System.out.println("\nBooking History Report");

        if (reservations.isEmpty()) {
            System.out.println("No bookings available.");
            return;
        }

        for (Reservation r : reservations) {
            System.out.println(r);
        }
    }
}

// Main Class
public class bookmystayapp{

    public static void main(String[] args) {

        System.out.println("Booking History and Reporting");

        // Initialize components
        BookingHistory bookingHistory = new BookingHistory();
        BookingReportService reportService = new BookingReportService();

        // Simulate confirmed bookings
        bookingHistory.addReservation(new Reservation("Abhi", "Single"));
        bookingHistory.addReservation(new Reservation("Subha", "Double"));
        bookingHistory.addReservation(new Reservation("Vanmathi", "Suite"));

        // Admin requests report
        List<Reservation> reservations = bookingHistory.getAllReservations();
        reportService.printBookingReport(reservations);
    }
}
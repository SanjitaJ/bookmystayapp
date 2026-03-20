import java.util.*;

/**
 * UseCase7AddOnServiceSelection
 *
 * Simplified add-on service calculation.
 *
 * @author Sanjita
 * @version 7.1
 */

// Service class
class Service {
    private String name;
    private double cost;

    public Service(String name, double cost) {
        this.name = name;
        this.cost = cost;
    }

    public double getCost() {
        return cost;
    }
}

// Manager
class AddOnServiceManager {

    private Map<String, List<Service>> serviceMap = new HashMap<>();

    public void addService(String reservationId, Service service) {
        serviceMap.putIfAbsent(reservationId, new ArrayList<>());
        serviceMap.get(reservationId).add(service);
    }

    public void displayTotalCost(String reservationId) {

        System.out.println("Add-On Service Selection");
        System.out.println("Reservation ID: " + reservationId);

        List<Service> services = serviceMap.get(reservationId);

        double total = 0;

        if (services != null) {
            for (Service s : services) {
                total += s.getCost();
            }
        }

        System.out.println("Total Add-On Cost: " + total);
    }
}

// Main
public class bookmystayapp{

    public static void main(String[] args) {

        AddOnServiceManager manager = new AddOnServiceManager();

        String reservationId = "Single-1";

        // Add services (example → total = 1500)
        manager.addService(reservationId, new Service("Breakfast", 500));
        manager.addService(reservationId, new Service("Spa", 1000));

        // Display result
        manager.displayTotalCost(reservationId);
    }
}
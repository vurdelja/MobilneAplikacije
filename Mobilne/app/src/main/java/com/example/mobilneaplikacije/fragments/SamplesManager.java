package com.example.mobilneaplikacije.fragments;

import com.example.mobilneaplikacije.model.Availability;
import com.example.mobilneaplikacije.model.Company;
import com.example.mobilneaplikacije.model.Event;
import com.example.mobilneaplikacije.model.Service;
import com.example.mobilneaplikacije.model.Users.Worker;
import com.example.mobilneaplikacije.model.enums.UserRole;
import com.google.firebase.Timestamp;
import com.google.firebase.firestore.FirebaseFirestore;
import com.example.mobilneaplikacije.model.Budget;
import com.example.mobilneaplikacije.model.Product;
import com.example.mobilneaplikacije.model.Package;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SamplesManager {

    private FirebaseFirestore db;

    public SamplesManager() {
        db = FirebaseFirestore.getInstance();
    }

    public void createSampleBudget() {
        // Create sample products
        Product product1 = new Product();
        product1.setName("Product 1");
        product1.setDescription("Description of Product 1");
        product1.setPrice(29.99);
        product1.setSubCategory("product");

        Product product2 = new Product();
        product2.setName("Product 2");
        product2.setDescription("Description of Product 2");
        product2.setPrice(39.99);
        product2.setSubCategory("product");

        // Create a sample budget
        Budget budget = new Budget();
        budget.setEventName("Sample Event");
        budget.getProducts().add(product1);
        budget.getProducts().add(product2);
        budget.setTotalCost(product1.getPrice() + product2.getPrice());

        // Save the budget to Firestore
        db.collection("budgets")
                .add(budget)
                .addOnSuccessListener(documentReference -> {
                    System.out.println("Budget added with ID: " + documentReference.getId());
                })
                .addOnFailureListener(e -> {
                    System.err.println("Error adding budget: " + e.getMessage());
                });
    }
/*
    public void createSampleWorker() {
        // Define working hours for the worker
        Map<String, String> workingHours = new HashMap<>();
        workingHours.put("Monday", "08:00-16:00");
        workingHours.put("Tuesday", "08:00-16:00");
        workingHours.put("Wednesday", "08:00-16:00");
        workingHours.put("Thursday", "08:00-16:00");
        workingHours.put("Friday", "08:00-16:00");

        // Create a sample worker
        Worker worker = new Worker(
                "igor@example.com",
                "password123",
                "Igor",
                "Petrovic",
                "123 Main St",
                "123-456-7890",
                UserRole.WORKER,
                workingHours,
                null,
                null
        );

        // Save the worker to Firestore
        db.collection("workers")
                .add(worker)
                .addOnSuccessListener(documentReference -> {
                    String workerId = documentReference.getId();
                    db.collection("workers").document(workerId)
                            .update("workerId", workerId)
                            .addOnSuccessListener(aVoid -> {
                                System.out.println("Worker ID updated: " + workerId);
                                createSampleAvailabilities(workerId);
                            })
                            .addOnFailureListener(e -> {
                                System.err.println("Error updating worker ID: " + e.getMessage());
                            });
                })
                .addOnFailureListener(e -> {
                    System.err.println("Error adding worker: " + e.getMessage());
                });
    }*/

    public void createSampleServices() {
        // Sample Service 1
        Service service1 = new Service(
                "Foto i video",
                "Snimanje dronom",
                "Snimanje dronom",
                "Ovo je snimanje iz vazduha sa dronom",
                "ne radimo praznicima",
                3000,
                0,
                Arrays.asList("Slika 1", "Slika 2", "Slika 3"),
                Arrays.asList("vencanje", "krstenje", "1 rodjendan"),
                true,
                true,
                Arrays.asList("Igor", "Pera"),
                "2",
                null,
                null,
                12,
                2,
                "ručno",
                "pending"
        );

        // Sample Service 2
        Service service2 = new Service(
                "Foto i video",
                "Videografija",
                "Snimanje kamerom 4k",
                "Ovo je snimanje u 4k rezoluciji",
                "/",
                5000,
                0,
                Arrays.asList("Slika 1", "Slika 2", "Slika 3"),
                Arrays.asList("vencanje", "krstenje", "1 rodjendan"),
                true,
                true,
                Arrays.asList("Igor", "Pera"),
                null,
                "1",
                "5",
                12,
                2,
                "ručno",
                "pending"
        );

        // Sample Service 3
        Service service3 = new Service(
                "Foto i video",
                "Fotografisanje",
                "Fotografisanje događaja",
                "Fotografisanje događaja sa najboljim kamerama.",
                "/",
                5000,
                0,
                Arrays.asList("Slika 1", "Slika 2", "Slika 3"),
                Arrays.asList("vencanje", "krstenje", "1 rodjendan"),
                true,
                true,
                Arrays.asList("Pera"),
                null,
                "1h",
                "5h",
                12,
                2,
                "ručno",
                "pending"
        );

        // Save the services to Firestore
        addServiceToFirestore(service1);
        addServiceToFirestore(service2);
        addServiceToFirestore(service3);
    }

    public void createSamplePackage() {
        // Create sample products
        Product product1 = new Product();
        product1.setName("Album sa 50 fotografija");
        product1.setDescription("Album sa 50 fotografija");
        product1.setPrice(2000);
        product1.setSubCategory("product");

        // Create sample services
        Service service1 = new Service();
        service1.setCategory("Foto i video");
        service1.setSubcategory("Snimanje dronom");
        service1.setName("Snimanje dronom");
        service1.setDescription("Ovo je snimanje iz vazduha sa dronom");
        service1.setPrice(6000);
        service1.setDuration("2h");
        service1.setDiscount(0);
        service1.setEmployees(new ArrayList<>());  // Add appropriate employee names
        service1.setEventTypes(new ArrayList<>());  // Add appropriate event types
        service1.setVisibility(true);
        service1.setAvailability(true);
        service1.setReservationLeadTime(12);
        service1.setCancellationLeadTime(2);
        service1.setConfirmationMethod("manual");

        // Create the package
        List<Product> products = new ArrayList<>();
        products.add(product1);

        List<Service> services = new ArrayList<>();
        services.add(service1);


        List<String> eventTypes = new ArrayList<>();
        eventTypes.add("vencanje");
        eventTypes.add("krstenje");
        eventTypes.add("1 rodjendan");

        List<String> images = new ArrayList<>();
        images.add("slike snimanje dronom");
        images.add("slike albuma");
        images.add("slike fotografija");

        Package packageSample = new Package(
                "Snimanje događaja",
                "Ovaj paket uključuje sve potrebne stavke za dekoraciju vašeg venčanja.",
                5.0,
                true,
                true,
                "Foto i video",
                products,
                services,
                eventTypes,
                18000.0,
                images,
                12,
                14,
                "manual",
                "active"
        );

        // Save the package to Firestore
        db.collection("packages")
                .add(packageSample)
                .addOnSuccessListener(documentReference -> {
                    System.out.println("Package added with ID: " + documentReference.getId());
                })
                .addOnFailureListener(e -> {
                    System.err.println("Error adding package: " + e.getMessage());
                });
    }

    private void addServiceToFirestore(Service service) {
        db.collection("services")
                .add(service)
                .addOnSuccessListener(documentReference -> {
                    System.out.println("Service added with ID: " + documentReference.getId());
                })
                .addOnFailureListener(e -> {
                    System.err.println("Error adding service: " + e.getMessage());
                });
    }

    public void createSamplePackages() {
        // Package 1
        Package package1 = new Package(
                "Snimanje događaja - Paket 1",
                "Ovaj paket uključuje sve potrebne stavke za dekoraciju vašeg venčanja.",
                5.0,
                true,
                true,
                "Foto i video",
                new ArrayList<>(), // Products will be added later
                new ArrayList<>(), // Services will be added later
                Arrays.asList("vencanje", "krstenje", "1 rodjendan"),
                18000.0,
                Arrays.asList("slike snimanje dronom", "slike albuma", "slike fotografija"),
                12,
                14,
                "manual",
                "active"
        );

        // Package 2
        Package package2 = new Package(
                "Snimanje događaja - Paket 2",
                "Ovaj paket uključuje sve potrebne stavke za dekoraciju vašeg venčanja.",
                5.0,
                true,
                true,
                "Foto i video",
                new ArrayList<>(), // Products will be added later
                new ArrayList<>(), // Services will be added later
                Arrays.asList("vencanje", "krstenje", "1 rodjendan"),
                18000.0,
                Arrays.asList("slike snimanje dronom", "slike albuma", "slike fotografija"),
                12,
                14,
                "manual",
                "active"
        );

        // Package 3
        Package package3 = new Package(
                "Snimanje događaja - Paket 3",
                "Ovaj paket uključuje sve potrebne stavke za dekoraciju vašeg venčanja.",
                5.0,
                true,
                true,
                "Foto i video",
                new ArrayList<>(), // Products will be added later
                new ArrayList<>(), // Services will be added later
                Arrays.asList("vencanje", "krstenje", "1 rodjendan"),
                18000.0,
                Arrays.asList("slike snimanje dronom", "slike albuma", "slike fotografija"),
                12,
                14,
                "manual",
                "active"
        );

        addSampleProductsAndServicesToPackage1(package1);
        addSampleProductsToPackage2(package2);
        addSampleServiceAndProductToPackage3(package3);
    }

    private void addSampleProductsAndServicesToPackage1(Package package1) {
        // Add services to package 1
        List<String> serviceIds1 = Arrays.asList("mRvenCB1az9sphXuLte8", "l8VquY4sUX0SHyQ2wit0");
        for (String serviceId : serviceIds1) {
            db.collection("services").document(serviceId).get()
                    .addOnSuccessListener(documentSnapshot -> {
                        Service service = documentSnapshot.toObject(Service.class);
                        if (service != null) {
                            package1.getServices().add(service);
                        }
                    });
        }

        // Add product to package 1
        db.collection("products").document("na61KxtBaMvcl7cAgNaI").get()
                .addOnSuccessListener(documentSnapshot -> {
                    Product product = documentSnapshot.toObject(Product.class);
                    if (product != null) {
                        package1.getProducts().add(product);
                    }
                });

        // Save package 1 to Firestore
        savePackageToFirestore(package1);
    }

    private void addSampleProductsToPackage2(Package package2) {
        // Add products to package 2
        List<String> productIds2 = Arrays.asList("Ab1lZuS171dvJbQaZZQo", "gTTqUN8Jcb0o8GusJsIL", "vtquHlXG8ciLmKfGklSI");
        for (String productId : productIds2) {
            db.collection("products").document(productId).get()
                    .addOnSuccessListener(documentSnapshot -> {
                        Product product = documentSnapshot.toObject(Product.class);
                        if (product != null) {
                            package2.getProducts().add(product);
                        }
                    });
        }

        // Save package 2 to Firestore
        savePackageToFirestore(package2);
    }

    private void addSampleServiceAndProductToPackage3(Package package3) {
        // Add service to package 3
        db.collection("services").document("mRvenCB1az9sphXuLte8").get()
                .addOnSuccessListener(documentSnapshot -> {
                    Service service = documentSnapshot.toObject(Service.class);
                    if (service != null) {
                        package3.getServices().add(service);
                    }
                });

        // Add product to package 3
        db.collection("products").document("vtquHlXG8ciLmKfGklSI").get()
                .addOnSuccessListener(documentSnapshot -> {
                    Product product = documentSnapshot.toObject(Product.class);
                    if (product != null) {
                        package3.getProducts().add(product);
                    }
                });

        // Save package 3 to Firestore
        savePackageToFirestore(package3);
    }

    private void savePackageToFirestore(Package packageSample) {
        db.collection("packages")
                .add(packageSample)
                .addOnSuccessListener(documentReference -> {
                    System.out.println("Package added with ID: " + documentReference.getId());
                })
                .addOnFailureListener(e -> {
                    System.err.println("Error adding package: " + e.getMessage());
                });
    }


    public void createSampleEvents() {
        Event event1 = new Event(
                "Wedding",
                "John and Jane's Wedding",
                "A beautiful wedding event",
                100,
                "Central Park",
                "2024-07-15",
                false
        );

        Event event2 = new Event(
                "Birthday",
                "Tom's 30th Birthday",
                "Celebrating Tom's 30th birthday",
                50,
                "Tom's House",
                "2024-08-20",
                true
        );

        Event event3 = new Event(
                "Conference",
                "Tech Conference 2024",
                "Annual tech conference",
                300,
                "Convention Center",
                "2024-09-10",
                false
        );

        Event event4 = new Event(
                "Workshop",
                "Photography Workshop",
                "Learn the basics of photography",
                20,
                "Community Center",
                "2024-10-05",
                false
        );

        Event event5 = new Event(
                "Anniversary",
                "Company 10th Anniversary",
                "Celebrating 10 years of success",
                150,
                "Headquarters",
                "2024-11-25",
                true
        );

        // List of events to add
        Event[] events = {event1, event2, event3, event4, event5};

        // Adding events to Firestore
        for (Event event : events) {
            db.collection("events")
                    .add(event)
                    .addOnSuccessListener(documentReference -> {
                        System.out.println("Event added with ID: " + documentReference.getId());
                    })
                    .addOnFailureListener(e -> {
                        System.err.println("Error adding event: " + e.getMessage());
                    });
        }
    }

    private Timestamp createTime(Date date, int hour, int minute) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(Calendar.HOUR_OF_DAY, hour);
        calendar.set(Calendar.MINUTE, minute);
        return new Timestamp(calendar.getTime());
    }

    public void createSampleWorkers() {
        Worker worker1 = createWorker(
                "igor@example.com",
                "password123",
                "Igor",
                "Petrovic",
                "123 Main St",
                "123-456-7890",
                UserRole.WORKER,
                createWorkingHours(),
                null,
                "jgttEecRNW3Rzq0eKBnW"
        );

        Worker worker2 = createWorker(
                "pera@example.com",
                "password123",
                "Pera",
                "Peric",
                "456 Main St",
                "234-567-8901",
                UserRole.WORKER,
                createWorkingHours(),
                null,
                "hjkkEbcFNW3Qw3rJHGnN"
        );

        Worker worker3 = createWorker(
                "marko@example.com",
                "password123",
                "Marko",
                "Markovic",
                "789 Main St",
                "345-678-9012",
                UserRole.WORKER,
                createWorkingHours(),
                null,
                "jyttQecPNW3Rfw0rLJnQ"
        );

        Worker worker4 = createWorker(
                "ana@example.com",
                "password123",
                "Ana",
                "Anic",
                "101 Main St",
                "456-789-0123",
                UserRole.WORKER,
                createWorkingHours(),
                null,
                "lkkkRwcWNW3Qgw1rKOnR"
        );

        Worker worker5 = createWorker(
                "jovana@example.com",
                "password123",
                "Jovana",
                "Jovic",
                "202 Main St",
                "567-890-1234",
                UserRole.WORKER,
                createWorkingHours(),
                null,
                "pkhhDvcZNW3Rfw2rMPnS"
        );

        addWorkerToFirestore(worker1);
        addWorkerToFirestore(worker2);
        addWorkerToFirestore(worker3);
        addWorkerToFirestore(worker4);
        addWorkerToFirestore(worker5);
    }

    private Worker createWorker(String email, String password, String firstName, String lastName, String address, String phone, UserRole role, Map<String, String> workingHours, Company company, String workerId) {
        return new Worker(email, password, firstName, lastName, address, phone, role, workingHours, company, workerId);
    }

    private Map<String, String> createWorkingHours() {
        Map<String, String> workingHours = new HashMap<>();
        workingHours.put("Monday", "08:00-16:00");
        workingHours.put("Tuesday", "08:00-16:00");
        workingHours.put("Wednesday", "08:00-16:00");
        workingHours.put("Thursday", "08:00-16:00");
        workingHours.put("Friday", "08:00-16:00");
        return workingHours;
    }

    private void addWorkerToFirestore(Worker worker) {
        db.collection("workers")
                .add(worker)
                .addOnSuccessListener(documentReference -> {
                    System.out.println("Worker added with ID: " + documentReference.getId());
                    createSampleAvailabilities(documentReference.getId());
                })
                .addOnFailureListener(e -> {
                    System.err.println("Error adding worker: " + e.getMessage());
                });
    }

    public void createSampleAvailabilities(String documentId) {
        // Define sample availability for 3 days
        Calendar calendar = Calendar.getInstance();
        for (int i = 0; i < 3; i++) {
            calendar.add(Calendar.DAY_OF_YEAR, i);
            Date date = calendar.getTime();

            Availability availability1 = new Availability(new Timestamp(date), createTime(date, 8, 0), createTime(date, 10, 0), "free");
            Availability availability2 = new Availability(new Timestamp(date), createTime(date, 10, 0), createTime(date, 12, 0), "busy");
            Availability availability3 = new Availability(new Timestamp(date), createTime(date, 12, 0), createTime(date, 15, 0), "free");
            Availability availability4 = new Availability(new Timestamp(date), createTime(date, 15, 0), createTime(date, 16, 0), "busy");
            Availability availability5 = new Availability(new Timestamp(date), createTime(date, 8, 0), createTime(date, 10, 0), "free");
            Availability availability6 = new Availability(new Timestamp(date), createTime(date, 10, 0), createTime(date, 12, 0), "busy");
            Availability availability7 = new Availability(new Timestamp(date), createTime(date, 12, 0), createTime(date, 15, 0), "free");
            Availability availability8 = new Availability(new Timestamp(date), createTime(date, 15, 0), createTime(date, 16, 0), "busy");

            // Save availabilities to Firestore
            db.collection("workers").document(documentId).collection("availability")
                    .add(availability1)
                    .addOnSuccessListener(aVoid -> System.out.println("Availability added for worker with ID: " + documentId))
                    .addOnFailureListener(e -> System.err.println("Error adding availability: " + e.getMessage()));

            db.collection("workers").document(documentId).collection("availability")
                    .add(availability2)
                    .addOnSuccessListener(aVoid -> System.out.println("Availability added for worker with ID: " + documentId))
                    .addOnFailureListener(e -> System.err.println("Error adding availability: " + e.getMessage()));

            db.collection("workers").document(documentId).collection("availability")
                    .add(availability3)
                    .addOnSuccessListener(aVoid -> System.out.println("Availability added for worker with ID: " + documentId))
                    .addOnFailureListener(e -> System.err.println("Error adding availability: " + e.getMessage()));

            db.collection("workers").document(documentId).collection("availability")
                    .add(availability4)
                    .addOnSuccessListener(aVoid -> System.out.println("Availability added for worker with ID: " + documentId))
                    .addOnFailureListener(e -> System.err.println("Error adding availability: " + e.getMessage()));
            // Save availabilities to Firestore
            db.collection("workers").document(documentId).collection("availability")
                    .add(availability5)
                    .addOnSuccessListener(aVoid -> System.out.println("Availability added for worker with ID: " + documentId))
                    .addOnFailureListener(e -> System.err.println("Error adding availability: " + e.getMessage()));

            db.collection("workers").document(documentId).collection("availability")
                    .add(availability6)
                    .addOnSuccessListener(aVoid -> System.out.println("Availability added for worker with ID: " + documentId))
                    .addOnFailureListener(e -> System.err.println("Error adding availability: " + e.getMessage()));

            db.collection("workers").document(documentId).collection("availability")
                    .add(availability7)
                    .addOnSuccessListener(aVoid -> System.out.println("Availability added for worker with ID: " + documentId))
                    .addOnFailureListener(e -> System.err.println("Error adding availability: " + e.getMessage()));

            db.collection("workers").document(documentId).collection("availability")
                    .add(availability8)
                    .addOnSuccessListener(aVoid -> System.out.println("Availability added for worker with ID: " + documentId))
                    .addOnFailureListener(e -> System.err.println("Error adding availability: " + e.getMessage()));
        }
    }



    private void addAvailabilityToFirestore(String documentId, Availability availability) {
        db.collection("workers")
                .document(documentId)
                .collection("availabilities")
                .add(availability)
                .addOnSuccessListener(documentReference -> {
                    System.out.println("Availability added with ID: " + documentReference.getId());
                })
                .addOnFailureListener(e -> {
                    System.err.println("Error adding availability: " + e.getMessage());
                });
    }
}

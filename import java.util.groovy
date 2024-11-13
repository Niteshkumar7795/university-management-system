import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

// Model Classes
class User {
    private String userId;
    private String name;
    private String email;
    private String password;
    private String role; // e.g., Student or Instructor

    public User(String userId, String name, String email, String password, String role) {
        this.userId = userId;
        this.name = name;
        this.email = email;
        this.password = password;
        this.role = role;
    }

    // Getters
    public String getUserId() { return userId; }
    public String getName() { return name; }
    public String getEmail() { return email; }
    public String getPassword() { return password; }
    public String getRole() { return role; }
}

class Course {
    private String courseId;
    private String courseName;
    private String description;

    public Course(String courseId, String courseName, String description) {
        this.courseId = courseId;
        this.courseName = courseName;
        this.description = description;
    }

    // Getters
    public String getCourseId() { return courseId; }
    public String getCourseName() { return courseName; }
    public String getDescription() { return description; }
}

// Main Application Class
public class GalgotiasUniversityPortal {
    private static List<User> users = new ArrayList<>();
    private static List<Course> courses = new ArrayList<>();
    private static List<String> enrollments = new ArrayList<>(); // Store userId and courseId

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        User currentUser = null; // To store currently logged-in user

        while (true) {
            System.out.println("1. Register");
            System.out.println("2. Login");
            System.out.println("3. Add Course (Admin only)");
            System.out.println("4. Display Courses");
            System.out.println("5. Enroll in Course");
            System.out.println("6. View Enrolled Courses");
            System.out.println("7. Exit");
            System.out.print("Choose an option: ");
            int option = scanner.nextInt();
            scanner.nextLine(); // Consume newline

            switch (option) {
                case 1: // Register
                    System.out.print("User ID: ");
                    String userId = scanner.nextLine();
                    System.out.print("Name: ");
                    String name = scanner.nextLine();
                    System.out.print("Email: ");
                    String email = scanner.nextLine();
                    System.out.print("Password: ");
                    String password = scanner.nextLine();
                    System.out.print("Role (Student/Instructor): ");
                    String role = scanner.nextLine();

                    User user = new User(userId, name, email, password, role);
                    users.add(user);
                    System.out.println("User registered successfully!");
                    break;

                case 2: // Login
                    System.out.print("Email: ");
                    email = scanner.nextLine();
                    System.out.print("Password: ");
                    password = scanner.nextLine();

                    currentUser = loginUser(email, password);
                    if (currentUser != null) {
                        System.out.println("Welcome, " + currentUser.getName() + "!");
                    } else {
                        System.out.println("Invalid credentials.");
                    }
                    break;

                case 3: // Add Course (Admin only)
                    if (currentUser != null && currentUser.getRole().equalsIgnoreCase("Instructor")) {
                        System.out.print("Course ID: ");
                        String courseId = scanner.nextLine();
                        System.out.print("Course Name: ");
                        String courseName = scanner.nextLine();
                        System.out.print("Description: ");
                        String description = scanner.nextLine();

                        Course course = new Course(courseId, courseName, description);
                        courses.add(course);
                        System.out.println("Course added successfully!");
                    } else {
                        System.out.println("You must be an Instructor to add courses.");
                    }
                    break;

                case 4: // Display Courses
                    System.out.println("Available Courses:");
                    for (Course c : courses) {
                        System.out.println("Course ID: " + c.getCourseId() +
                                           ", Course Name: " + c.getCourseName() +
                                           ", Description: " + c.getDescription());
                    }
                    break;

                case 5: // Enroll in Course
                    if (currentUser != null) {
                        System.out.print("Enter Course ID to enroll: ");
                        String courseId = scanner.nextLine();
                        enrollInCourse(currentUser.getUserId(), courseId);
                    } else {
                        System.out.println("You must be logged in to enroll in courses.");
                    }
                    break;

                case 6: // View Enrolled Courses
                    if (currentUser != null) {
                        System.out.println("Enrolled Courses:");
                        for (String enrollment : enrollments) {
                            String[] parts = enrollment.split(":");
                            if (parts[0].equals(currentUser.getUserId())) {
                                String courseId = parts[1];
                                Course course = getCourseById(courseId);
                                if (course != null) {
                                    System.out.println("Course ID: " + course.getCourseId() +
                                                       ", Course Name: " + course.getCourseName());
                                }
                            }
                        }
                    } else {
                        System.out.println("You must be logged in to view enrolled courses.");
                    }
                    break;

                case 7: // Exit
                    System.out.println("Exiting the portal. Goodbye!");
                    scanner.close();
                    return;

                default:
                    System.out.println("Invalid option. Please try again.");
            }
        }
    }

    private static User loginUser(String email, String password) {
        for (User user : users) {
            if (user.getEmail().equals(email) && user.getPassword().equals(password)) {
                return user;
            }
        }
        return null; // Invalid credentials
    }

    private static void enrollInCourse(String userId, String courseId) {
        Course course = getCourseById(courseId);
        if (course != null) {
            enrollments.add(userId + ":" + courseId);
            System.out.println("Successfully enrolled in Course ID: " + courseId);
        } else {
            System.out.println("Course not found.");
        }
    }

    private static Course getCourseById(String courseId) {
        for (Course course : courses) {
            if (course.getCourseId().equals(courseId)) {
                return course;
            }
        }
        return null; // Course not found
    }
}

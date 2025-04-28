package FitPlan.controller;

import FitPlan.model.User;
import FitPlan.service.ProgressService;
import FitPlan.service.UserService;
import FitPlan.view.InputHelper;
import FitPlan.view.ViewRenderer;
import FitPlan.repository.ProgressRepository;
import FitPlan.repository.UserRepository;

import java.util.Scanner;

public class ConsoleRouter {

    private final AuthController authController;
    private final MenuController menuController;
    private final ViewRenderer viewRenderer;
    private final InputHelper inputHelper; // For closing scanner

    private User currentUser = null;

    public ConsoleRouter() {
        Scanner scanner = new Scanner(System.in);
        this.inputHelper = new InputHelper(scanner);
        this.viewRenderer = new ViewRenderer();

        // Repositories (using mock data constructors)
        UserRepository userRepository = new UserRepository();
        ProgressRepository progressRepository = new ProgressRepository();

        // Services
        UserService userService = new UserService(userRepository);
        ProgressService progressService = new ProgressService(progressRepository);

        // Controllers
        this.authController = new AuthController(userService, inputHelper, viewRenderer);
        this.menuController = new MenuController(userService, progressService, inputHelper, viewRenderer);
        // --- End Dependency Injection Setup ---
    }

    public void run() {
        viewRenderer.displayWelcomeMessage();

        while (true) {
            if (currentUser == null) {
                // Not logged in, show authentication flow
                currentUser = authController.authenticate();
                if (currentUser == null && !menuController.isUserLoggedOut()) {
                    // User chose to exit from the auth menu
                    break; // Exit the main application loop
                }
                // If authenticate() returns a user, the loop continues to the main menu part
                // If authenticate() returns null because user chose exit, loop breaks.
            } else {
                // Logged in, show main menu flow
                menuController.showMainMenu(currentUser);

                // Check if the user logged out from the main menu
                if (menuController.isUserLoggedOut()) {
                    currentUser = null; // Clear current user
                    menuController.resetLogoutFlag(); // Reset the flag in MenuController
                    viewRenderer.clearConsole();
                    viewRenderer.displayMessage("You have been logged out.");
                    // Loop continues, will go back to authentication flow
                }
                // If user didn't logout, loop continues showing main menu again (handled within showMainMenu)
                // This external loop structure mainly handles the transition between logged-out and logged-in states.
            }
        }

        // Close resources
        System.out.println("Closing application resources...");
        // It's good practice to close the scanner when the application exits.
        // Note: Closing System.in is generally discouraged if other parts of a larger app might need it.
        // In a simple standalone console app like this, it's acceptable.
        if (inputHelper != null) {
             // Get the underlying scanner and close it
            try {
                java.lang.reflect.Field scannerField = InputHelper.class.getDeclaredField("scanner");
                scannerField.setAccessible(true);
                Scanner sc = (Scanner) scannerField.get(inputHelper);
                sc.close();
                 System.out.println("Scanner closed.");
            } catch (NoSuchFieldException | IllegalAccessException e) {
                System.err.println("Could not close scanner resource: " + e.getMessage());
            }

        }
         System.out.println("FitPlan closed.");
    }

    // Main entry point for the application
    public static void main(String[] args) {
        ConsoleRouter app = new ConsoleRouter();
        app.run();
    }
}
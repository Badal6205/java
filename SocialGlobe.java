import java.util.*;

public class SocialGlobe {
    static class User {
        String username, email, password, role;
        String bio = "", country = "", niche = "", securityAnswer;
        List<String> campaigns = new ArrayList<>();
        List<String> applied = new ArrayList<>();
        List<Integer> receivedRatings = new ArrayList<>();

        User(String username, String email, String password, String role, String securityAnswer) {
            this.username = username;
            this.email = email;
            this.password = password;
            this.role = role;
            this.securityAnswer = securityAnswer;
        }
    }

    static class Campaign {
        String title, description, createdBy, status = "open";
        List<String> applicants = new ArrayList<>();

        Campaign(String title, String description, String createdBy) {
            this.title = title;
            this.description = description;
            this.createdBy = createdBy;
        }
    }

    static class Message {
        String from, to, content, timestamp;

        Message(String from, String to, String content, String timestamp) {
            this.from = from;
            this.to = to;
            this.content = content;
            this.timestamp = timestamp;
        }
    }

    static Scanner sc = new Scanner(System.in);
    static List<User> users = new ArrayList<>();
    static List<Campaign> campaigns = new ArrayList<>();
    static List<Message> messages = new ArrayList<>();
    static User loggedInUser = null;

    public static void main(String[] args) {
        while (true) {
            System.out.println("\n=== Welcome to SocialGlobe ===");
            System.out.println("1. Register");
            System.out.println("2. Login");
            System.out.println("3. Forgot Password");
            System.out.println("4. Exit");
            System.out.print("Choose an option: ");
            String choice = sc.nextLine();

            switch (choice) {
                case "1" -> register();
                case "2" -> login();
                case "3" -> forgotPassword();
                case "4" -> {
                    System.out.println("Thanks for using SocialGlobe!");
                    return;
                }
                default -> System.out.println("Invalid choice.");
            }
        }
    }

    static void register() {
        System.out.println("\n=== Register ===");
        System.out.print("Username: ");
        String username = sc.nextLine();
        System.out.print("Email: ");
        String email = sc.nextLine();
        for (User u : users) {
            if (u.email.equalsIgnoreCase(email)) {
                System.out.println("An account with this email already exists. Please login.");
                return;
            }
        }
        System.out.print("Password: ");
        String password = sc.nextLine();

        String role = "";
        while (true) {
            System.out.print("Choose Role (influencer / brand): ");
            role = sc.nextLine().toLowerCase();
            if (role.equals("influencer") || role.equals("brand")) break;
            System.out.println("Invalid role. Try again.");
        }

        System.out.print("Security Question - What is your favorite color?: ");
        String securityAnswer = sc.nextLine();

        users.add(new User(username, email, password, role, securityAnswer));
        System.out.println("Registered as " + role + " successfully!");
    }

    static void login() {
        System.out.println("\n=== Login ===");
        System.out.print("Email: ");
        String email = sc.nextLine();
        System.out.print("Password: ");
        String password = sc.nextLine();

        for (User user : users) {
            if (user.email.equals(email) && user.password.equals(password)) {
                loggedInUser = user;
                System.out.println("Logged in as " + user.role);
                checkProfileCompletion();
                dashboard();
                return;
            }
        }
        System.out.println("Invalid login credentials.");
    }

    static void forgotPassword() {
        System.out.println("\n=== Forgot Password ===");
        System.out.print("Enter your registered Email: ");
        String email = sc.nextLine();

        for (User user : users) {
            if (user.email.equals(email)) {
                System.out.print("Security Question - What is your favorite color?: ");
                String answer = sc.nextLine();
                if (user.securityAnswer.equalsIgnoreCase(answer)) {
                    System.out.print("Enter New Password: ");
                    String newPassword = sc.nextLine();
                    user.password = newPassword;
                    System.out.println("Password reset successfully!");
                    return;
                } else {
                    System.out.println("Incorrect answer. Cannot reset password.");
                    return;
                }
            }
        }
        System.out.println("Email not found.");
    }

    static void checkProfileCompletion() {
        if (loggedInUser.bio.isEmpty() || loggedInUser.country.isEmpty() || loggedInUser.niche.isEmpty()) {
            System.out.println("\n--- Complete Your Profile ---");
            System.out.print("Bio: ");
            loggedInUser.bio = sc.nextLine();
            System.out.print("Country: ");
            loggedInUser.country = sc.nextLine();
            System.out.print("Niche (e.g., Tech, Fashion): ");
            loggedInUser.niche = sc.nextLine();
            System.out.println("Profile updated.");
        }
    }

    static void dashboard() {
        while (loggedInUser != null) {
            System.out.println("\n=== " + loggedInUser.role.toUpperCase() + " DASHBOARD ===");
            System.out.println("Welcome, " + loggedInUser.username);

            if (loggedInUser.role.equals("influencer")) {
                System.out.println("1. View Open Campaigns");
                System.out.println("2. My Applications");
                System.out.println("3. View Profile");
                System.out.println("4. View User Profiles");
                System.out.println("5. Search Campaigns");
                System.out.println("6. Messages");
                System.out.println("7. Change Password");
                System.out.println("8. Logout");
                System.out.print("Choose: ");
                String ch = sc.nextLine();

                switch (ch) {
                    case "1" -> viewCampaigns();
                    case "2" -> viewApplications();
                    case "3" -> viewMyProfile();
                    case "4" -> viewUserProfile();
                    case "5" -> searchCampaigns();
                    case "6" -> messagingMenu();
                    case "7" -> changePassword();
                    case "8" -> loggedInUser = null;
                    default -> System.out.println("Invalid.");
                }
            } else {
                System.out.println("1. Create Campaign");
                System.out.println("2. My Campaigns");
                System.out.println("3. View Profile");
                System.out.println("4. View User Profiles");
                System.out.println("5. Messages");
                System.out.println("6. Change Password");
                System.out.println("7. Logout");
                System.out.print("Choose: ");
                String ch = sc.nextLine();

                switch (ch) {
                    case "1" -> createCampaign();
                    case "2" -> viewMyCampaigns();
                    case "3" -> viewMyProfile();
                    case "4" -> viewUserProfile();
                    case "5" -> messagingMenu();
                    case "6" -> changePassword();
                    case "7" -> loggedInUser = null;
                    default -> System.out.println("Invalid.");
                }
            }
        }
    }

    static void changePassword() {
        System.out.print("\nEnter current password: ");
        String current = sc.nextLine();

        if (!loggedInUser.password.equals(current)) {
            System.out.println("Incorrect current password.");
            return;
        }

        System.out.print("Enter new password: ");
        String newPassword = sc.nextLine();
        loggedInUser.password = newPassword;
        System.out.println("Password changed successfully!");
    }

    static void viewMyProfile() {
        System.out.println("\n--- Your Profile ---");
        System.out.println("Username: " + loggedInUser.username);
        System.out.println("Role    : " + loggedInUser.role);
        System.out.println("Email   : " + loggedInUser.email);
        System.out.println("Bio     : " + loggedInUser.bio);
        System.out.println("Country : " + loggedInUser.country);
        System.out.println("Niche   : " + loggedInUser.niche);
        if (!loggedInUser.receivedRatings.isEmpty()) {
            double avg = loggedInUser.receivedRatings.stream().mapToInt(Integer::intValue).average().orElse(0.0);
            System.out.printf("Rating  : %.1f ⭐\n", avg);
        } else {
            System.out.println("Rating  : Not rated yet");
        }
    }

    static void viewUserProfile() {
        System.out.print("\nEnter username to search: ");
        String name = sc.nextLine();
        for (User u : users) {
            if (u.username.equals(name)) {
                System.out.println("\n--- Profile of " + name + " ---");
                System.out.println("Role   : " + u.role);
                System.out.println("Country: " + (u.country.isEmpty() ? "N/A" : u.country));
                System.out.println("Niche  : " + (u.niche.isEmpty() ? "N/A" : u.niche));
                System.out.println("Bio    : " + (u.bio.isEmpty() ? "No bio yet" : u.bio));
                if (!u.receivedRatings.isEmpty()) {
                    double avg = u.receivedRatings.stream().mapToInt(Integer::intValue).average().orElse(0.0);
                    System.out.printf("Rating : %.1f ⭐\n", avg);
                } else {
                    System.out.println("Rating : Not rated yet");
                }
                return;
            }
        }
        System.out.println("User not found.");
    }

    static void createCampaign() {
        System.out.println("\n=== Create Campaign ===");
        System.out.print("Campaign Title: ");
        String title = sc.nextLine();
        System.out.print("Description: ");
        String desc = sc.nextLine();

        Campaign campaign = new Campaign(title, desc, loggedInUser.username);
        campaigns.add(campaign);
        loggedInUser.campaigns.add(title);

        System.out.println("Campaign Created.");
    }

    static void viewCampaigns() {
        System.out.println("\n--- Open Campaigns ---");
        List<Campaign> openCampaigns = campaigns.stream()
            .filter(c -> c.status.equals("open"))
            .toList();

        if (openCampaigns.isEmpty()) {
            System.out.println("No open campaigns available.");
            return;
        }

        for (int i = 0; i < openCampaigns.size(); i++) {
            Campaign c = openCampaigns.get(i);
            System.out.println((i + 1) + ". " + c.title + " by " + c.createdBy);
            System.out.println("   " + c.description);
        }

        System.out.print("Apply to campaign (number or 0 to cancel): ");
        int choice = Integer.parseInt(sc.nextLine());

        if (choice > 0 && choice <= openCampaigns.size()) {
            Campaign selected = openCampaigns.get(choice - 1);
            if (!selected.applicants.contains(loggedInUser.username)) {
                selected.applicants.add(loggedInUser.username);
                loggedInUser.applied.add(selected.title);
                System.out.println("Applied to: " + selected.title);
                System.out.print("Do you want to rate this brand? (y/n): ");
                if (sc.nextLine().equalsIgnoreCase("y")) {
                    rateUser(selected.createdBy);
                }
            } else {
                System.out.println("Already applied.");
            }
        }
    }

    static void searchCampaigns() {
        System.out.print("\nEnter keyword or brand name: ");
        String keyword = sc.nextLine().toLowerCase();

        List<Campaign> results = campaigns.stream()
            .filter(c -> c.status.equals("open") &&
                         (c.title.toLowerCase().contains(keyword) || c.createdBy.toLowerCase().contains(keyword)))
            .toList();

        if (results.isEmpty()) {
            System.out.println("No matching campaigns found.");
            return;
        }

        System.out.println("\n--- Search Results ---");
        for (Campaign c : results) {
            System.out.println("- " + c.title + " by " + c.createdBy);
            System.out.println("  " + c.description);
        }
    }

    static void viewApplications() {
        System.out.println("\n--- My Applications ---");
        if (loggedInUser.applied.isEmpty()) {
            System.out.println("You haven't applied to any campaigns yet.");
        } else {
            for (String app : loggedInUser.applied) {
                System.out.println("- " + app);
            }
        }
    }

    static void viewMyCampaigns() {
        System.out.println("\n--- My Campaigns ---");

        List<Campaign> owned = campaigns.stream()
            .filter(c -> c.createdBy.equals(loggedInUser.username))
            .toList();

        if (owned.isEmpty()) {
            System.out.println("You haven't created any campaigns.");
            return;
        }

        for (int i = 0; i < owned.size(); i++) {
            Campaign c = owned.get(i);
            System.out.println((i + 1) + ". " + c.title + " [Status: " + c.status + "]");
            System.out.println("   Applicants: " + (c.applicants.isEmpty() ? "None" : String.join(", ", c.applicants)));
        }
    }

    static void messagingMenu() {
        while (true) {
            System.out.println("\n=== Messaging ===");
            System.out.println("1. Send Message");
            System.out.println("2. View Inbox");
            System.out.println("3. View Sent Messages");
            System.out.println("4. Back");
            System.out.print("Choose: ");
            String ch = sc.nextLine();

            switch (ch) {
                case "1" -> sendMessage();
                case "2" -> viewInbox();
                case "3" -> viewSentMessages();
                case "4" -> { return; }
                default -> System.out.println("Invalid.");
            }
        }
    }

    static void sendMessage() {
        System.out.print("\nEnter receiver's username: ");
        String receiver = sc.nextLine();

        if (users.stream().noneMatch(u -> u.username.equals(receiver))) {
            System.out.println("User not found.");
            return;
        }

        System.out.print("Enter your message: ");
        String content = sc.nextLine();
        String time = new Date().toString();

        messages.add(new Message(loggedInUser.username, receiver, content, time));
        System.out.println("Message sent to " + receiver + "!");
    }

    static void viewInbox() {
        System.out.println("\n--- Inbox ---");
        List<Message> inbox = messages.stream()
            .filter(m -> m.to.equals(loggedInUser.username))
            .toList();

        if (inbox.isEmpty()) {
            System.out.println("No messages.");
        } else {
            for (Message m : inbox) {
                System.out.println("From: " + m.from);
                System.out.println("Time: " + m.timestamp);
                System.out.println("Message: " + m.content);
                System.out.println("--------------------");

                System.out.print("Reply to this message? (y/n): ");
                String replyChoice = sc.nextLine().trim().toLowerCase();

                if (replyChoice.equals("y")) {
                    System.out.print("Enter your reply: ");
                    String replyContent = sc.nextLine();
                    String time = new Date().toString();
                    messages.add(new Message(loggedInUser.username, m.from, replyContent, time));
                    System.out.println("Reply sent to " + m.from + "!");
                }
            }
        }
    }

    static void viewSentMessages() {
        System.out.println("\n--- Sent Messages ---");
        List<Message> sent = messages.stream()
            .filter(m -> m.from.equals(loggedInUser.username))
            .toList();

        if (sent.isEmpty()) {
            System.out.println("No sent messages.");
        } else {
            for (Message m : sent) {
                System.out.println("To: " + m.to);
                System.out.println("Time: " + m.timestamp);
                System.out.println("Message: " + m.content);
                System.out.println("--------------------");
            }
        }
    }

    static void rateUser(String username) {
        for (User u : users) {
            if (u.username.equals(username)) {
                System.out.print("Give a rating (1 to 5 stars): ");
                int rating = Integer.parseInt(sc.nextLine());
                if (rating >= 1 && rating <= 5) {
                    u.receivedRatings.add(rating);
                    System.out.println("Rated " + username + " successfully!");
                } else {
                    System.out.println("Invalid rating.");
                }
                return;
            }
        }
        System.out.println("User not found.");
    }
}

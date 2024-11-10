package com.example.demo;

import javafx.animation.FadeTransition;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.util.ArrayList;
import java.util.List;

public class MainApp extends Application {

    private List<Subject> subjects = new ArrayList<>();
    private VBox attendanceTableView;

    private static class Subject {
        String name;
        int totalClasses;
        int attendedClasses;
        double attendancePercentage;

        Subject(String name, int total, int attended) {
            this.name = name;
            this.totalClasses = total;
            this.attendedClasses = attended;
            this.attendancePercentage = ((double) attended / total) * 100;
        }
    }

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Sarathi - Student Assistant Platform");
        Scene loginScene = createLoginScene(primaryStage);
        primaryStage.setScene(loginScene);
        primaryStage.setMinWidth(900);
        primaryStage.setMinHeight(700);
        primaryStage.show();
    }

    private Scene createLoginScene(Stage primaryStage) {
        // Create a modern card layout
        VBox cardLayout = new VBox(20);
        cardLayout.setStyle("-fx-background-color: white; -fx-background-radius: 15; -fx-effect: dropshadow(gaussian, rgba(0,0,0,0.2), 10, 0, 0, 2);");
        cardLayout.setPadding(new Insets(30));
        cardLayout.setMaxWidth(400);
        cardLayout.setAlignment(Pos.CENTER);

        // Main container with pastel background color
        StackPane container = new StackPane();
        container.setStyle("-fx-background-color: linear-gradient(to bottom right, #a8c0ff, #f0f8ff);");
        container.setPadding(new Insets(20));

        // Logo and title
        Image logoImage = new Image(".png");
        ImageView logoView = new ImageView(logoImage);
        logoView.setFitWidth(200);
        logoView.setPreserveRatio(true);

        Label titleLabel = new Label("Welcome Back!");
        titleLabel.setFont(Font.font("Arial", FontWeight.BOLD, 28));
        titleLabel.setTextFill(Color.web("#333333"));

        Label subtitleLabel = new Label("Please login to continue");
        subtitleLabel.setFont(Font.font("Arial", 14));
        subtitleLabel.setTextFill(Color.web("#666666"));

        // Enhanced input fields
        TextField usernameField = createStyledTextField("Username");
        PasswordField passwordField = createStyledPasswordField("Password");

        // Remember me checkbox
        CheckBox rememberMe = new CheckBox("Remember me");
        rememberMe.setStyle("-fx-text-fill: #666666;");

        // Forgot password link
        Hyperlink forgotPassword = new Hyperlink("Forgot Password?");
        forgotPassword.setStyle("-fx-text-fill: #2575fc;");

        // Login button with animation
        Button loginButton = new Button("Login");
        styleButton(loginButton, "#2575fc", "#1c54c4");

        // Add hover animation
        loginButton.setOnMouseEntered(e -> {
            FadeTransition ft = new FadeTransition(Duration.millis(200), loginButton);
            ft.setFromValue(1.0);
            ft.setToValue(0.8);
            ft.play();
        });

        loginButton.setOnMouseExited(e -> {
            FadeTransition ft = new FadeTransition(Duration.millis(200), loginButton);
            ft.setFromValue(0.8);
            ft.setToValue(1.0);
            ft.play();
        });

        loginButton.setOnAction(e -> {
            if (!usernameField.getText().isEmpty() && !passwordField.getText().isEmpty()) {
                primaryStage.setScene(createMainScene(primaryStage));
            } else {
                showError("Please enter both username and password");
            }
        });

        // Organize elements in the card
        cardLayout.getChildren().addAll(
                logoView,
                titleLabel,
                subtitleLabel,
                new Separator(),
                usernameField,
                passwordField,
                rememberMe,
                loginButton,
                forgotPassword
        );

        container.getChildren().add(cardLayout);
        return new Scene(container, 900, 700);
    }

    private Scene createMainScene(Stage primaryStage) {
        BorderPane mainLayout = new BorderPane();
        mainLayout.setStyle("-fx-background-color: #f5f6fa;");

        // Create modern header
        HBox header = createHeader();
        mainLayout.setTop(header);

        // Create sidebar navigation
        VBox sidebar = createSidebar();
        mainLayout.setLeft(sidebar);

        // Create main content area with tabs
        TabPane tabPane = new TabPane();
        tabPane.setTabClosingPolicy(TabPane.TabClosingPolicy.UNAVAILABLE);
        tabPane.getTabs().addAll(
                createAttendanceTab(),
                createCGPACalculatorTab(),
                createProfileEvaluatorTab(),
                createUniversityRecommenderTab()
        );

        // Add padding around the tab content
        BorderPane.setMargin(tabPane, new Insets(0, 20, 20, 0));
        mainLayout.setCenter(tabPane);

        return new Scene(mainLayout, 900, 700);
    }

    private HBox createHeader() {
        HBox header = new HBox();
        header.setStyle("-fx-background-color: white; -fx-effect: dropshadow(gaussian, rgba(0,0,0,0.1), 5, 0, 0, 2);");
        header.setPadding(new Insets(10, 20, 10, 20));
        header.setAlignment(Pos.CENTER_LEFT);
        header.setSpacing(20);

        Image logoImage = new Image(".png");
        ImageView logoView = new ImageView(logoImage);
        logoView.setFitHeight(40);
        logoView.setPreserveRatio(true);

        Label titleLabel = new Label("Sarathi");
        titleLabel.setFont(Font.font("Arial", FontWeight.BOLD, 20));

        Region spacer = new Region();
        HBox.setHgrow(spacer, Priority.ALWAYS);

        Button profileButton = new Button("Profile");
        styleButton(profileButton, "#6c5ce7", "#5341c4");

        header.getChildren().addAll(logoView, titleLabel, spacer, profileButton);
        return header;
    }

    private VBox createSidebar() {
        VBox sidebar = new VBox(10);
        sidebar.setStyle("-fx-background-color: white; -fx-effect: dropshadow(gaussian, rgba(0,0,0,0.1), 5, 0, 0, 2);");
        sidebar.setPadding(new Insets(20));
        sidebar.setPrefWidth(200);

        Label menuLabel = new Label("MENU");
        menuLabel.setFont(Font.font("Arial", FontWeight.BOLD, 12));
        menuLabel.setTextFill(Color.gray(0.6));

        VBox menuItems = new VBox(5);
        menuItems.getChildren().addAll(
                createMenuItem("Dashboard", true),
                createMenuItem("Attendance"),
                createMenuItem("CGPA Calculator"),
                createMenuItem("Profile"),
                createMenuItem("Settings")
        );

        sidebar.getChildren().addAll(menuLabel, new Separator(), menuItems);
        return sidebar;
    }

    private Button createMenuItem(String text) {
        return createMenuItem(text, false);
    }

    private Button createMenuItem(String text, boolean isActive) {
        Button button = new Button(text);
        button.setMaxWidth(Double.MAX_VALUE);
        button.setAlignment(Pos.CENTER_LEFT);
        button.setPadding(new Insets(10, 15, 10, 15));

        if (isActive) {
            button.setStyle("-fx-background-color: #f0f0f0; -fx-text-fill: #2575fc; -fx-background-radius: 5;");
        } else {
            button.setStyle("-fx-background-color: transparent; -fx-text-fill: #666666;");

            button.setOnMouseEntered(e ->
                    button.setStyle("-fx-background-color: #f8f9fa; -fx-text-fill: #2575fc; -fx-background-radius: 5;"));

            button.setOnMouseExited(e ->
                    button.setStyle("-fx-background-color: transparent; -fx-text-fill: #666666;"));
        }

        return button;
    }

    private Tab createAttendanceTab() {
        Tab tab = new Tab("Attendance Manager");
        VBox layout = new VBox(20);
        layout.setPadding(new Insets(20));
        layout.setStyle("-fx-background-color: white;");

        // Header section
        Label headerLabel = new Label("Attendance Manager");
        headerLabel.setFont(Font.font("Arial", FontWeight.BOLD, 24));
        headerLabel.setTextFill(Color.web("#2c3e50"));

        // Input section
        HBox inputSection = new HBox(15);
        inputSection.setAlignment(Pos.CENTER);

        TextField subjectField = createStyledTextField("Subject Name");
        TextField totalClassesField = createStyledTextField("Total Classes");
        TextField attendedClassesField = createStyledTextField("Attended Classes");

        Button addSubjectButton = new Button("Add Subject");
        styleButton(addSubjectButton, "#27ae60", "#1c7c4d");

        inputSection.getChildren().addAll(subjectField, totalClassesField, attendedClassesField, addSubjectButton);

        // Table to display attendance data
        attendanceTableView = new VBox();
        attendanceTableView.setSpacing(10);

        addSubjectButton.setOnAction(e -> {
            String name = subjectField.getText();
            int total = Integer.parseInt(totalClassesField.getText());
            int attended = Integer.parseInt(attendedClassesField.getText());

            subjects.add(new Subject(name, total, attended));
            updateAttendanceTable();
        });

        layout.getChildren().addAll(headerLabel, inputSection, attendanceTableView);
        tab.setContent(layout);
        return tab;
    }

    private Tab createCGPACalculatorTab() {
        Tab tab = new Tab("CGPA Calculator");

        VBox layout = new VBox(20);
        layout.setPadding(new Insets(20));
        layout.setStyle("-fx-background-color: white;");

        Label headerLabel = new Label("CGPA Calculator");
        headerLabel.setFont(Font.font("Arial", FontWeight.BOLD, 24));
        headerLabel.setTextFill(Color.web("#2c3e50"));

        TextField totalCreditsField = createStyledTextField("Total Credits");
        TextField gradePointsField = createStyledTextField("Grade Points");

        Button calculateCGPAButton = new Button("Calculate CGPA");
        styleButton(calculateCGPAButton, "#27ae60", "#1c7c4d");

        layout.getChildren().addAll(
                headerLabel,
                totalCreditsField,
                gradePointsField,
                calculateCGPAButton
        );

        tab.setContent(layout);
        return tab;
    }

    private Tab createProfileEvaluatorTab() {
        Tab tab = new Tab("Profile Evaluator");

        VBox layout = new VBox(20);
        layout.setPadding(new Insets(20));
        layout.setStyle("-fx-background-color: white;");

        Label headerLabel = new Label("Profile Evaluation");
        headerLabel.setFont(Font.font("Arial", FontWeight.BOLD, 24));
        headerLabel.setTextFill(Color.web("#2c3e50"));

        TextField cgpaField = createStyledTextField("CGPA");
        TextField extracurricularField = createStyledTextField("Extracurricular Activities");

        Button evaluateProfileButton = new Button("Evaluate Profile");
        styleButton(evaluateProfileButton, "#f39c12", "#e67e22");

        layout.getChildren().addAll(
                headerLabel,
                cgpaField,
                extracurricularField,
                evaluateProfileButton
        );

        tab.setContent(layout);
        return tab;
    }

    private Tab createUniversityRecommenderTab() {
        Tab tab = new Tab("University Recommender");

        VBox layout = new VBox(20);
        layout.setPadding(new Insets(20));
        layout.setStyle("-fx-background-color: white;");

        Label headerLabel = new Label("University Recommender");
        headerLabel.setFont(Font.font("Arial", FontWeight.BOLD, 24));
        headerLabel.setTextFill(Color.web("#2c3e50"));

        TextField preferredSubjectField = createStyledTextField("Preferred Subject");
        TextField locationField = createStyledTextField("Preferred Location");

        Button recommendUniversityButton = new Button("Recommend University");
        styleButton(recommendUniversityButton, "#6c5ce7", "#5341c4");

        layout.getChildren().addAll(
                headerLabel,
                preferredSubjectField,
                locationField,
                recommendUniversityButton
        );

        tab.setContent(layout);
        return tab;
    }

    private void updateAttendanceTable() {
        attendanceTableView.getChildren().clear();
        for (Subject subject : subjects) {
            Label label = new Label(subject.name + " | Total: " + subject.totalClasses + " | Attended: " + subject.attendedClasses + " | Attendance: " + subject.attendancePercentage + "%");
            attendanceTableView.getChildren().add(label);
        }
    }

    private TextField createStyledTextField(String promptText) {
        TextField textField = new TextField();
        textField.setPromptText(promptText);
        textField.setStyle("-fx-background-color: #f5f6fa; -fx-border-radius: 5; -fx-border-color: #ccc;");
        return textField;
    }

    private PasswordField createStyledPasswordField(String promptText) {
        PasswordField passwordField = new PasswordField();
        passwordField.setPromptText(promptText);
        passwordField.setStyle("-fx-background-color: #f5f6fa; -fx-border-radius: 5; -fx-border-color: #ccc;");
        return passwordField;
    }

    private void styleButton(Button button, String color, String hoverColor) {
        button.setStyle("-fx-background-color: " + color + "; -fx-text-fill: white; -fx-font-size: 14px; -fx-background-radius: 5;");
        button.setOnMouseEntered(e -> button.setStyle("-fx-background-color: " + hoverColor + "; -fx-text-fill: white; -fx-font-size: 14px; -fx-background-radius: 5;"));
        button.setOnMouseExited(e -> button.setStyle("-fx-background-color: " + color + "; -fx-text-fill: white; -fx-font-size: 14px; -fx-background-radius: 5;"));
    }

    private void showError(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    public static void main(String[] args) {
        launch(args);
    }
}

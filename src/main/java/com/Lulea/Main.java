package com.Lulea;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        // Constants for solar panel calculations
        final double SOLAR_RADIATION = 166; // Wh/m²/hour
        final double EFFICIENCY = 0.20;
        final int PANEL_COUNT = 26;
        final double PANEL_WIDTH = 1.7; // in meters
        final double PANEL_HEIGHT = 1;  // in meters
        final double PRICE_PER_KWH = 0.9; // in SEK per kWh

        // Total surface area of all panels in m²
        final double TOTAL_SURFACE = PANEL_COUNT * PANEL_WIDTH * PANEL_HEIGHT;

        // Use a Scanner with a delimiter that splits on hyphen, colon, or whitespace.
        Scanner scanner = new Scanner(System.in).useDelimiter("[-:\\s]+");

        // -----------------------------
        // Read and validate the date
        // -----------------------------
        System.out.print("Enter today's date [mm-dd]> ");
        if (!scanner.hasNextInt()) {
            System.out.println("Error! Invalid date format.");
            System.exit(0);
        }
        int month = scanner.nextInt();
        if (!scanner.hasNextInt()) {
            System.out.println("Error! Invalid date format.");
            System.exit(0);
        }
        int day = scanner.nextInt();

        // Validate month: only June (6) or July (7) are allowed
        if (month != 6 && month != 7) {
            System.out.println("Error! Invalid month. You have entered " + month + ", but only 6 or 7 allowed.");
            System.exit(0);
        }
        // Validate day: proper range based on month
        if (day < 1 || (month == 6 && day > 30) || (month == 7 && day > 31)) {
            System.out.println("Error! Invalid day.");
            System.exit(0);
        }

        // -----------------------------
        // Read and validate sunrise time
        // -----------------------------
        System.out.print("Enter the time of sunrise [hh: mm]> ");
        if (!scanner.hasNextInt()) {
            System.out.println("Error! Invalid time format.");
            System.exit(0);
        }
        int sunriseHour = scanner.nextInt();
        if (!scanner.hasNextInt()) {
            System.out.println("Error! Invalid time format.");
            System.exit(0);
        }
        int sunriseMinute = scanner.nextInt();
        // Validate hour and minute for sunrise
        if (sunriseHour < 0 || sunriseHour > 23) {
            System.out.println("Error! Invalid time. Hours must be between 0 and 23.");
            System.exit(0);
        }
        if (sunriseMinute < 0 || sunriseMinute > 59) {
            System.out.println("Error! Invalid time. Minutes must be between 0 and 59.");
            System.exit(0);
        }

        // -----------------------------
        // Read and validate sunset time
        // -----------------------------
        System.out.print("Enter the time of sunset [hh: mm]> ");
        if (!scanner.hasNextInt()) {
            System.out.println("Error! Invalid time format.");
            System.exit(0);
        }
        int sunsetHour = scanner.nextInt();
        if (!scanner.hasNextInt()) {
            System.out.println("Error! Invalid time format.");
            System.exit(0);
        }
        int sunsetMinute = scanner.nextInt();
        // Validate hour and minute for sunset
        if (sunsetHour < 0 || sunsetHour > 23) {
            System.out.println("Error! Invalid time. Hours must be between 0 and 23.");
            System.exit(0);
        }
        if (sunsetMinute < 0 || sunsetMinute > 59) {
            System.out.println("Error! Invalid time. Minutes must be between 0 and 59.");
            System.exit(0);
        }

        // Ensure sunrise is before sunset by converting to total minutes
        int sunriseTotalMinutes = sunriseHour * 60 + sunriseMinute;
        int sunsetTotalMinutes = sunsetHour * 60 + sunsetMinute;
        if (sunriseTotalMinutes >= sunsetTotalMinutes) {
            System.out.println("Error! Sunrise must be before sunset.");
            System.exit(0);
        }

        // -----------------------------
        // Calculate sunshine hours and production
        // -----------------------------
        double sunshineHours = (sunsetTotalMinutes - sunriseTotalMinutes) / 60.0;

        // Production in Wh: Solar radiation * efficiency * total surface area * sunshine hours
        double productionWh = SOLAR_RADIATION * EFFICIENCY * TOTAL_SURFACE * sunshineHours;
        double productionKWh = productionWh / 1000.0;

        // Calculate profit based on electricity price
        double profit = productionKWh * PRICE_PER_KWH;

        // -----------------------------
        // Output the results
        // -----------------------------
        System.out.println("==========================================");
        System.out.printf("Sun hours: %.2f hours\n", sunshineHours);
        System.out.printf("The production on %d/%d is: %.2f kWh to a value of: SEK %.2f\n", month, day, productionKWh, profit);
    }
}

package com.Lulea;
import java.util.Scanner;

/**
 * The program calculates the daily energy production of a solar panel
 * system based on the date, sunrise, and sunset times provided by the user.
 * It validates the input, computes the total sunshine hours, and determines the
 * expected energy output and its monetary value.
 *
 * Pseudocode:
 *   1. Define constants for solar radiation, panel efficiency, dimensions, and
 *      price per kWh.
 *   2. Prompt the user to enter the date and validate that it falls within June or
 *      July.
 *   3. Read and validate the sunrise and sunset times.
 *   4. Ensure the sunrise time is earlier than the sunset time.
 *   5. Calculate the total sunshine hours.
 *   6. Compute the energy production using the formula:
 *      - Production (Wh) = solar radiation * efficiency * total panel area *
 *        sunshine hours.
 *      - Convert to kWh by dividing by 1000.
 *   7. Calculate the profit based on electricity price.
 *   8. Print the results in a structured format.
 *
 * @author Ludvig
 */
public class Main {
    public static void main(final String[] args) {
        // Constants for solar panel calculations

        final double solarRadiation = 166;  // Wh/m²/hour
        final double efficiency = 0.20;
        final int panelCount = 26;
        final double panelWidth = 1.7;      // in meters
        final double panelHeight = 1;       // in meters
        final double pricePerKwh = 0.9;       // in SEK per kWh
        final int june = 6;
        final int july = 7;
        final int daysInJune = 30;
        final int daysInJuly = 31;
        final int minHour = 0;
        final int maxHour = 23;
        final int minMinute = 0;
        final int maxMinute = 59;
        final int minutesPerHour = 60;
        final double whToKwh = 1000.0;

        // Total surface area of all panels in m²
        final double totalSurface = panelCount * panelWidth * panelHeight;

        // Use a Scanner with a delimiter that splits on hyphen, colon, or
        // whitespace.
        Scanner scanner =
                new Scanner(System.in).useDelimiter("[-:\\s]+");

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
        if (month != june && month != july) {
            System.out.println("Error! Invalid month. You have entered " + month +
                    ", but only " + june + " or " + july + " allowed.");
            System.exit(0);
        }
        // Validate day: proper range based on month
        if (day < 1 ||
                (month == june && day > daysInJune) ||
                (month == july && day > daysInJuly)) {
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
        if (sunriseHour < minHour || sunriseHour > maxHour) {
            System.out.println("Error! Invalid time. Hours must be between " +
                    minHour + " and " + maxHour + ".");
            System.exit(0);
        }
        if (sunriseMinute < minMinute || sunriseMinute > maxMinute) {
            System.out.println("Error! Invalid time. Minutes must be between " +
                    minMinute + " and " + maxMinute + ".");
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
        if (sunsetHour < minHour || sunsetHour > maxHour) {
            System.out.println("Error! Invalid time. Hours must be between " +
                    minHour + " and " + maxHour + ".");
            System.exit(0);
        }
        if (sunsetMinute < minMinute || sunsetMinute > maxMinute) {
            System.out.println("Error! Invalid time. Minutes must be between " +
                    minMinute + " and " + maxMinute + ".");
            System.exit(0);
        }

        // Ensure sunrise is before sunset by converting to total minutes
        int sunriseTotalMinutes = sunriseHour * minutesPerHour + sunriseMinute;
        int sunsetTotalMinutes = sunsetHour * minutesPerHour + sunsetMinute;
        if (sunriseTotalMinutes >= sunsetTotalMinutes) {
            System.out.println("Error! Sunrise must be before sunset.");
            System.exit(0);
        }

        // -----------------------------
        // Calculate sunshine hours and production
        // -----------------------------
        double sunshineHours = (sunsetTotalMinutes - sunriseTotalMinutes) /
                (double) minutesPerHour;

        // Production in Wh:
        // solarRadiation * efficiency * totalSurface * sunshineHours
        double productionWh = solarRadiation * efficiency * totalSurface *
                sunshineHours;
        double productionKwh = productionWh / whToKwh;

        // Calculate profit based on electricity price
        double profit = productionKwh * pricePerKwh;

        // -----------------------------
        // Output the results
        // -----------------------------
        System.out.println("==========================================");
        System.out.printf("Sun hours: %.2f hours\n", sunshineHours);
        System.out.printf("The production on %d/%d is: %.2f kWh to a value of: SEK " +
                "%.2f\n", month, day, productionKwh, profit);
    }
}

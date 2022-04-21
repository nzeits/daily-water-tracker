import logger.WaterLog;

import java.io.File;
import java.io.FileNotFoundException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Scanner;

public class Menu {

    private DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    private String date = LocalDateTime.now().format(formatter);
    private Scanner scanner = new Scanner(System.in);
    WaterLog log = new WaterLog();

    public void run() {

        boolean isRunning = true;

        while (isRunning) {
            String choice = mainMenu();

            if (choice.equals("1")) {
                logWaterMenu();
            } else if (choice.equals("2")) {
                runWaterReport();
            } else if (choice.equals("3")) {
                log.close();
                isRunning = false;
            }
        }
    }

    public String mainMenu() {
        System.out.println("☼☼☼☼☼☼☼☼☼☼☼☼☼☼☼☼☼☼☼☼☼☼☼☼☼");
        System.out.println("   Welcome to your water tracker  ");
        System.out.println("☼☼☼☼☼☼☼☼☼☼☼☼☼☼☼☼☼☼☼☼☼☼☼☼☼");

        System.out.println("\nWhat would you like to do?");
        System.out.println();
        System.out.println("1) Log water consumption");
        System.out.println("2) Run drinking report");
        System.out.println("3) Exit");
        System.out.println();
        System.out.print("Please select an option: ");
        String selectedOption = scanner.nextLine();
        String option = selectedOption.toLowerCase().trim();
        return option;
    }

    public void logWaterMenu() {
        System.out.print("Enter the date (yyyy-mm-dd) of consumption (Blank = today): "); //can I convert their date to match the format automatic dates
        String dateEntered = scanner.nextLine();
        if (dateEntered.isEmpty()) {
            dateEntered = date;
        }
        log.waterLog("WaterData.csv");

        try {
            System.out.print("How much water did you drink (ounces)? ");
            String waterEntered = scanner.nextLine();
            int checkWaterFormat = Integer.parseInt(waterEntered);

            log.write(dateEntered.trim() + "," + waterEntered.trim());
            System.out.println();
            System.out.println(waterEntered + " oz has been logged for " + dateEntered);
            System.out.println();

        } catch (NumberFormatException e) {
            System.out.println("\nError! System can only log whole numbers\n");
        }
    }

    public void runWaterReport() {
        Map<String, Integer> waterLog = new LinkedHashMap<>();

        File waterFile = new File("WaterData.csv");
        try (Scanner fileInput = new Scanner(waterFile)) {
            while (fileInput.hasNextLine()) {
                String lineOfText = fileInput.nextLine();
                String[] textArray = lineOfText.split(",");
                if (waterLog.size() > 1 && waterLog.containsKey(textArray[0])) {
                    int waterValue = waterLog.get(textArray[0]);
                    waterLog.put(textArray[0], Integer.parseInt(textArray[1]) + waterValue);
                } else {
                    waterLog.put(textArray[0], Integer.parseInt(textArray[1]));
                }
            }
        } catch (FileNotFoundException e) {
            System.out.println("File not found");
        }

        System.out.println("\nDate         Water Consumption");
        System.out.println("-----------  ------------------");

        for (Map.Entry<String, Integer> entry : waterLog.entrySet()) {
            System.out.println(entry.getKey() + "   " + entry.getValue() + " oz");
        }
    }

}

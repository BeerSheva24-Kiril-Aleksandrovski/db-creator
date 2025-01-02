package telran.employees;

import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class Main {
    public static void main(String[] args) {
        Map<String, Map<String, Integer>> departments = new HashMap<>();
        departments.put("QA", Map.of("Manager", 1, "Employee", 2, "WageEmployee", 10));
        departments.put("Development", Map.of("Manager", 1, "WageEmployee", 30));
        departments.put("DevOps", Map.of("Manager", 1, "Employee", 5));
        departments.put("Sales", Map.of("Manager", 1, "SalesPerson", 3));

        int idStart = 1000;
        int basicSalaryMin = 5000;
        int basicSalaryMax = 50000;
        int wageMin = 30;
        int wageMax = 300;
        int hoursMin = 1;
        int hoursMax = 200;
        double percentMin = 0.1;
        double percentMax = 1.5;
        double factorMin = 1.5;
        double factorMax = 4.0;

        String fileName = "employees-sql-data.csv";

        Random random = new Random();

        try (FileWriter writer = new FileWriter(fileName)) {
            int currentId = idStart;

            for (Map.Entry<String, Map<String, Integer>> departmentEntry : departments.entrySet()) {
                String department = departmentEntry.getKey();
                Map<String, Integer> roles = departmentEntry.getValue();

                for (Map.Entry<String, Integer> roleEntry : roles.entrySet()) {
                    String role = roleEntry.getKey();
                    int count = roleEntry.getValue();

                    for (int i = 0; i < count; i++) {
                        int id = currentId++;
                        int basicSalary = random.nextInt(basicSalaryMax - basicSalaryMin + 1) + basicSalaryMin;
                        Integer wage = role.equals("WageEmployee") ? random.nextInt(wageMax - wageMin + 1) + wageMin : null;
                        Integer hours = role.equals("WageEmployee") ? random.nextInt(hoursMax - hoursMin + 1) + hoursMin : null;
                        Double percent = role.equals("SalesPerson") ? round(random.nextDouble() * (percentMax - percentMin) + percentMin, 2) : null;
                        Integer sales = role.equals("SalesPerson") ? random.nextInt(1000) + 1 : null;
                        Double factor = role.equals("Manager") ? round(random.nextDouble() * (factorMax - factorMin) + factorMin, 2) : null;

                        writer.write(String.format("%d,%s,%s,%d,%s,%s,%s,%s,%s\n",
                                id, department, role, basicSalary,
                                wage == null ? "" : wage,
                                hours == null ? "" : hours,
                                percent == null ? "" : percent,
                                sales == null ? "" : sales,
                                factor == null ? "" : factor
                        ));
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static double round(double value, int places) {
        double scale = Math.pow(10, places);
        return Math.round(value * scale) / scale;
}
}

package telran.employees;

import java.util.*;
import java.util.function.*;
import java.util.stream.IntStream;

import static telran.employees.Ranges.*;
import static telran.employees.ConfigurationProperties.*;

public class CompanyGenerator {
    private static long ID = 1000;
    private static Map<String, UnaryOperator<String>> functionsMap = new HashMap<String, UnaryOperator<String>>() {
        {
            put(EMPLOYEE, CompanyGenerator::getEmployeeLine);
            put(MANAGER, CompanyGenerator::getManagerLine);
            put(WAGE_EMPLOYEE, CompanyGenerator::getWageEmployeeLine);
            put(SALES_PERSON, CompanyGenerator::getSalesPersonLine);
        }
    };

    public static List<String> getGenDataLines(Map<String, Map<String, Integer>> config) {
        List<String> res = new ArrayList<>();
        config.forEach((department, configMap) -> {
            configMap.forEach((type, counter) -> {
                IntStream.range(0, counter).forEach(i -> {
                    String line = functionsMap.get(type).apply(department);
                    res.add(line);
                });
            });
        });
        return res;
    }

    private static String getEmployeePart(String department, String type) {
        return String.format("%d,%s,%d,%s,", ID++, type, getBasicSalary(), department);
    }

    private static String getEmployeeLine(String department) {
        return getEmployeePart(department, EMPLOYEE) + getCommas(4);
    }

    private static String getCommas(int count) {
        return ",".repeat(count);
    }

    private static String getWageEmployeePart(String department, String type) {
        return String.format("%s,%d,%d,", getEmployeePart(department, type), getWage(), getHours());
    }

    private static int getHours() {
        return new Random().nextInt(MIN_HOURS, MAX_HOURS + 1);
    }

    private static int getWage() {
        return new Random().nextInt(MIN_WAGE, MAX_WAGE);
    }

    private static String getWageEmployeeLine(String department) {
        return getWageEmployeePart(department, WAGE_EMPLOYEE) + ",";
    }

    private static String getManagerLine(String department) {
        return String.format("%s%.2f%s", getEmployeePart(department, MANAGER), getFactor(), getCommas(4));
    }

    private static float getFactor() {
        return new Random().nextFloat(MIN_FACTOR, MAX_FACTOR);
    }

    private static String getSalesPersonLine(String department) {
        return String.format("%s%.2f,%d", getWageEmployeePart(department, SALES_PERSON), getPercent(),
                getSales());
    }

    private static long getSales() {
        return new Random().nextLong(MIN_SALES, MAX_SALES);
    }

    private static float getPercent() {
        return new Random().nextFloat(MIN_SALES_PERCENT, MAX_SALES_PERCENT);
    }

    private static int getBasicSalary() {
        return new Random().nextInt(MIN_BASIC_SALARY, MAX_BASIC_SALARY) / 100 * 100;
    }

}

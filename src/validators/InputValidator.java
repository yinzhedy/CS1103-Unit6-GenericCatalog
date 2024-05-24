package validators;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Collection;
import java.util.Scanner;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.regex.Pattern;

public class InputValidator {

    // Formatter for parsing dates in the format YYYY-MM-DD
    private static final DateTimeFormatter DEFAULT_DATE_FORMATTER = DateTimeFormatter.ISO_LOCAL_DATE;

    /**
     * Generic method to get input from user and validate it.
     * @param scanner The scanner object for reading input.
     * @param prompt The prompt to display to the user.
     * @param validator A predicate that tests if the input is valid.
     * @param errorMessage The error message to display if validation fails.
     * @return The original input string if it is valid.
     */
    public static String getInput(Scanner scanner, String prompt, Predicate<String> validator, String errorMessage) {
        while (true) {
            System.out.println(prompt);
            String input = scanner.nextLine();
            if (validator.test(input)) {
                return input;
            } else {
                System.out.println(errorMessage);
            }
        }
    }

    /**
     * Method to get input and parse it into another type.
     * @param scanner The scanner object for reading input.
     * @param prompt The prompt to display to the user.
     * @param parser A function that parses the input.
     * @param errorMessage The error message to display if parsing fails.
     * @return The parsed input if it is valid.
     */
    public static <T> T getParsedInput(Scanner scanner, String prompt, Function<String, T> parser, String errorMessage) {
        while (true) {
            System.out.println(prompt);
            String input = scanner.nextLine();
            try {
                return parser.apply(input);
            } catch (Exception e) {
                System.out.println(errorMessage);
            }
        }
    }

    /**
     * Validates date and returns LocalDate if valid, explicitly throws DateTimeParseException if parsing fails.
     * @param dateInput The date input string.
     * @param formatter The DateTimeFormatter to use for parsing.
     * @return The parsed LocalDate.
     * @throws DateTimeParseException if the date format is invalid.
     */
    public static LocalDate validateAndParseDate(String dateInput, DateTimeFormatter formatter) throws DateTimeParseException {
        LocalDate date = LocalDate.parse(dateInput, formatter);
        // Further checks like past or future may be added here in the future
        return date;
    }

    // Generic Validator Methods

    /**
     * Validates non-null and non-empty input.
     * @param input The input to validate.
     * @return true if the input is non-null and non-empty, false otherwise.
     */
    public static <T> boolean validateNonNullAndNonEmpty(T input) {
        if (input == null) {
            System.out.println("Input must not be null.");
            return false;
        }
        if (input instanceof String && ((String) input).trim().isEmpty()) {
            System.out.println("Input must not be empty.");
            return false;
        }
        if (input instanceof Collection && ((Collection<?>) input).isEmpty()) {
            System.out.println("Collection must not be empty.");
            return false;
        }
        return true;
    }

    /**
     * Ensures that the input is unique in the given collection.
     * @param value The value to check for uniqueness.
     * @param collection The collection to check against.
     * @param extractor A function to extract the value to compare.
     * @return true if the value is unique, false otherwise.
     */
    public static <T, U> boolean validateUniqueness(U value, Collection<T> collection, Function<T, U> extractor) {
        return collection.stream().map(extractor).noneMatch(v -> v.equals(value));
    }

    /**
     * Validates the presence or absence of alphabetical characters in any input.
     * @param input The input to validate.
     * @param shouldInclude true if the input should include alphabetical characters, false otherwise.
     * @return true if the input meets the specified condition, false otherwise.
     */
    public static <T> boolean includeAlphabets(T input, boolean shouldInclude) {
        String inputAsString = String.valueOf(input); // Converts any input to its string representation
        boolean containsLetters = Pattern.compile("[a-zA-Z]").matcher(inputAsString).find();
        if (shouldInclude && !containsLetters) {
            System.out.println("Input must contain alphabetical characters.");
            return false;
        } else if (!shouldInclude && containsLetters) {
            System.out.println("Input must not contain alphabetical characters.");
            return false;
        }
        return true;
    }

    /**
     * Validates the presence or absence of special characters in any input.
     * @param input The input to validate.
     * @param shouldInclude true if the input should include special characters, false otherwise.
     * @return true if the input meets the specified condition, false otherwise.
     */
    public static <T> boolean includeSpecialCharacters(T input, boolean shouldInclude) {
        String inputAsString = String.valueOf(input); // Converts any input to its string representation
        boolean containsSpecial = Pattern.compile("[^a-zA-Z0-9\\s]").matcher(inputAsString).find();
        if (shouldInclude && !containsSpecial) {
            System.out.println("Input must contain special characters.");
            return false;
        } else if (!shouldInclude && containsSpecial) {
            System.out.println("Input must not contain special characters.");
            return false;
        }
        return true;
    }

    /**
     * Validates the presence or absence of numbers in any input.
     * @param input The input to validate.
     * @param shouldInclude true if the input should include numbers, false otherwise.
     * @return true if the input meets the specified condition, false otherwise.
     */
    public static <T> boolean includeNumbers(T input, boolean shouldInclude) {
        String inputAsString = String.valueOf(input); // Converts any input to its string representation
        boolean containsNumbers = Pattern.compile("\\d").matcher(inputAsString).find();
        if (shouldInclude && !containsNumbers) {
            System.out.println("Input must contain numbers.");
            return false;
        } else if (!shouldInclude && containsNumbers) {
            System.out.println("Input must not contain numbers.");
            return false;
        }
        return true;
    }

    /**
     * Ensures that the input is unique in the given collection.
     * @param input The input to check for uniqueness.
     * @param collection The collection to check against.
     * @param attribute The attribute of the objects in the collection to compare.
     * @return true if the input is unique, false otherwise.
     */
    public static <T> boolean validateUniqueness(String input, Collection<T> collection, String attribute) {
        return collection.stream()
                .map(t -> {
                    try {
                        return t.getClass().getField(attribute).get(t).toString();
                    } catch (IllegalAccessException | NoSuchFieldException e) {
                        e.printStackTrace();
                        return null;
                    }
                })
                .noneMatch(input::equals);
    }

    // Data Validation Methods

    /**
     * Validate a date from a string input with multiple possible formats.
     * @param dateInput The date input string.
     * @param mustBePast true if the date must be in the past.
     * @param mustBeFuture true if the date must be in the future.
     * @param formatters The DateTimeFormatters to use for parsing.
     * @return true if the date is valid, false otherwise.
     */
    public static boolean validateDate(String dateInput, boolean mustBePast, boolean mustBeFuture, DateTimeFormatter... formatters) {
        LocalDate date = parseDateFromString(dateInput, formatters);
        return date != null && validateDateLogic(date, mustBePast, mustBeFuture);
    }

    /**
     * Validate a LocalDate object directly.
     * @param date The LocalDate to validate.
     * @param mustBePast true if the date must be in the past.
     * @param mustBeFuture true if the date must be in the future.
     * @return true if the date is valid, false otherwise.
     */
    public static boolean validateDate(LocalDate date, boolean mustBePast, boolean mustBeFuture) {
        return validateDateLogic(date, mustBePast, mustBeFuture);
    }

    /**
     * Attempt to parse the date from string using provided formatters.
     * @param dateInput The date input string.
     * @param formatters The DateTimeFormatters to use for parsing.
     * @return The parsed LocalDate, or null if no formats matched.
     */
    private static LocalDate parseDateFromString(String dateInput, DateTimeFormatter[] formatters) {
        if (formatters.length == 0) {
            formatters = new DateTimeFormatter[]{DEFAULT_DATE_FORMATTER}; // Use default if none provided
        }
        for (DateTimeFormatter formatter : formatters) {
            try {
                return LocalDate.parse(dateInput, formatter);
            } catch (DateTimeParseException ignore) {
                // Try the next format - currently we only have 1 format (will add more later as the inputValidator class is improved)
            }
        }
        System.out.println("Invalid date format. Please use a valid format.");
        return null; // Return null if no formats matched
    }

    /**
     * Common date validation logic.
     * @param date The LocalDate to validate.
     * @param mustBePast true if the date must be in the past.
     * @param mustBeFuture true if the date must be in the future.
     * @return true if the date is valid, false otherwise.
     */
    private static boolean validateDateLogic(LocalDate date, boolean mustBePast, boolean mustBeFuture) {
        if (mustBePast && date.isAfter(LocalDate.now())) {
            System.out.println("Date must be in the past.");
            return false;
        }
        if (mustBeFuture && date.isBefore(LocalDate.now())) {
            System.out.println("Date must be in the future.");
            return false;
        }
        return true;
    }
}

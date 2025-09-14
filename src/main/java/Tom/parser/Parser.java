package tom.parser;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import tom.exception.TomException;
import tom.task.Deadline;
import tom.task.Events;
import tom.task.ToDo;

/**
 * Utility class for parsing user input into various task types and date/time formats.
 * <p>
 * This class provides static methods to convert raw input strings into objects such
 * as {@link LocalDateTime}, {@link Deadline}, {@link Events}, and {@link ToDo}. It
 * also validates input formats and throws {@link TomException} if invalid data is provided.
 * </p>
 */

public class Parser {
    
    /**
     * Parses the input string to extract a {@link LocalDateTime} for checking recurring dates.
     * <p>
     * The input must be in the format: {@code occur [yyyy-MM-dd HH:mm]}.
     * </p>
     *
     * @param input The user input string containing the command and date/time.
     * @param fmt The {@link DateTimeFormatter} to use for parsing.
     * @return A {@link LocalDateTime} parsed from the input.
     * @throws java.time.format.DateTimeParseException If the date/time portion cannot be parsed.
     * @throws ArrayIndexOutOfBoundsException If the input does not contain the required components.
     */

    public static LocalDateTime parseCheckRecurringDates(String input, DateTimeFormatter fmt) {
        String[] s = input.split(" ");
        String dateTime = s[1] + " " + s[2];
        LocalDateTime tempDateTime = LocalDateTime.parse(dateTime, fmt);
        return tempDateTime;
    }

    /**
     * Parses the input string to create a {@link Deadline} task.
     * <p>
     * The input must be in the format:
     * {@code deadline [description] /by [yyyy-MM-dd HH:mm]}.
     * </p>
     *
     * @param input The user input string containing the deadline task details.
     * @param fmt The {@link DateTimeFormatter} to parse the date/time.
     * @return A new {@link Deadline} object created from the input.
     * @throws TomException If the input is missing the "/by" section,
     *                      has blank fields, or contains an invalid format.
     */

    public static Deadline parseForDeadlineTask(String input, DateTimeFormatter fmt) throws TomException {
        String[] val;
        
        if (!input.contains("/by")) {
                    throw new TomException(
                            "Please enter in this format \"deadline [description] /by [yyyy-MM-dd HH:mm] \"");
                }

                val = input.substring(9).trim().split("/by", 2);
                boolean fieldHasBlanks = val[0].isBlank() || val[1].isBlank();

                if (fieldHasBlanks) {
                    throw new TomException(
                            "Please enter in this format \"deadline [description] /by [yyyy-MM-dd HH:mm] \"");
                }

                return new Deadline(val[0].trim(), LocalDateTime.parse(val[1].trim(), fmt));
    }

    /**
     * Parses the input string to create a {@link ToDo} task.
     * <p>
     * The input must be in the format: {@code todo [description]}.
     * </p>
     *
     * @param input The user input string containing the to-do task details.
     * @return A new {@link ToDo} object created from the input.
     * @throws TomException If the description is missing or blank.
     */

    public static ToDo parseForToDoTask(String input) throws TomException {
        String description = input.substring(4).trim();

        if (description.isBlank()) {
            throw new TomException(
                    "Please enter in this format \"todo [description]\"");
        }

        
        return new ToDo(description);
    }

    /**
     * Parses the input string to create an {@link Events} task.
     * <p>
     * The input must be in the format:
     * {@code event [description] /from [yyyy-MM-dd HH:mm] /to [yyyy-MM-dd HH:mm]}.
     * </p>
     *
     * @param input The user input string containing the event task details.
     * @param fmt The {@link DateTimeFormatter} to parse the start and end date/times.
     * @return A new {@link Events} object created from the input.
     * @throws TomException If required sections are missing ("/from" or "/to"),
     *                      have blank fields, or contain an invalid format.
     */

    public static Events parseForEventTask(String input, DateTimeFormatter fmt) throws TomException {
        String[] val;

        val = input.substring(6).trim().split("/from", 2);
                boolean hasFormatingIssues = !input.contains("/from") || !input.contains("/to");
                if (hasFormatingIssues) {
                    throw new TomException(
                            "Please enter in this format \"event [description] /from [yyyy-MM-dd HH:mm]"
                                    + " /to [yyyy-MM-dd HH:mm] \"");
                }

                String[] val2 = val[1].split("/to", 2);

                boolean hasBlankFields = val[0].isBlank() || val2[0].isBlank() || val2[1].isBlank();
                if (hasBlankFields) {
                    throw new TomException(
                            "Please enter in this format \"event [description] /from [yyyy-MM-dd HH:mm]"
                                    + " /to [yyyy-MM-dd HH:mm] \"");
                }

                return new Events(val[0].trim(), LocalDateTime.parse(val2[0].trim(), fmt),
                        LocalDateTime.parse(val2[1].trim(), fmt));
    }
}

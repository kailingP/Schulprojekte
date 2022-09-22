import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class DateConverter {
	public static void main(String[] args) {
		String usDate = "";
		String euDate = "";
		BufferedReader inputReader = new BufferedReader(new InputStreamReader(System.in));
		System.out.print("Enter date in US Format:\n");
		try {
			usDate = inputReader.readLine();
		} catch (IOException e) {
			System.out.println("Error reading input!");
			e.printStackTrace();
		}
		Pattern pattern = Pattern.compile(
				"(?<weekday>Mon|Tue|Wed|Thu|Fri|Sat|Sun) (?<month>1[0-2]|[1-9])\\/(?<day>3[0-1]|[1-2]\\d|[1-9])\\/(?<year>\\d{1,4}) (?<hour>|1[0-2]|\\d):(?<minutes>[0-6]\\d) (?<antepost>AM|PM)");
		Matcher m = pattern.matcher(usDate);
		if (m.matches()) {
			String weekday = m.group("weekday");
			String month = m.group("month");
			String day = m.group("day");
			String year = m.group("year");
			Integer hour = Integer.parseInt(m.group("hour"));
			String minutes = m.group("minutes");
			String antepost = m.group("antepost");
			StringBuilder output = new StringBuilder();

			switch (weekday) {
			case ("Mon"): {
				output.append("Mo");
				break;
			}
			case ("Tue"): {
				output.append("Di");
				break;
			}
			case ("Wed"): {
				output.append("Mi");
				break;
			}
			case ("Thu"): {
				output.append("Do");
				break;
			}
			case ("Fri"): {
				output.append("Fr");
				break;
			}
			case ("Sat"): {
				output.append("Sa");
				break;
			}
			case ("Sun"): {
				output.append("So");
				break;
			}
			}
			output.append(" " + day + "." + month + "." + year + " ");
			switch (antepost) {
			case ("AM"): {
				output.append((hour.compareTo(12) == 0) ? (0) : hour.intValue());
				break;
			}
			case ("PM"): {
				output.append((hour.compareTo(12) == 0) ? (hour.intValue()) : (hour.intValue() + 12));
				break;
			}
			}
			output.append(":" + minutes);
			euDate = output.toString();
			System.out.println(euDate);

		} else {
			System.out.println("Not a valid datetime in US format!");
		}
	}
}

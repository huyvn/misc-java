import java.util.*;
import java.io.*;
import java.text.*;
public class TimeConverter {
	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		String ans = "Y";
		DecimalFormat fm = new DecimalFormat("00");
		do {
			try {
				System.out.println("Enter in 24-hour notation (HH:MM)");
				String date24 = sc.nextLine();
				int comma = date24.indexOf(":");
				String hour24S = date24.substring(0,comma);
				int hour24 = Integer.parseInt(hour24S);
				String minS = date24.substring(comma+1, date24.length());
				int min = Integer.parseInt(minS);

				if (min >= 60)
					throw new TimeFormatException("There is no such time as "+date24);

				if (hour24 >= 12) {
					hour24 -= 12;
					System.out.println("That is the same as");
					System.out.println(fm.format(hour24)+":"+fm.format(min)+" PM");
				} else {
					System.out.println("That is the same as");
					System.out.println(fm.format(hour24)+":"+fm.format(min)+" AM");
				}
				System.out.println("Again? (y/n)");
				ans = sc.nextLine();
			}
			catch (TimeFormatException e) {
				System.out.println(e.getMessage());
				System.out.println("Try again!");
				System.out.println();
			}
		} while (ans.equalsIgnoreCase("Y"));
		System.out.println("End of program");
	}
}







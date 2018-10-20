package us.malfeasant.pb;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class Line {
	private final LocalDate drawn;
	private final LocalDate expires;
	private final int[] numbers;
	private final int powerball;
	
	public Line(String text) throws Exception {	// TODO	more specific exception types
		String[] fields = text.split("\\s");
		if (fields.length < 4) throw new Exception("Too few fields: " + text);
/*		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd/uuuu");
		drawn = LocalDate.parse(fields[0]);
		expires = LocalDate.parse(fields[1]);	*/
		drawn = null; expires = null;	// TODO	tame this wild beast
		String[] nums = fields[2].split("-");
		if (nums.length != 5) throw new Exception("Too many/few numbers: " + fields[2]);
		
		numbers = new int[5];
		int pos = 0;
		for (String num : nums) {
			numbers[pos++] = Integer.parseInt(num);
		}
		powerball = Integer.parseInt(fields[3]);
	}
	
	public int getNumber(int which) throws ArrayIndexOutOfBoundsException {	// don't need to declare this, but why not...
		return numbers[which];
	}
	
	public int[] getNumbers() {
		return numbers.clone();
	}
	
	public int getPowerball() {
		return powerball;
	}
	
	public LocalDate getDrawDate() {
		return drawn;
	}
	
	public LocalDate getExpireDate() {
		return expires;
	}
}

package us.malfeasant.pb;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.SortedMap;
import java.util.TreeMap;

import javax.swing.JFileChooser;
import javax.swing.SwingUtilities;

public class Main {
	public static void main(String[] args) {
		SwingUtilities.invokeLater(() -> pickFile());
	}
	
	private static void pickFile() {
		JFileChooser chooser = new JFileChooser();
		ArrayList<Line> lines = new ArrayList<>();
		if (chooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
			File file = chooser.getSelectedFile();
			
			try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
				String line=null;
				while ((line = reader.readLine()) != null) {
					lines.add(new Line(line));
				}
			} catch (FileNotFoundException e) {
				System.out.println("Problem finding file.");
			} catch (IOException e) {
				System.out.println("Problem reading file.");
			} catch (Exception e) {
				System.out.println("Some other problem, most likely parsing.");
				e.printStackTrace();
			}
		} else {
			System.exit(-1);
		}
		lines.forEach(line -> tally(line));
		
		System.out.println("Top numbers:");
		for (Map.Entry<Integer, Integer> me : numCount.entrySet()) {
			if (!hotNums.containsKey(me.getValue())) {
				hotNums.put(me.getValue(), new ArrayList<>());
			}
			hotNums.get(me.getValue()).add(me.getKey());
		}
		for (int times = hotNums.lastKey(), found=0; times > 0; times--) {
			if (hotNums.get(times) == null) continue;	// hole in the results, skip the rest of this iteration but keep searching
			System.out.println("Number(s): " + hotNums.get(times) + " occurred " + times + " times");
			found += hotNums.get(times).size();
			if (found >= 5) break;	// found enough results, we're done
		}
		System.out.println("Top powerball(s):");
		for (Map.Entry<Integer, Integer> me : pbCount.entrySet()) {
			if (!hotPbs.containsKey(me.getValue())) {
				hotPbs.put(me.getValue(), new ArrayList<>());
			}
			hotPbs.get(me.getValue()).add(me.getKey());
		}
		for (int times = hotPbs.lastKey(), found=0; times > 0; times--) {	// TODO	could conceivably want more than just the top 1...
			if (hotPbs.get(times) == null) continue;
			System.out.println("Number(s): " + hotPbs.get(times) + " occurred " + times + " times");
			found += hotPbs.get(times).size();
			if (found >= 1) break;
		}
	}
	
//	private static int[] numCount = new int[70];	// keeping it simple, 0 is wasted
//	private static int[] pbCount = new int[27];
	private static final Map<Integer, Integer> numCount = new TreeMap<>();
	private static final Map<Integer, Integer> pbCount = new TreeMap<>();
	private static final SortedMap<Integer, List<Integer>> hotNums = new TreeMap<>();
	private static final SortedMap<Integer, List<Integer>> hotPbs = new TreeMap<>();
	
	private static void tally(Line l) {
		for (int n : l.getNumbers()) {
			//numCount[n]++;
			numCount.put(n, numCount.getOrDefault(n, 0) + 1);
		}
		//pbCount[l.getPowerball()]++;
		pbCount.put(l.getPowerball(), pbCount.getOrDefault(l.getPowerball(), 0) + 1);
		//System.out.println(" Processed line: " + l.getNumbers() + " " + l.getPowerball());
	}
}

package train;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

/**
 * @author Fabien Dagnat <fabien.dagnat@imt-atlantique.fr>
 */
public class Main {
	public static void main(String[] args) throws InterruptedException{
		List<Element> elements = new ArrayList<>();
		Station stationA = new Station("StationA", 3);
		Station stationB = new Station("StationB", 3);
		Section sectionAB = new Section("AB");
		Section sectionBC = new Section("BC");
		Section sectionCD = new Section("CD");
		elements.addAll(Arrays.asList(stationA, sectionAB, sectionBC, sectionCD, stationB));
//		elements.addAll(Arrays.asList(stationA, sectionAB, stationB));
		Railway railway = new Railway(elements);
		
		System.out.println("The railway is:");
		System.out.println("\t" + railway);
		
		// Create trains
		Position initialPosition = new Position(stationA, Direction.LR);
		try {
			Train train1 = new Train("Train1", initialPosition);
			Train train2 = new Train("Train2", initialPosition);
			Train train3 = new Train("Train3", initialPosition);
			
			// Create and start threads for trains
			Thread thread1 = new Thread(train1);
			Thread thread2 = new Thread(train2);
			Thread thread3 = new Thread(train3);
			
			thread1.start();
			thread2.start();
			thread3.start();
		} catch (BadPositionForTrainException e) {
			System.out.println("Le train " + e.getMessage());
		}
		
		

	}
}

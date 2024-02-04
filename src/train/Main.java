package train;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author Fabien Dagnat <fabien.dagnat@imt-atlantique.fr>
 */
public class Main {
	public static void main(String[] args) {
		Station stationA = new Station("StationA", 3);
		Station stationB = new Station("StationB", 2);
		Station stationC = new Station("StationC", 3);
		Section sectionAB = new Section("AB");
		Section sectionBC = new Section("BC");
		Section sectionCD = new Section("CD");
		Section sectionMN = new Section("MN");
		Section sectionNP = new Section("NP");
		List<Element> elements = new ArrayList<>(Arrays.asList(stationA, sectionAB, sectionBC, sectionCD, stationB, sectionMN, sectionNP, stationC));

		Railway railway = new Railway(elements);
		
		System.out.println("The railway is:");
		System.out.println("\t" + railway);
		
		// Create trains
		Position initialPosition = new Position(stationA, Direction.LR);
		Position lastPosition = new Position(stationC, Direction.RL);
		try {
			Train train1 = new Train("Train1", initialPosition);
			Train train2 = new Train("Train2", initialPosition);
			Train train3 = new Train("Train3", lastPosition);
			
			stationA.setCount(2);
			stationC.setCount(1);
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

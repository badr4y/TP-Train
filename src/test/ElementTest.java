package test;

import train.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ElementTest {
	
	@org.junit.jupiter.api.Test
	void next() throws BadPositionForTrainException {
		List<Element> elements = new ArrayList<>();
		Station stationA = new Station("StationA", 3);
		Station stationB = new Station("StationB", 3);
		Section sectionAB = new Section("AB");
		Section sectionBC = new Section("BC");
		Section sectionCD = new Section("CD");
		elements.addAll(Arrays.asList(stationA, sectionAB, sectionBC, sectionCD, stationB));
		Railway railway = new Railway(elements);
		System.out.println("The railway is:");
		System.out.println("\t" + railway);
		
		// Create trains
		Position initialPosition = new Position(stationA, Direction.LR);
		Train train1 = new Train("Train1", initialPosition);
		Train train2 = new Train("Train2", initialPosition);
		Train train3 = new Train("Train3", initialPosition);
		assertEquals(stationA.next(Direction.LR),sectionAB);
		assertEquals(sectionAB.next(Direction.LR),sectionBC);
		assertEquals(sectionBC.next(Direction.LR),sectionCD);
		assertEquals(sectionCD.next(Direction.LR),stationB);
		
	}
}
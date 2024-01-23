package train;

import java.util.LinkedList;
import java.util.List;

/**
 * @author Fabien Dagnat <fabien.dagnat@imt-atlantique.fr>
 */
public class Main {
	public static void main(String[] args) {
		Station A = new Station("GareA", 3);
		Station D = new Station("GareD", 3);
		Section AB = new Section("AB");
		Section BC = new Section("BC");
		Section CD = new Section("CD");
		List<Element> elements = new LinkedList<>();
		elements.add(A);
		elements.add(AB);
		elements.add(BC);
		elements.add(CD);
		elements.add(D);
		Railway r = new Railway(elements);
		
		System.out.println("The railway is:");
		System.out.println("\t" + r);
		Position p = new Position(A, Direction.LR);
		try {
			Train t1 = new Train("1", p);
			System.out.println(t1);
			t1.move();
			System.out.println(t1);
			t1.move();
			System.out.println(t1);
			t1.move();
			System.out.println(t1);
			t1.move();
			System.out.println(t1);
			t1.move();
			System.out.println(t1);
			t1.move();
			System.out.println(t1);
			t1.move();
			System.out.println(t1);
			t1.move();
			System.out.println(t1);
		} catch (BadPositionForTrainException e) {
			System.out.println("Le train " + e.getMessage());
		}
		
		

	}
}

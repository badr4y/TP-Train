package train;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Représentation d'un circuit constitué d'éléments de voie ferrée : gare ou
 * section de voie
 *
 * @author Fabien Dagnat <fabien.dagnat@imt-atlantique.fr>
 * @author Philippe Tanguy <philippe.tanguy@imt-atlantique.fr>
 */

public class Railway {
	private final List<Element> elements;
	private final Map<Element, Object> record;
	
	// Constructor
	public Railway(List<Element> elements) {
		if (elements == null)
			throw new NullPointerException("Elements cannot be null");
		
		this.elements = elements;
		this.record = new HashMap<>();
		
		for (Element e : elements) {
			record.put(e, null);
			e.setRailway(this);
		}
	}
	
	public Map<Element, Object> getRecord() {
		return record;
	}
	
	@Override
	public String toString() {
		StringBuilder result = new StringBuilder();
		boolean first = true;
		
		for (Element e : this.elements) {
			if (first)
				first = false;
			else
				result.append("--");
			result.append(e);
		}
		
		return result.toString();
	}
	
	public List<Element> elements() {
		return elements;
	}
}


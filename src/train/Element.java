package train;

/**
 * Cette classe abstraite est la représentation générique d'un élément de base d'un
 * circuit, elle factorise les fonctionnalitÃ©s communes des deux sous-classes :
 * l'entrée d'un train, sa sortie et l'appartenance au circuit.<br/>
 * Les deux sous-classes sont :
 * <ol>
 *   <li>La représentation d'une gare : classe {@link Station}</li>
 *   <li>La représentation d'une section de voie ferrée : classe {@link Section}</li>
 * </ol>
 * 
 * @author Fabien Dagnat <fabien.dagnat@imt-atlantique.fr>
 * @author Philippe Tanguy <philippe.tanguy@imt-atlantique.fr>
 */
public abstract class Element {
	private final String name;
	protected Railway railway;

	protected Element(String name) {
		if(name == null)
			throw new NullPointerException();
		
		this.name = name;
	}
	
	abstract void arrive() throws InterruptedException;
	abstract void depart();

	public void setRailway(Railway r) {
		if(r == null)
			throw new NullPointerException();
		
		this.railway = r;
	}
	
	public Element next(Direction dir) {
		int currentIndex = railway.getElements().indexOf(this);
		int elementsSize = railway.getElements().size();
		
		if (currentIndex != -1) {
			if (dir == Direction.LR && currentIndex < elementsSize - 1) {
				return railway.getElements().get(currentIndex + 1);
			} else if (dir == Direction.RL && currentIndex > 0) {
				return railway.getElements().get(currentIndex - 1);
			}
		}
		return null;
	}

	@Override
	public String toString() {
		return this.name;
	}
}

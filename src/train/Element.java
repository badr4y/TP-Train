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
	protected Element previous; // this variable is changed each time a train is moving to this element and sets in it the previous element of the railway he came from, this variable is used when wanting to release the previous element of the train
	private final String name;
	protected Railway railway;

	protected Element(String name) {
		if(name == null)
			throw new NullPointerException();
		
		this.name = name;
		this.previous = null;
	}
	
	public Element getPrevious() {
		return previous;
	}
	
	public void setPrevious(Element previous) {
		this.previous = previous;
	}
	
	public abstract boolean isAvailable();
	
	public abstract boolean isEmpty();
	
	public abstract void reserve();
	
	public abstract void release();
	
	/**
	 * this method is called by a train when wanting to depart and leave from this element of the railway
	 * @param dir represents the direction of the train departing from this element of the railway
	 * @throws InterruptedException
	 */
	public synchronized void depart(Direction dir) throws InterruptedException {
		while (!this.next(dir).isAvailable()) {
			this.wait();
		}
		this.next(dir).reserve();
		railway.getRecord().replace(this.next(dir),dir);
	}
	
	/**
	 * this method is called by a train when arriving in a new element of the railway
	 */
	public void arrive() {
		railway.getRecord().replace(previous,null);
		this.previous.release();
		if (previous.isEmpty() && this.previous.getPrevious() != null) {
			synchronized(this.previous.getPrevious()) {
				this.previous.getPrevious().notifyAll();
			}
		}
		synchronized(this.previous) {
			this.previous.notifyAll();
		}
	}
	
	public void setRailway(Railway r) {
		if(r == null)
			throw new NullPointerException();
		this.railway = r;
	}
	
	/**
	 * returns the next element on the railway in the direction given as a parameter
	 * @param dir direction
	 * @return
	 */
	public Element next(Direction dir) {
		int currentIndex = railway.elements().indexOf(this);
		int elementsSize = railway.elements().size();
		
		if (currentIndex != -1) {
			if (dir == Direction.LR && currentIndex < elementsSize - 1) {
				return railway.elements().get(currentIndex + 1);
			} else if (dir == Direction.RL && currentIndex > 0) {
				return railway.elements().get(currentIndex - 1);
			}
		}
		return null;
	}

	@Override
	public String toString() {
		return this.name;
	}
	
}

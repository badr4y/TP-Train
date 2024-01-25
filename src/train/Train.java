package train;

/**
 * Représentation d'un train. Un train est caractérisé par deux valeurs :
 * <ol>
 *   <li>
 *     Son nom pour l'affichage.
 *   </li>
 *   <li>
 *     La position qu'il occupe dans le circuit (un élément avec une direction) : classe {@link Position}.
 *   </li>
 * </ol>
 * 
 * @author Fabien Dagnat <fabien.dagnat@imt-atlantique.fr>
 * @author Mayte segarra <mt.segarra@imt-atlantique.fr>
 * Test if the first element of a train is a station
 * @author Philippe Tanguy <philippe.tanguy@imt-atlantique.fr>
 * @version 0.3
 */
public class Train implements Runnable {
	private final String name;
	private final Position pos;

	public Train(String name, Position p) throws BadPositionForTrainException {
		if (name == null || p == null)
			throw new NullPointerException();

		// A train should be first be in a station
		if (!(p.getPos() instanceof Station))
			throw new BadPositionForTrainException(name);

		this.name = name;
		this.pos = p.clone();
	}
	
	public synchronized void move() throws InterruptedException {
		synchronized (pos.getPos()) {
			this.pos.changeElement();
			System.out.println(this);
		}
	}
	
	
	@Override
	public String toString() {
		StringBuilder result = new StringBuilder("Train[");
		result.append(this.name);
		result.append("]");
		result.append(" is on ");
		result.append(this.pos);
		return result.toString();
	}
	
	private void depart() throws InterruptedException {
		pos.getPos().depart(pos.getDirection());
	}
	
	private void arrive() throws InterruptedException {
		pos.getPos().arrive();
	}
	
	@Override
	public void run() {
		while(true) {
			try {
				move();
				Thread.sleep(2000);
				depart(); // Move this line here
			} catch(InterruptedException e) {
				throw new RuntimeException(e);
			}
			try {
				arrive();
			} catch(InterruptedException e) {
				throw new RuntimeException(e);
			}
		}
	}
}

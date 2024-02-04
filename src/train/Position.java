package train;

/**
 * Représentation de la position d'un train dans le circuit. Une position
 * est caractérisée par deux valeurs :
 * <ol>
 *   <li>
 *     L'élément où se positionne le train : une gare (classe  {@link Station})
 *     ou une section de voie ferrée (classe {@link Section}).
 *   </li>
 *   <li>
 *     La direction qu'il prend (enumération {@link Direction}) : de gauche à
 *     droite ou de droite à gauche.
 *   </li>
 * </ol>
 * @author Fabien Dagnat <fabien.dagnat@imt-atlantique.fr> Modifié par Mayte
 *         Segarra 
 * @author Philippe Tanguy <philippe.tanguy@imt-atlantique.fr>
 *         
 * @version 0.3
 */
public class Position implements Cloneable {
	private Direction direction;
	private Element pos;

	public Position(Element elt, Direction d) {
		if (elt == null || d == null)
			throw new NullPointerException();

		this.pos = elt;
		this.direction = d;
	}
	
	public Direction getDirection() {
		return direction;
	}
	
	public Element getPos() {
		return pos;
	}
	
	@Override
	public Position clone() {
		try {
			return (Position) super.clone();
		} catch (CloneNotSupportedException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	@Override
	public String toString() {
		StringBuilder result = new StringBuilder(this.pos.toString());
		result.append(" going ");
		result.append(this.direction);
		return result.toString();
	}
	
	/**
	 * changes the direction
	 */
	public void changeDirection() {
		if (direction == Direction.LR) {
			direction = Direction.RL;
		} else {
			direction = Direction.LR;
		}
	}
	
	/**
	 * changes the element assigned to the position of the train to the one calling the method
	 */
	public synchronized void changeElement() {
		Element saved = this.pos;
		this.pos = pos.next(direction);
		pos.setPrevious(saved);
		if (pos.next(direction) == null) {
			this.changeDirection();
		}
	}
	
	
	
}

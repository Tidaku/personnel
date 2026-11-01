package personnel;
/**
 * Levée si la date de départ d'un employé précède sa date d'arrivée,
 * ou si une date de départ est indiquée sans date d'arrivée.
 */
public class DatesIncoherentes extends RuntimeException
{
	private static final long serialVersionUID = 3518862407413152901L;
}

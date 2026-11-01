package personnel;

import java.io.Serializable;
import java.time.LocalDate;

/**
 * Employé d'une ligue hébergée par la M2L. Certains peuvent 
 * être administrateurs des employés de leur ligue.
 * Un seul employé, rattaché à aucune ligue, est le root.
 * Il est impossible d'instancier directement un employé, 
 * il faut passer la méthode {@link Ligue#addEmploye addEmploye}.
 */

public class Employe implements Serializable, Comparable<Employe>
{
	private static final long serialVersionUID = 4795721718037994734L;
	private String nom, prenom, password, mail;
	private Ligue ligue;
	private GestionPersonnel gestionPersonnel;
	private LocalDate dateArrivee, dateDepart;
	
	Employe(GestionPersonnel gestionPersonnel, Ligue ligue, String nom, String prenom, String mail, String password)
	{
		this(gestionPersonnel, ligue, nom, prenom, mail, password, null, null);
	}
	
	Employe(GestionPersonnel gestionPersonnel, Ligue ligue, String nom, String prenom, String mail, String password, LocalDate dateArrivee, LocalDate dateDepart)
	{
		verifierDates(dateArrivee, dateDepart);
		this.gestionPersonnel = gestionPersonnel;
		this.nom = nom;
		this.prenom = prenom;
		this.password = password;
		this.mail = mail;
		this.ligue = ligue;
		this.dateArrivee = dateArrivee;
		this.dateDepart = dateDepart;
	}
	
	/**
	 * Lève DatesIncoherentes si le départ précède l'arrivée, ou si un départ
	 * est indiqué sans arrivée. Un départ le jour de l'arrivée est accepté.
	 */
	
	private static void verifierDates(LocalDate dateArrivee, LocalDate dateDepart)
	{
		if (dateDepart != null && (dateArrivee == null || dateDepart.isBefore(dateArrivee)))
			throw new DatesIncoherentes();
	}
	
	/**
	 * Retourne vrai ssi l'employé est administrateur de la ligue 
	 * passée en paramètre.
	 * @return vrai ssi l'employé est administrateur de la ligue 
	 * passée en paramètre.
	 * @param ligue la ligue pour laquelle on souhaite vérifier si this 
	 * est l'admininstrateur.
	 */
	
	public boolean estAdmin(Ligue ligue)
	{
		return ligue.getAdministrateur() == this;
	}
	
	/**
	 * Retourne vrai ssi l'employé est le root.
	 * @return vrai ssi l'employé est le root.
	 */
	
	public boolean estRoot()
	{
		return gestionPersonnel.getRoot() == this;
	}
	
	/**
	 * Retourne le nom de l'employé.
	 * @return le nom de l'employé. 
	 */
	
	public String getNom()
	{
		return nom;
	}

	/**
	 * Change le nom de l'employé.
	 * @param nom le nouveau nom.
	 */
	
	public void setNom(String nom)
	{
		boolean present = retirerDeLaLigue();
		this.nom = nom;
		if (present)
			ligue.add(this);
	}

	/**
	 * Retourne le prénom de l'employé.
	 * @return le prénom de l'employé.
	 */
	
	public String getPrenom()
	{
		return prenom;
	}
	
	/**
	 * Change le prénom de l'employé.
	 * @param prenom le nouveau prénom de l'employé. 
	 */

	public void setPrenom(String prenom)
	{
		boolean present = retirerDeLaLigue();
		this.prenom = prenom;
		if (present)
			ligue.add(this);
	}

	/**
	 * Retourne le mail de l'employé.
	 * @return le mail de l'employé.
	 */
	
	public String getMail()
	{
		return mail;
	}
	
	/**
	 * Change le mail de l'employé.
	 * @param mail le nouveau mail de l'employé.
	 */

	public void setMail(String mail)
	{
		boolean present = retirerDeLaLigue();
		this.mail = mail;
		if (present)
			ligue.add(this);
	}

	/**
	 * Retire l'employé de sa ligue avant la modification d'une valeur utilisée
	 * pour le tri : un TreeSet ne retrouve plus un élément dont le tri a changé.
	 * @return vrai ssi l'employé était présent dans sa ligue.
	 */
	
	private boolean retirerDeLaLigue()
	{
		return ligue != null && ligue.remove(this);
	}

	/**
	 * Retourne vrai ssi le password passé en paramètre est bien celui
	 * de l'employé.
	 * @return vrai ssi le password passé en paramètre est bien celui
	 * de l'employé.
	 * @param password le password auquel comparer celui de l'employé.
	 */
	
	public boolean checkPassword(String password)
	{
		return this.password.equals(password);
	}

	/**
	 * Change le password de l'employé.
	 * @param password le nouveau password de l'employé. 
	 */
	
	public void setPassword(String password)
	{
		this.password= password;
	}

	/**
	 * Retourne la date d'arrivée de l'employé.
	 * @return la date d'arrivée, null si elle n'est pas renseignée.
	 */
	
	public LocalDate getDateArrivee()
	{
		return dateArrivee;
	}

	/**
	 * Change la date d'arrivée de l'employé.
	 * Lève DatesIncoherentes si elle est incohérente avec la date de départ.
	 * @param dateArrivee la nouvelle date d'arrivée.
	 */
	
	public void setDateArrivee(LocalDate dateArrivee)
	{
		verifierDates(dateArrivee, dateDepart);
		this.dateArrivee = dateArrivee;
	}

	/**
	 * Retourne la date de départ de l'employé.
	 * @return la date de départ, null si l'employé n'est pas parti.
	 */
	
	public LocalDate getDateDepart()
	{
		return dateDepart;
	}

	/**
	 * Change la date de départ de l'employé.
	 * Lève DatesIncoherentes si elle est incohérente avec la date d'arrivée.
	 * @param dateDepart la nouvelle date de départ.
	 */
	
	public void setDateDepart(LocalDate dateDepart)
	{
		verifierDates(dateArrivee, dateDepart);
		this.dateDepart = dateDepart;
	}

	/**
	 * Retourne la ligue à laquelle l'employé est affecté.
	 * @return la ligue à laquelle l'employé est affecté.
	 */
	
	public Ligue getLigue()
	{
		return ligue;
	}

	/**
	 * Supprime l'employé. Si celui-ci est un administrateur, le root
	 * récupère les droits d'administration sur sa ligue.
	 */
	
	public void remove()
	{
		Employe root = gestionPersonnel.getRoot();
		if (this != root)
		{
			if (estAdmin(getLigue()))
				getLigue().setAdministrateur(root);
			getLigue().remove(this);
		}
		else
			throw new ImpossibleDeSupprimerRoot();
	}

	@Override
	public int compareTo(Employe autre)
	{
		int cmp = getNom().compareTo(autre.getNom());
		if (cmp != 0)
			return cmp;
		cmp = getPrenom().compareTo(autre.getPrenom());
		if (cmp != 0)
			return cmp;
		// Le mail départage les homonymes : le TreeSet supprime les éléments égaux.
		return getMail().compareTo(autre.getMail());
	}
	
	@Override
	public String toString()
	{
		String res = nom + " " + prenom + " " + mail + " (";
		if (estRoot())
			res += "super-utilisateur";
		else
			res += ligue.toString();
		return res + ")";
	}
}

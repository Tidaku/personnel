package testsUnitaires;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDate;

import org.junit.jupiter.api.Test;

import personnel.*;

class testDates
{
	GestionPersonnel gestionPersonnel = GestionPersonnel.getGestionPersonnel();
	LocalDate arrivee = LocalDate.of(2024, 9, 1);
	LocalDate depart = LocalDate.of(2026, 6, 30);

	private Employe creerEmploye(LocalDate dateArrivee, LocalDate dateDepart) throws SauvegardeImpossible
	{
		Ligue ligue = gestionPersonnel.addLigue("Tennis");
		return ligue.addEmploye("Bouchard", "Gérard", "g.bouchard@gmail.com", "azerty", dateArrivee, dateDepart);
	}

	@Test
	void constructeurAvecDates() throws SauvegardeImpossible
	{
		Employe employe = creerEmploye(arrivee, depart);
		assertEquals(arrivee, employe.getDateArrivee());
		assertEquals(depart, employe.getDateDepart());
	}

	@Test
	void employeSansDate() throws SauvegardeImpossible
	{
		Ligue ligue = gestionPersonnel.addLigue("Tennis");
		Employe employe = ligue.addEmploye("Bouchard", "Gérard", "g.bouchard@gmail.com", "azerty");
		assertNull(employe.getDateArrivee());
		assertNull(employe.getDateDepart());
	}

	@Test
	void employeToujoursPresent() throws SauvegardeImpossible
	{
		assertNull(creerEmploye(arrivee, null).getDateDepart());
	}

	@Test
	void departLeJourDeLArrivee() throws SauvegardeImpossible
	{
		assertEquals(arrivee, creerEmploye(arrivee, arrivee).getDateDepart());
	}

	@Test
	void constructeurDepartAvantArrivee()
	{
		assertThrows(DatesIncoherentes.class, () -> creerEmploye(depart, arrivee));
	}

	@Test
	void constructeurDepartSansArrivee()
	{
		assertThrows(DatesIncoherentes.class, () -> creerEmploye(null, depart));
	}

	@Test
	void setDateArrivee() throws SauvegardeImpossible
	{
		Employe employe = creerEmploye(arrivee, depart);
		LocalDate nouvelle = arrivee.plusDays(10);
		employe.setDateArrivee(nouvelle);
		assertEquals(nouvelle, employe.getDateArrivee());
	}

	@Test
	void setDateDepart() throws SauvegardeImpossible
	{
		Employe employe = creerEmploye(arrivee, null);
		employe.setDateDepart(depart);
		assertEquals(depart, employe.getDateDepart());
	}

	@Test
	void setDateArriveeApresDepartRefuse() throws SauvegardeImpossible
	{
		Employe employe = creerEmploye(arrivee, depart);
		assertThrows(DatesIncoherentes.class, () -> employe.setDateArrivee(depart.plusDays(1)));
		assertEquals(arrivee, employe.getDateArrivee());
	}

	@Test
	void setDateDepartAvantArriveeRefuse() throws SauvegardeImpossible
	{
		Employe employe = creerEmploye(arrivee, depart);
		assertThrows(DatesIncoherentes.class, () -> employe.setDateDepart(arrivee.minusDays(1)));
		assertEquals(depart, employe.getDateDepart());
	}

	@Test
	void setDateArriveeNullAvecDepartRefuse() throws SauvegardeImpossible
	{
		Employe employe = creerEmploye(arrivee, depart);
		assertThrows(DatesIncoherentes.class, () -> employe.setDateArrivee(null));
	}

	@Test
	void setDateDepartNullAnnuleLeDepart() throws SauvegardeImpossible
	{
		Employe employe = creerEmploye(arrivee, depart);
		employe.setDateDepart(null);
		assertNull(employe.getDateDepart());
	}
}

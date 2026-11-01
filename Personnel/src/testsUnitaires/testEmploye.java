package testsUnitaires;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import personnel.*;

class testEmploye
{
	GestionPersonnel gestionPersonnel = GestionPersonnel.getGestionPersonnel();

	private Employe creerEmploye() throws SauvegardeImpossible
	{
		Ligue ligue = gestionPersonnel.addLigue("Pétanque");
		return ligue.addEmploye("Bouchard", "Gérard", "g.bouchard@gmail.com", "azerty");
	}

	@Test
	void setNom() throws SauvegardeImpossible
	{
		Employe employe = creerEmploye();
		employe.setNom("Martin");
		assertEquals("Martin", employe.getNom());
	}

	@Test
	void setPrenom() throws SauvegardeImpossible
	{
		Employe employe = creerEmploye();
		employe.setPrenom("Paul");
		assertEquals("Paul", employe.getPrenom());
	}

	@Test
	void setMail() throws SauvegardeImpossible
	{
		Employe employe = creerEmploye();
		employe.setMail("p.martin@gmail.com");
		assertEquals("p.martin@gmail.com", employe.getMail());
	}

	@Test
	void setPassword() throws SauvegardeImpossible
	{
		Employe employe = creerEmploye();
		assertTrue(employe.checkPassword("azerty"));
		employe.setPassword("qwerty");
		assertTrue(employe.checkPassword("qwerty"));
		assertFalse(employe.checkPassword("azerty"));
	}

	@Test
	void getLigue() throws SauvegardeImpossible
	{
		Ligue ligue = gestionPersonnel.addLigue("Pétanque");
		Employe employe = ligue.addEmploye("Bouchard", "Gérard", "g.bouchard@gmail.com", "azerty");
		assertSame(ligue, employe.getLigue());
	}

	@Test
	void estRoot() throws SauvegardeImpossible
	{
		assertTrue(gestionPersonnel.getRoot().estRoot());
		assertFalse(creerEmploye().estRoot());
	}

	@Test
	void setNomRoot()
	{
		Employe root = gestionPersonnel.getRoot();
		String ancienNom = root.getNom();
		root.setNom("superadmin");
		assertEquals("superadmin", root.getNom());
		root.setNom(ancienNom);
	}

	@Test
	void removeEmploye() throws SauvegardeImpossible
	{
		Employe employe = creerEmploye();
		Ligue ligue = employe.getLigue();
		employe.remove();
		assertFalse(ligue.getEmployes().contains(employe));
	}

	@Test
	void removeNeSupprimeQueCetEmploye() throws SauvegardeImpossible
	{
		Employe employe = creerEmploye();
		Ligue ligue = employe.getLigue();
		Employe collegue = ligue.addEmploye("Martin", "Paul", "p.martin@gmail.com", "qwerty");
		employe.remove();
		assertEquals(1, ligue.getEmployes().size());
		assertSame(collegue, ligue.getEmployes().first());
	}

	@Test
	void removeRootImpossible()
	{
		Employe root = gestionPersonnel.getRoot();
		assertThrows(ImpossibleDeSupprimerRoot.class, () -> root.remove());
	}

	@Test
	void removeAdministrateurRendLaLigueAuRoot() throws SauvegardeImpossible
	{
		Employe employe = creerEmploye();
		Ligue ligue = employe.getLigue();
		ligue.setAdministrateur(employe);
		employe.remove();
		assertSame(gestionPersonnel.getRoot(), ligue.getAdministrateur());
	}

	@Test
	void removeEmployeSimpleGardeLAdministrateur() throws SauvegardeImpossible
	{
		Employe employe = creerEmploye();
		Ligue ligue = employe.getLigue();
		Employe administrateur = ligue.addEmploye("Martin", "Paul", "p.martin@gmail.com", "qwerty");
		ligue.setAdministrateur(administrateur);
		employe.remove();
		assertSame(administrateur, ligue.getAdministrateur());
	}

	@Test
	void removeApresChangementDeNom() throws SauvegardeImpossible
	{
		Ligue ligue = gestionPersonnel.addLigue("Pétanque");
		Employe premier = ligue.addEmploye("Albert", "Jean", "a@gmail.com", "azerty");
		ligue.addEmploye("Martin", "Paul", "m@gmail.com", "azerty");
		ligue.addEmploye("Petit", "Luc", "p@gmail.com", "azerty");
		premier.setNom("Zola");
		premier.remove();
		assertFalse(ligue.getEmployes().contains(premier));
		assertEquals(2, ligue.getEmployes().size());
	}

	@Test
	void homonymesConserves() throws SauvegardeImpossible
	{
		Ligue ligue = gestionPersonnel.addLigue("Pétanque");
		ligue.addEmploye("Martin", "Paul", "p.martin@gmail.com", "azerty");
		ligue.addEmploye("Martin", "Paul", "paul.martin@gmail.com", "qwerty");
		assertEquals(2, ligue.getEmployes().size());
	}
}

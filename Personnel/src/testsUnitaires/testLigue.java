package testsUnitaires;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

import personnel.*;

class testLigue
{
	GestionPersonnel gestionPersonnel = GestionPersonnel.getGestionPersonnel();

	@Test
	void createLigue() throws SauvegardeImpossible
	{
		Ligue ligue = gestionPersonnel.addLigue("Fléchettes");
		assertEquals("Fléchettes", ligue.getNom());
	}

	@Test
	void addEmploye() throws SauvegardeImpossible
	{
		Ligue ligue = gestionPersonnel.addLigue("Fléchettes");
		Employe employe = ligue.addEmploye("Bouchard", "Gérard", "g.bouchard@gmail.com", "azerty");
		assertEquals(employe, ligue.getEmployes().first());
	}

	@Test
	void setNom() throws SauvegardeImpossible
	{
		Ligue ligue = gestionPersonnel.addLigue("Fléchettes");
		ligue.setNom("Bowling");
		assertEquals("Bowling", ligue.getNom());
	}

	@Test
	void administrateurParDefautEstLeRoot() throws SauvegardeImpossible
	{
		Ligue ligue = gestionPersonnel.addLigue("Fléchettes");
		assertSame(gestionPersonnel.getRoot(), ligue.getAdministrateur());
	}

	@Test
	void setAdministrateurEmployeDeLaLigue() throws SauvegardeImpossible
	{
		Ligue ligue = gestionPersonnel.addLigue("Fléchettes");
		Employe employe = ligue.addEmploye("Bouchard", "Gérard", "g.bouchard@gmail.com", "azerty");
		ligue.setAdministrateur(employe);
		assertSame(employe, ligue.getAdministrateur());
		assertTrue(employe.estAdmin(ligue));
	}

	@Test
	void setAdministrateurRevoqueLAncien() throws SauvegardeImpossible
	{
		Ligue ligue = gestionPersonnel.addLigue("Fléchettes");
		Employe ancien = ligue.addEmploye("Bouchard", "Gérard", "g.bouchard@gmail.com", "azerty");
		Employe nouveau = ligue.addEmploye("Martin", "Paul", "p.martin@gmail.com", "qwerty");
		ligue.setAdministrateur(ancien);
		ligue.setAdministrateur(nouveau);
		assertFalse(ancien.estAdmin(ligue));
		assertTrue(nouveau.estAdmin(ligue));
	}

	@Test
	void setAdministrateurRoot() throws SauvegardeImpossible
	{
		Ligue ligue = gestionPersonnel.addLigue("Fléchettes");
		Employe employe = ligue.addEmploye("Bouchard", "Gérard", "g.bouchard@gmail.com", "azerty");
		ligue.setAdministrateur(employe);
		ligue.setAdministrateur(gestionPersonnel.getRoot());
		assertSame(gestionPersonnel.getRoot(), ligue.getAdministrateur());
	}

	@Test
	void setAdministrateurAutreLigueInterdit() throws SauvegardeImpossible
	{
		Ligue ligue = gestionPersonnel.addLigue("Fléchettes");
		Ligue autre = gestionPersonnel.addLigue("Bowling");
		Employe etranger = autre.addEmploye("Martin", "Paul", "p.martin@gmail.com", "qwerty");
		assertThrows(DroitsInsuffisants.class, () -> ligue.setAdministrateur(etranger));
		assertSame(gestionPersonnel.getRoot(), ligue.getAdministrateur());
	}

	@Test
	void getLigueDeLAdministrateur() throws SauvegardeImpossible
	{
		Ligue ligue = gestionPersonnel.addLigue("Fléchettes");
		Employe administrateur = ligue.addEmploye("Bouchard", "Gérard", "g.bouchard@gmail.com", "azerty");
		Employe simple = ligue.addEmploye("Martin", "Paul", "p.martin@gmail.com", "qwerty");
		ligue.setAdministrateur(administrateur);
		assertSame(ligue, gestionPersonnel.getLigue(administrateur));
		assertNull(gestionPersonnel.getLigue(simple));
	}

	@Test
	void getLigueDuRootSansLigue()
	{
		assertNull(gestionPersonnel.getLigue(gestionPersonnel.getRoot()));
	}

	@Test
	void removeLigue() throws SauvegardeImpossible
	{
		Ligue ligue = gestionPersonnel.addLigue("Ligue à supprimer");
		ligue.remove();
		assertFalse(gestionPersonnel.getLigues().contains(ligue));
	}

	@Test
	void removeLigueSupprimeSesEmployes() throws SauvegardeImpossible
	{
		Ligue ligue = gestionPersonnel.addLigue("Ligue à supprimer");
		ligue.addEmploye("Bouchard", "Gérard", "g.bouchard@gmail.com", "azerty");
		ligue.remove();
		assertTrue(ligue.getEmployes().isEmpty());
	}

	@Test
	void removeApresRenommage() throws SauvegardeImpossible
	{
		Ligue premiere = gestionPersonnel.addLigue("AAA");
		gestionPersonnel.addLigue("MMM");
		gestionPersonnel.addLigue("NNN");
		premiere.setNom("ZZZ");
		premiere.remove();
		assertFalse(gestionPersonnel.getLigues().stream().anyMatch(ligue -> ligue == premiere));
	}
}

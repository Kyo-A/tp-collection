package org.eclipse.main;

import java.util.List;

import org.eclipse.classes.Salle;
import org.eclipse.services.SalleService;

public class Main {

	public static void main(String[] args) {

		SalleService salleService = new SalleService();
		
		salleService.create(new Salle("A", "Salle 1"));
		salleService.create(new Salle("B", "Salle 2"));
		salleService.create(new Salle("C", "Salle 3"));
		salleService.create(new Salle("D", "Salle 4"));
		
		System.out.println("La liste des salles : ");
		
		List<Salle> salles = salleService.findAll();
		
		for(Salle s : salles) {
			System.out.println(s);
		}
		
		// salleService.findAll().forEach((salle) -> System.out.println(salle));
		
		System.out.println("Supprimer la salle avec id = 1");
		salleService.delete(salleService.findById(1));

		System.out.println("Modifier la salle avec id = 2");
		Salle salle = salleService.findById(2);
		
		System.out.println("\tSalle à modifier : " + salle);
		salle.setCode("E");
		salle.setLibelle("Salle 5");
		
		salleService.update(salle);
		
		System.out.println("La liste des salles après les mises à jour :");
		
		for(Salle s : salles) {
			System.out.println(s);
		}
	
	}

}

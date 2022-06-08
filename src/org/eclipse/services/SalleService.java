package org.eclipse.services;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.classes.Salle;
import org.eclipse.dao.IDao;

public class SalleService implements IDao<Salle> {
	
	private List<Salle> salles;
	
	public SalleService() {
		salles = new ArrayList<Salle>();
		salles.add(new Salle(1, "A", "Salle 1"));
		salles.add(new Salle(2, "B", "Salle 2"));
		salles.add(new Salle(3, "C", "Salle 3"));
	}
	
	@Override
	public boolean create(Salle o) {
		return salles.add(o);
	}

	@Override
	public boolean delete(Salle o) {
		return salles.remove(o);
	}

	@Override
	public boolean update(Salle o) {
		for (Salle s : salles) {
			if (s.getId() == o.getId()) {
				s.setCode(o.getCode());
				s.setLibelle(o.getLibelle());
				return true;
			}
		}
		return false;
	}

	@Override
	public Salle findById(int id) {
		for (Salle s : salles) {
			if (s.getId() == id)
				return s;
		}
		return null;
	}

	@Override
	public List<Salle> findAll() {
		return salles;
	}

}

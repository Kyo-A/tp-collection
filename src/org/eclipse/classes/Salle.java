package org.eclipse.classes;

public class Salle {
	
	private int id;
	private String code;
	private String libelle;

	private static int count;

	public Salle(String code, String libelle) {
		this.id = ++count;
		this.code = code;
		this.libelle = libelle;
	}

	public Salle(int id, String code, String libelle) {
		this.id = id;
		this.code = code;
		this.libelle = libelle;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getLibelle() {
		return libelle;
	}

	public void setLibelle(String libelle) {
		this.libelle = libelle;
	}

	public String toString() {
		return this.code + " " + this.libelle;
	}

}

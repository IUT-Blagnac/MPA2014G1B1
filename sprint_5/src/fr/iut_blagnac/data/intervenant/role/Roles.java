package fr.iut_blagnac.data.intervenant.role;

public enum Roles {
	superv("Superviseur"), client("Client"), techass("Support Technique");
	
	private String role;
	
	Roles (String role) {
		this.role = role;
	}
	
	public String getRole () {
		return this.role;
	}
}

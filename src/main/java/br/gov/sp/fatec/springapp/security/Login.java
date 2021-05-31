package br.gov.sp.fatec.springapp.security;

public class Login {
	private String id;
	
	private String username;
	
	private String password;
	
	private String autorizacao;
	
	private String token;

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public String getAutorizacao() {
		return autorizacao;
	}

	public void setAutorizacao(String autorizacao) {
		this.autorizacao = autorizacao;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

}

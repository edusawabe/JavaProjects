package model;

public class JogadorConfigFile {
	private String nome;
	private String email;
	private String[] results;

	public JogadorConfigFile() {
		nome = new String();
		email = new String();
		results = new String[12];
	}

	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String[] getResults() {
		return results;
	}
	public void setResults(String[] results) {
		this.results = results;
	}

	public String generateFileLine(){
		String line = new String();
		line  = line + nome + ";";
		line  = line + email + ";";
		for (int i = 0; i < results.length; i++) {
			line  = line + results[i] + ";";
		}
		return line;
	}

	public void parseFileLine(String line){
		String[] splitline = line.split(";");
		nome = splitline[0];
		email = splitline[1];
		if (results == null)
			results = new String[12];
		for (int i = 2; i < splitline.length; i++) {
			results[i - 2] = splitline[i];
		}
	}
}

package polimi.provafinale.trickytickets.bean;

// Bean per le possibili categorie deti ticket, estende la classe astratta BaseBean aggiungendo solo i dati rilevanti (in questo caso nome
// e descrizione della categoria) 

public class CategoryBean extends BaseBean {
	
	private String name;
	private String description;
	
	@Override
	public String getKey() {
		return String.valueOf(id);
	}

	public String getValue() {
		return name;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}


}

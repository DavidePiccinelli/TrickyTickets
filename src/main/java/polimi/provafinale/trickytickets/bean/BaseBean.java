package polimi.provafinale.trickytickets.bean;

import java.sql.Timestamp;

// Bean base, astratto poiché utilizzato per creare, per ereditarietà, gli altri Bean utilizzati in concreto
// Dotato degli attributi base e dei getter/setter

public abstract class BaseBean implements Comparable<BaseBean> {
	
	
	protected long id;
	private String name;
	protected String createdBy;
	protected String modifiedBy;
	protected Timestamp createdDatetime;
	protected Timestamp modifiedDatetime;
	
	public String getKey() {
		return String.valueOf(id);
	}

	public String getValue() {
		return name;
	}
	
	@Override
	public int compareTo(BaseBean b) {
	
		return getValue().compareTo(b.getValue());
	}
	
	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	public String getModifiedBy() {
		return modifiedBy;
	}

	public void setModifiedBy(String modifiedBy) {
		this.modifiedBy = modifiedBy;
	}

	public Timestamp getCreatedDatetime() {
		return createdDatetime;
	}

	public void setCreatedDatetime(Timestamp createdDatetime) {
		this.createdDatetime = createdDatetime;
	}

	public Timestamp getModifiedDatetime() {
		return modifiedDatetime;
	}

	public void setModifiedDatetime(Timestamp modifiedDatetime) {
		this.modifiedDatetime = modifiedDatetime;
	}
	
	

	
	
	
}

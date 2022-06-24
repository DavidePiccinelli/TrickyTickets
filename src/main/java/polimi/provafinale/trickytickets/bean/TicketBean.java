package polimi.provafinale.trickytickets.bean;

import java.util.Date;

//Bean per i ticket che gli utenti possono creare estende la classe astratta BaseBean aggiungendo solo i dati rilevanti 
// ossia nome e id della categoria, numero del ticket, titolo, dati di chi lo crea e infine la descrizione


public class TicketBean extends BaseBean {

	private long categoryId;
	private String categoryName;
	private long ticketNo;
	private String title;
	private Date date;
	private String status;
	private String userName;
	private long userId;
	private String description;

	@Override
	public String getKey() {
		// TODO Auto-generated method stub
		return String.valueOf(id);
	}

	public String getValue() {
			return title;
	}

	public long getCategoryId() {
		return categoryId;
	}

	public void setCategoryId(long categoryId) {
		this.categoryId = categoryId;
	}

	public String getCategoryName() {
		return categoryName;
	}

	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}

	public long getTicketNo() {
		return ticketNo;
	}

	public void setTicketNo(long ticketNo) {
		this.ticketNo = ticketNo;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public long getUserId() {
		return userId;
	}

	public void setUserId(long userId) {
		this.userId = userId;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

}

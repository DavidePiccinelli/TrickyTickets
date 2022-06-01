package polimi.provafinale.trickytickets.bean;

import java.util.Date;

//Bean per i commenti che si possono aggiungere ai ticket, estende la classe astratta BaseBean aggiungendo solo i dati rilevanti 
// (riferimento al ticket e utente che ha generato il commento)

public class CommentBean extends BaseBean {
	
	private long ticketNo;
	private long ticketId;
	private String ticketTitle;
	private String userName;
	private Date date;
	private String comment;
	

	@Override
	public String getKey() {
		return null;
	}


	public long getTicketNo() {
		return ticketNo;
	}

	public void setTicketNo(long ticketNo) {
		this.ticketNo = ticketNo;
	}

	public long getTicketId() {
		return ticketId;
	}

	public void setTicketId(long ticketId) {
		this.ticketId = ticketId;
	}

	public String getTicketTitle() {
		return ticketTitle;
	}

	public void setTicketTitle(String ticketTitle) {
		this.ticketTitle = ticketTitle;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

}

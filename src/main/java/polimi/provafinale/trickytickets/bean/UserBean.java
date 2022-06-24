package polimi.provafinale.trickytickets.bean;

//Bean per gli utenti del servizio, estende la classe astratta BaseBean aggiungendo solo i dati rilevanti 
//che possono comunque essere definiti dall'azienda che implementa il sistema; nel mio esempio
//ho aggiunto email, telefono, e il reparto aziendale di cui fa parte l'utente.

public class UserBean extends BaseBean {

	private String name;
	private String userName;
	private String password;
	private String confirmPassword;
	private String email;
	private String contactNo;
	private String businessArea;
	private long roleId;
	private String roleName;
	

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

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getConfirmPassword() {
		return confirmPassword;
	}

	public void setConfirmPassword(String confirmPassword) {
		this.confirmPassword = confirmPassword;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getContactNo() {
		return contactNo;
	}

	public void setContactNo(String contactNo) {
		this.contactNo = contactNo;
	}

	public String getBusinessArea() {
		return businessArea;
	}

	public void setBusinessArea(String businessArea) {
		this.businessArea = businessArea;
	}


	public long getRoleId() {
		return roleId;
	}

	public void setRoleId(long roleId) {
		this.roleId = roleId;
	}

	public String getRoleName() {
		return roleName;
	}

	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}

	
	
}

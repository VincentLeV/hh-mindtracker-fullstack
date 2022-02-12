package haagahelia.sp.app.domain;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.data.mongodb.core.mapping.FieldType;

@Document(collection = "users")
public class User {

	@Id
	@Field(targetType = FieldType.OBJECT_ID)
	private String id;
	
	private String username;
	private String passwordHash;
	private String fullname;
	private String role;
	
	public User() {
    }

	public User(String username, String fullname, String passwordHash, String role) {
		super();
		this.username = username;
		this.fullname = fullname;
		this.passwordHash = passwordHash;
		this.role = role;
	}
	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPasswordHash() {
		return passwordHash;
	}

	public void setPasswordHash(String passwordHash) {
		this.passwordHash = passwordHash;
	}

	public String getFullname() {
		return fullname;
	}

	public void setFullname(String fullname) {
		this.fullname = fullname;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}
}

package haagahelia.sp.app.domain;

import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.data.mongodb.core.mapping.FieldType;

@Document(collection = "influencers")
public class Influencer {

	@Id
	@Field(targetType = FieldType.OBJECT_ID)
	private String id;
	private String name;
	
	@DBRef
	@Field("entries")
	private List<Entry> entries;
	
	public Influencer() {
    }

	public Influencer(String name) {
		super();
		this.name = name;
	}
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	public List<Entry> getEntries() {
		return entries;
	}
	
	public void setBooks(List<Entry> entries) {
		this.entries = entries;
	}
	
	@Override
	public String toString() {
		return "Influencer [influencerId=" + id + ", name=" + name + "]";
	}
}

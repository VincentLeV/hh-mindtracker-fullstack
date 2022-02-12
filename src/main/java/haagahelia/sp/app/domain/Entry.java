package haagahelia.sp.app.domain;

import java.util.Date;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.data.mongodb.core.mapping.FieldType;
import org.springframework.format.annotation.DateTimeFormat;

@Document(collection = "entries")
public class Entry {
	
	@Id
	@Field(targetType = FieldType.OBJECT_ID)
	private String id;
	
	private String headline;
	private String notes;
	private Integer moodRating;
	private String symptom;
	private String userId;
	private String gratitude;
	
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date date;
	
	private String time;
	
	@DBRef
    private Influencer influencer;

	
	public Entry() {
    }

	public Entry(
		String headline, 
		Integer moodRating, 
		String symptom, 
		Date date,
		String time,
		Influencer influencer,
		String userId,
		String notes,
		String gratitude
	) {
		super();
		this.headline = headline;
		this.notes = notes;
		this.moodRating = moodRating;
		this.symptom = symptom;
		this.influencer = influencer;
		this.date = date;
		this.time = time;
		this.userId = userId;
		this.gratitude = gratitude;
	}
	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}
	
	public String getUserId() {
		return userId;
	} 
	
	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getHeadline() {
		return headline;
	}

	public void setHeadline(String headline) {
		this.headline = headline;
	}

	public String getNotes() {
		return notes;
	}

	public void setNotes(String notes) {
		this.notes = notes;
	}

	public Integer getMoodRating() {
		return moodRating;
	}

	public void setMoodRating(Integer moodRating) {
		this.moodRating = moodRating;
	}

	public String getSymptom() {
		return symptom;
	}

	public void setSymptom(String symptom) {
		this.symptom = symptom;
	}

	public Influencer getInfluencer() {
		return influencer;
	}

	public void setInfluencer(Influencer influencer) {
		this.influencer = influencer;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}
	
	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}
	
	public String getGratitude() {
		return gratitude;
	}
	
	public void setGratitude(String gratitude) {
		this.gratitude = gratitude;
	}
	
	
	@Override
	public String toString() {
		if (this.influencer == null) {
			return "Entry [id=" + id + ", headline=" + headline + ", moodRating=" + moodRating + ", symptom=" + symptom + ", date=" + date + ", time=" + time + ", influencer=" + influencer + "]";
		} else {
			return "Entry [id=" + id + ", headline=" + headline + ", moodRating=" + moodRating + ", symptom=" + symptom + ", date=" + date + ", time=" + time + ", influencer=" + influencer + "]";
		}
	}
}

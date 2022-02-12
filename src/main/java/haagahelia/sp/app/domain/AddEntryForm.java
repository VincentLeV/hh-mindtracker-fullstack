package haagahelia.sp.app.domain;

import java.util.Date;

import javax.validation.constraints.NotEmpty;

import org.springframework.format.annotation.DateTimeFormat;

public class AddEntryForm {
	
	@NotEmpty
	private String headline;
	
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date date;
	
	@NotEmpty
	private String time;
	
	private String notes;
	private Integer moodRating;
	private String symptom;
	private String gratitude;
	private Influencer influencer;
	
	public String getHeadline() {
		return headline;
	}
	public void setHeadline(String headline) {
		this.headline = headline;
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

	public String getGratitude() {
		return gratitude;
	}
	public void setGratitude(String gratitude) {
		this.gratitude = gratitude;
	}
	public Influencer getInfluencer() {
		return influencer;
	}
	public void setInfluencer(Influencer influencer) {
		this.influencer = influencer;
	}
}

import java.io.Serializable;

public class Movie implements Serializable{
	private String title ;
	private String director ;
	private String description;
	private int yearOfRealease ;
	private String kind ;
	
	//create Movie Constructor 
	public Movie(String title  , String director ,int yearOfRelease , String kind ,  String desctription   ) {
		this.title=title;
		this.director=director;
		this.yearOfRealease=yearOfRelease;
		this.kind=kind;
		this.description =desctription;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDirector() {
		return director;
	}

	public void setDirector(String director) {
		this.director = director;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public int getYearOfRealease() {
		return yearOfRealease;
	}

	public void setYearOfRealease(int yearOfRealease) {
		this.yearOfRealease = yearOfRealease;
	}

	public String getKind() {
		return kind;
	}

	public void setKind(String kind) {
		this.kind = kind;
	}

	
	public String getInformation() {
		return director+","+yearOfRealease+","+kind+","+description;
	}
	
	
	

}

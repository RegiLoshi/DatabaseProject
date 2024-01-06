
public class Grades {
	private String type;
	private int grade;
	private int perc;
	public Grades(String type, int perc, int grade) {
		this.type = type;
		this.grade = grade;
		this.perc = perc;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public int getGrade() {
		return grade;
	}
	public void setGrade(int grade) {
		this.grade = grade;
	}
	public int getPerc() {
		return perc;
	}
	public void setPerc(int perc) {
		this.perc = perc;
	}
}

import java.util.Date;

public class Student {
    private int studentID;
    private String firstName;
    private String lastName;
    private String personalID;
    private Date birthday;
    private Date registrationDate;
    private float scholarship;
    private String paymentStatus;
    private int departmentID;
    private int programID;
    private int accountID;
    private int advisor;
    private String department_name;
    private String program_name;
    private int fee;
    
    public Student(int studentID, String firstName, String lastName, String personalID, Date birthday, Date registrationDate,
            float scholarship, String paymentStatus, int departmentID, int programID, int accountID, int advisor , String departmentt , String program_namee , int feee) {
    	this.studentID = studentID;
		 this.firstName = firstName;
		 this.lastName = lastName;
		 this.personalID = personalID;
		 this.birthday = birthday;
		 this.registrationDate = registrationDate;
		 this.scholarship = scholarship;
		 this.paymentStatus = paymentStatus;
		 this.departmentID = departmentID;
		 this.programID = programID;
		 this.accountID = accountID;
		 this.advisor = advisor;
		 this.department_name = departmentt;
		 this.program_name = program_namee;
		 this.fee = feee;
		}
    
    public String getDepartment_name() {
		return department_name;
	}

	public void setDepartment_name(String department_name) {
		this.department_name = department_name;
	}

	public String getProgram_name() {
		return program_name;
	}

	public void setProgram_name(String program_name) {
		this.program_name = program_name;
	}

	public int getFee() {
		return fee;
	}

	public void setFee(int fee) {
		this.fee = fee;
	}

	public int getStudentID() {
		return studentID;
	}

	public void setStudentID(int studentID) {
		this.studentID = studentID;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getPersonalID() {
		return personalID;
	}

	public void setPersonalID(String personalID) {
		this.personalID = personalID;
	}

	public String getBirthday() {
		return birthday.toString();
	}

	public void setBirthday(Date birthday) {
		this.birthday = birthday;
	}

	public String getRegistrationDate() {
		return registrationDate.toString();
	}

	public void setRegistrationDate(Date registrationDate) {
		this.registrationDate = registrationDate;
	}

	public String getScholarship() {
		return String.valueOf(scholarship * 100);
	}

	public void setScholarship(float scholarship) {
		this.scholarship = scholarship;
	}

	public String getPaymentStatus() {
		return paymentStatus;
	}

	public void setPaymentStatus(String paymentStatus) {
		this.paymentStatus = paymentStatus;
	}

	public int getDepartmentID() {
		return departmentID;
	}

	public void setDepartmentID(int departmentID) {
		this.departmentID = departmentID;
	}

	public int getProgramID() {
		return programID;
	}

	public void setProgramID(int programID) {
		this.programID = programID;
	}

	public int getAccountID() {
		return accountID;
	}

	public void setAccountID(int accountID) {
		this.accountID = accountID;
	}

	public int getAdvisor() {
		return advisor;
	}

	public void setAdvisor(int advisor) {
		this.advisor = advisor;
	}

}


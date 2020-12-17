import java.util.ArrayList;

public class Student {
	private String name;
	private String rNumber;
	private int isolationStatus;
	private int quarantineStatus;
	private boolean positivityStatus;
	private ArrayList<Course> enrolledCourses = new ArrayList<Course>();
	private Residency residency;
	
	public Student(String n, String r) {
		name = n;
		rNumber = r;
		isolationStatus = 0;
		quarantineStatus = 0;
		if(Math.random() < 0.1) {
			positivityStatus = true;
		} else {
			positivityStatus = false;
		}
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getrNumber() {
		return rNumber;
	}

	public void setrNumber(String rNumber) {
		this.rNumber = rNumber;
	}

	public int getIsolationStatus() {
		return isolationStatus;
	}

	public void setIsolationStatus(int isolationStatus) {
		this.isolationStatus = isolationStatus;
	}

	public int getQuarantineStatus() {
		return quarantineStatus;
	}

	public void setQuarantineStatus(int quaratineStatus) {
		this.quarantineStatus = quaratineStatus;
	}
	
	public boolean getPositivityStatus() {
		return positivityStatus;
	}

	public void setPositivityStatus(boolean positivityStatus) {
		this.positivityStatus = positivityStatus;
	}

	public ArrayList<Course> getEnrolledCourses() {
		return enrolledCourses;
	}

	public void setEnrolledCourses(ArrayList<Course> enrolledCourses) {
		this.enrolledCourses = enrolledCourses;
	}

	public Residency getResidency() {
		return residency;
	}

	public void setResidency(Residency residency) {
		this.residency = residency;
	}

	public void removeIsolationDays(int days) {
		this.isolationStatus -= days;
		if (isolationStatus <= 0) {
			this.isolationStatus = 0;
			this.positivityStatus = false;
		}
	}
	
	public void removeQuarantineDays(int days) {
		this.quarantineStatus -= days;
		if (quarantineStatus < 0) {
			this.quarantineStatus = 0;
		}
	}
	
	public void addCourse(Course course) {
		enrolledCourses.add(course);
	}
	
	// Called when removing from student first
	public void removeCourse(Course course) {
		for (int i = 0; i < enrolledCourses.size(); i++) {
			if (course.getCourseName().equals(enrolledCourses.get(i).getCourseName())) {
				enrolledCourses.get(i).removeStudent(this.rNumber);
				enrolledCourses.remove(i);
			}
		}
	}
	
	// Called when removing from course first
	public void removeCourse(String courseName) {
		for (int i = 0; i < enrolledCourses.size(); i++) {
			if (courseName.equals(enrolledCourses.get(i).getCourseName())) {
				enrolledCourses.remove(i);
			}
		}
	}
	
	public String toString() {
		if (isolationStatus > 0) {
			return name + " has tested positive with COVID and is in isolation for " + isolationStatus + " more days";
		} else if (quarantineStatus > 0) {
			return name + " was in close contact with someone who tested positive for COVID and is in quarantine for " + quarantineStatus + " more days";
		} else {
			return name + " has not tested positive for COVID and has not been reported to have been in contact with someone who has tested positive.";
		}
	}
}

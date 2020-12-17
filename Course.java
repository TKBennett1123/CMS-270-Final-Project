import java.util.ArrayList;

public class Course {
	private String courseName;
	private ArrayList<Student> enrolledStudents = new ArrayList<Student>();
	boolean inPerson;
	
	public Course(String name, boolean ip) {
		this.courseName = name;
		this.inPerson = ip;
	}

	public String getCourseName() {
		return courseName;
	}

	public void setCourseName(String courseName) {
		this.courseName = courseName;
	}

	public ArrayList<Student> getEnrolledStudents() {
		return enrolledStudents;
	}

	public void setEnrolledStudents(ArrayList<Student> enrolledStudents) {
		this.enrolledStudents = enrolledStudents;
	}

	public boolean getInPerson() {
		return inPerson;
	}

	public void setInPerson(boolean inPerson) {
		this.inPerson = inPerson;
	}
	
	public void addStudent(Student student) {
		enrolledStudents.add(student);
	}
	
	// Called when removing from course first
	public void removeStudent(Student student) {
		for (int i = 0; i < enrolledStudents.size(); i++) {
			if (student.getrNumber().equals(enrolledStudents.get(i).getrNumber())) {
				enrolledStudents.get(i).removeCourse(this.courseName);
                enrolledStudents.remove(i);
			}
		}
	}
	
	// Called when removing from student first
	public void removeStudent(String studentRNumber) {
		for (int i = 0; i < enrolledStudents.size(); i++) {
			if (studentRNumber.equals(enrolledStudents.get(i).getrNumber())) {
                enrolledStudents.remove(i);
			}
		}
	}
	
	public String toString() {
		String stringReturn = courseName + " ";
		if (inPerson) {
			stringReturn = stringReturn + "(in-person): ";
		} else {
			stringReturn = stringReturn + "(online): ";
		}
		for (int i = 0; i < enrolledStudents.size(); i++) {
			if (i != enrolledStudents.size() - 1) {
				stringReturn = stringReturn + enrolledStudents.get(i).getName() + ", ";
			} else {
				stringReturn = stringReturn + enrolledStudents.get(i).getName();
			}
			
		}
		return stringReturn;
	}
}

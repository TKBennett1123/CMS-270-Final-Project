import java.util.ArrayList;

public class Course {
	//
	// Data members
	//
	private String courseName;
	private ArrayList<Student> enrolledStudents = new ArrayList<Student>();
	boolean inPerson; // if course is taught in person or not. true: in person, false: online
	
	//
	// Constructors
	//
	
	/** Constructor
	 * 
	 * @param name	name of the course
	 * @param ip	rather or not the course is in person or online. true: in person, false: online
	 */
	public Course(String name, boolean ip) {
		this.courseName = name;
		this.inPerson = ip;
	}

	//
	// Getters and Setters
	//
	
	/** returns the name of the course
	 * 
	 * @return	String	name of the course
	 */
	public String getCourseName() {
		return courseName;
	}

	/** sets the name of the course
	 * 
	 * @param courseName	name of the course
	 */
	public void setCourseName(String courseName) {
		this.courseName = courseName;
	}

	/**	returns all enrolled students
	 * 
	 * @return	ArrayList<Student>	returns an array list of all enrolled students in the course
	 */
	public ArrayList<Student> getEnrolledStudents() {
		return enrolledStudents;
	}

	/** sets the list of enrolled students
	 * 
	 * @param enrolledStudents	an array list of all enrolled students
	 */
	public void setEnrolledStudents(ArrayList<Student> enrolledStudents) {
		this.enrolledStudents = enrolledStudents;
	}

	/** returns a boolean of if the course is taught in person or not
	 * 
	 * @return	boolean	true: in person, false: online only
	 */
	public boolean getInPerson() {
		return inPerson;
	}

	/** sets rather or not a course is taught in person
	 * 
	 * @param inPerson	true: in person, false: online only
	 */
	public void setInPerson(boolean inPerson) {
		this.inPerson = inPerson;
	}
	
	//
	// Methods
	//
	
	/** adds a student to the roster
	 * 
	 * @param student	Student object added to roster
	 */
	public void addStudent(Student student) {
		enrolledStudents.add(student);
	}
	
	// Call when removing from course first
	/** Removes a student from the roster
	 * 
	 * @param student	Student object to be removed
	 */
	public void removeStudent(Student student) {
		// finds the student
		for (int i = 0; i < enrolledStudents.size(); i++) {
			if (student.getrNumber().equals(enrolledStudents.get(i).getrNumber())) {
				// Removes the course from the schedule in the student object
				enrolledStudents.get(i).removeCourse(this.courseName);
				// removes student from the roster
                enrolledStudents.remove(i);
			}
		}
	}
	
	// Call when removing from student first
	/** Removes a student from the roster
	 * 
	 * @param studentRNumber	r number used to identify the student
	 */
	public void removeStudent(String studentRNumber) {
		// Finds the student
		for (int i = 0; i < enrolledStudents.size(); i++) {
			if (studentRNumber.equals(enrolledStudents.get(i).getrNumber())) {
                // removes the student from the roster
				enrolledStudents.remove(i);
			}
		}
	}
	
	/**Outputs in a readable format the roster for the course and rather or not it's online or in person
	 * 
	 * @return	String	output
	 */
	public String toString() {
		// String builder approach with information
		// name of the course
		String stringReturn = courseName + " ";
		
		// if in person
		if (inPerson) {
			stringReturn = stringReturn + "(in-person): ";
		// if online
		} else {
			stringReturn = stringReturn + "(online): ";
		}
		// adds the list of students to the string
		for (int i = 0; i < enrolledStudents.size(); i++) {
			// adds comma seperators after each student's name except for the last element in the list
			if (i != enrolledStudents.size() - 1) {
				stringReturn = stringReturn + enrolledStudents.get(i).getName() + ", ";
			} else {
				stringReturn = stringReturn + enrolledStudents.get(i).getName();
			}
			
		}
		// returns the completed string output for the course
		return stringReturn;
	}
}

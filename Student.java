import java.util.ArrayList;

public class Student {
	//
	// Data members
	//
	private String name;
	private String rNumber;
	private int isolationStatus; // int value reprsents days in isolation remaining. 0 represents not in isolation
	private int quarantineStatus; // int value reprsents days in quarantine remaining. 0 represents not in quarantine
	private boolean positivityStatus;
	private ArrayList<Course> enrolledCourses = new ArrayList<Course>();
	private Residency residency;
	
	//
	// Constructors
	//
	
	/** Constructor
	 * 
	 * @param n	name
	 * @param r	rnumber
	 */
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

	//
	// Getters and Setters
	// 
	
	/** returns the name
	 * 
	 * @return	String	name
	 */
	public String getName() {
		return name;
	}

	/** Sets the value of name
	 * 
	 * @param name	sets name
	 */
	public void setName(String name) {
		this.name = name;
	}

	/** returns the r number
	 * 
	 * @return	String	r number (ID)
	 */
	public String getrNumber() {
		return rNumber;
	}

	/** Sets r number
	 * 
	 * @param rNumber	set r number
	 */
	public void setrNumber(String rNumber) {
		this.rNumber = rNumber;
	}

	/** returns days remaining in isolation
	 * 
	 * @return	int	days remaining in isolation
	 */
	public int getIsolationStatus() {
		return isolationStatus;
	}

	/** sets the number of days remaining in isolation
	 * 
	 * @param isolationStatus	number days should be set to
	 */
	public void setIsolationStatus(int isolationStatus) {
		this.isolationStatus = isolationStatus;
	}

	/** returns days remaining in quarantine
	 * 
	 * @return	int	days remaining in quarantine
	 */
	public int getQuarantineStatus() {
		return quarantineStatus;
	}

	/** sets the number of days remaining in quarantine
	 * 
	 * @param quaratineStatus	new number of days remaining in quarantine
	 */
	public void setQuarantineStatus(int quaratineStatus) {
		this.quarantineStatus = quaratineStatus;
	}
	
	/** Returns rather or not someone is positive with COVID
	 * 
	 * @return	boolean	true: positive, false: negative
	 */
	public boolean getPositivityStatus() {
		return positivityStatus;
	}

	/** Sets the positivity status 
	 * 
	 * @param positivityStatus	true: positive, false: negative
	 */
	public void setPositivityStatus(boolean positivityStatus) {
		this.positivityStatus = positivityStatus;
	}

	/** returns an array list of all courses the student is enrolled in
	 * 
	 * @return	ArrayList<Course>	list of all enrolled course objects
	 */
	public ArrayList<Course> getEnrolledCourses() {
		return enrolledCourses;
	}

	/** sets the array list of all enrolled courses
	 * 
	 * @param enrolledCourses	sets all enrolled course objects
	 */
	public void setEnrolledCourses(ArrayList<Course> enrolledCourses) {
		this.enrolledCourses = enrolledCourses;
	}

	/** returns someone's residence
	 * 
	 * @return	Residency	residency object
	 */
	public Residency getResidency() {
		return residency;
	}
	
	/** Sets someones residence
	 * 
	 * @param residency	Residency object
	 */
	public void setResidency(Residency residency) {
		this.residency = residency;
	}

	/** Removes a certain number of days the student is to remain in isolation
	 * 
	 * @param days	days to be removed
	 */
	public void removeIsolationDays(int days) {
		this.isolationStatus -= days;
		// Can't have a negative number of days
		if (isolationStatus <= 0) {
			this.isolationStatus = 0;
			// If released from isolation, they're no longer positive
			this.positivityStatus = false;
		}
	}
	
	/** Removes a certain number of days the student is to remain in quarantine
	 * 
	 * @param days	days to be removed
	 */
	public void removeQuarantineDays(int days) {
		this.quarantineStatus -= days;
		// Can't have a negative number of days remaining
		if (quarantineStatus < 0) {
			this.quarantineStatus = 0;
		}
	}
	
	/** Adds a course to a student's schedule
	 * 
	 * @param course	course object to be added to array list
	 */
	public void addCourse(Course course) {
		// Adds to array list of all other courses
		enrolledCourses.add(course);
	}
	
	// Call when removing from student object first
	/** Removes a student from a course
	 * 
	 * @param course	course to be removed from
	 */
	public void removeCourse(Course course) {
		// Finds the course
		for (int i = 0; i < enrolledCourses.size(); i++) {
			if (course.getCourseName().equals(enrolledCourses.get(i).getCourseName())) {
				// Removes student from the array list of students in the course object
				enrolledCourses.get(i).removeStudent(this.rNumber);
				// removes course from the array list
				enrolledCourses.remove(i);
			}
		}
	}
	
	// Call when removing from course object first
	/** Removes a student from a course
	 * 
	 * @param courseName	course to be removed
	 */
	public void removeCourse(String courseName) {
		// Finds the course
		for (int i = 0; i < enrolledCourses.size(); i++) {
			if (courseName.equals(enrolledCourses.get(i).getCourseName())) {
				// Removes the course from the list of enrolled courses
				enrolledCourses.remove(i);
			}
		}
	}
	
	/** Returns in a readable format information regarding isolation status
	 * 
	 * @return	String	One of three strings with isolation/quarantine status
	 */
	public String toString() {
		// in isolation
		if (isolationStatus > 0) {
			return name + " has tested positive with COVID and is in isolation for " + isolationStatus + " more days";
		} 
		// in quarantine
		else if (quarantineStatus > 0) {
			return name + " was in close contact with someone who tested positive for COVID and is in quarantine for " + quarantineStatus + " more days";
		} 
		// in neither isolation or quarantine
		else {
			return name + " has not tested positive for COVID and has not been reported to have been in contact with someone who has tested positive.";
		}
	}
}

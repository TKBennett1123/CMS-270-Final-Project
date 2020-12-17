import java.util.ArrayList;

public class Residency {
	//
	// Data members
	//
	private boolean onCampus; // rather of not someone lives on campus or not. true: on campus, false: off campus
	private String dormName;
	private String dormNumber;
	private ArrayList<Student> roommates = new ArrayList<Student>();
	
	//
	// Constuctors
	//
	
	/** Constructor - Used to create off campus object
	 * 
	 */
	public Residency() {
		this.onCampus = false;
		this.dormName = "commuter";
		this.dormNumber = "commuter";
	}
	
	/** Constructor - Creates each dorm
	 * 
	 * @param dname		name of the dorm
	 * @param dnumber	dorm number
	 */
	public Residency(String dname, String dnumber) {
		this.onCampus = true;
		this.dormName = dname;
		this.dormNumber = dnumber;
	}

	//
	// Getters and Setters
	//
	
	/** returns rather or not the dorm is on campus
	 * 
	 * @return	boolean	true: on campus, false: off campus
	 */
	public boolean getOnCampus() {
		return onCampus;
	}

	/**	sets rather or not the dorm is located on campus
	 * 
	 * @param resident	sets rather or not the dorm is on campus
	 */
	public void setOnCampus(boolean onCampus) {
		this.onCampus = onCampus;
	}

	/** returns the dorm building name
	 * 
	 * @return	String	name of the dorm building
	 */
	public String getDormName() {
		return dormName;
	}

	/** Sets the dorm building name
	 * 
	 * @param dormName	name of the dorm
	 */
	public void setDormName(String dormName) {
		this.dormName = dormName;
	}

	/** returns the room number
	 * 
	 * @return	String	room number
	 */
	public String getDormNumber() {
		return dormNumber;
	}

	/** sets dorm number
	 * 
	 * @param dormNumber	dorm number
	 */
	public void setDormNumber(String dormNumber) {
		this.dormNumber = dormNumber;
	}

	/** Returns an array list of all students living in the dorm
	 * 
	 * @return	ArrayList<Student>	all students in the dorm
	 */
	public ArrayList<Student> getRoommates() {
		return roommates;
	}

	/** Sets the list off roommates in the dorm
	 * 
	 * @param roommates	all roommates
	 */
	public void setRoommates(ArrayList<Student> roommates) {
		this.roommates = roommates;
	}
	
	//
	// Methods
	//
	
	/** adds a student to the dorm room
	 * 
	 * @param student	Student object to be added to the dorm
	 */
	public void addRoommate(Student student) {
		roommates.add(student);
	}
	
	/**	removes a student from the list of roommates and from the room
	 * 
	 * @param student	Student object to be removed
	 */
	public void removeStudent(Student student) {
		// finds the student
		for (int i = 0; i < roommates.size(); i++) {
			if (student.getName().equals(roommates.get(i).getName())) {
                // removes it from the array list of roommates
				roommates.remove(i);
			}
		}
	}
	
	/**	removes a student from the list of roommates and from the room
	 * 
	 * @param name	name of student to be removed
	 */
	public void removeStudent(String name) {
		// finds the student
		for (int i = 0; i < roommates.size(); i++) {
			if (name.equals(roommates.get(i).getName())) {
				// removes it from the array list of roommates
				roommates.remove(i);
			}
		}
	}
	
	/** Returns in a readable format the information about the room
	 * 
	 * @return	String	Readable format of list of individuals in a room
	 */
	public String toString() {
		// String builder approach
		String stringReturn;
		
		// If living on campus
		if (onCampus == true) {
			stringReturn = dormName + " " + dormNumber + ": ";
			// Gets list of roommates and adds to the string
			for (int i = 0; i < roommates.size(); i++) {
				// Adds comma seperators between each student name
				if (i != roommates.size() - 1) {
					stringReturn = stringReturn + roommates.get(i).getName() + ", ";
				} else {
					stringReturn = stringReturn + roommates.get(i).getName();
				}	
			}
			// If no one lives in the room expresses as such
			// Could be used to house isolated students
			if (roommates.size() == 0) {
				stringReturn = stringReturn + "Empty";
			}
		// If off campus
		} else {
			stringReturn = "Communters: ";
			// Gets list of roommates and adds to the string
			for (int i = 0; i < roommates.size(); i++) {
				// Adds comma seperators between each student name
				if (i != roommates.size() - 1) {
					stringReturn = stringReturn + roommates.get(i).getName() + ", ";
				} else {
					stringReturn = stringReturn + roommates.get(i).getName();
				}
				
			}
			// If no one lives in the room expresses as such
			// Could be used to house isolated students
			if (roommates.size() == 0) {
				stringReturn = stringReturn + "Empty";
			}
		}
		
		// Returns the completed string
		return stringReturn;
	}
	
}

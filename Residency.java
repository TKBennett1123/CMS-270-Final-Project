import java.util.ArrayList;

public class Residency {
	private boolean campusResident;
	private String dormName;
	private String dormNumber;
	private ArrayList<Student> roommates = new ArrayList<Student>();
	
	public Residency() {
		this.campusResident = false;
		this.dormName = "commuter";
		this.dormNumber = "commuter";
	}
	
	public Residency(String dname, String dnumber) {
		this.campusResident = true;
		this.dormName = dname;
		this.dormNumber = dnumber;
	}

	public boolean iscampusResident() {
		return campusResident;
	}

	public void setResident(boolean resident) {
		this.campusResident = resident;
	}

	public String getDormName() {
		return dormName;
	}

	public void setDormName(String dormName) {
		this.dormName = dormName;
	}

	public String getDormNumber() {
		return dormNumber;
	}

	public void setDormNumber(String dormNumber) {
		this.dormNumber = dormNumber;
	}

	public ArrayList<Student> getRoommates() {
		return roommates;
	}

	public void setRoommates(ArrayList<Student> roommates) {
		this.roommates = roommates;
	}
	
	public void addRoommate(Student student) {
		roommates.add(student);
	}
	
	public void removeStudent(Student student) {
		for (int i = 0; i < roommates.size(); i++) {
			if (student.getName().equals(roommates.get(i).getName())) {
                roommates.remove(i);
			}
		}
	}
	
	public void removeStudent(String name) {
		for (int i = 0; i < roommates.size(); i++) {
			if (name.equals(roommates.get(i).getName())) {
                roommates.remove(i);
			}
		}
	}
	
	public String toString() {
		String stringReturn;
		if (campusResident == true) {
			stringReturn = dormName + " " + dormNumber + ": ";
			for (int i = 0; i < roommates.size(); i++) {
				if (i != roommates.size() - 1) {
					stringReturn = stringReturn + roommates.get(i).getName() + ", ";
				} else {
					stringReturn = stringReturn + roommates.get(i).getName();
				}	
			}	
			if (roommates.size() == 0) {
				stringReturn = stringReturn + "Empty";
			}
		} else {
			stringReturn = "Communters: ";
			for (int i = 0; i < roommates.size(); i++) {
				if (i != roommates.size() - 1) {
					stringReturn = stringReturn + roommates.get(i).getName() + ", ";
				} else {
					stringReturn = stringReturn + roommates.get(i).getName();
				}
				
			}
			if (roommates.size() == 0) {
				stringReturn = stringReturn + "Empty";
			}
		}
		return stringReturn;
	}
}

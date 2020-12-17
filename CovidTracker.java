import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class CovidTracker {
	private static ArrayList<Student> allStudents = new ArrayList<Student> ();
	private static ArrayList<Student> isolatedStudents = new ArrayList<Student> ();
	private static ArrayList<Student> quarantinedStudents = new ArrayList<Student> ();
	private static ArrayList<Course> allCourses = new ArrayList<Course>();
	private static ArrayList<Residency> allResHalls = new ArrayList<Residency>();
	private static Scanner userInputScanner = new Scanner(System.in);
	private static Scanner fileReader;
	private static int day = 1;
	private static int testsAdministered = 0;
	
	public static void importStudentData() {
		try {
			File file = new File("StudentData.txt");
			fileReader = new Scanner(file);
			
			int num = Integer.parseInt(fileReader.nextLine());
			for (int i = 0; i < num; i++) {
		          String input = fileReader.nextLine();
		          String[] tokens = input.split(" ");
		          
		          String name = tokens[0] + " " + tokens[1];
		          String rNumber = tokens[2];
		          
		          Student newStudent = new Student(name, rNumber);
		          
		          if (tokens[3].equals("0")) {
		        	  newStudent.setResidency(allResHalls.get(0));
		        	  allResHalls.get(0).addRoommate(newStudent);
		          } else {
		        	  for (int n = 1; n < allResHalls.size(); n++) {
		      			if (tokens[4].equals(allResHalls.get(n).getDormName()) && tokens[5].equals(allResHalls.get(n).getDormNumber())) {
		      				newStudent.setResidency(allResHalls.get(n));
		      				allResHalls.get(n).addRoommate(newStudent);
		      			}
		      		}
		          }
		          allStudents.add(newStudent);
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}
	
	public static void importCourseData() {
		try {
			File file = new File("CourseData.txt");
			fileReader = new Scanner(file);
			
			int num = Integer.parseInt(fileReader.nextLine());
			for (int i = 0; i < num; i++) {
				String input = fileReader.nextLine();
		        String[] tokens = input.split(" ");
		        String courseName = tokens[0];
		        boolean inPerson;
		        if (tokens[1].equals("1")) {
		        	inPerson = true;
		        } else {
		        	inPerson = false;
		        }
		        Course course = new Course(courseName, inPerson);
		        allCourses.add(course);
		        
		        int numOfStudents = Integer.parseInt(tokens[2]);
		        for (int n = 0; n < numOfStudents; n++) {
		        	Student student = studentFromR(tokens[3 + n]);
		        	student.addCourse(course);
		        	course.addStudent(student);
		        }
			}
		}  catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}
	
	public static void importResidenciesData() {
		try {
			File file = new File("ResHalls.txt");
			fileReader = new Scanner(file);
			
			Residency residency = new Residency();
			allResHalls.add(residency);
			
			int num = Integer.parseInt(fileReader.nextLine());
			for (int i = 0; i < num; i++) {
				String input = fileReader.nextLine();
		        String[] tokens = input.split(" ");
		        
		        String dormName = tokens[0];
		        String dormNumber = tokens[1];
		        
		        residency = new Residency(dormName, dormNumber);
		        allResHalls.add(residency);
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}
	
	public static Student studentFromR(String rNumber) {
        for (int i = 0; i < allStudents.size(); i++) { // Loop through all Students and check if there is a match with the rNumber.
            if (rNumber.equals(allStudents.get(i).getrNumber())) { // If there's a match, return that Student
                    return allStudents.get(i);
            }
        }

        // The following will only execute if there is no matching Student found with the given R-Number
        System.out.println("There is no student in our directory with the R-Number " + rNumber + "\n");
        return null; // If none is found, return null
    }
	
	public static void removeFromIsolation(String rNumber) {
		for (int i = 0; i < isolatedStudents.size(); i++) { // Loop through all isolated students and check if there is a match with the rNumber.
            if (rNumber.equals(isolatedStudents.get(i).getrNumber())) { // If there's a match, remove the student from the list
                    isolatedStudents.remove(i);
            }
        }
	}
	
	public static void removeFromQuarantine(String rNumber) {
		for (int i = 0; i < quarantinedStudents.size(); i++) { // Loop through all quarantined students and check if there is a match with the rNumber.
            if (rNumber.equals(quarantinedStudents.get(i).getrNumber())) { // If there's a match, remove the student from the list
                    quarantinedStudents.remove(i);
            }
        }
	}
	
	public static void isolate(Student student) {
		String userInput;
		System.out.println(student.getName() + " has been moved to isolation for 10 days");
		student.setIsolationStatus(10);
		isolatedStudents.add(student);
		if (student.getQuarantineStatus() > 0) {
			removeFromQuarantine(student.getrNumber());
			student.setQuarantineStatus(0);
		}
		
		ArrayList<Course> courses = student.getEnrolledCourses();
		for (int i = 0; i < courses.size(); i++) {
			if (courses.get(i).getInPerson() == true) {
				for (int n = 0; n < courses.get(i).getEnrolledStudents().size(); n++) {
					Student classmate = courses.get(i).getEnrolledStudents().get(n);
					System.out.println(student.getName() + " is in " + courses.get(i).getCourseName() + " which is an in-person course with " + classmate.getName());
					quarantine(classmate);
				}
			}
		}
		
		if (student.getResidency().iscampusResident() == true) {
			ArrayList<Student> roommates = student.getResidency().getRoommates();
			for (int i = 0; i < roommates.size(); i++) {
				System.out.println(student.getName() + " lives with " + roommates.get(i).getName());
				quarantine(roommates.get(i));
			}
		}
		
		System.out.println("\nHave you been in close contact with anyone? Students you live with or share a class with have already been notified (y/n)");
		userInput = userInputScanner.nextLine();
		if (userInput.equals("n") || userInput.equals("N")) {
			return;
		}
		
		boolean looping = true;
		while (looping) {
			System.out.println("Please input the r number of a student you've been in close contact with");
			userInput = userInputScanner.nextLine();
			student = studentFromR(userInput);
			if (student != null) {
				quarantine(student);
			}
			System.out.println("\nWould you like to input another student? (y/n)");
			userInput = userInputScanner.nextLine();
			if (userInput.equals("n") || userInput.equals("N")) {
				looping = false;
			}
		}
	}
	
	public static void quarantine(Student student) {
		if (student.getIsolationStatus() > 0) {
			System.out.println(student.getName() + " is already in isolation");
		} else if (student.getQuarantineStatus() > 0) {
			System.out.println(student.getName() + " is already in quarantine");
		} else {
			System.out.println(student.getName() + " has been contacted via contact tracing and moved to quarantine for 14 days");
			student.setQuarantineStatus(14);
			quarantinedStudents.add(student);
		}
	}
	
	public static void printStatistics() {
		System.out.println("It is currently day " + day);
		System.out.println("Total students: "+ allStudents.size());
		System.out.println("Total number in isolation: " + isolatedStudents.size());
		System.out.println("Total number in quarantine: " + quarantinedStudents.size());
		System.out.println("Percent of student body that's tested positive: " + (double)isolatedStudents.size() / allStudents.size() * 100 + "%");
		System.out.println("Total tests administered: " + testsAdministered);
	}
	
	public static void inputTestResults() {
		System.out.println("Please enter the r number for the student to be tested:");
		String userInput = userInputScanner.nextLine();
		Student student = studentFromR(userInput);
		if (student == null) {
			return;
		}
		testsAdministered++;
		if (student.getPositivityStatus() == true) {
			System.out.println("\n" + student.getName() + " tested positive and is being moved to isolation");
			isolate(student);
		} else {
			System.out.println(student.getName() + " tested negative");
		}
		
	}
	
	public static void checkStudentStatus() {
		System.out.println("Please enter the r number for the student that you want to check the status of:");
		String userInput = userInputScanner.nextLine();
		Student student = studentFromR(userInput);
		if (student == null) {
			return;
		}
		System.out.println(student.toString());
	}
	
	public static void printAllStudentData() {
		for (int i = 0; i < allStudents.size(); i++) {
			System.out.println(allStudents.get(i).toString());
		}
	}
	
	public static void advanceDays() {
		day += 5;
		for (int i = 0; i < isolatedStudents.size(); i++) {
			isolatedStudents.get(i).removeIsolationDays(5);
		}
		for (int i = 0; i < quarantinedStudents.size(); i++) {
			quarantinedStudents.get(i).removeQuarantineDays(5);
		}
		
		for (int i = 0; i < allStudents.size(); i++) {
			if(Math.random() < 0.05) {
				allStudents.get(i).setPositivityStatus(true);
			}
			
			if(Math.random() < 0.25) {
				testsAdministered++;
				Student student = allStudents.get(i);
				System.out.println(student.getName() + " was selected for random testing.");
				if (student.getPositivityStatus() == false) {
					System.out.println(student.getName() + " tested negative");
				} else if (student.getPositivityStatus() == true) {
					System.out.println(student.getName() + " tested positive and is being moved to isolation");
					isolate(student);
				}
			}
		}
	}
	
	public static void printAllCourseInfo() {
		for (int i = 0; i < allCourses.size(); i++) {
			System.out.println(allCourses.get(i).toString());
		}
	}
	
	public static void printAllResInfo() {
		for (int i = 0; i < allResHalls.size(); i++) {
			System.out.println(allResHalls.get(i).toString());
		}
	}
	
	public static void changeStudentInfo() {
		System.out.println("Please enter the r number for the student whose information you want to update:");
		String userInput = userInputScanner.nextLine();
		Student student = studentFromR(userInput);
		boolean running = true;
		while (running) {
			System.out.println("\nStudent selected: " + student.getName() + "\n"
					+ "Which would you like to do?\n"
					+ "[1.]Drop course\n"
					+ "[2.]Add course\n"
					+ "[3.]Change residency\n"
					+ "[4.]Move off campus\n"
					+ "[5.]Change name\n"
					+ "[6.]Change r number\n"
					+ "[7.]Withdraw from school\n"
					+ "[8.]Go back");
			int userMenuChoice = Integer.parseInt(userInputScanner.nextLine());
			if (userMenuChoice == 1) {
				System.out.println("Which course would you like to drop?");
				userInput = userInputScanner.nextLine();
				for (int i = 0; i < allCourses.size(); i++) {
					if (userInput.equals(allCourses.get(i).getCourseName())) {
						student.removeCourse(allCourses.get(i));
						System.out.println(student.getName() + " has dropped " + userInput);
						return;
					}
				}
				System.out.println("Course not found. Not processed");
			} else if (userMenuChoice == 2) {
				System.out.println("Which course would you like to add?");
				userInput = userInputScanner.nextLine();
				for (int i = 0; i < allCourses.size(); i++) {
					if (userInput.equals(allCourses.get(i).getCourseName())) {
						student.addCourse(allCourses.get(i));
						System.out.println(student.getName() + " has added " + userInput);
						return;
					}
				}
				System.out.println("Course not found. Not processed");
			} else if (userMenuChoice == 3) {
				System.out.println("Where would you like to move " + student.getName() + "?");
				userInput = userInputScanner.nextLine();
				for (int i = 1; i < allResHalls.size(); i++) {
					if (userInput.equals(allResHalls.get(i).getDormName() + " " + allResHalls.get(i).getDormNumber())) {
						student.getResidency().removeStudent(student);
						student.setResidency(allResHalls.get(i));
						allResHalls.get(i).addRoommate(student);
						System.out.println(student.getName() + " has moved into " + userInput);
						return;
					}
				}
				System.out.println("Residence not found. Not processed");
			} else if (userMenuChoice == 4) {
				System.out.println(student.getName() + " has moved off campus");
				student.getResidency().removeStudent(student);
				student.setResidency(allResHalls.get(0));
			} else if (userMenuChoice == 5) {
				System.out.println("What should the new name be?");
				userInput = userInputScanner.nextLine();
				student.setName(userInput);
				System.out.println("Their name has been changed to " + student.getName());
				return;
			} else if (userMenuChoice == 6) {
				System.out.println("What should the new r number be?");
				userInput = userInputScanner.nextLine();
				student.setrNumber(userInput);
				System.out.println("Their r number has been changed to " + student.getrNumber());
			} else if (userMenuChoice == 7) {
				student.getResidency().removeStudent(student);
				for (int i = 0; i < student.getEnrolledCourses().size(); i++) {
					student.removeCourse(allCourses.get(i));
				}
				removeFromIsolation(student.getrNumber());
				removeFromQuarantine(student.getrNumber());
				for (int i = 0; i < allStudents.size(); i++) {
					if (student.getName().equals(allStudents.get(i).getName())) { 
		                    allStudents.remove(i);
		            }
		        }
				System.out.println("Withdraw successfully processed");
				return;
			} else if (userMenuChoice == 8) {
				return;
			}
		}
	}
	
	public static void main(String[] args) {
		importResidenciesData();
		importStudentData();
		importCourseData();
		
		System.out.println("Welcome to the COVID-19 Contact Tracing Application.");
		
		boolean running = true;
		while (running) {
			System.out.println("\nIt is currently day " + day +"\n"
					+ "Would you like to:\n"
					+ "[1.] View COVID statistics\n"
					+ "[2.] Test a student\n"
					+ "[3.] Check the information of a student\n"
					+ "[4.] Change student information\n"
					+ "[5.] Print all student COVID information\n"
					+ "[6.] Print all course information\n"
					+ "[7.] Print all residency information\n"
					+ "[8.] Exit the application\n"
					+ "[9.] Advance 5 days\n\n"
					+ "Please type the number corresponding to your request below.");
			int userInput = Integer.parseInt(userInputScanner.nextLine());
			if (userInput == 1) {
				printStatistics();
			} else if (userInput == 2) {
				inputTestResults();
			} else if (userInput == 3) {
				checkStudentStatus();
			} else if (userInput == 4) {
				changeStudentInfo();
			} else if (userInput == 5) {
				printAllStudentData();
			} else if(userInput == 6) {
				printAllCourseInfo();
			} else if(userInput == 7) {
				printAllResInfo();
			} else if(userInput == 8) {
				break;
			} else if(userInput == 9) {
				advanceDays();
			} else {
				System.out.println("Only input a number representing one of the menu choices listed");
			}
		}
		
		System.out.println("Thank you for using the COVID-19 Contact Tracing Application. Have a nice day.");
	    
		userInputScanner.close();
		fileReader.close();
	}
}

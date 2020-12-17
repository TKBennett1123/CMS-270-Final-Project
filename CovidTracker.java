import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

// Driver class
public class CovidTracker {
	//
	// Global variables
	//
	
	// all students
	private static ArrayList<Student> allStudents = new ArrayList<Student> ();
	// all students in isolation
	private static ArrayList<Student> isolatedStudents = new ArrayList<Student> ();
	// all students in quarantine
	private static ArrayList<Student> quarantinedStudents = new ArrayList<Student> ();
	// all courses
	private static ArrayList<Course> allCourses = new ArrayList<Course>();
	// all res halls
	private static ArrayList<Residency> allResHalls = new ArrayList<Residency>();
	// Scanners
	// reads from the console
	private static Scanner userInputScanner = new Scanner(System.in);
	// reads from .txt files
	private static Scanner fileReader;
	// day counter
	private static int day = 1;
	// number of tests administered
	private static int testsAdministered = 0;
	
	/**
	 * Imports student data from StudentData.txt into the program
	 */
	public static void importStudentData() {
		try {
			// opens file
			File file = new File("StudentData.txt");
			fileReader = new Scanner(file);
			
			// number of student data points
			int num = Integer.parseInt(fileReader.nextLine());
			for (int i = 0; i < num; i++) {
				  // Reads the line from the file and splits it by " " into an array
		          String input = fileReader.nextLine();
		          String[] tokens = input.split(" ");
		          
		          // Creates name
		          String name = tokens[0] + " " + tokens[1];
		          // gets r number
		          String rNumber = tokens[2];
		          
		          // Creates the student object using the name and r number
		          Student newStudent = new Student(name, rNumber);
		          
		          // Adds residential information to the new Student object
		          // 0 in the res hall will always be living off campus
		          // off campus students
		          if (tokens[3].equals("0")) {
		        	  // adds residency information to the Student object
		        	  newStudent.setResidency(allResHalls.get(0));
		        	  // adds student information to the Residency object
		        	  allResHalls.get(0).addRoommate(newStudent);
		          // Living on campus
		          } else {
		        	  // finds dorm hall and room
		        	  for (int n = 1; n < allResHalls.size(); n++) {
		      			if (tokens[4].equals(allResHalls.get(n).getDormName()) && tokens[5].equals(allResHalls.get(n).getDormNumber())) {
		      				// adds residency information to the Student object
		      				newStudent.setResidency(allResHalls.get(n));
		      				// adds student information to the Residency object
		      				allResHalls.get(n).addRoommate(newStudent);
		      			}
		      		}
		          }
		          
		          // Adds the student to the array list of all students
		          allStudents.add(newStudent);
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * imports data from CourseData.txt file on available courses
	 */
	public static void importCourseData() {
		try {
			// Opens CourseData.txt file
			File file = new File("CourseData.txt");
			fileReader = new Scanner(file);
			
			// number of entries to be processed
			int num = Integer.parseInt(fileReader.nextLine());
			for (int i = 0; i < num; i++) {
				// Gets input from file
				String input = fileReader.nextLine();
				// Splits input into an array by spaces
		        String[] tokens = input.split(" ");
		        
		        // Gets the name of the course
		        String courseName = tokens[0];
		        
		        // Gets rather or not the course is in person
		        // 1: in person
		        // 2: online
		        boolean inPerson;
		        if (tokens[1].equals("1")) {
		        	inPerson = true;
		        } else {
		        	inPerson = false;
		        }
		        
		        // Creates the Course object
		        Course course = new Course(courseName, inPerson);
		        
		        // Adds the course to the list of courses
		        allCourses.add(course);
		        
		        // Gets number of students in the course
		        int numOfStudents = Integer.parseInt(tokens[2]);
		        // adds student info to the Course object and the course info to the respective students
		        for (int n = 0; n < numOfStudents; n++) {
		        	// Gets student r number
		        	Student student = studentFromR(tokens[3 + n]);
		        	// adds to student object
		        	student.addCourse(course);
		        	// adds student to course object
		        	course.addStudent(student);
		        }
			}
		}  catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * imports residence hall data from ResHalls.txt into the program
	 */
	public static void importResidenciesData() {
		try {
			// Opens file
			File file = new File("ResHalls.txt");
			fileReader = new Scanner(file);
			
			// Creates off campus residency using blank constructor
			Residency residency = new Residency();
			// adds off campus residency to the list of residences
			allResHalls.add(residency);
			
			// Number of residence fields to process
			int num = Integer.parseInt(fileReader.nextLine());
			for (int i = 0; i < num; i++) {
				// Gets input from the text file
				String input = fileReader.nextLine();
		        String[] tokens = input.split(" ");
		        
		        // Name of the residence hall
		        String dormName = tokens[0];
		        // Dorm number
		        String dormNumber = tokens[1];
		        
		        // Creates the Residency object
		        residency = new Residency(dormName, dormNumber);
		        
		        // Adds the residency to the array list of all res halls
		        allResHalls.add(residency);
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}
	
	/** Finds a student by their ID number
	 * 
	 * @param rNumber	r number to be searched
	 * 
	 * @return			returns a Student object with matching r number
	 */
	public static Student studentFromR(String rNumber) {
		// loops through the array list to find the student
        for (int i = 0; i < allStudents.size(); i++) {
            if (rNumber.equals(allStudents.get(i).getrNumber())) {
            	// If there's a match, return that Student
                return allStudents.get(i);
            }
        }

        // The following will only execute if there is no matching Student found with the given R-Number
        System.out.println("There is no student in our directory with the R-Number " + rNumber + "\n");
        
        // If none is found, return null
        return null; 
    }
	
	/** Removes a student from the array list of isolated students
	 * 
	 * @param rNumber	finds student by r number
	 */
	public static void removeFromIsolation(String rNumber) {
		// Finds the Student object
		for (int i = 0; i < isolatedStudents.size(); i++) {
			if (rNumber.equals(isolatedStudents.get(i).getrNumber())) {
            	// If there's a match, remove the student from the list
                isolatedStudents.remove(i);
            }
        }
	}
	
	/** Removes a student from the array list of quarantined students
	 * 
	 * @param rNumber	finds student by r number
	 */
	public static void removeFromQuarantine(String rNumber) {
		// Finds the Student object
		for (int i = 0; i < quarantinedStudents.size(); i++) {
			if (rNumber.equals(quarantinedStudents.get(i).getrNumber())) {
            	// If there's a match, remove the student from the list  
            	quarantinedStudents.remove(i);
            }
        }
	}
	
	/** Isolates a student and runs contact tracing
	 * 
	 * @param student	Student object representing the student to be moved into isolation
	 */
	public static void isolate(Student student) {
		// Used to gather console input
		String userInput;
		
		// Informs the user that the student has been moved to isolation
		System.out.println(student.getName() + " has been moved to isolation for 10 days");
		
		// Isolates student for 10 days
		student.setIsolationStatus(10);
		// Adds the student to the array list of isolated students
		isolatedStudents.add(student);
		
		// Can't be isolated and quarantined at the same time
		// Isolated is more restrictive so if in quarantine removes them from that list so they're only on one
		if (student.getQuarantineStatus() > 0) {
			removeFromQuarantine(student.getrNumber());
			student.setQuarantineStatus(0);
		}
		
		// Contact traces to and quarantines all students who share an in person class with the positive individual
		// courses of the positive individual
		ArrayList<Course> courses = student.getEnrolledCourses();
		for (int i = 0; i < courses.size(); i++) {
			// if the course is in person and not online
			if (courses.get(i).getInPerson() == true) {
				// Goes through each student in the course
				for (int n = 0; n < courses.get(i).getEnrolledStudents().size(); n++) {
					// Gets classmate information
					Student classmate = courses.get(i).getEnrolledStudents().get(n);
					
					// Ensures the classmate selected isn't the student that test positive being compared against themselves
					if (!student.getName().equals(classmate.getName())) {
						// Informs the user of the contact traced connection
						System.out.println(student.getName() + " is in " + courses.get(i).getCourseName() + " which is an in-person course with " + classmate.getName());
						// Moves to classmate into quarantine
						quarantine(classmate);
					}
				}
			}
		}
		
		
		// Contact traces to all roommates
		// If the individual lives on campus
		if (student.getResidency().getOnCampus() == true) {
			// Gets an array list of all roommates
			ArrayList<Student> roommates = student.getResidency().getRoommates();
			
			// Loops through each roommate
			for (int i = 0; i < roommates.size(); i++) {
				// Ensures the classmate selected isn't the student that test positive being compared against themselves
				if (!student.getName().equals(roommates.get(i).getName())) {
					// Informs the user of the contact traced connection
					System.out.println(student.getName() + " lives with " + roommates.get(i).getName());
					// Moves to roommate into quarantine
					quarantine(roommates.get(i));
				}
			}
		}
		
		// Ensures valid input
		boolean looping = true;
		
		do {
			// Asks for input for any other close contacts that need to be contact traced
			System.out.println("\nHas " + student.getName() + " been in close contact with anyone else? Students they live with or share an in-person class with have already been notified (y/n)");
			// Gets user input from console
			userInput = userInputScanner.nextLine();
			
			// Is the user answers there's no more connections exits the isolation and returns
			if (userInput.equals("n") || userInput.equals("N")) {
				return;
			} else if (userInput.equals("y") || userInput.equals("Y")) {
				looping = false;
			} else {
				System.out.println("Please only type 'y' or 'n'");
			}
		} while (looping);
		
		
		looping = true;
		while (looping) {
			
			// Asks for r number of individaul to be contact traced
			System.out.println("Please input the r number of a student you've been in close contact with");
			
			// Gets user input
			userInput = userInputScanner.nextLine();
			
			// Finds the student by r number
			student = studentFromR(userInput);
			
			// If it found a student moves them into quarantine
			if (student != null) {
				quarantine(student);
			}
			
			// Ensures valid input
			boolean looping2 = true;
			
			do {
				// Asks for user input
				System.out.println("\nWould you like to input another student? (y/n)");
				userInput = userInputScanner.nextLine();
				
				// Processes input on rather or not there's more data
				if (userInput.equals("n") || userInput.equals("N")) {
					return;
				}  else if (userInput.equals("y") || !userInput.equals("Y")) {
					looping2 = false;
				} else {
					System.out.println("Please only type 'y' or 'n'");
				}
			} while(looping2);
		}
	}
	
	/** Moves students into quarantine
	 * 
	 * @param student	student to be quarantined
	 */
	public static void quarantine(Student student) {
		// If already in isolation they can't be moved to isolation
		if (student.getIsolationStatus() > 0) {
			System.out.println(student.getName() + " is already in isolation");
		// If already in quarantine
		} else if (student.getQuarantineStatus() > 0) {
			System.out.println(student.getName() + " is already in quarantine");
		// Quarantines student
		} else {
			// Informs user the contact traced student has been contacted and moved to quarantine
			System.out.println(student.getName() + " has been contacted via contact tracing and moved to quarantine for 14 days");
			// Moves to quarantine for 14 days
			student.setQuarantineStatus(14);
			// Adds to the list of quarantined students
			quarantinedStudents.add(student);
		}
	}
	
	/** 
	 * Prints out COVID statistics
	 */
	public static void printStatistics() {
		System.out.println("It is currently day " + day);
		System.out.println("Total students: "+ allStudents.size());
		System.out.println("Total number in isolation: " + isolatedStudents.size());
		System.out.println("Total number in quarantine: " + quarantinedStudents.size());
		System.out.println("Percent of student body that's tested positive: " + (double)isolatedStudents.size() / allStudents.size() * 100 + "%");
		System.out.println("Total tests administered: " + testsAdministered);
	}
	
	/**
	 * Allows for manual testing outside the random testing
	 */
	public static void inputTestResults() {
		// Asks for user input
		System.out.println("Please enter the r number for the student to be tested:");
		String userInput = userInputScanner.nextLine();
		
		// Finds student by r number
		Student student = studentFromR(userInput);
		// if no student was found by the number return
		if (student == null) {
			return;
		}
		
		// Increases the number of tests administered for statistic tracing
		testsAdministered++;
		
		// If a student tests positive
		if (student.getPositivityStatus() == true) {
			// Informs user the student tested positive
			System.out.println("\n" + student.getName() + " tested positive and is being moved to isolation");
			// Begins isolation and contact tracing procedures for the student
			isolate(student);
		// If the test is negative
		} else {
			// Informs the user of the negative test
			System.out.println(student.getName() + " tested negative");
		}
		
	}
	
	/**
	 * Used to check the status of a specific student
	 */
	public static void checkStudentStatus() {
		// Asks for suer input
		System.out.println("Please enter the r number for the student that you want to check the status of:");
		String userInput = userInputScanner.nextLine();
		
		// Finds student by r number
		Student student = studentFromR(userInput);
		// If no student is found returns
		if (student == null) {
			return;
		}
		
		// Prints out student information
		System.out.println(student.toString());
	}
	
	/**
	 * Prints out the information for all students
	 */
	public static void printAllStudentData() {
		// Loops through every student
		for (int i = 0; i < allStudents.size(); i++) {
			// Prints out student information
			System.out.println(allStudents.get(i).toString());
		}
	}
	
	/**
	 * Advances the number of days in the system. 5 days each time
	 */
	public static void advanceDays() {
		// Advances the day counter
		day += 5;
		
		// Updates isolation information
		for (int i = 0; i < isolatedStudents.size(); i++) {
			// Removes 5 days from each student's isolation timer
			isolatedStudents.get(i).removeIsolationDays(5);
			
			// If they're now out of isolation, removes them from the list
			if (isolatedStudents.get(i).getIsolationStatus() == 0) {
				removeFromIsolation(isolatedStudents.get(i).getrNumber());
			}
		}
		
		// Updates quarantine status
		for (int i = 0; i < quarantinedStudents.size(); i++) {
			// Removes 5 days from each student's quarantine timer
			quarantinedStudents.get(i).removeQuarantineDays(5);
			
			// If they're now out of quarantine, removes them from the list
			if (quarantinedStudents.get(i).getQuarantineStatus() == 0) {
				removeFromQuarantine(quarantinedStudents.get(i).getrNumber());
			}
		}
		
		// Loops through every student
		for (int i = 0; i < allStudents.size(); i++) {
			
			// 5% of the student body on average is randomly infected with COVID
			if(Math.random() < 0.05) {
				allStudents.get(i).setPositivityStatus(true);
			}
			
			// 25% of the student body on average randomly recieves a COVID test
			if(Math.random() < 0.25) {
				// Increases the number of tests administered for statistics
				testsAdministered++;
				
				// Gets student information
				Student student = allStudents.get(i);
				
				// Informs the user that the student was randomly selected for testing
				System.out.println(student.getName() + " was selected for random testing.");
				
				// If negative
				if (student.getPositivityStatus() == false) {
					// Informs user that the student has tested negative
					System.out.println(student.getName() + " tested negative");
				// If positive
				} else if (student.getPositivityStatus() == true) {
					// Informs the user that the student tested positive
					System.out.println(student.getName() + " tested positive and is being moved to isolation");
					// Isolates the student and begins contact tracing
					isolate(student);
				}
			}
		}
	}
	
	
	/**
	 * Prints all the courses and enrolled students
	 */
	public static void printAllCourseInfo() {
		// Loops through all courses
		for (int i = 0; i < allCourses.size(); i++) {
			// Prints out their information
			System.out.println(allCourses.get(i).toString());
		}
	}
	
	/**
	 * Prints all information about res halls
	 */
	public static void printAllResInfo() {
		// Loops through all res halls
		for (int i = 0; i < allResHalls.size(); i++) {
			// Prints out their information
			System.out.println(allResHalls.get(i).toString());
		}
	}
	
	/**
	 * Used by user to change student information
	 */
	public static void changeStudentInfo() {
		// Prompts the user for input
		System.out.println("Please enter the r number for the student whose information you want to update:");
		String userInput = userInputScanner.nextLine();
		
		// Finds student by r number
		Student student = studentFromR(userInput);
		// No student found, returns
		if (student == null) {
			return;
		}
		
		// Keeps the menu up for bad input
		boolean running = true;
		while (running) {
			try {
				// Prints out menu options
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
				
				// Gets user input
				int userMenuChoice = Integer.parseInt(userInputScanner.nextLine());
				
				// Drop course
				if (userMenuChoice == 1) {
					// Asks for user input
					System.out.println("Which course would you like to drop?");
					userInput = userInputScanner.nextLine();
					
					// Loops through the courses
					for (int i = 0; i < allCourses.size(); i++) {
						// If a matching course has been found
						if (userInput.equals(allCourses.get(i).getCourseName())) {
							// Removes the student from the course
							student.removeCourse(allCourses.get(i));
							
							// Informs the user of the change
							System.out.println(student.getName() + " has dropped " + userInput);
							
							// Returns back to main menu
							return;
						}
					}
					
					// Informs the user that no match was found for the course listed. Will ask again
					System.out.println("Course not found. Not processed");
					
				// Add course
				} else if (userMenuChoice == 2) {
					// Prompts for user input
					System.out.println("Which course would you like to add?");
					userInput = userInputScanner.nextLine();
					
					// Finds the course
					for (int i = 0; i < allCourses.size(); i++) {
						if (userInput.equals(allCourses.get(i).getCourseName())) {
							// adds student to the course
							student.addCourse(allCourses.get(i));
							
							// Informs the user that the action was successful
							System.out.println(student.getName() + " has added " + userInput);
							// Returns to main
							return;
						}
					}
					
					// Informs the user that no match was found for the course listed. Will ask again
					System.out.println("Course not found. Not processed");
					
				// Change residency
				} else if (userMenuChoice == 3) {
					// Prompts user input
					System.out.println("Where would you like to move " + student.getName() + "?");
					userInput = userInputScanner.nextLine();
					
					// Finds res hall
					// Assumes staying on campus
					for (int i = 1; i < allResHalls.size(); i++) {
						if (userInput.equals(allResHalls.get(i).getDormName() + " " + allResHalls.get(i).getDormNumber())) {
							// Removes the student from the residency
							student.getResidency().removeStudent(student);
							
							// Adds student to new residency
							student.setResidency(allResHalls.get(i));
							// Updates residency records
							allResHalls.get(i).addRoommate(student);
							
							// Informs user the action was successful
							System.out.println(student.getName() + " has moved into " + userInput);
							
							// Returns to main
							return;
						}
					}
					
					// Informs the user that no match was found for the residence listed. Will ask again
					System.out.println("Residence not found. Not processed");
					
				// Move off campus
				} else if (userMenuChoice == 4) {
					// Informs user the student has been moved off campus
					System.out.println(student.getName() + " has moved off campus");
					
					// Removes student from current residency
					student.getResidency().removeStudent(student);
					// Adds to off campus housing lists
					student.setResidency(allResHalls.get(0));
					
				// Changes student name
				} else if (userMenuChoice == 5) {
					// Asks for user input
					System.out.println("What should the new name be?");
					userInput = userInputScanner.nextLine();
					
					// Changes name
					student.setName(userInput);
					
					// Informs the user of the change
					System.out.println("Their name has been changed to " + student.getName());
					
					// Returns to main
					return;
					
				// Change r number
				} else if (userMenuChoice == 6) {
					// Asks for user input
					System.out.println("What should the new r number be?");
					userInput = userInputScanner.nextLine();
					
					// Changes r number
					student.setrNumber(userInput);
					
					// Informs user of the change
					System.out.println("Their r number has been changed to " + student.getrNumber());
					
					// Returns to main
					return;
					
				// Withdraw from school
				} else if (userMenuChoice == 7) {
					// Removes from residency hall records
					student.getResidency().removeStudent(student);
					
					// Removes from course records
					for (int i = 0; i < student.getEnrolledCourses().size(); i++) {
						student.removeCourse(allCourses.get(i));
					}
					
					// Removes from isolation records
					removeFromIsolation(student.getrNumber());
					// removed from quarantine records
					removeFromQuarantine(student.getrNumber());
					// Removes from student 
					for (int i = 0; i < allStudents.size(); i++) {
						if (student.getName().equals(allStudents.get(i).getName())) { 
			                    allStudents.remove(i);
			            }
			        }
					
					// Informs user the withdraw was successfully processed
					System.out.println("Withdraw successfully processed");
					
					// Returns to main
					return;
					
				// Exit
				} else if (userMenuChoice == 8) {
					// Returns to main
					return;
				}
			// Prevents bad input causing the program to crash
			} catch (NumberFormatException e) {	
				System.out.println("Please only input a number");
			} 
		}
	}
	
	/** main
	 * 
	 * @param args	command line args
	 */
	public static void main(String[] args) {
		// Imports all data from txt files into program
		importResidenciesData();
		importStudentData();
		importCourseData();
		
		// Prints welcome message
		System.out.println("Welcome to the COVID-19 Contact Tracing Application.");
		
		// Keeps menu up
		boolean running = true;
		while (running) {
		// Bad 
			try {
				// Prints menu choices
				System.out.println("\nIt is currently day " + day +"\n"
						+ "Would you like to:\n"
						+ "[1.] View COVID statistics\n"
						+ "[2.] Test a student\n"
						+ "[3.] Check the information of a student\n"
						+ "[4.] Change student information\n"
						+ "[5.] Print all student COVID information\n"
						+ "[6.] Print all course information\n"
						+ "[7.] Print all residency information\n"
						+ "[8.] Advance 5 days\n"
						+ "[9.] Exit the application\n\n"
						+ "Please type the number corresponding to your request below.");
				
				// Gets user input
				int userInput = Integer.parseInt(userInputScanner.nextLine());
				
				// statistics
				if (userInput == 1) {
					printStatistics();
				// tests a specific student
				} else if (userInput == 2) {
					inputTestResults();
				// checks information of a specific student
				} else if (userInput == 3) {
					checkStudentStatus();
				// Changes student informatio
				} else if (userInput == 4) {
					changeStudentInfo();
				// Prints all student testing and isolation data
				} else if (userInput == 5) {
					printAllStudentData();
				// prints all registration info
				} else if(userInput == 6) {
					printAllCourseInfo();
				// prints all residency info
				} else if(userInput == 7) {
					printAllResInfo();
				// advances the program forward in time
				} else if(userInput == 8) {
					advanceDays();
				// Exits program
				} else if(userInput == 9) {
					break;
				// Bad input
				} else {
					System.out.println("Only input a number representing one of the menu choices listed");
				}
			// Prevents bad input causing the program to crash
			} catch (NumberFormatException e) {	
				System.out.println("Please only input a number");
			} 
		}
		
		// Thanks the user
		System.out.println("Thank you for using the COVID-19 Contact Tracing Application. Have a nice day.");
	    
		// Closes scanners
		userInputScanner.close();
		fileReader.close();
	}
}

package kindergarten;
/**
 * This class represents a Classroom, with:
 * - an SNode instance variable for students in line,
 * - an SNode instance variable for musical chairs, pointing to the last student in the list,
 * - a boolean array for seating availability (eg. can a student sit in a given seat), and
 * - a Student array parallel to seatingAvailability to show students filed into seats 
 * --- (more formally, seatingAvailability[i][j] also refers to the same seat in studentsSitting[i][j])
 * 
 * @author Ethan Chou
 * @author Kal Pandit
 * @author Maksims Kurjanovics Kravcenko
 * @author Kevin Joseph
 *         kbj24@rutgers.edu
 *         kbj24
 */
public class Classroom {
    private SNode studentsInLine;             // when students are in line: references the FIRST student in the LL
    private SNode musicalChairs;              // when students are in musical chairs: references the LAST student in the CLL
    private boolean[][] seatingAvailability;  // represents the classroom seats that are available to students
    private Student[][] studentsSitting;      // when students are sitting in the classroom: contains the students

    /**
     * Constructor for classrooms. Do not edit.
     * @param l passes in students in line
     * @param m passes in musical chairs
     * @param a passes in availability
     * @param s passes in students sitting
     */
    public Classroom ( SNode l, SNode m, boolean[][] a, Student[][] s ) {
		studentsInLine      = l;
        musicalChairs       = m;
		seatingAvailability = a;
        studentsSitting     = s;
	}

    /**
     * Default constructor starts an empty classroom. Do not edit.
     */
    public Classroom() {
        this(null, null, null, null);
    }

    /**
    * This private method finds the minimum SNode of SNode[] arr by height.
    * @param arr array of SNode[] students
    * @return idx, the index of the minimum of SNode[] arr
    */
    private int findMin(SNode[] arr){
        int min = Integer.MAX_VALUE;
        int idx = 0;
        for(int i = 0; i < arr.length; i++){
            if(arr[i] != null && arr[i].getStudent().getHeight() <= min){
                min = arr[i].getStudent().getHeight();
                idx = i;
            }
        }
        return idx;
    }

    /**
     * This private method adds the students to the line by height (ascending order).
     * @param arr array of SNode[] students
     */
    private void addToLine(SNode[] arr){
        SNode[] tmp = new SNode[arr.length];
        for(int i = 0; i < arr.length; i++){
            int temp = findMin(arr);
            tmp[i] = arr[temp];
            arr[temp] = null;
        }
        arr = tmp;
        studentsInLine = arr[0];
        for(int j = 1; j < arr.length; j++){
            arr[j-1].setNext(arr[j]);
        }
        arr[arr.length-1].setNext(null);
    }

    /**
     * This private method returns an array of SNode[] from the front (studentsInLine) to the back.
     * 
     * @return SNode[] array that holds all SNode of the LL. 
     */
    private SNode[] findStudents(){
        SNode temp1 = studentsInLine;
        SNode temp2 = studentsInLine;
        SNode[] result;
        int resLength = 0;
        int idx = 0;
        while(temp1 != null){
            resLength++;
            temp1 = temp1.getNext();
        }
        result = new SNode[resLength];
        while(temp2 != null){
            result[idx] = temp2;
            temp2 = temp2.getNext();
            idx++;
        }
        return result;
    }

    /**
     * This private method returns an array of SNode[] from the front (musicalChairs.getNext()) to the back (musicalChairs).
     * 
     * @return SNode[] array that holds all SNode of the CLL. 
     */
    private SNode[] findMusicalChairs(){
        SNode temp1 = musicalChairs;
        SNode temp2 = musicalChairs;
        SNode temp3 = musicalChairs;
        SNode[] result;
        int resLength = 0;
        int idx = 0;
        if(temp1.getNext() != temp1 && temp1.getNext() != null){
            resLength++;
            temp1 = temp1.getNext();
        }
        else if(temp1.getNext() == temp1){
            result = new SNode[1];
            result[0] = temp1;
            return result;
        }
        while(temp1 != temp2){
            resLength++;
            temp1 = temp1.getNext();
        }
        result = new SNode[resLength];
        if(temp2.getNext() != temp3){
            result[idx] = temp2.getNext();
            temp2 = temp2.getNext().getNext();
            idx++;
        }
        while(temp2 != temp3){
            result[idx] = temp2;
            temp2 = temp2.getNext();
            idx++;
        }
        result[idx] = temp3;
        return result;
    }
    /**
     * This private method deletes a loser from musicalChairs at nth node and returns it.
     * 
     * @param SNode loser
     * @return SNode loser
     */
    private SNode deleteFromMusicalChairs(SNode idx){
        SNode[] musLine = findMusicalChairs();
        SNode loser = new SNode();
        for(int i = 0; i < musLine.length; i++){
            if(musLine[i] == idx){
                if(i == 0){
                    SNode ptr = musLine[i].getNext();
                    musicalChairs.setNext(ptr);
                    loser = musLine[i];
                    break;

                }
                else if(i == musLine.length - 1){
                    musLine[i-1].setNext(musLine[0]);
                    musicalChairs = musLine[i-1];
                    loser = musLine[i];
                    break;

                }
                else{
                    musLine[i-1].setNext(musLine[i+1]);
                    loser = musLine[i];
                    break;
                }
            }
        }
        return loser;
    }

    /**
     * This private method simulates one round of the Musical Chairs game and returns the winner of the game.
     * 
     * @return SNode winner
     */
    private SNode musicalChairsGame(){
        SNode winner;
        SNode[] losers;
        SNode[] musLine = findMusicalChairs();
        losers = new SNode[musLine.length - 1];
        while(musLine.length != 1){
            int idx = StdRandom.uniform(musLine.length);
            for(int i = 0; i < losers.length; i++){
                if(losers[i] == null){
                    losers[i] = deleteFromMusicalChairs(musLine[idx]);
                    break;
                }
            }
            musLine = findMusicalChairs();
        }
        addToLine(losers);
        winner = musLine[0];
        return winner;
    }

    /**
     * This method simulates students coming into the classroom and standing in line.
     * 
     * Reads students from input file and inserts these students in alphabetical 
     * order to studentsInLine singly linked list.
     * 
     * Input file has:
     * 1) one line containing an integer representing the number of students in the file, say x
     * 2) x lines containing one student per line. Each line has the following student 
     * information separated by spaces: FirstName LastName Height
     * 
     * @param filename the student information input file
     */
    public void makeClassroom ( String filename ) {
        StdIn.setFile(filename);
        int maxIdx = StdIn.readInt();
        Student[] studentsLine = new Student[maxIdx];
        
        for(int i = 0; i < maxIdx; i++){
           studentsLine[i] = new Student(StdIn.readString(), StdIn.readString(), StdIn.readInt());
        }
		
        for(int i = 0; i < maxIdx - 1; i++) {
			for(int j = i + 1; j < maxIdx; j++) {
				if (studentsLine[i].getLastName().compareTo(studentsLine[j].getLastName()) > 0) {
					Student temp = studentsLine[i];
					studentsLine[i] = studentsLine[j];
					studentsLine[j] = temp;
				}
			}
		}
		for(int i = maxIdx - 1; i >= 0; i--) {
			SNode node = new SNode(studentsLine[i], studentsInLine);
			studentsInLine = node;
		}

    }

    /**
     * 
     * This method creates and initializes the seatingAvailability (2D array) of 
     * available seats inside the classroom. Imagine that unavailable seats are broken and cannot be used.
     * 
     * Reads seating chart input file with the format:
     * An integer representing the number of rows in the classroom, say r
     * An integer representing the number of columns in the classroom, say c
     * Number of r lines, each containing c true or false values (true denotes an available seat)
     *  
     * This method also creates the studentsSitting array with the same number of
     * rows and columns as the seatingAvailability array
     * 
     * This method does not seat students on the seats.
     * 
     * @param seatingChart the seating chart input file
     */
    public void setupSeats(String seatingChart) {
        StdIn.setFile(seatingChart);
        int rows = StdIn.readInt();
        int cols = StdIn.readInt();
        seatingAvailability = new boolean[rows][cols];
        studentsSitting = new Student[rows][cols];

        for(int i = 0; i < rows; i++){
            for(int j = 0; j < cols; j++){
                if(StdIn.readBoolean()){
                    seatingAvailability[i][j] = true;
                }
                else{
                    seatingAvailability[i][j] = false;
                }
            }
        } 
    }

    /**
     * 
     * This method simulates students taking their seats in the classroom.
     * 
     * 1. seats any remaining students from the musicalChairs starting from the front of the list
     * 2. starting from the front of the studentsInLine singly linked list
     * 3. removes one student at a time from the list and inserts them into studentsSitting according to
     *    seatingAvailability
     * 
     * studentsInLine will then be empty
     */
    public void seatStudents () {
        int i = 0;
        int j = 0;
        SNode last = musicalChairs;
        if(musicalChairs != null){
            SNode[] musLine = findMusicalChairs();
            if(musLine.length != 1){
                musicalChairs = musicalChairs.getNext();
            }
        }
        outerloop:
        for(int r = 0; r < studentsSitting.length; r++){
            for(int c = 0; c < studentsSitting[0].length; c++){
                if(musicalChairs != null && musicalChairs != last){
                    if(seatingAvailability[r][c]){
                        studentsSitting[r][c] = musicalChairs.getStudent();
                        musicalChairs = musicalChairs.getNext();
                    }
                }
                else if(musicalChairs != null && musicalChairs == last){
                    if(seatingAvailability[r][c]){
                        studentsSitting[r][c] = musicalChairs.getStudent();
                        musicalChairs = null;
                        i = r;
                        j = c;
                        break outerloop;
                    }
                }
                else{
                    i = r;
                    j = c;
                    break outerloop;
                }
            }
        }

        for(int r = i; r < studentsSitting.length; r++){
            for(int c = j; c < studentsSitting[0].length; c++){
                if(studentsInLine != null){
                    if(seatingAvailability[r][c]){
                        if(studentsSitting[r][c] == null){
                            studentsSitting[r][c] = studentsInLine.getStudent();
                            studentsInLine = studentsInLine.getNext();
                        }
                    }
                }
            }
        }
  
    }

    /**
     * Traverses studentsSitting row-wise (starting at row 0) removing a seated
     * student and adding that student to the end of the musicalChairs list.
     * 
     * row-wise: starts at index [0][0] traverses the entire first row and then moves
     * into second row.
     */
    public void insertMusicalChairs () {
        int isZero = 0;
		musicalChairs = new SNode();
		for(int i = studentsSitting.length - 1; i >= 0; i--) {
			for(int j = studentsSitting[0].length - 1; j >= 0; j--) {
				if(studentsSitting[i][j] != null && isZero == 0) {
					musicalChairs.setStudent(studentsSitting[i][j]);
					musicalChairs.setNext(musicalChairs);
					studentsSitting[i][j] = null;
					isZero++;
				}else if(studentsSitting[i][j] != null) {
					musicalChairs.setNext(new SNode(studentsSitting[i][j], musicalChairs.getNext()));
					studentsSitting[i][j] = null;
				}
			}
		}
    }

    /**
     * 
     * This method repeatedly removes students from the musicalChairs until there is only one
     * student (the winner).
     * 
     * Choose a student to be elimnated from the musicalChairs using StdRandom.uniform(int b),
     * where b is the number of students in the musicalChairs. 0 is the first student in the 
     * list, b-1 is the last.
     * 
     * Removes eliminated student from the list and inserts students back in studentsInLine 
     * in ascending height order (shortest to tallest).
     * 
     * The last line of this method calls the seatStudents() method so that students can be seated.
     */
    public void playMusicalChairs() {
        musicalChairs = musicalChairsGame();
        seatStudents();

    } 

    /**
     * Insert a student to wherever the students are at (ie. whatever activity is not empty)
     * Note: adds to the end of either linked list or the next available empty seat
     * @param firstName the first name
     * @param lastName the last name
     * @param height the height of the student
     */
    public void addLateStudent ( String firstName, String lastName, int height ) {
        Student lstu = new Student(firstName, lastName, height);
        if(musicalChairs != null){
            SNode[] musLine = findMusicalChairs();
            SNode m1 = new SNode(lstu, musLine[0]);
            musicalChairs.setNext(m1);
            musicalChairs = m1;
        }
        else if(studentsInLine != null){
            SNode[] stuLine = findStudents();
            SNode s1 = new SNode(lstu, null);
            stuLine[stuLine.length-1].setNext(s1);
        }
        else{
            op:
            for(int r = 0; r < studentsSitting.length; r++){
                for(int c = 0; c < studentsSitting[0].length; c++){
                    if(studentsSitting[r][c] == null && seatingAvailability[r][c]){
                        studentsSitting[r][c] = lstu;
                        break op;
                    }
                }
            }
        }

    }

    /**
     * A student decides to leave early
     * This method deletes an early-leaving student from wherever the students 
     * are at (ie. whatever activity is not empty)
     * 
     * Assume the student's name is unique
     * 
     * @param firstName the student's first name
     * @param lastName the student's last name
     */
    public void deleteLeavingStudent ( String firstName, String lastName ) {
        
        if(studentsSitting != null){
            for(int r = 0; r < studentsSitting.length; r++){
                for(int c = 0; c < studentsSitting[0].length; c++){
                    if(studentsSitting[r][c] != null){
                        String fName = studentsSitting[r][c].getFirstName();
                        String lName = studentsSitting[r][c].getLastName();
                        if(fName.equalsIgnoreCase(firstName) && lName.equalsIgnoreCase(lastName)){
                            studentsSitting[r][c] = null;
                        }
                    }
                }
            }
        }


        if(studentsInLine != null){
            SNode[] stuLine = findStudents();
            for(int i = 0; i < stuLine.length; i++){
                if(stuLine[i].getStudent().getFirstName().equalsIgnoreCase(firstName) && stuLine[i].getStudent().getLastName().equalsIgnoreCase(lastName)){
                    if(i == 0){
                        SNode ptr = studentsInLine.getNext();
                        studentsInLine = ptr;
                        break;
                    }
                    else if(i == stuLine.length - 1){
                        stuLine[i-1].setNext(null);
                        break;
                    }
                    else{
                        stuLine[i-1].setNext(stuLine[i+1]);
                        break;
                    }
                }
            }
        }

        if(musicalChairs != null){
            SNode[] musLine = findMusicalChairs();
            for(int i = 0; i < musLine.length; i++){
                if(musLine[i].getStudent().getFirstName().equalsIgnoreCase(firstName) && musLine[i].getStudent().getLastName().equalsIgnoreCase(lastName)){
                    if(i == 0){
                        SNode ptr = musLine[i].getNext();
                        musicalChairs.setNext(ptr);
                        break;
                    }
                    else if(i == musLine.length - 1){
                        musLine[i-1].setNext(musLine[0]);
                        musicalChairs = musLine[i-1];
                        break;
                    }
                    else{
                        musLine[i-1].setNext(musLine[i+1]);
                        break;
                    }
                }
            }
        }
    }

    /**
     * Used by driver to display students in line
     * DO NOT edit.
     */
    public void printStudentsInLine () {

        //Print studentsInLine
        StdOut.println ( "Students in Line:" );
        if ( studentsInLine == null ) { StdOut.println("EMPTY"); }

        for ( SNode ptr = studentsInLine; ptr != null; ptr = ptr.getNext() ) {
            StdOut.print ( ptr.getStudent().print() );
            if ( ptr.getNext() != null ) { StdOut.print ( " -> " ); }
        }
        StdOut.println();
        StdOut.println();
    }

    /**
     * Prints the seated students; can use this method to debug.
     * DO NOT edit.
     */
    public void printSeatedStudents () {

        StdOut.println("Sitting Students:");

        if ( studentsSitting != null ) {
        
            for ( int i = 0; i < studentsSitting.length; i++ ) {
                for ( int j = 0; j < studentsSitting[i].length; j++ ) {

                    String stringToPrint = "";
                    if ( studentsSitting[i][j] == null ) {

                        if (seatingAvailability[i][j] == false) {stringToPrint = "X";}
                        else { stringToPrint = "EMPTY"; }

                    } else { stringToPrint = studentsSitting[i][j].print();}

                    StdOut.print ( stringToPrint );
                    
                    for ( int o = 0; o < (10 - stringToPrint.length()); o++ ) {
                        StdOut.print (" ");
                    }
                }
                StdOut.println();
            }
        } else {
            StdOut.println("EMPTY");
        }
        StdOut.println();
    }

    /**
     * Prints the musical chairs; can use this method to debug.
     * DO NOT edit.
     */
    public void printMusicalChairs () {
        StdOut.println ( "Students in Musical Chairs:" );

        if ( musicalChairs == null ) {
            StdOut.println("EMPTY");
            StdOut.println();
            return;
        }
        SNode ptr;
        for ( ptr = musicalChairs.getNext(); ptr != musicalChairs; ptr = ptr.getNext() ) {
            StdOut.print(ptr.getStudent().print() + " -> ");
        }
        if ( ptr == musicalChairs) {
            StdOut.print(musicalChairs.getStudent().print() + " - POINTS TO FRONT");
        }
        StdOut.println();
    }

    /**
     * Prints the state of the classroom; can use this method to debug.
     * DO NOT edit.
     */
    public void printClassroom() {
        printStudentsInLine();
        printSeatedStudents();
        printMusicalChairs();
    }

    /**
     * Used to get and set objects.
     * DO NOT edit.
     */

    public SNode getStudentsInLine() { return studentsInLine; }
    public void setStudentsInLine(SNode l) { studentsInLine = l; }

    public SNode getMusicalChairs() { return musicalChairs; }
    public void setMusicalChairs(SNode m) { musicalChairs = m; }

    public boolean[][] getSeatingAvailability() { return seatingAvailability; }
    public void setSeatingAvailability(boolean[][] a) { seatingAvailability = a; }

    public Student[][] getStudentsSitting() { return studentsSitting; }
    public void setStudentsSitting(Student[][] s) { studentsSitting = s; }

}

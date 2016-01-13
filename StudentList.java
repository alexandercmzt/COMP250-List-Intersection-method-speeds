	// COMP 250 - ASSIGNMENT #1

	// NAME: Alexander Chatron-Michaud
	// STUDENT ID: 260611509


import java.io.*;    
import java.util.*;

class StudentList 
{
	int studentID[];
	int numberOfStudents;
	String courseName;


	public StudentList(int[] a)
	{
		//created a contructor for the combined array in Q4. sort() can only be used on a StudentList, not an array.
		this.studentID = a;
		this.numberOfStudents = a.length;
		this.courseName = "combined";
	} 

	    // A constructor that reads a StudentList from the given fileName and assigns it the given courseName
	public StudentList(String fileName, String course) 
	{
		String line;
		int tempID[]=new int[4000000]; // this will only work if the number of students is less than 4000000.
		numberOfStudents=0;
		courseName=course;
		BufferedReader myFile;
		try 
		{
			myFile = new BufferedReader(new FileReader( fileName ) );	

			while ( (line=myFile.readLine())!=null ) 
			{
				tempID[numberOfStudents]=Integer.parseInt(line);
				numberOfStudents++;
			}
			studentID=new int[numberOfStudents];
			for (int i=0;i<numberOfStudents;i++) 
			{
				studentID[i]=tempID[i];
			}
		} catch (Exception e) {System.out.println("Can't find file "+fileName);}
		
	    } //__2015__

	    // This method produces a String containing the information about a StudentList.
	    public String toString() 
	    {
	    	return("Course name: " + courseName + "\n" + "Number of students: " + numberOfStudents + "Student IDs:" + Arrays.toString(studentID));
	    }


	    //A copy constructor that copies the content original StudentList 
	    // ranging from minStudentIndex to maxStudentIndex inclusively.
	    // Used for sort();
	    public StudentList(StudentList original, int minStudentIndex, int maxStudentIndex) 
	    {
	    	numberOfStudents=maxStudentIndex-minStudentIndex+1;
	    	courseName=original.courseName;
	    	studentID=new int[original.numberOfStudents];
	    	for (int i=minStudentIndex;i<=maxStudentIndex;i++) 
	    	{
	    		studentID[i-minStudentIndex]=original.studentID[i];
	    	}
	    }
	    

	    // A constructor that generates a random student list of the given size and assigns it the given courseName
	    public StudentList(int size, String course) 
	    {
	    	int IDrange=2*size;
	    	studentID=new int[size];
	    	boolean[] usedID=new boolean[IDrange];
	    	for (int i=0;i<IDrange;i++) usedID[i]=false;
	    		for (int i=0;i<size;i++) 
	    		{
	    			int t;
	    			do 
	    			{
	    				t=(int)(Math.random()*IDrange);
	    			} while (usedID[t]);
	    			usedID[t]=true;
	    			studentID[i]=t;
	    		}
	    		courseName=course;
	    		numberOfStudents=size;
	    	}


	    // Sorts a student list using the MergeSort algorithm
	    	public void sort() 
	    	{
	    		if (numberOfStudents<=1) return;
		StudentList left=new StudentList(this, 0, numberOfStudents/2-1); // the left half of the list
		StudentList right=new StudentList(this, numberOfStudents/2, numberOfStudents-1); // the right half of the list
		left.sort(); // recursively sort the left and right halves
		right.sort();

		// now merge the two sorted halves
		int tmpIndex=0;
		int indexLeft=0;
		int indexRight=0;
		while (tmpIndex<numberOfStudents) 
		{
			if (indexRight>=right.numberOfStudents || 
				(indexLeft<left.numberOfStudents && left.studentID[indexLeft]<=right.studentID[indexRight])) {
				studentID[tmpIndex]=left.studentID[indexLeft];
			indexLeft++;
		}
		else 
		{
			studentID[tmpIndex]=right.studentID[indexRight];
			indexRight++;
		}
		tmpIndex++;
	}
}




	    // Returns the number of students present in both lists L1 and L2
public static int intersectionSizeNestedLoops(StudentList L1, StudentList L2) 
{
	int match = 0;
	for (int i=0; i<L1.numberOfStudents;i++) 
	{
		for (int j=0; j<L2.numberOfStudents;j++) 
		{
			if (L1.studentID[i] == L2.studentID[j])
			{
				match++;
			}
		}
	}
	return match;
}


	    // This algorithm takes as input a sorted array of integers called mySortedArray, the number of elements it contains, and the student ID number to look for
	    // It returns true if the array contains an element equal to ID, and false otherwise.
public static boolean myBinarySearch(int mySortedArray[], int numberOfStudents, int ID) 
{
	int left = 0;
	int right = numberOfStudents;
	int mid = 0;
	while (right > left+1)
	{
		mid = ((left+right)/2);
		if(mySortedArray[mid]>ID)
		{
			right = mid;
		}
		else
		{
			left = mid;
		}
	}
	if(mySortedArray[left] == ID)
	{
		return true;
	}
	else
	{
		return false;
	}
}


public static int intersectionSizeBinarySearch(StudentList L1, StudentList L2) 
{
	int match = 0;
	L2.sort();
	for (int i=0; i<L1.numberOfStudents-1;i++) 
	{
		if(myBinarySearch(L2.studentID, L2.numberOfStudents,L1.studentID[i]))
		{
			match++;
		}
	}
	return match;
}


public static int intersectionSizeSortAndParallelPointers(StudentList L1, StudentList L2)
{
	int match = 0;
	L1.sort();
	L2.sort();
	int ptrA = 0;
	int ptrB = 0;
	while((ptrA < L1.numberOfStudents) && ptrB < L2.numberOfStudents)
	{
		if (L1.studentID[ptrA] == L2.studentID[ptrB])
		{
			match++;
			ptrA++;
			ptrB++;
		}
		else if(L1.studentID[ptrA] < L2.studentID[ptrB])
		{
			ptrA++;
		}
		else
		{
			ptrB++;
		}
	}
	return match;

}


public static int intersectionSizeMergeAndSort(StudentList L1, StudentList L2) 
{
	int match = 0;
	int[] combined = new int[L1.numberOfStudents + L2.numberOfStudents];
	StudentList C = new StudentList(combined);
	for (int i=0; i<L1.numberOfStudents-1; i++) 
	{
		C.studentID[i]=L1.studentID[i];
	}
	for (int i=0; i<L2.numberOfStudents-1; i++) 
	{
		C.studentID[i+L1.numberOfStudents]=L2.studentID[i];
	}
	C.sort();
	int ptr = 0;
	while (ptr < L1.numberOfStudents+L2.numberOfStudents-1)
	{
		if (C.studentID[ptr] == C.studentID[ptr+1]) 
		{
			match++;
			ptr = ptr + 2;
		}
		else
		{
			ptr++;
		}
	}
	return match;
}



/* The main method */
/* Write code here to test your methods, and evaluate the running time of each.*/
/* This method will not be marked */

//i changed this method's parameters so I could automate the process to get all the data points at 
public static void main(int iterations, int studentsnumber) throws Exception
{

	StudentList firstList;
	StudentList secondList;

		// This is how to read lists from files. Useful for debugging.

		//	firstList=new StudentList("COMP250.txt", "COMP250 - Introduction to Computer Science");
		//	secondList=new StudentList("MATH240.txt", "MATH240 - Discrete Mathematics");

		// get the time before starting the intersections
	long startTime = System.nanoTime();

		// repeat the process a certain number of times, to make more accurate average measurements.
	int numberRepetitions=iterations;
	for (int rep=0;rep<numberRepetitions;rep++) 
	{

		    // This is how to generate lists of random IDs. 
		    // For firstList, we generate 16 IDs
		    // For secondList, we generate 16 IDs

		firstList=new StudentList(studentsnumber , "COMP250 - Introduction to Computer Science"); 
		secondList=new StudentList(studentsnumber , "MATH240 - Discrete Mathematics"); 

		    // print the two lists, for future debugging purposes
		//System.out.println(firstList);
		//System.out.println(secondList);

		    // run the intersection method
		//int intersection=StudentList.intersectionSizeNestedLoops(firstList,secondList);
		int intersection=StudentList.intersectionSizeBinarySearch(firstList,secondList);
		//int intersection=StudentList.intersectionSizeSortAndParallelPointers(firstList,secondList);
		//int intersection=StudentList.intersectionSizeMergeAndSort(firstList,secondList);
		//System.out.println("The intersection size is: "+intersection);
	}

		// get the time after the intersection
	long endTime = System.nanoTime();


	//System.out.println("Running time: "+ (float)(endTime-startTime)/numberRepetitions + " nanoseconds");
	System.out.println("Running time: "+ (float)(endTime-startTime)/(numberRepetitions*1000000) + " milliseconds");
}	
}



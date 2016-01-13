public class Generator
{
	public static void main(String[] args) throws Exception
	{
		int iterations = 1024;
		int studentsnumber = 1000;
		for (int i=0;i<11;i++) 
		{
			System.out.println(studentsnumber+" | "+iterations);
			StudentList.main(iterations, studentsnumber);
			iterations = iterations/2;
			studentsnumber = studentsnumber*2;
		}
		System.out.println("DONE.");
	}
}
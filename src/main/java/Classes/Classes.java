

abstract class User 
{
	String id;

	public abstract void displayOptions(); 
}

public class Student extends User
{
	public void displayOptions()
	{
		System.out.println("1. Display most recent evaluation");
		System.out.println("2. Display all evaluations");
	}
}

public class Teacher extends User
{
	public void displayOptions()
	{
		System.out.println("1. Display class evaluations");
		System.out.println("2. Display single student evaluation");
	}
}

public class Admin extends User
{
	public void displayOptions()
	{
		System.out.println("1. Display class evaluations");
		System.out.println("2. Display single student evaluation");
		System.out.println("3. Display school evaluation stats");
	}
}
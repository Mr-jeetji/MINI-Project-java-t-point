package com.miniproject;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class TestQuizCopy {
	
	
	static void test_on_java() {
		
		Scanner scanner = new Scanner(System.in);
		System.out.println("Enter Student ID");
		int id = scanner.nextInt();
		System.out.println("Enter First Name");
		String FirstName = scanner.next();
		System.out.println("Enter Last Name ");
		String LastName = scanner.next();
		System.out.println();
		
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/quiz", "root", "root");
			PreparedStatement ps1 = con.prepareStatement(
					"insert into student(Student_Id,First_Name,Last_Name,Score)" + " values (?,?,?,?)");
			ps1.setInt(1, id);
			ps1.setString(2, FirstName);
			ps1.setString(3, LastName);

			PreparedStatement ps = con.prepareStatement("select* from test_on_java order by rand()");
			ResultSet rs = ps.executeQuery();
			int score = 0;
			int i=1;
			while (rs.next()) {

				System.out.print("Q" + i + ".");
				System.out.println(rs.getString(2) + "\r");
				System.out.println(rs.getString(3));
				System.out.println(rs.getString(4));
				System.out.println(rs.getString(5));
				System.out.println(rs.getString(6));
				System.out.println("\n Enter Option no ");
				Scanner sc = new Scanner(System.in);
				int choice = sc.nextInt();
				if (choice == rs.getInt(7)) {
					score++;
				}
				System.out.println("\n-----------------------------------------------------------------------------------------\n");
				i++;
			}
			ps1.setInt(4, score);
			ps1.execute();
			
			System.out.println("You have got " + score + " out of 10");

			if (score <= 10 && score > 8) {
				System.out.println("\n Congratulations...You passed with grade A");
			} else if (score <= 8 && score >= 6) {
				System.out.println("\n Congratulations...You passed with grade B");
			} else if (score == 5) {
				System.out.println("\n Congratulations...You passed with grade C");
			} else {
				System.out.println("\n Sorry You Have Failed...! got Grade D");
			}

			System.out.println("\nThank You..!\n\n\n");
			con.close();
			ps.close();
			ps1.close();
			
		} catch (Exception e) {
			System.out.println(e);
		} 
		
	}
	static void getStudentList() throws ClassNotFoundException, SQLException {
		
		Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/quiz", "root", "root");
		PreparedStatement ps3 = con.prepareStatement(" select* from student");
		ResultSet rs = ps3.executeQuery();
		System.out.printf("%-10s%-25s%15s\n\n","Id" , "Student_Name"  ,"Score");
		while (rs.next()) {
			System.out.printf("%-10d%-25s%15d\n",rs.getInt(1) , rs.getString(2)+" " + rs.getString(3) , rs.getInt(4));
		}		
		con.close();
		ps3.close();
	}
	static void getScoreById() throws SQLException {
		
		Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/quiz", "root", "root");
		System.out.println("\nEnter Your Id");
		Scanner sc = new Scanner(System.in);
		int id = sc.nextInt();
		PreparedStatement ps3 = con.prepareStatement(" select* from student where Student_Id="+id);
		ResultSet rs = ps3.executeQuery();
		System.out.printf("\n%-10s%-25s%15s\n","Id" , "Student_Name"  ,"Score");
		while(rs.next()) {
			System.out.printf("%-10d%-25s%15d\n",rs.getInt(1) , rs.getString(2)+" " + rs.getString(3) , rs.getInt(4));
		}
		con.close();
	}
	public static void main(String[] args) throws ClassNotFoundException, SQLException  {
		
		test_on_java();
		getStudentList();
		getScoreById();
		
	}
}
import java.sql.Connection;
import java.sql.DriverManager;
import static java.sql.JDBCType.NULL;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Logger;
import java.util.Scanner;


class Teacher
{

    void Create_Quiz()
    {

        try
        {
            Scanner sc=new Scanner(System.in);
            Scanner CIN = new Scanner(System.in);
            Scanner sc_int=new Scanner(System.in);

            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/Quiz_Eze" , "root" , "avpp2468");

            String quiz_subj = null ;
            int quiz_no = 0 ; int done2 = 0 ;


            while(done2 == 0)
            {
                done2 = 1;

                System.out.println("Enter-Quiz-Subject");
                quiz_subj = CIN.nextLine();

                System.out.println("Enter-Quiz-Number");
                quiz_no = sc_int.nextInt();

                Statement st = con.createStatement();
                String query1 = "Select * from av_quiz" ;
                ResultSet rs = st.executeQuery(query1);

                while(rs.next() )
                {
                    int quiz_no2 = rs.getInt("Quiz_No");
                    String sub = rs.getString("Sub_Name");

                    if(quiz_no2 == quiz_no && sub.equals(quiz_subj) )
                    {
                        done2 = 0 ; break;
                    }
                }

                if(done2 > 0)break;

                System.out.println("This quiz already exists please enter a different quiz number");


            }
            // ADD QUIZ NUMBER
            System.out.println("Enter number of questions in the quiz: ");
            int n= sc_int.nextInt(); sc_int.nextLine();

            String query1 = "insert into av_quiz" + "(Sub_Name , Quiz_No ) " + "values(?,?) ";
            PreparedStatement Pst1 = con.prepareStatement(query1);
            Pst1.setString(1,quiz_subj);
            Pst1.setInt(2,quiz_no);
            Pst1.executeUpdate();

            for(int i=0;i<n;i++)
            {
                System.out.println("Question: " + (i+1) );
                String question=sc.nextLine();
                System.out.println("Option 1: ");
                String opt1=sc.nextLine();
                System.out.println("Option 2: ");
                String opt2=sc.nextLine();
                System.out.println("Option 3: ");
                String opt3=sc.nextLine();
                System.out.println("Option 4: ");
                String opt4=sc.nextLine();
                int  ans_opt = 0; // update in dbms
                boolean flg=true;

                while(flg==true)
                {
                    System.out.println("Correct option is: (1/2/3/4) ");
                    ans_opt=sc_int.nextInt();
                    if(ans_opt==1 || ans_opt==2 || ans_opt==3 || ans_opt==4)
                        flg=false;
                    else
                        System.out.println("Enter the correct option");
                }

                String query = "insert into Questions" + "( Subject,Quiz_No ,Question_No, Question ,Answer,A,B,C,D)" + "values(?,?,?,?,?,?,?,?,?)";
                PreparedStatement Pst = con.prepareStatement(query);

                Pst.setString(1,quiz_subj);
                Pst.setInt(2,quiz_no); // confirm this
                Pst.setInt(3,i+1);
                Pst.setString(4,question);
                Pst.setInt(5,ans_opt);
                Pst.setString(6,opt1);
                Pst.setString(7,opt2);
                Pst.setString(8,opt3);
                Pst.setString(9,opt4);

                Pst.executeUpdate();

                System.out.println("");
            }
            System.out.println("Quiz Created Successfully");
            System.out.println("----------------------------------------");
            System.out.println("");


        }

        catch(ClassNotFoundException e)
        {
            System.out.println("NO");
        }
        catch(SQLException ex)
        {
            System.out.println(ex.getMessage() );
        }


    }

    void Review_Quiz()
    {
        try
        {

            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/Quiz_Eze" , "root" , "mysqlpass");

            Scanner CIN_INT = new Scanner(System.in);
            Scanner CIN = new Scanner(System.in);

            System.out.println("Enter-Quiz-Subject");
            String quiz_subj = CIN.nextLine();

            System.out.println("Enter-Quiz-Number");
            int quiz_no = CIN.nextInt();

            Statement st = con.createStatement();
            String query = "Select * from  questions";

            ResultSet rs = st.executeQuery(query);

            System.out.println("----------------------------------------");
            System.out.println("");

            boolean done = false;
            while(rs.next() )
            {
                int qno = rs.getInt("Question_No");
                int quizno = rs.getInt("Quiz_No");
                String ques = rs.getString("Question");
                String A = rs.getString("A");
                String B = rs.getString("B");
                String C = rs.getString("C");
                String D = rs.getString("D");
                int ans = rs.getInt("Answer");


                String sub = rs.getString("Subject");

                if(sub.equals(quiz_subj) && quiz_no == quizno )
                {
                    System.out.println("Question " + qno + " : " + ques);
                    System.out.println( "Option-A  : " + A);
                    System.out.println( "Option-B  : " + B);
                    System.out.println( "Option-C  : " + C);
                    System.out.println( "Option-D  : " + D);
                    System.out.println("Correct option is : " + ans);

                    System.out.println("");
                    System.out.println("");
                    done = true;
                }

            }

            if(!done)
            {
                System.out.println("No_Quiz_Found");
            }

            System.out.println("----------------------------------------");
            System.out.println("");


        }
        catch(ClassNotFoundException e)
        {
            System.out.println("NO");
        }
        catch(SQLException ex)
        {
            System.out.println(ex.getMessage() );
        }

    }

    void Update_Quiz()
    {

        try
        {
            Scanner sc=new Scanner(System.in);
            Scanner CIN = new Scanner(System.in);
            Scanner sc_int=new Scanner(System.in);

            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/Quiz_Eze" , "root" , "avpp2468");

            String quiz_subj = null ;
            int quiz_no = 0 ;

            System.out.println("Enter-Quiz-Subject");
            quiz_subj = CIN.nextLine();

            System.out.println("Enter-Quiz-Number");
            quiz_no = sc_int.nextInt();

            System.out.println("Enter number of questions to add in the quiz: ");
            int n= sc_int.nextInt(); sc_int.nextLine();


            for(int i=0;i<n;i++)
            {
                System.out.println("Question: " + (i+1) );
                String question=sc.nextLine();
                System.out.println("Option 1: ");
                String opt1=sc.nextLine();
                System.out.println("Option 2: ");
                String opt2=sc.nextLine();
                System.out.println("Option 3: ");
                String opt3=sc.nextLine();
                System.out.println("Option 4: ");
                String opt4=sc.nextLine();
                int  ans_opt = 0; // update in dbms
                boolean flg=true;

                while(flg==true)
                {
                    System.out.println("Correct option is: (1/2/3/4) ");
                    ans_opt=sc_int.nextInt();
                    if(ans_opt==1 || ans_opt==2 || ans_opt==3 || ans_opt==4)
                        flg=false;
                    else
                        System.out.println("Enter the correct option");
                }

                String query = "insert into Questions" + "( Subject,Quiz_No ,Question_No, Question ,Answer,A,B,C,D)" + "values(?,?,?,?,?,?,?,?,?)";
                PreparedStatement Pst = con.prepareStatement(query);

                Pst.setString(1,quiz_subj);
                Pst.setInt(2,quiz_no); // confirm this
                Pst.setInt(3,i+1);
                Pst.setString(4,question);
                Pst.setInt(5,ans_opt);
                Pst.setString(6,opt1);
                Pst.setString(7,opt2);
                Pst.setString(8,opt3);
                Pst.setString(9,opt4);

                Pst.executeUpdate();

                System.out.println("");
            }

            System.out.println("----------------------------------------");
            System.out.println("");


        }

        catch(ClassNotFoundException e)
        {
            System.out.println("NO");
        }
        catch(SQLException ex)
        {
            System.out.println(ex.getMessage() );
        }


    }

    void List_Grades()
    {
        try
        {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/Quiz_Eze" , "root" , "avpp2468");

            Scanner CIN_INT = new Scanner(System.in);
            Scanner CIN = new Scanner(System.in);

            System.out.println(" 1: List all grades ");
            System.out.println(" 2: List grades of a particular Quiz");
            System.out.println("");

            int flag = CIN_INT.nextInt();

            Statement st = con.createStatement();
            String query1 = "Select * from marks" ;
            ResultSet rs = st.executeQuery(query1);




            if(flag == 1)
            {
                System.out.println("NAME  SUB  QUIZ_NO  MARKS-OBTAINED ");

                while(rs.next() )
                {
                    int quiz_no = rs.getInt("quiz_No");
                    String sub = rs.getString("Subj");
                    int marks = rs.getInt("marks");
                    String name = rs.getString("Std_name");
                    System.out.println();

                    System.out.println(name + " " + sub + "-" + quiz_no + " Marks-Obtained :  " + marks  );

                }

            }
            else
            {
                System.out.println("Enter Subject Name");
                String subc = CIN.nextLine();

                System.out.println("Enter quiz-no");
                int quizno = CIN_INT.nextInt();

                System.out.println("NAME  SUB  QUIZ_NO  MARKS-OBTAINED ");



                while(rs.next() )
                {
                    int quiz_no = rs.getInt("quiz_No");
                    String sub = rs.getString("Subj");
                    int marks = rs.getInt("marks");
                    String name = rs.getString("Std_name");

                    System.out.println();
                    if(quiz_no == quizno && subc.equals(sub)) System.out.println(name + " " + sub + " " + quiz_no + " Marks Obtained :  " + marks  );

                }

            }

            System.out.println("----------------------------------------");
            System.out.println("");


        }
        catch(ClassNotFoundException e)
        {
            System.out.println("NO");
        }
        catch(SQLException ex)
        {
            System.out.println(ex.getMessage() );
        }

    }



}



class Student
{


    void List_Av_Quizzes()
    {
        try
        {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/Quiz_Eze" , "root" , "avpp2468");

            Scanner CIN_INT = new Scanner(System.in);
            Scanner CIN = new Scanner(System.in);

            System.out.println(" 1: List all quizes ");
            System.out.println(" 2: List quiz of a particular Subject");
            System.out.println("");

            int flag = CIN_INT.nextInt();

            Statement st = con.createStatement();
            String query1 = "Select * from av_quiz" ;
            ResultSet rs = st.executeQuery(query1);

            if(flag == 1)
            {
                while(rs.next() )
                {
                    int quiz_no = rs.getInt("Quiz_No");
                    String sub = rs.getString("Sub_Name");

                    System.out.println(sub + " " + quiz_no );
                }

            }
            else
            {
                System.out.println("Enter Subject Name");
                String subc = CIN.nextLine();
                System.out.println("");

                while(rs.next() )
                {
                    int quiz_no = rs.getInt("Quiz_No");
                    String sub = rs.getString("Sub_Name");

                    if(sub.equals(subc) ) System.out.println(sub + " " + quiz_no );

                }

            }


        }
        catch(ClassNotFoundException e)
        {
            System.out.println("NO");
        }
        catch(SQLException ex)
        {
            System.out.println(ex.getMessage() );
        }

    }

    void Attempt_Quiz()
    {
        try
        {

            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/Quiz_Eze" , "root" , "avpp2468");

            Scanner CIN_INT = new Scanner(System.in);
            Scanner CIN = new Scanner(System.in);

            System.out.println("Enter-Quiz-Subject");
            String quiz_subj = CIN.nextLine();

            System.out.println("Enter-Quiz-Number");
            int quiz_no = CIN.nextInt();

            Statement st = con.createStatement();
            String query = "Select * from  questions";

            ResultSet rs = st.executeQuery(query);

            boolean done = false;

            int marks = 0 ;

            System.out.println("----------------------------------------");
            System.out.println("");

            while(rs.next() )
            {
                int qno = rs.getInt("Question_No");
                int quizno = rs.getInt("Quiz_No");
                String ques = rs.getString("Question");
                String A = rs.getString("A");
                String B = rs.getString("B");
                String C = rs.getString("C");
                String D = rs.getString("D");
                int ans = rs.getInt("Answer");

                String sub = rs.getString("Subject");

                if(sub.equals(quiz_subj) && quiz_no == quizno )
                {
                    System.out.println("Question " + qno + " : " + ques);
                    System.out.println( "Option-1  : " + A);
                    System.out.println( "Option-2  : " + B);
                    System.out.println( "Option-3  : " + C);
                    System.out.println( "Option-4  : " + D);

                    System.out.println("Enter your answer");

                    int ANS = CIN_INT.nextInt();
                    if(ANS == ans)marks++;

                    System.out.println("");
                    System.out.println("");
                    done = true;
                }
            }

            if(!done)
            {
                System.out.println("No_Quiz_Found");
            }
            else
            {
                System.out.println("Please Confirm Your User-Name ");
                System.out.println(" (Note : Enter the User-Name you used while loging in else your marks may not be updated)  ");
                Scanner CIN2 = new Scanner(System.in);
                String F_name = CIN2.nextLine();
                String query2 = "Insert into marks" + "(Std_Name , Subj , quiz_no , marks ) " + "VALUES(?,?,?,?) ";
                PreparedStatement Pst1 = con.prepareStatement(query2);
                Pst1.setString(1,F_name);
                Pst1.setString(2,quiz_subj);
                Pst1.setInt(3,quiz_no);
                Pst1.setInt(4,marks);

                Pst1.executeUpdate();

                System.out.println("Quiz attempted successfully");
                System.out.println("Marks obtained are " + marks);


            }

            System.out.println("----------------------------------------");
            System.out.println("");


        }
        catch(ClassNotFoundException e)
        {
            System.out.println("NO");
        }
        catch(SQLException ex)
        {
            System.out.println(ex.getMessage() );
        }

    }


    void List_Grades()
    {
        try
        {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/Quiz_Eze" , "root" , "avpp2468");

            Scanner CIN_INT = new Scanner(System.in);
            Scanner CIN = new Scanner(System.in);

            System.out.println(" 1: List all grades ");
            System.out.println(" 2: List grades of a particular Quiz");
            System.out.println("");

            int flag = CIN_INT.nextInt();

            Statement st = con.createStatement();
            String query1 = "Select * from marks" ;
            ResultSet rs = st.executeQuery(query1);

            System.out.println("Please enter your User-name");
            String User_Name = CIN.nextLine();

            System.out.println("");


            if(flag == 1)
            {
                while(rs.next() )
                {
                    int quiz_no = rs.getInt("quiz_No");
                    String sub = rs.getString("Subj");
                    int marks = rs.getInt("marks");
                    String name = rs.getString("Std_name");


                    if(name.equals(User_Name) )
                    {
                        System.out.println(sub + " " + quiz_no + " Marks-Obtained :  " + marks  );
                        System.out.println();
                    }

                }

            }
            else
            {
                System.out.println("Enter Subject Name");
                String subc = CIN.nextLine();

                System.out.println("Enter quiz-no");
                int quizno = CIN_INT.nextInt();

                System.out.println("");

                while(rs.next() )
                {
                    int quiz_no = rs.getInt("quiz_No");
                    String sub = rs.getString("Subj");
                    int marks = rs.getInt("marks");
                    String name = rs.getString("Std_name");


                    if(name.equals(User_Name) && quiz_no == quizno && subc.equals(sub)) System.out.println(sub + " " + quiz_no + " Marks Obtained :  " + marks  );

                }



            }

            System.out.println("----------------------------------------");
            System.out.println("");


        }
        catch(ClassNotFoundException e)
        {
            System.out.println("NO");
        }
        catch(SQLException ex)
        {
            System.out.println(ex.getMessage() );
        }

    }

}

class Log_In
{
    int main()
    {
        try
        {
            Scanner CIN = new Scanner(System.in);
            Scanner s = new Scanner(System.in);

            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/Quiz_Eze" , "root" , "avpp2468");


            int done = 0 ;

            System.out.println("Press 1 For Student");
            System.out.println("Press 2 For Teacher");

            System.out.println("----------------------------------------");
            System.out.println("");


            int flag = CIN.nextInt();

            while( done == 0  )
            {
                System.out.println("Please Enter correct username and password");


                System.out.print("Enter username:");
                String User_Name = s.nextLine();
                System.out.print("Enter password:");
                String Password = s.nextLine();

                System.out.println("");


                Statement st = con.createStatement();
                String query = "SELECT * FROM user";
                ResultSet rs = st.executeQuery(query);

                while(rs.next() )
                {
                    String u_name = rs.getString("User_Name");
                    String u_pass = rs.getString("User_Pass");
                    int f = rs.getInt("Flag");



                    if(u_name.equals(User_Name) && Password.equals(u_pass)  && f == flag)
                    {
                        done = 1; break;
                    }
                }


                if(done > 0)break;
            }

            System.out.println("Login Succefully");

            return flag;



        }

        catch(ClassNotFoundException e)
        {
            System.out.println("NO");
        }
        catch(SQLException ex)
        {
            System.out.println(ex.getMessage() );
        }

        return 0;
    }


}

class Sign_Up
{
    int main()
    {
        try
        {
            Scanner CIN = new Scanner(System.in);
            Scanner s = new Scanner(System.in);

            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/Quiz_Eze" , "root" , "avpp2468");


            System.out.println("Press 1 For Student");
            System.out.print("Press 2 For Teacher");
            int flag = CIN.nextInt();

            System.out.println("");
            String queryM = "INSERT INTO user" + "( User_Name , User_Pass , Flag) " +  " VALUES (?,?,?) ";
            String queryM2 = "SELECT * FROM user";

            PreparedStatement Pst = con.prepareStatement(queryM);
            Statement st = con.createStatement();

            boolean done = false;

            String Password = null , User_Name = null;

            while( !done )          // TO CHECK IF USERNAME EXISTS
            {
                int c = 0 ;

                System.out.print("Enter username:");
                User_Name = s.nextLine();
                System.out.print("Enter password:");
                Password = s.nextLine();

                ResultSet rs = st.executeQuery(queryM2);

                while(rs.next() )
                {
                    String u_name = rs.getString("User_Name");
                    String u_pass = rs.getString("User_Pass");
                    int f = rs.getInt("Flag");



                    if(u_name.equals(User_Name) )
                    {
                        System.out.println("User-Name already exists. Please Enter a new User-Name"); c++;
                        break;
                    }
                }

                if(c == 0)done = true;


            }

//               System.out.println(User_Name);
            Pst.setString(1,User_Name);
            Pst.setString(2,Password);
            Pst.setInt(3,flag);
//
            Pst.executeUpdate();

            System.out.println("Registerd Successfully");

            return flag;


        }
        catch(ClassNotFoundException e)
        {
            System.out.println("NO");
        }
        catch(SQLException ex)
        {
            System.out.println(ex.getMessage() );
        }
        return 0;
    }


}


public class Quiz_Eze
{

    public static void main(String[] args)
    {

        try
        {

            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/Quiz_Eze" , "root" , "avpp2468");

            System.out.println("Welcome To Quiz-Eze. This is one place solution for creating and solving online quizzes for Teachers and Students");
            System.out.println("Press 1 for Login ");
            System.out.println("Press 2 for Sign-up");

            System.out.println("----------------------------------------");
            System.out.println("");

            Scanner CIN = new Scanner(System.in);
            Scanner s = new Scanner(System.in);

            int basic , flag = 0;
            String User_Name = null , Password = null;
            basic = CIN.nextInt();

            if(basic == 1)
            {
                Log_In log1 = new Log_In();
                flag = log1.main();
            }
            else
            {
                Sign_Up sig1 = new Sign_Up();
                flag = sig1.main();
            }



            if(flag == 1)
            {
                System.out.println("Student");

                System.out.println("----------------------------------------");
                System.out.println("");

                Student s1 = new Student();
                int fun = 0 ;

                while(fun != 4)
                {

                    System.out.println("Choose the option");

                    System.out.println("1 : View the available quizzes");
                    System.out.println("2 : Attempt a Quiz");
                    System.out.println("3 : Show Grades");
                    System.out.println("4 : Log-Out");

                    fun = CIN.nextInt();

                    if(fun == 1)
                    {
                        s1.List_Av_Quizzes();
                    }
                    else if(fun == 2)
                    {
                        s1.Attempt_Quiz();
                    }
                    else if(fun == 3)
                    {
                        s1.List_Grades();
                    }

                }
            }
            else
            {
                System.out.println("Teacher");
                Teacher t1 = new Teacher();

                System.out.println("----------------------------------------");
                System.out.println("");
                System.out.println("Choose the option");

                int fun = 0 ;

                while(fun != 5)
                {
                    System.out.println("1 : Create a quiz  ");
                    System.out.println("2 : Show Grades of students");
                    System.out.println("3 : Review Quiz");
                    System.out.println("4 : Update Quiz ( Add questions ) ");
                    System.out.println("5 : Log-Out");

                    fun = CIN.nextInt();

                    if(fun == 1)
                    {
                        t1.Create_Quiz();
                    }
                    else if(fun == 2)
                    {
                        t1.List_Grades();
                    }
                    else if(fun == 3)
                    {
                        t1.Review_Quiz();
                    }
                    else if(fun == 4)
                    {
                        t1.Update_Quiz();
                    }

                }

            }

            System.out.println("Thank-You for using Quiz-Eze");

        }
        catch(ClassNotFoundException e)
        {
            System.out.println("Error-Connectiong to Quiz-Eze");
            System.out.println(e.getMessage() );

        }
        catch(SQLException ex)
        {
            System.out.println("Error-Connecting to Quiz-Eze");
            System.out.println(ex.getMessage() );
        }



    }



}



import javax.servlet.*;
import javax.servlet.http.*;
import java.io.*;
import java.sql.*;
import java.util.*;
import java.text.SimpleDateFormat;
import java.util.Date;
public class VerifyTransfer extends HttpServlet
{
	public void service(HttpServletRequest req , HttpServletResponse res)throws IOException , ServletException
	{
		res.setContentType("text/html");
		PrintWriter out=res.getWriter();
		HttpSession session1=req.getSession();
		String otp=(String)session1.getAttribute("otp");
		String accountno=(String)session1.getAttribute("accountnumber");
		String verifyotp=req.getParameter("txtname");
		String money=(String)session1.getAttribute("sumtransfer");
		long credit=Long.parseLong(money);
		String name=(String)session1.getAttribute("name");
		SimpleDateFormat dtf=new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
		Date now=new Date();
		String date=dtf.format(now);
		
		    try{
			Class.forName("oracle.jdbc.driver.OracleDriver");
			Connection con=DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:XE","sagar","verma");
			Statement s1=con.createStatement();
		    ResultSet rs=s1.executeQuery("select balance from accountdata where accno='"+accountno+"'");
			while(rs.next()){
				
			      long amount=Long.parseLong(rs.getString(1));
				 
 			      long newbalance=amount+credit;
				  if(otp.equals(verifyotp)){
			      Statement s=con.createStatement();
			      s.executeUpdate("update  accountdata set balance="+newbalance+" where accno='"+accountno+"'");
				  PreparedStatement ps=con.prepareStatement("insert into credit values(?,?,?)");
			ps.setString(1,name);
			ps.setString(2,money);
			ps.setString(3,date);
			ps.executeUpdate();
				  out.println("<center> new balance is "+newbalance+" </center>" );
				  
				  RequestDispatcher disp=req.getRequestDispatcher("AfterLogin.html");
				  disp.include(req,res);
				  }
				  else{
		          out.println("invalid otp");
				  RequestDispatcher disp=req.getRequestDispatcher("verifycredit");
				  disp.include(req,res);
	              }
			}
		}
			
			
			
			
			
		
		catch(Exception e){System.out.println(e);}
	}
	
	
}
import javax.servlet.*;
import javax.servlet.http.*;
import java.io.*;
import java.sql.*;
import java.util.*;
public class Verify extends HttpServlet
{
	public void doGet(HttpServletRequest req , HttpServletResponse res)throws IOException , ServletException
	{
		res.setContentType("text/html");
		PrintWriter out=res.getWriter();
		HttpSession session1=req.getSession();
		String otp=(String)session1.getAttribute("otp");
		String accountno=(String)session1.getAttribute("accountnumber");
		String money=(String)session1.getAttribute("sumtransfer");
		
		
		
		out.println("<html><body bgcolor='skyblue'><body>");
		out.println("<center><h1>VERIFY ACCOUNT</h1></center>");
        out.println("<form action='ReceiverServlet'>");
        out.println("<center>Enter OTP:<input type='number' name='txtname'></center>");
        out.println("<br><br><center><input type='submit' value='Verify'></center>");
        out.println("</form></body></html>");
		
		
		
	
	
		
	}
}
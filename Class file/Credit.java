import javax.servlet.*;
import javax.servlet.http.*;
import java.io.*;
import java.sql.*;
import java.util.*;
import java.util.Random;
import javax.mail.*;
import javax.mail.internet.*;
import javax.activation.*;

public class Credit extends HttpServlet
{
	public void doGet(HttpServletRequest req , HttpServletResponse res)throws IOException , ServletException
	{
		res.setContentType("text/html");
		PrintWriter out=res.getWriter();
		String accountno=req.getParameter("accno");
		String money=req.getParameter("amount");
		String to="";
		HttpSession session1=req.getSession();
		
		
		
		
		try{
			Class.forName("oracle.jdbc.driver.OracleDriver");
		    Connection con=DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:XE","sagar","verma");
			Statement s=con.createStatement();
			ResultSet rs=s.executeQuery("select email from accountdata where accno='"+accountno+"'");
			while(rs.next()){
				to=rs.getString(1);
			
			}
			
			String s11="1234567890";
		    Random random=new Random();
		    char[] otp=new char[6];
		    for(int i=0;i<6;i++){
			otp[i]=s11.charAt(random.nextInt(s11.length()));
			}
			
			String strArray[]=new String[otp.length];
			for(int i=0;i<otp.length;i++){
				strArray[i]=String.valueOf(otp[i]);
			}
			String s1=Arrays.toString(strArray);
			
			String res1="";
			for(String num:strArray){
				res1+=num;
			}
			final String username="mail.sdfgv8792@gmail.com";
			final String password="26262626sdfg";
            Properties properties = new Properties();
            properties.put("mail.smtp.host","smtp.gmail.com");
			properties.put("mail.smtp.auth","true");
			properties.put("mail.smtp.port","587");
			properties.put("mail.smtp.starttls.enable","true");
			
			Session session = Session.getInstance(properties,new javax.mail.Authenticator(){ protected PasswordAuthentication getPasswordAuthentication(){return new PasswordAuthentication(username,password);}});
			try {
                // Create a default MimeMessage object.
                MimeMessage message = new MimeMessage(session);

                 // Set From: header field of the header.
                 message.setFrom(new InternetAddress(username));

                 // Set To: header field of the header.
                 message.setRecipient(Message.RecipientType.TO, new InternetAddress(username));

                  // Set Subject: header field
                  message.setSubject("Sbi otp");

                  // Now set the actual message
                 message.setText("your one time password is:" +res1);

                 // Send message
				 
                 Transport.send(message);
			}
                 
            catch (MessagingException mex) {
             mex.printStackTrace();
			}
			session1.setAttribute("accountnumber",accountno);
			session1.setAttribute("sumtransfer",money);
			session1.setAttribute("otp",res1);
			
		    out.println("<center> otp successfully sent to your registered mail id</center><br><br>");
			RequestDispatcher disp=req.getRequestDispatcher("verifycredit");
			disp.include(req,res);
		}
		catch(Exception e1){
			out.println(e1);
		}
	}
}

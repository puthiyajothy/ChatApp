import java.net.URL;
import java.io.*;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.net.ssl.HttpsURLConnection;

public class Client {

//	  private static PrintWriter printwriter = null;
//	  private static BufferedReader bufferereader = null;
	  private static String ip = "localhost", name = "jothi", port = "2222";


	  //Exception in thread "main" javax.net.ssl.SSLHandshakeException: java.security.cert.CertificateException: No name matching localhost found
	  
	  static {
		  
	    //localhost testing only to pass the security
		  
		  
		  
	    javax.net.ssl.HttpsURLConnection.setDefaultHostnameVerifier(
	            new javax.net.ssl.HostnameVerifier() {

	              public boolean verify(String hostname,
	                                    javax.net.ssl.SSLSession sslSession) {
	                if (hostname.equals("127.0.0.1")) {
	                  return true;
	                }
	                return false;
	              }
	            });
	  }


	  public static void main(String[] args) throws Exception {

	    Scanner scanner = new Scanner(System.in);

	    System.out.println("connect ip:port as <Yourname> (To register)");
	    String command = scanner.nextLine();
	    Pattern regex1 = Pattern.compile("^connect " +
	            "(?<ip>((25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\.){3}(25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?))" +
	            ":(?<port>\\d{4})" +
	            " as " +
	            "(?<name>\\w*)$");
	    Matcher matches = regex1.matcher(command);

	    if (matches.find()) {
	    	
	      ip = matches.group("ip");
	      name = matches.group("name");
	      port = matches.group("port");

	     


	      System.setProperty("javax.net.ssl.trustStore", "src/client.jks");
	      System.setProperty("javax.net.ssl.trustStorePassword", "123456");
	      


	      String httpsURL = "https://" + ip + ":" + port + "/register?name=" + name;
	      
	      URL myUrl = new URL(httpsURL);
	      HttpsURLConnection conn = (HttpsURLConnection) myUrl.openConnection();

	      
	      InputStream is = conn.getInputStream();
	      InputStreamReader isr = new InputStreamReader(is);
	      BufferedReader br = new BufferedReader(isr);
	      String inputLine;
	      while ((inputLine = br.readLine()) != null) {
	        System.out.println(inputLine);
	      }
	      br.close();

	
	      new Thread(() -> {
	        Scanner send = new Scanner(System.in);
	        System.out.println("**send <Your message>-><Sender Name> (To send message)**");
	        while (true) {
	          System.out.print(":");
	          String sendToServer = send.nextLine();

	         
	          if (sendToServer.equals("list")) {
	            getList();
	          }
	          
	          if (Pattern.matches("send (?<msg>.*)->(?<name>.*)", sendToServer)) {
	            sendToServer(sendToServer);
	          }
	          
	       
	        }
	      }).start();


	      new Thread(() -> {
	        while (true) {

	          try {
	            Thread.sleep(4000);
	            String httpsURL1 = "https://127.0.0.1:2222/inbox?name=" + name;
	            URL myUrl1 = new URL(httpsURL1);
	            HttpsURLConnection conn1 = (HttpsURLConnection) myUrl1.openConnection();

	            
	            InputStream is1 = conn1.getInputStream();
	            InputStreamReader isr1 = new InputStreamReader(is1);
	            BufferedReader br1 = new BufferedReader(isr1);
	            String inputLine1;
	            while ((inputLine1 = br1.readLine()) != null) {
	              if (!inputLine1.equals("no"))
	                System.out.println(inputLine1.substring(2,inputLine1.length()));
	            }
	            br1.close();
	          } catch (Exception e) {
	            e.printStackTrace();
	          }
	        }
	      }).start();

	    } else {
	      System.out.println("Invalid command !");
	    }
	  }

	  private static void sendToServer(String sendToServer) {
	    try {
	     

	      Matcher matcher2 = Pattern.compile("send (?<msg>.*)->(?<name>.*)").matcher(sendToServer);
	      if (matcher2.find()) {
	       
	        String message = matcher2.group("msg");
	        String receiver = matcher2.group("name");
	        String sender = name;
	       

	        String httpsURL1 = "https://127.0.0.1:2222/send?message=" + message.trim()
	                + "&receiver=" + receiver.trim()
	                + "&sender=" + sender.trim();
	        URL myUrl1 = new URL(httpsURL1);
	        HttpsURLConnection conn1 = (HttpsURLConnection) myUrl1.openConnection();

	        
	        InputStream is1 = conn1.getInputStream();
	        InputStreamReader isr1 = new InputStreamReader(is1);
	        BufferedReader br1 = new BufferedReader(isr1);
	        String inputLine1;
	        while ((inputLine1 = br1.readLine()) != null) {
	          System.out.println(inputLine1);
	        }
	        br1.close();
	      }

	    } catch (Exception e) {
	      e.printStackTrace();
	    }
	  }

	  private static void getList() {
	    try {
	      System.out.println("--List of connected users--");
	      String httpsURL1 = "https://127.0.0.1:2222/list?name=" + name;
	      URL myUrl1 = new URL(httpsURL1);
	      HttpsURLConnection conn1 = (HttpsURLConnection) myUrl1.openConnection();

	      
	      InputStream is1 = conn1.getInputStream();
	      InputStreamReader isr1 = new InputStreamReader(is1);
	      BufferedReader br1 = new BufferedReader(isr1);
	      String inputLine1;
	      while ((inputLine1 = br1.readLine()) != null) {
	        System.out.println(inputLine1);
	      }
	      br1.close();
	    } catch (Exception e) {
	      e.printStackTrace();
	    }
	  }

	}

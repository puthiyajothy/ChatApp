import java.io.IOException;
import java.io.OutputStream;
import java.net.URI;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

public class Listusers implements HttpHandler {
	
	@SuppressWarnings("rawtypes")
	private static HashMap<String, ArrayList> userList = new HashMap<>();
	
	@Override
	public void handle(HttpExchange exchange) throws IOException {
		  
	      Map<String, Object> map = new HashMap<String, Object>();
	      
		  URI requestedUri = exchange.getRequestURI();
	      String query = requestedUri.getRawQuery();

	      String clientName = query.substring(query.indexOf("=") + 1, query.length());
	      System.out.println(clientName);

	     
	      String response = "";

	    
		for (String name : userList.keySet()) {
	        if (clientName.equals(name)) {
	          response += clientName + "(You)\n";
	        } else {
	          response += name + "\n";
	        }
	      }

	      System.out.println(userList + "Sent");
	      
	      exchange.sendResponseHeaders(200, response.length());
	      OutputStream os = exchange.getResponseBody();
	      os.write(response.toString().getBytes());

	      os.close();
	    }
	

}


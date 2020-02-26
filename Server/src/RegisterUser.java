import java.io.IOException;
import java.io.OutputStream;
import java.net.URI;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

public class RegisterUser implements HttpHandler{
	
	 private static HashMap<String, ArrayList> userList = new HashMap<>();


	@Override
	public void handle(HttpExchange exchange) throws IOException {
		  Map<String, Object> parameters = new HashMap<String, Object>();
	      URI requestedUri = exchange.getRequestURI();
	      String query = requestedUri.getRawQuery();


	      String clientName = query.substring(query.indexOf("=") + 1, query.length());
	      

	      String response = "You are registered as "+ clientName + " with 127.0.0.1 ip and port number 2222" ;

	      
	      if (userList.keySet().stream().anyMatch(clientName::equals)) {
	        System.out.println("you are already in!");
	        response = "you are already in!";
	      } else {
	        userList.put(clientName, new ArrayList());
	      }

	      System.out.println(response);
	      // send response
	      exchange.sendResponseHeaders(200, response.length());
	     OutputStream os = exchange.getResponseBody();
	      os.write(response.toString().getBytes());

	      os.close();
	    }
	  

		
	}


import java.io.IOException;
import java.io.OutputStream;
import java.net.URI;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

public class Message implements HttpHandler  {

	private static HashMap<String, ArrayList> userList = new HashMap<>();
	

	@Override
	public void handle(HttpExchange exchange) throws IOException{
		 try {
//			    HttpExchange he = null;
		        Map<String, Object> parameters = new HashMap<String, Object>();
		        URI requestedUri = exchange.getRequestURI();
		        String query = requestedUri.getRawQuery();

		        String clientName = query.substring(query.indexOf("=") + 1, query.length());
		        

		        
		        String response = "no";
		        if ( userList.get(clientName).size() >1) {
		          for (String name : userList.keySet()) {
		            if (clientName.equals(name)) {
		              response += userList.get(name).get(1);
		              userList.get(name).set(1, "");
		            }
		          }
		        }

		        //System.out.println(userList + "Sent");
		        // send response
		        exchange.sendResponseHeaders(200, response.length());
		        OutputStream os = exchange.getResponseBody();
		        os.write(response.toString().getBytes());

		        os.close();
		      } catch (Exception e) {
		        e.printStackTrace();
		      }
		    }
		
	
		
    }
  



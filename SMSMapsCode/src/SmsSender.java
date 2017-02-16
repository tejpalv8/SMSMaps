import com.twilio.Twilio;
import com.twilio.base.ResourceSet;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Iterator;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


public class SmsSender {

    // Find yogw    ur Account Sid and Auth Token at twilio.com/console
    public static final String ACCOUNT_SID = "AC48dbe4415aaf462551e6402fd5468085";
    public static final String AUTH_TOKEN = "129628478c5830b5bbd1dabdf4a711d8";
    public static final String PHONENUMBER = "+16507997327"
    		+ "";

    public static void main(String[] args) throws URISyntaxException, IOException, JSONException {
        Twilio.init(ACCOUNT_SID, AUTH_TOKEN);

       Message message = Message
                .creator(new PhoneNumber(PHONENUMBER),  // to
                        new PhoneNumber("+16506009887"),  // from
                       "Welcome to SMS Maps. Type 'start at (input)' and 'go to (input)' to start your navigation!")
                .create();
        
       //Message messageStart = Message.creator(new PhoneNumber("+16507994086"), new PhoneNumber("+16506009887"), "start at ").create();
       //Message messageEnd = Message.creator(new PhoneNumber("+16507994086"), new PhoneNumber("+16506009887"), "go to  ").create(); 
       String StartLoc = "a";
        String EndLoc = "a";
        
       while(StartLoc == "a" || EndLoc == "a"){
        	
        	
        ResourceSet<Message> messages = Message.reader().read();
        Iterator it = messages.iterator();
        Message current = (Message) it.next();
        

        
        if(current.getBody().toString().toLowerCase().startsWith("start at ") || current.getBody().toString().toLowerCase().startsWith("Start at ") || current.getBody().toString().toLowerCase().startsWith("start At ") || current.getBody().toString().toLowerCase().startsWith("Start At ")){
     	   //System.out.println("it worked!");
     	   StartLoc = current.getBody().toString().substring(9).toLowerCase();
        }
        
        if(current.getBody().toString().toLowerCase().startsWith("go to ")|| current.getBody().toString().toLowerCase().startsWith("Go to ") || current.getBody().toString().toLowerCase().startsWith("go To ") || current.getBody().toString().toLowerCase().startsWith("Go To ")){
     	   //System.out.println("it worked!");
     	   EndLoc = current.getBody().toString().substring(6).toLowerCase();
        }
        
        System.out.println(current.getBody().toString());
      
        
        }
        
        
        System.out.println(StartLoc);
        System.out.println(EndLoc);
    	
    	JSONObject json;
		try {
			json = new JSONObject(readUrl(replace("https://maps.googleapis.com/maps/api/directions/json?origin=" + StartLoc + "&destination=" + EndLoc + "l&key=AIzaSyD7M8x1hsi7HWKfOJPZRZFuIRsuQvcIe8M")));
			JSONArray test = (JSONArray) json.getJSONArray("routes").getJSONObject(0).get("legs");
			JSONObject test1 = (JSONObject) test.getJSONObject(0);
			ArrayList<String> directions = new ArrayList<String>();
			JSONArray counter = (JSONArray) test1.getJSONArray("steps");
			
			for(int i = 0; i < counter.length(); i++){
				JSONObject test2 = (JSONObject) test1.getJSONArray("steps").get(i);
				String PREHTMLSTRING = test2.getString("html_instructions");
				String feet = test2.getJSONObject("distance").getString("text");
				String FINALSTRING = PREHTMLSTRING.replaceAll("(?s)<[^>]*>(\\s*<[^>]*>)*", " ");
				System.out.println(FINALSTRING);
				directions.add(FINALSTRING + " Distance: " + feet);
			}
			
			
			
			for(int i = 0; i < directions.size(); i++){
				Message directionMessage = Message
		                .creator(new PhoneNumber(PHONENUMBER),  // to
		                        new PhoneNumber("+16506009887"),  // from
		                       directions.get(i))
		                .create();
			}
			
		} catch (Exception e) {
			
			e.printStackTrace();
			
		}
		
		
		
    	
       
        
    
    
      
    }
    private static String readUrl(String urlString) throws Exception {
        BufferedReader reader = null;
        try {
            URL url = new URL(urlString);
            reader = new BufferedReader(new InputStreamReader(url.openStream()));
            StringBuffer buffer = new StringBuffer();
            int read;
            char[] chars = new char[1024];
            while ((read = reader.read(chars)) != -1)
                buffer.append(chars, 0, read); 

            return buffer.toString();
        } finally {
            if (reader != null)
                reader.close();
        }
        
    }
    


public static String replace(String str) {
    String[] words = str.split(" ");
    StringBuilder sentence = new StringBuilder(words[0]);

    for (int i = 1; i < words.length; ++i) {
        sentence.append("%20");
        sentence.append(words[i]);
    }

    return sentence.toString();
}
    
   
    
    
    
}

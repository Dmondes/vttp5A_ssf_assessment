package vttp.batch5.ssf.noticeboard.services;

import java.io.StringReader;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import jakarta.json.Json;
import jakarta.json.JsonArray;
import jakarta.json.JsonObject;
import jakarta.json.JsonReader;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;

import vttp.batch5.ssf.noticeboard.repositories.NoticeRepository;

@Service
public class NoticeService {
	@Autowired
	private NoticeRepository noRepo;

	RestTemplate restTemplate = new RestTemplate();

	public static final String publishUrl = "https://publishing-production-d35a.up.railway.app";

	// TODO: Task 3
	// You can change the signature of this method by adding any number of
	// parameters
	// and return any type

	public ResponseEntity<String> postToNoticeServer( String form ) { //return response payload
		
		RequestEntity<String> req = RequestEntity
		.post(publishUrl)
		.contentType(MediaType.APPLICATION_JSON)
		.accept(MediaType.APPLICATION_JSON)
		.body("", String.class);

		try{
			ResponseEntity<String> resp = restTemplate.exchange(req, String.class);
			String payload = resp.getBody();
			// System.out.printf(">> %s\n", payload);
			// System.out.println(payload);
			JsonReader reader = Json.createReader(new StringReader(payload));
            JsonArray notices = reader.readArray();
            JsonObject noObj = notices.asJsonObject();
            String keyName = noObj.getString("id");
			saveToRedis(keyName,"responseMap", noObj);
            JsonObject response = Json.createObjectBuilder()
            .add("message","Your message has been posted to the community message board")
            .add("id", "THe notice posting id is "  + keyName)
            .build();
              return ResponseEntity
			  .ok()
			  .body(response.toString());
		
		} catch (Exception ex) {
                    JsonObject error = Json.createObjectBuilder()
							.add("message", "Cannot post notice to the server")
							.add("header", "Error message")
                            .add("error", ex.getMessage())
                            .build();
                    return ResponseEntity
                            .status(HttpStatus.INTERNAL_SERVER_ERROR)
                            .body(error.toString());
                }
	}
	
	public void saveToRedis(String key, String mapkey, Object value){
		noRepo.insertNotices(key, mapkey, value);
	}

}

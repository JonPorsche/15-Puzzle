package puzzle;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class ReadJSON {
	
	public int readResult() {
		
		JSONParser jsonParser = new JSONParser();
		int time = 0;
		
		try(FileReader reader = new FileReader("game_result.json")){
			
			Object obj = jsonParser.parse(reader);
			JSONObject jsonObj = (JSONObject) obj;
			long timeLong = (long) jsonObj.get("time");
			time = (int)timeLong;
			
		} catch (FileNotFoundException e) {
			System.out.printf("ERROR - A %s ocurred:\n\t%s\n", e.getClass().toString(), e.getMessage());
			time = 0;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("Recorded time = " + time);
		return time;
	}
}

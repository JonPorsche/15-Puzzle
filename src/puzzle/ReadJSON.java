package puzzle;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class ReadJSON {
	
	private boolean record;
	
	public boolean hasRecord() {
		return record;
	}

	public int readResult() {
		
		JSONParser jsonParser = new JSONParser();
		int time = 0;
		
		try(FileReader reader = new FileReader("game_result.json")){
			
			Object obj = jsonParser.parse(reader);
			JSONObject jsonObj = (JSONObject) obj;
			long timeLong = (long) jsonObj.get("time");
			System.out.println("File has time records.");
			record = true;
			time = (int)timeLong;
		} catch (FileNotFoundException e) {
			System.out.printf("\nERROR - A %s ocurred:\n\t%s\n", e.getClass().toString(), e.getMessage());
			System.out.println("\n");
			System.out.println("File doesn't has time records");
			record = false;
			time = 0;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return time;
	}
}

package puzzle;

import java.io.FileWriter;
import java.io.IOException;

import org.json.simple.JSONObject;

public class WriteJSON {

	private int time;
	
	public WriteJSON() {
		super();
	}

	public void writeResult(int time) {
		JSONObject gameResult = new JSONObject();
		gameResult.put("time", time);
		writeJSONFile(gameResult);
	}

	private void writeJSONFile(JSONObject gameResult) {
		try(FileWriter file = new FileWriter("game_result.json")){
			file.write(gameResult.toJSONString());
			file.flush();
		}
		catch(IOException e) {
			e.printStackTrace();
		}		
	}
}

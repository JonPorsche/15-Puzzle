package puzzle;

import java.io.FileWriter;
import java.io.IOException;

import org.json.simple.JSONObject;

public class WriteJSON {

	private int time;
	
	public WriteJSON(int time) {
		super();
		this.time = time;
	}

	public void writeResult() {
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

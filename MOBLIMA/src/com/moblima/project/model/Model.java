package com.moblima.project.model;

import org.json.JSONException;
import org.json.JSONObject;

/**The base class for all the models.
 * Holds the id attribute of all the models.
 * @author sunzhihao
 *
 */
public abstract class Model {
	protected int id;
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}
	
	/**Converts the model to JSONObject.
	 * Each model has distinct format of JSONObject
	 * @return JSONObject
	 * @throws JSONException
	 */
	public abstract JSONObject toJSONObject() throws JSONException;
	
	public String toString(){
		try {
			return this.toJSONObject().toString();
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return null;
	}
	
	/**The string format of the model f
	 * @return String
	 */
	public abstract String toDisplay();
}

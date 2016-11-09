package app.base;

import org.json.JSONObject;

public class JsonTool {
    public static String toString(Object object){
        return JSONObject.valueToString(object);
    }
}

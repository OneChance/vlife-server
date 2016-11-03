package app.base;

public class NetMessage {

    public static String DANGER = "danger";
    public static String SUCCESS = "success";

    private String type;
    private String content;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public void setMessage(String type,String content){
        this.type = type;
        this.content = content;
    }
}

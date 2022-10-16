package ru.job4j.pooh;

public class Req {

    private final String httpRequestType;
    private final String poohMode;
    private final String sourceName;
    private final String param;

    public final static String GET = "GET";
    public final static String POST = "POST";

    public Req(String httpRequestType, String poohMode, String sourceName, String param) {
        this.httpRequestType = httpRequestType;
        this.poohMode = poohMode;
        this.sourceName = sourceName;
        this.param = param;
    }

    public static Req of(String content) {
        var method = content.contains(GET) ? GET : POST;
        var contentLines = content.split(System.lineSeparator());
        var url = contentLines[0].split(" ")[1].split("/");
        var lastLine = contentLines[contentLines.length - 1];
        var isLastLineAccept = lastLine.startsWith("Accept");
        var param = (isLastLineAccept && url.length == 4) ? url[3] : (!isLastLineAccept ? lastLine : "");

        return new Req(method, url[1], url[2], param);
    }

    public String httpRequestType() {
        return httpRequestType;
    }

    public String getPoohMode() {
        return poohMode;
    }

    public String getSourceName() {
        return sourceName;
    }

    public String getParam() {
        return param;
    }
}
package wenjing.xdtic.model;

/**
 *
 * @author Michael Chow <mizhoux@gmail.com>
 */
public class RespCode {

    /**
     * {"code" : "ok"}
     */
    public static RespCode OK = new RespCode("ok");

    /**
     * {"code" : "error"}
     */
    public static RespCode ERROR = new RespCode("error");

    public static RespCode okOf(String comment) {
        return new RespCode("ok", comment);
    }

    public static RespCode errorOf(String comment) {
        return new RespCode("error", comment);
    }

    private final String code;
    private final String comment;

    private RespCode(String code) {
        this(code, "");
    }

    private RespCode(String code, String comment) {
        this.code = code;
        this.comment = comment;
    }

    public String getCode() {
        return code;
    }

    public String getComment() {
        return comment;
    }

}

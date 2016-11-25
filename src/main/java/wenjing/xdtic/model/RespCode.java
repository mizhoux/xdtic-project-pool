package wenjing.xdtic.model;

/**
 *
 * @author Michael Chow <mizhoux@gmail.com>
 */
public class RespCode {

    public static RespCode OK = new RespCode("ok");
    
    public static RespCode ERROR = new RespCode("error");

    private final String code;

    public RespCode(String code) {
        this.code = code;
    }

    public String getCode() {
        return code;
    }

}

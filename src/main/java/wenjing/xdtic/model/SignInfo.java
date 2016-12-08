package wenjing.xdtic.model;

/**
 *
 * @author Michael Chow <mizhoux@gmail.com>
 */
public class SignInfo {

    private Integer sid;
    private String username;
    private String career;
    private String date;
    private String time;

    public SignInfo() {
    }

    public Integer getSid() {
        return sid;
    }

    public void setSid(Integer sid) {
        this.sid = sid;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getCareer() {
        return career;
    }

    public void setCareer(String career) {
        this.career = career;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

}

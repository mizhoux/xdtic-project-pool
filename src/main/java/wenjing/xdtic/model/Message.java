package wenjing.xdtic.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import java.util.Date;

/**
 *
 * @author wenjing
 */
public class Message {

    public static enum Type {
        POST, PASS, JOIN
    }

    private Integer id;

    private Integer proId;
    private Integer userId;
    private String content;
    private String type;
    private boolean read;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date date;

    // 兼容前端
    private Integer mid;
    private Integer uid;
    private String massage;

    public static void syncDataForBack(Message message) {
        message.setId(message.getMid());
        message.setUserId(message.getUid());
        message.setContent(message.getMassage());
    }

    public static void syncDataForFront(Message message) {
        message.setMid(message.getId());
        message.setUid(message.getUserId());
        message.setMassage(message.getContent());
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getUid() {
        return uid;
    }

    public void setUid(Integer uid) {
        this.uid = uid;
    }

    public Integer getMid() {
        return mid;
    }

    public void setMid(Integer mid) {
        this.mid = mid;
    }

    public String getMassage() {
        return massage;
    }

    public void setMassage(String massage) {
        this.massage = massage;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public boolean isRead() {
        return read;
    }

    public void setRead(boolean read) {
        this.read = read;
    }

    public Integer getProId() {
        return proId;
    }

    public void setProId(Integer proId) {
        this.proId = proId;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

}

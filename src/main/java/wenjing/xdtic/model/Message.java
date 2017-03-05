package wenjing.xdtic.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.util.Date;

/**
 *
 * @author wenjing
 */
@JsonIgnoreProperties({"id", "userId", "content"})
public class Message {

    public static enum Type {
        POST, PASS, JOIN, REJECT
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
    private Integer mid; // id
    private Integer uid; // userId
    private String massage; // content

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

    public static Message of(Project project, Message.Type type) {
        return of(project, type, null);
    }

    public static Message of(Project project, Message.Type type, String comment) {
        Message message = new Message();

        message.setUserId(project.getUserId());
        message.setProId(project.getId());
        message.setContent(getMessageContent(project.getName(), type, comment));
        message.setType(type.name().toLowerCase());

        return message;
    }

    private static String getMessageContent(String proName, Message.Type type, String comment) {
        StringBuilder content = new StringBuilder(50);

        switch (type) {
            case POST:
                content.append("棒棒哒~ 已经发布项目【").append(proName).append("】，请等待审核");
                break;
            case PASS:
                content.append("厉害了~ 项目【").append(proName).append("】通过了审核");
                break;
            case JOIN:
                content.append("好开心~ 有用户报名了项目【").append(proName).append("】");
                break;
            case REJECT:
                content.append("注意了~ 项目【").append(proName).append("】被拒绝了，请修改后重新提交");
                break;
        }

        if (comment != null) {
            content.append("。备注：").append(comment);
        }

        return content.toString();
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

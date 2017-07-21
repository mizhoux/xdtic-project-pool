package xdtic.projpool.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import java.util.Date;

/**
 *
 * @author wenjing
 */
public class Message {

    public static enum Type {
        POST, PASS, JOIN, REJECT
    }

    private Integer id;

    private Integer proId;
    private Integer userId;
    private String content;
    private boolean read;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date creationDate;

    public static Message of(Project project, Message.Type type) {
        return of(project, type, null);
    }

    public static Message of(Project project, Message.Type type, String comment) {
        Message message = new Message();

        message.setUserId(project.getUserId());
        message.setProId(project.getId());
        message.setContent(getMessageContent(project.getName(), type, comment));

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
            content.append("。备注：").append(comment.isEmpty() ? "无" : content);
        }

        return content.toString();
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Date getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
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

package wenjing.xdtic.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import java.util.Date;
import java.util.List;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import org.hibernate.validator.constraints.Length;

/**
 *
 * @author wenjing
 */
public class Project {

    private Integer id;

    @NotNull(message = "用户 ID 不能为 null")
    private Integer userId;
    private String username;

    @NotNull
    @Length(min = 2, max = 30, message = "项目名称长度需要在 2~30 之间")
    private String name;

    @NotNull
    @Size(min = 10, message = "项目内容至少 10 个字")
    private String content;

    @NotNull
    @Size(min = 2, max = 30, message = "项目标签长度需要在 2~30 之间")
    private String tag;

    @NotNull
    @Size(min = 6, message = "招聘信息最少 6 个字")
    private String recruit;

    @NotNull
    @Size(min = 11, message = "联系方式最少 11 个字")
    private String contact;

    /**
     * 0：待审核；1：审核通过；2：被拒绝
     */
    private byte status;

    @JsonFormat(pattern = "yyyy.MM.dd")
    private Date creationDate;

    // 非数据库中字段，前端需要
    private boolean isCollected;
    private List<String> tags;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public byte getStatus() {
        return status;
    }

    public void setStatus(byte status) {
        this.status = status;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    /**
     * 获得参与项目的联系方式
     *
     * @return
     */
    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    /**
     * 获得项目的具体内容
     *
     * @return
     */
    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getRecruit() {
        return recruit;
    }

    public void setRecruit(String recruit) {
        this.recruit = recruit;
    }

    public List<String> getTags() {
        return tags;
    }

    public void setTags(List<String> tags) {
        this.tags = tags;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public Date getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }

    public boolean isIsCollected() {
        return isCollected;
    }

    public void setIsCollected(boolean isCollected) {
        this.isCollected = isCollected;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @Override
    public String toString() {
        return "Project{" + "id=" + id + ", userId=" + userId + ", username=" + username + ", name=" + name + ", content=" + content + ", tag=" + tag + ", recruit=" + recruit + ", contact=" + contact + '}';
    }

}

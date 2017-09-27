package xdtic.projpool.model;

import com.alibaba.fastjson.annotation.JSONField;
import java.util.Date;
import java.util.List;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * Project
 *
 * @author wenjing
 */
public class Project {

    public static final byte STATUS_UNCHECK = 0;
    public static final byte STATUS_ACCEPTED = 1;
    public static final byte STATUS_REJECTED = 2;

    private Integer id;

    @NotNull(message = "用户 ID 不能为 null")
    private Integer userId;  // 发布项目的用户的 id
    private String username; // 发布项目的用户的用户名，冗余数据，方便查询

    @NotNull
    @Size(min = 2, max = 30, message = "项目标签长度需要在 2~30 之间")
    private String tag;

    @NotNull
    @Size(min = 2, max = 30, message = "项目名称长度需要在 2~30 之间")
    private String name;

    @NotNull
    @Size(min = 10, message = "项目内容至少 10 个字")
    private String content;

    @NotNull
    @Size(min = 6, message = "招聘信息最少 6 个字")
    private String recruit;

    @NotNull
    @Size(min = 11, message = "联系方式最少 11 个字")
    private String contact;

    /**
     * 0：待审核；1：审核通过；2：被拒绝
     * （应该命名为 state，但是前端已经使用 status）
     */
    private byte status = 0;

    @JSONField(format = "yyyy.MM.dd")
    private Date creationDate;

    // 非数据库中字段，前端需要
    private boolean isCollected;
    private List<String> tags;

    public Project() {
    }

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

    public static class Builder {

        private Integer id;
        private Integer userId;
        private String username;
        private String tag;
        private String name;
        private String content;
        private String recruit;
        private String contact;
        private byte status;
        private Date creationDate;
        private boolean isCollected;
        private List<String> tags;

        private Builder() {
        }

        public Builder id(final Integer value) {
            this.id = value;
            return this;
        }

        public Builder userId(final Integer value) {
            this.userId = value;
            return this;
        }

        public Builder username(final String value) {
            this.username = value;
            return this;
        }

        public Builder tag(final String value) {
            this.tag = value;
            return this;
        }

        public Builder name(final String value) {
            this.name = value;
            return this;
        }

        public Builder content(final String value) {
            this.content = value;
            return this;
        }

        public Builder recruit(final String value) {
            this.recruit = value;
            return this;
        }

        public Builder contact(final String value) {
            this.contact = value;
            return this;
        }

        public Builder status(final byte value) {
            this.status = value;
            return this;
        }

        public Builder creationDate(final Date value) {
            this.creationDate = value;
            return this;
        }

        public Builder isCollected(final boolean value) {
            this.isCollected = value;
            return this;
        }

        public Builder tags(final List<String> value) {
            this.tags = value;
            return this;
        }

        public Project build() {
            return new xdtic.projpool.model.Project(id, userId, username, tag, name, content, recruit, contact, status, creationDate, isCollected, tags);
        }
    }

    public static Project.Builder builder() {
        return new Project.Builder();
    }

    private Project(final Integer id, final Integer userId, final String username, final String tag, final String name, final String content, final String recruit, final String contact, final byte status, final Date creationDate, final boolean isCollected, final List<String> tags) {
        this.id = id;
        this.userId = userId;
        this.username = username;
        this.tag = tag;
        this.name = name;
        this.content = content;
        this.recruit = recruit;
        this.contact = contact;
        this.status = status;
        this.creationDate = creationDate;
        this.isCollected = isCollected;
        this.tags = tags;
    }

}

package wenjing.xdtic.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.util.Date;
import java.util.List;

/**
 *
 * @author wenjing
 */
@JsonIgnoreProperties({"id", "userId", "name", "content", "recruit", "contact", "status"})
public class Project {

    private Integer id;

    private Integer userId;
    private String username;

    private String name;
    private String content;
    private String tag;

    private String recruit;
    private String contact;

    private String status;

    @JsonFormat(pattern = "yyyy.MM.dd")
    private Date date;

    // 非数据库中字段，前端需要
    private boolean isCollected;
    private List<String> tags;

    // 兼容前端
    private Integer proId;  // id
    private Integer userid; // userId
    private String proname; // name
    private String desc; // content
    private String promassage; // content
    private String prowant; // recruit
    private String concat;  // contact
    private String statu;   // status

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
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

    public String getConcat() {
        return concat;
    }

    public void setConcat(String concat) {
        this.concat = concat;
    }

    public String getStatu() {
        return statu;
    }

    public void setStatu(String statu) {
        this.statu = statu;
    }

    public List<String> getTags() {
        return tags;
    }

    public void setTags(List<String> tags) {
        this.tags = tags;
    }

    public Integer getUserid() {
        return userid;
    }

    public void setUserid(Integer userid) {
        this.userid = userid;
    }

    public Integer getProId() {
        return proId;
    }

    public void setProId(Integer proId) {
        this.proId = proId;
    }

    public String getProname() {
        return proname;
    }

    public void setProname(String proname) {
        this.proname = proname;
    }

    public String getProwant() {
        return prowant;
    }

    public void setProwant(String prowant) {
        this.prowant = prowant;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
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

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getPromassage() {
        return promassage;
    }

    public void setPromassage(String promassage) {
        this.promassage = promassage;
    }

}

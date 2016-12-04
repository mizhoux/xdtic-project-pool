package wenjing.xdtic.model;

import java.util.List;

/**
 *
 * @author wenjing
 */
public class Project {

    private Integer userid;
    private Integer proId;
    private String proname;
    private String promassage;
    private String prowant;
    private String tag;
    private String date;
    private String concat;
    private String statu;

    private boolean isCollected;

    private List<String> tags;
    private String username;
    private String desc;
    
    public Project() {
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

    public String getPromassage() {
        return promassage;
    }

    public void setPromassage(String promassage) {
        this.promassage = promassage;
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

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
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

}

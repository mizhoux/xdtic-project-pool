package wenjing.xdtic.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import java.util.Date;

/**
 *
 * @author Michael Chow <mizhoux@gmail.com>
 */
public class SignInfo {

    private Integer id;

    private Integer userId;
    private Integer proId;

    private String apply;
    private String skill;
    private String experience;

    @JsonFormat(pattern = "yyyy.MM.dd HH:mm")
    private Date signDate;

    private String username;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getSkill() {
        return skill;
    }

    public void setSkill(String skill) {
        this.skill = skill;
    }

    public String getExperience() {
        return experience;
    }

    public void setExperience(String experience) {
        this.experience = experience;
    }

    public Date getSignDate() {
        return signDate;
    }

    public void setSignDate(Date signDate) {
        this.signDate = signDate;
    }

    public Integer getProId() {
        return proId;
    }

    public void setProId(Integer proId) {
        this.proId = proId;
    }

    public String getApply() {
        return apply;
    }

    public void setApply(String apply) {
        this.apply = apply;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

}

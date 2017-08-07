package xdtic.projpool.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import java.util.Date;

/**
 * Sign Information
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

    public static class Builder {

        private Integer id;
        private Integer userId;
        private Integer proId;
        private String apply;
        private String skill;
        private String experience;
        private Date signDate;
        private String username;

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

        public Builder proId(final Integer value) {
            this.proId = value;
            return this;
        }

        public Builder apply(final String value) {
            this.apply = value;
            return this;
        }

        public Builder skill(final String value) {
            this.skill = value;
            return this;
        }

        public Builder experience(final String value) {
            this.experience = value;
            return this;
        }

        public Builder signDate(final Date value) {
            this.signDate = value;
            return this;
        }

        public Builder username(final String value) {
            this.username = value;
            return this;
        }

        public SignInfo build() {
            return new SignInfo(id, userId, proId, apply, skill, experience, signDate, username);
        }
    }

    public static SignInfo.Builder builder() {
        return new SignInfo.Builder();
    }

    private SignInfo(final Integer id, final Integer userId, final Integer proId, final String apply, final String skill, final String experience, final Date signDate, final String username) {
        this.id = id;
        this.userId = userId;
        this.proId = proId;
        this.apply = apply;
        this.skill = skill;
        this.experience = experience;
        this.signDate = signDate;
        this.username = username;
    }

}

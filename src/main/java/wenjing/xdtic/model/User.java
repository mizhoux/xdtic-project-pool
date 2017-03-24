package wenjing.xdtic.model;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import org.hibernate.validator.constraints.Email;

/**
 *
 * @author wenjing
 */
public class User {

    private Integer id;

    @NotNull
    @Size(min = 2, max = 20, message = "用户名长度需要在 2~20 之间")
    private String username;

    @Size(min = 6, max = 30, message = "密码长度需要在 6~30 之间")
    private String password;

    @Email(message = "邮箱格式不正确")
    private String email;

    @Size(min = 11, max = 11, message = "电话号码必须为 11 位")
    private String phone;

    @Pattern(regexp = "[M|F]{1}", message = "男为 M，女为 F")
    private String gender;

    @Size(min = 2, max = 30, message = "真实名字长度需要在 2~30 之间")
    private String realname;

    private String nickname; // 暂未使用

    @Size(min = 4, max = 20, message = "专业长度需要在 4~30 之间")
    private String major;

    @Size(min = 6, max = 20, message = "学号长度需要在 6~20 之间")
    private String stuNum;

    private String skill = "无";
    private String experience = "无";

    /**
     * 前端所需字段，判断当前用户是否有未读消息
     */
    private boolean hasMsg;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public boolean isHasMsg() {
        return hasMsg;
    }

    public void setHasMsg(boolean hasMsg) {
        this.hasMsg = hasMsg;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getRealname() {
        return realname;
    }

    public void setRealname(String realname) {
        this.realname = realname;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getMajor() {
        return major;
    }

    public void setMajor(String major) {
        this.major = major;
    }

    public String getStuNum() {
        return stuNum;
    }

    public void setStuNum(String stuNum) {
        this.stuNum = stuNum;
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

    @Override
    public String toString() {
        return "User{" + "id=" + id + ", username=" + username + ", password=" + password + ", gender=" + gender + ", email=" + email + ", phone=" + phone + ", realname=" + realname + ", nickname=" + nickname + ", major=" + major + ", stuNum=" + stuNum + '}';
    }

}

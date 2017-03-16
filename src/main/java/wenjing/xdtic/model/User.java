package wenjing.xdtic.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 *
 * @author wenjing
 */
@JsonIgnoreProperties({"realname", "gender", "specialty", "stuNum", "skill", "experience"})
public class User {

    private Integer id;
    private String username;
    private String password;

    private String email;
    private String phone;

    private String realname;
    private String nickname;

    private String gender;

    private String major;
    private String stuNum;
    private String skill;
    private String experience;

    private boolean hasMsg;

    // 兼容前端
    private String name;  // realname
    private String sex;   // gender
    private String profe; // specialty
    private String stunum;// stuNum
    private String profile;   // skill
    private String pexperice; // experience

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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getProfe() {
        return profe;
    }

    public void setProfe(String profe) {
        this.profe = profe;
    }

    public String getStunum() {
        return stunum;
    }

    public void setStunum(String stunum) {
        this.stunum = stunum;
    }

    public String getProfile() {
        return profile;
    }

    public void setProfile(String profile) {
        this.profile = profile;
    }

    public String getPexperice() {
        return pexperice;
    }

    public void setPexperice(String pexperice) {
        this.pexperice = pexperice;
    }

    @Override
    public String toString() {
        return "User{" + "id=" + id + ", username=" + username + ", realname=" + realname + ", email=" + email + ", phone=" + phone + '}';
    }

}

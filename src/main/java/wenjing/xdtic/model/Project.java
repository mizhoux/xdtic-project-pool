package wenjing.xdtic.model;

/**
 *
 * @author admin
 */
public class Project {
   
    private Integer  userid;
    private  Integer proid;
    private  String  proname;
    private  String  promassage;
    private  String  prowant;
    private  String  phone;
    private  String  tag;
    private  String  date;

    public Project(Integer userid, Integer proid, String proname, String promassage, String prowant,String phone, String tag, String date) {
        this.userid = userid;
        this.proid = proid;
        this.proname = proname;
        this.promassage = promassage;
        this.prowant = prowant;
        this.phone=phone;
        this.tag = tag;
        this.date = date;
    }

    public Project() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public Integer getUserid() {
        return userid;
    }

    public void setUserid(Integer userid) {
        this.userid = userid;
    }

    public Integer getProid() {
        return proid;
    }

    public void setProid(Integer proid) {
        this.proid = proid;
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

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
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
    
    

}

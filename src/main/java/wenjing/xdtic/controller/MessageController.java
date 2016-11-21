package wenjing.xdtic.controller;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import wenjing.xdtic.dao.SystemassageDao;
import wenjing.xdtic.model.Systemassage;

/**
 *
 * @author admin
 */
@Controller
@RequestMapping("/sysmsg")
public class MessageController {

    //查看系统消息
    @Autowired
    private SystemassageDao systemassageDao;

    @ResponseBody
    @RequestMapping(value = "/{uid}", method = RequestMethod.GET)
    public List<Systemassage> systemassage(@PathVariable("uid") Integer uid) {
    //    System.out.println("uid: " + uid);

        List<Systemassage> systemassages = systemassageDao.getSystemassageid(uid);

//        if ( systemassages.isEmpty()) {
//            return "personalinformation";
//        } else {
//            return "systemassage";
//        }
        
        return systemassages;

    }

}

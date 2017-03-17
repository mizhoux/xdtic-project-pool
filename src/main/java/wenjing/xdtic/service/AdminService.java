package wenjing.xdtic.service;

import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import wenjing.xdtic.dao.AdminDao;
import wenjing.xdtic.model.Admin;

/**
 *
 * @author Michael Chow <mizhoux@gmail.com>
 */
@Service
public class AdminService {

    @Autowired
    private AdminDao adminDao;

    public Optional<Admin> getAdmin(String username, String password) {
        return adminDao.getAdmin(username, password);
    }

}

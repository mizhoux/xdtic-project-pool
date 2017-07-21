package xdtic.projpool.service;

import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import xdtic.projpool.dao.AdminDao;
import xdtic.projpool.model.Admin;

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

package xdtic.projpool.service;

import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import xdtic.projpool.dao.AdminMapper;
import xdtic.projpool.model.Admin;

/**
 * Admin Service
 *
 * @author Michael Chow <mizhoux@gmail.com>
 */
@Service
public class AdminService {

    @Autowired
    private AdminMapper adminMapper;

    public Optional<Admin> getAdmin(String username, String password) {
        Admin admin = adminMapper.getAdmin(username, password);

        return Optional.ofNullable(admin);
    }

}

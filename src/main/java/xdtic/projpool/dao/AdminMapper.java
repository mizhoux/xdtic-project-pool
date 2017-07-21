package xdtic.projpool.dao;

import xdtic.projpool.model.Admin;

/**
 * Admin Mapper
 *
 * @author Michael Chow <mizhoux@gmail.com>
 */
public interface AdminMapper {
    
    Admin getAdmin(String username, String password);
    
}

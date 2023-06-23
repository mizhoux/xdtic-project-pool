/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package xdtic.projpool.dao;

import xdtic.projpool.model.Admin;

import java.util.Optional;

/**
 *
 * @author alexc
 */
public interface AdminService {
        Optional<Admin> getAdmin(String username, String password);
}

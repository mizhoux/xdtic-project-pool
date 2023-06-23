/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package xdtic.projpool.dao;

import java.util.List;
import xdtic.projpool.model.PagingModel;
import xdtic.projpool.model.User;

public interface UserService {
    PagingModel<User> getPagingUsers(String keyword, int pageNum, int size);
    boolean deleteUsers(List<Integer> userIds);
}

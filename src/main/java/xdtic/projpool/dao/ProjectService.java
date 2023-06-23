/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package xdtic.projpool.dao;


import xdtic.projpool.model.PagingModel;
import xdtic.projpool.model.Project;

public interface ProjectService {
    PagingModel<Project> getPagingUncheckedProjects(String keyword, int pageNum, int size);
    boolean updateProjectByOperation(Integer proId, String operation, String comment);
    PagingModel<Project> getPagingAcceptedProjects(String keyword, int pageNum, int size, Object nullObject);
}

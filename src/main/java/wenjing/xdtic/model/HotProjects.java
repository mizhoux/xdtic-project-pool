package wenjing.xdtic.model;

import java.util.List;

/**
 *
 * @author Michael Chow <mizhoux@gmail.com>
 */
public class HotProjects {

    private Integer hotSize;
    private List<Project> projects;

    public HotProjects(Integer hotSize, List<Project> projects) {
        this.hotSize = hotSize;
        this.projects = projects;
    }

    public Integer getHotSize() {
        return hotSize;
    }

    public void setHotSize(Integer hotSize) {
        this.hotSize = hotSize;
    }

    public List<Project> getProjects() {
        return projects;
    }

    public void setProjects(List<Project> projects) {
        this.projects = projects;
    }

}

package wenjing.xdtic.model;

import java.util.List;

/**
 *
 * @author Michael Chow <mizhoux@gmail.com>
 */
public class PagingUsers {

    private Integer pageNum;
    private Integer size;
    private boolean hasMore;
    List<User> users;

    public Integer getPageNum() {
        return pageNum;
    }

    public void setPageNum(Integer pageNum) {
        this.pageNum = pageNum;
    }

    public Integer getSize() {
        return size;
    }

    public void setSize(Integer size) {
        this.size = size;
    }

    public boolean isHasMore() {
        return hasMore;
    }

    public void setHasMore(boolean hasMore) {
        this.hasMore = hasMore;
    }

    public List<User> getUsers() {
        return users;
    }

    public void setUsers(List<User> users) {
        this.users = users;
    }

}

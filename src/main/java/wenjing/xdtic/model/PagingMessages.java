package wenjing.xdtic.model;

import java.util.List;

/**
 *
 * @author admin
 */
public class PagingMessages {

    private Integer pageNum;
    private Integer size;
    private boolean hasMore;
    List<Systemassage> msgs;

    public PagingMessages() {
    }

    public PagingMessages(Integer pageNum, Integer size, boolean hasMore, List<Systemassage> msgs) {
        this.pageNum = pageNum;
        this.size = size;
        this.hasMore = hasMore;
        this.msgs = msgs;
    }

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

    public List<Systemassage> getMsgs() {
        return msgs;
    }

    public void setMsgs(List<Systemassage> msgs) {
        this.msgs = msgs;
    }

}

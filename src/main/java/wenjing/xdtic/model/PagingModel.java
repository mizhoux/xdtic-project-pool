package wenjing.xdtic.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import java.util.List;
import java.util.function.Supplier;

/**
 *
 * @author Michael Chow
 * @param <T>
 */
@JsonSerialize(using = PagingModelSerializer.class)
public class PagingModel<T> {

    private Integer pageNum;
    private Integer size;
    private boolean hasMore;

    private List<T> entities;

    @JsonIgnore
    private String entitiesName = "entities";

    public PagingModel() {
    }

    public PagingModel(List<T> entities, Integer pageNum, Integer size) {
        this.pageNum = pageNum;
        this.size = size;
        this.entities = entities;
    }

    public PagingModel(List<T> entities, Integer pageNum, Integer size, String entitiesName) {
        this.pageNum = pageNum;
        this.size = size;
        this.entities = entities;
        this.entitiesName = entitiesName;
    }

    public static <T> PagingModel<T> of(
            Supplier<List<T>> entitiesSupplier, String entitiesName,
            Supplier<Long> countSupplier, int pageNum, int pageSize) {

        List<T> entities = entitiesSupplier.get();
        long count = countSupplier.get();

        PagingModel<T> pagingModel = new PagingModel<>(entities, pageNum, entities.size(), entitiesName);
        pagingModel.setHasMore((pageNum + 1) * pageSize < count);

        return pagingModel;
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

    public List<T> getEntities() {
        return entities;
    }

    public void setEntities(List<T> entities) {
        this.entities = entities;
    }

    public String getEntitiesName() {
        return entitiesName;
    }

    public void setEntitiesName(String entitiesName) {
        this.entitiesName = entitiesName;
    }

}

package xdtic.projpool.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import java.util.List;
import java.util.function.Supplier;

/**
 * 分页模型，包括当前页面的实体 {@code entities}，当前页号 {@code pageNum}<br>
 * 当前页面实体数量 {@code size}，以及是否之后还有页面 {@code hasMore}
 *
 * @author Michael Chow
 * @param <T> 实体类型
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

    private PagingModel(String entitiesName, List<T> entities, Integer pageNum, Integer size) {
        this.pageNum = pageNum;
        this.size = size;
        this.entities = entities;
        this.entitiesName = entitiesName;
    }

    /**
     * 获得一个分页模型
     *
     * @param <T> 实体类型
     * @param entitiesName 实体名称
     * @param entitiesSupplier 实体获取器
     * @param totalNumberSupplier 实体总数获取器
     * @param pageNum 请求的页面的页号
     * @param pageSize 请求的页面实体数量
     * @return
     */
    public static <T> PagingModel<T> of(
            String entitiesName, Supplier<List<T>> entitiesSupplier,
            Supplier<Long> totalNumberSupplier, int pageNum, int pageSize) {

        long number = totalNumberSupplier.get();
        List<T> entities = entitiesSupplier.get();

        PagingModel<T> pagingModel = new PagingModel<>(
                entitiesName, entities, pageNum, entities.size());
        pagingModel.setHasMore((pageNum + 1) * pageSize < number);

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

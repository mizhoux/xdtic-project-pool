package xdtic.projpool.model;

import com.alibaba.fastjson.annotation.JSONField;
import java.util.List;

/**
 * 分页模型，包括当前页面的实体 {@code entities}，当前页号 {@code pageNum}<br>
 * 当前页面实体数量 {@code size}，以及是否之后还有页面 {@code hasMore}
 *
 * @author Michael Chow
 * @param <T> 实体类型
 */
public class PagingModel<T> {

    private final int pageNum;
    private final int size;
    private final boolean hasMore;

    private final List<T> entities;

    @JSONField(serialize = false)
    private String entitiesName = "entities";

    public static class Builder<T> {

        private int pageNum;
        private int size;
        private boolean hasMore;
        private List<T> entities;
        private String entitiesName;

        private Builder() {
        }

        public Builder<T> pageNum(final int value) {
            this.pageNum = value;
            return this;
        }

        public Builder<T> size(final int value) {
            this.size = value;
            return this;
        }

        public Builder<T> hasMore(final boolean value) {
            this.hasMore = value;
            return this;
        }

        public Builder<T> entities(final List<T> value) {
            this.entities = value;
            return this;
        }

        public Builder<T> entitiesName(final String value) {
            this.entitiesName = value;
            return this;
        }

        public PagingModel<T> build() {
            return new PagingModel<>(pageNum, size, hasMore, entities, entitiesName);
        }
    }

    public static <T> PagingModel.Builder<T> builder() {
        return new PagingModel.Builder<>();
    }

    private PagingModel(final int pageNum, final int size,
            final boolean hasMore, final List<T> entities, final String entitiesName) {
        this.pageNum = pageNum;
        this.size = size;
        this.hasMore = hasMore;
        this.entities = entities;
        this.entitiesName = entitiesName;
    }

    public int getPageNum() {
        return pageNum;
    }

    public int getSize() {
        return size;
    }

    public boolean isHasMore() {
        return hasMore;
    }

    public List<T> getEntities() {
        return entities;
    }

    public String getEntitiesName() {
        return entitiesName;
    }

}

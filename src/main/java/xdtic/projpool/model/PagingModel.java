package xdtic.projpool.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import java.io.IOException;
import java.util.List;

/**
 * 分页模型，包括当前页面的实体 {@code entities}，当前页号 {@code pageNum}<br>
 * 当前页面实体数量 {@code size}，以及是否之后还有页面 {@code hasMore}
 *
 * @author Michael Chow
 * @param <T> 实体类型
 */
@JsonSerialize(using = PagingModelSerializer.class)
public class PagingModel<T> {

    private final int pageNum;
    private final int size;
    private final boolean hasMore;

    private final List<T> entities;

    @JsonIgnore
    private String entitiesName = "entities";

    public static class Builder<T> {

        private int pageNum;
        private int size;
        private boolean hasMore;
        private List<T> entities;
        private String entitiesName;

        private Builder() {
        }

        public Builder pageNum(final int value) {
            this.pageNum = value;
            return this;
        }

        public Builder size(final int value) {
            this.size = value;
            return this;
        }

        public Builder hasMore(final boolean value) {
            this.hasMore = value;
            return this;
        }

        public Builder entities(final List<T> value) {
            this.entities = value;
            return this;
        }

        public Builder entitiesName(final String value) {
            this.entitiesName = value;
            return this;
        }

        public PagingModel build() {
            return new PagingModel(pageNum, size, hasMore, entities, entitiesName);
        }
    }

    public static PagingModel.Builder builder() {
        return new PagingModel.Builder();
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

class PagingModelSerializer extends JsonSerializer<PagingModel> {

    @Override
    public void serialize(
            PagingModel model, JsonGenerator gen, SerializerProvider serializers)
            throws IOException, JsonProcessingException {
        gen.writeStartObject();

        gen.writeObjectField("pageNum", model.getPageNum());
        gen.writeObjectField("size", model.getSize());
        gen.writeBooleanField("hasMore", model.isHasMore());
        String entitiesName = model.getEntitiesName();
        gen.writeObjectField(entitiesName, model.getEntities());

        gen.writeEndObject();
    }

}

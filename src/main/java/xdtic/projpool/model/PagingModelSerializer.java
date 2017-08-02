package xdtic.projpool.model;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import java.io.IOException;

/**
 * Paging Model Serializer
 *
 * @author Michael Chow
 */
public class PagingModelSerializer extends JsonSerializer<PagingModel> {

    @Override
    public void serialize(PagingModel model, JsonGenerator gen, SerializerProvider serializers)
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

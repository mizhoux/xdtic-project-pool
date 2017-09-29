package xdtic.projpool.util;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.NameFilter;
import com.alibaba.fastjson.support.spring.FastJsonHttpMessageConverter;
import java.io.IOException;
import java.io.OutputStream;
import org.springframework.http.HttpOutputMessage;
import org.springframework.http.converter.HttpMessageNotWritableException;
import xdtic.projpool.model.PagingModel;

/**
 * Customer Fast Json Http Message Converter
 *
 * @author Michael Chow <mizhoux@gmail.com>
 */
public class JsonHttpMessageConverter extends FastJsonHttpMessageConverter {

    private final NameFilter nameFilter;

    public JsonHttpMessageConverter() {

        nameFilter = new NameFilter() {
            @Override
            public String process(Object obj, String name, Object value) {
                if (obj.getClass() == PagingModel.class) {
                    if (name.equals("entities")) {
                        PagingModel model = (PagingModel) obj;
                        return model.getEntitiesName();
                    }
                }

                return name;
            }
        };
    }

    @Override
    protected void writeInternal(Object obj, HttpOutputMessage outputMessage) throws IOException, HttpMessageNotWritableException {
        OutputStream out = outputMessage.getBody();
        String text = JSON.toJSONString(obj, nameFilter, super.getFeatures());
        byte[] bytes = text.getBytes(super.getCharset());
        out.write(bytes);
    }

}

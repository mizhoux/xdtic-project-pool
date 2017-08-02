package xdtic.projpool.service;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import xdtic.projpool.dao.MessageMapper;
import xdtic.projpool.model.Message;
import xdtic.projpool.model.PagingModel;
import xdtic.projpool.util.Pair;

/**
 * Message Service
 *
 * @author Michael Chow <mizhoux@gmail.com>
 */
@Service
public class MessageService {

    @Autowired
    private MessageMapper messageMapper;

    public boolean addMessage(Message msg) {
        int result = messageMapper.addMessage(msg);

        return result == 1;
    }

    /**
     * 通过用户 ID 获得该用户的消息，并使用 PageHelper 进行分页
     *
     * @param userId 用户 ID
     * @param pageNum 页码，从 0 开始
     * @param pageSize 页面大小
     * @return 分页后的用户的消息
     */
    public Pair<List<Message>, Long> getMessagesByUserId(Integer userId, int pageNum, int pageSize) {
        Page<Object> page = PageHelper.startPage(pageNum + 1, pageSize);
        List<Message> messages = messageMapper.getMessagesByUserId(userId);

        return Pair.of(messages, page.getTotal());
    }

    public PagingModel<Message> getPagingMessages(Integer userId, int pageNum, int pageSize) {

        Pair<List<Message>, Long> pair = getMessagesByUserId(userId, pageNum, pageSize);

        PagingModel model = PagingModel.builder()
                .entitiesName("msgs")
                .entities(pair.left())
                .pageNum(pageNum)
                .size(pair.left().size())
                .hasMore((pageNum + 1) * pageSize < pair.right())
                .build();

        return model;
    }

    public long countMessages(Integer userId) {
        return messageMapper.countMessagesByUserId(userId);
    }

    public boolean setMessagesRead(List<Integer> ids) {
        if (ids.isEmpty()) {
            return true;
        }

        int result = messageMapper.setMessagesRead(ids);

        return result > 0;
    }

    public long countUnreadMessages(Integer userId) {
        return messageMapper.countUnreadMessagesByUserId(userId);
    }

}

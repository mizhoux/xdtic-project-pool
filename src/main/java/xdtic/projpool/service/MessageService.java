package xdtic.projpool.service;

import com.github.pagehelper.PageHelper;
import java.util.List;
import java.util.function.Supplier;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import xdtic.projpool.dao.MessageMapper;
import xdtic.projpool.model.Message;
import xdtic.projpool.model.PagingModel;

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

    public List<Message> getMessagesByUserId(Integer userId, int pageNum, int pageSize) {
        PageHelper.startPage(pageNum + 1, pageSize);
        
        List<Message> messages = messageMapper.getMessagesByUserId(userId);

        return messages;
    }

    public PagingModel<Message> getPagingMessages(Integer userId, int pageNum, int pageSize) {

        Supplier<List<Message>> msgs = () -> getMessagesByUserId(userId, pageNum, pageSize);
        Supplier<Long> totalNumOfMsgs = () -> countMessages(userId);

        return PagingModel.of("msgs", msgs, totalNumOfMsgs, pageNum, pageSize);
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

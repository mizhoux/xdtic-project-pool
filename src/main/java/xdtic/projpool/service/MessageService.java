package xdtic.projpool.service;

import java.util.List;
import java.util.function.Supplier;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import xdtic.projpool.dao.MessageDao;
import xdtic.projpool.model.Message;
import xdtic.projpool.model.PagingModel;

/**
 *
 * @author Michael Chow <mizhoux@gmail.com>
 */
@Service
public class MessageService {

    @Autowired
    private MessageDao messageDao;

    public boolean addMessage(Message msg) {
        return messageDao.addMessage(msg);
    }

    public List<Message> getMessages(Integer userId, int pageNum, int pageSize) {
        int offset = pageNum * pageSize;
        return messageDao.getMessages(userId, offset, pageSize);
    }

    public PagingModel<Message> getPagingMessages(Integer userId, int pageNum, int pageSize) {

        Supplier<List<Message>> msgs = () -> getMessages(userId, pageNum, pageSize);
        Supplier<Long> totalNumOfMsgs = () -> countMessages(userId);

        return PagingModel.of("msgs", msgs, totalNumOfMsgs, pageNum, pageSize);
    }

    public long countMessages(Integer userId) {
        return messageDao.countMessages(userId);
    }

    public boolean setMessagesRead(List<Integer> ids) {
        return ids.isEmpty() ? true : messageDao.setMessagesRead(ids);
    }

    public long countUnreadMessages(Integer userId) {
        return messageDao.countUnreadMessages(userId);
    }

}

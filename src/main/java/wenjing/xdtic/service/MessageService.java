package wenjing.xdtic.service;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import wenjing.xdtic.dao.MessageDao;
import wenjing.xdtic.model.Message;
import wenjing.xdtic.model.PagingModel;

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

    public Message getMessage(Integer id) {
        Message message = messageDao.getMessage(id);
        Message.syncDataForFront(message);

        return message;
    }

    public List<Message> getMessages(Integer userId, int pageNum, int pageSize) {
        int offset = pageNum * pageSize;

        List<Message> messages = messageDao.getMessages(userId, offset, pageSize);
        messages.forEach(Message::syncDataForFront);

        return messages;
    }

    public PagingModel<Message> getPagingMessages(Integer userId, int pageNum, int pageSize) {
        List<Message> msgs = getMessages(userId, pageNum, pageSize);

        PagingModel<Message> pagingMsgs = new PagingModel<>(msgs, pageNum, msgs.size());

        long count = getMessagesCount(userId);
        pagingMsgs.setHasMore((pageNum + 1) * pageSize < count);

        pagingMsgs.setEntitiesName("msgs");

        return pagingMsgs;
    }

    public long getMessagesCount(Integer userId) {
        return messageDao.getMessagesCount(userId);
    }

    public boolean setMessageRead(Integer id) {
        return messageDao.setMessageRead(id);
    }

    public boolean setMessagesRead(List<Integer> ids) {
        if (ids.isEmpty()) {
            return true;
        }
        return messageDao.setMessagesRead(ids);
    }

    public long getUnreadMessagesCount(Integer userId) {
        return messageDao.getUnreadMessagesCount(userId);
    }

}

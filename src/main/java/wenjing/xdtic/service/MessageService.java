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

    public List<Message> getMessages(Integer userId, int pageNum, int pageSize) {
        int offset = pageNum * pageSize;

        List<Message> messages = messageDao.getMessages(userId, offset, pageSize);
        messages.forEach(this::syncDataForFront);

        return messages;
    }

    public PagingModel<Message> getPagingMessages(Integer userId, int pageNum, int pageSize) {
        List<Message> msgs = getMessages(userId, pageNum, pageSize);

        PagingModel<Message> pagingMsgs = new PagingModel<>(msgs, pageNum, msgs.size());

        long count = countMessages(userId);
        pagingMsgs.setHasMore((pageNum + 1) * pageSize < count);

        pagingMsgs.setEntitiesName("msgs");

        return pagingMsgs;
    }

    public long countMessages(Integer userId) {
        return messageDao.countMessages(userId);
    }

    public boolean setMessagesRead(List<Integer> ids) {
        if (ids.isEmpty()) {
            return true;
        }
        return messageDao.setMessagesRead(ids);
    }

    public long countUnreadMessages(Integer userId) {
        return messageDao.countUnreadMessages(userId);
    }

    public void syncDataForBack(Message message) {
        if (message == null) {
            return;
        }
        message.setId(message.getMid());
        message.setUserId(message.getUid());
        message.setContent(message.getMassage());
    }

    public void syncDataForFront(Message message) {
        if (message == null) {
            return;
        }
        message.setMid(message.getId());
        message.setUid(message.getUserId());
        message.setMassage(message.getContent());
    }

}

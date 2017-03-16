package wenjing.xdtic.service;

import java.util.List;
import java.util.function.Supplier;
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

    public Message syncDataForBack(Message message) {
        message.setId(message.getMid());
        message.setUserId(message.getUid());
        message.setContent(message.getMassage());

        return message;
    }

    public Message syncDataForFront(Message message) {
        message.setMid(message.getId());
        message.setUid(message.getUserId());
        message.setMassage(message.getContent());

        return message;
    }

}

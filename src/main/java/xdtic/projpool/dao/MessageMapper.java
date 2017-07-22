package xdtic.projpool.dao;

import java.util.List;
import xdtic.projpool.model.Message;

/**
 * Message Mapper
 *
 * @author Michael Chow <mizhoux@gmail.com>
 */
public interface MessageMapper {

    int addMessage(Message message);

    Message getMessage(Integer id);

    List<Message> getMessagesByUserId(Integer userId);

    long countMessagesByUserId(Integer userId);

    public long countUnreadMessagesByUserId(Integer userId);

    public int setMessagesRead(List<Integer> ids);
}

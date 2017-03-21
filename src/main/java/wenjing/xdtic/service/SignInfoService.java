package wenjing.xdtic.service;

import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import wenjing.xdtic.dao.MessageDao;
import wenjing.xdtic.dao.ProjectDao;
import wenjing.xdtic.dao.SignInfoDao;
import wenjing.xdtic.model.Message;
import wenjing.xdtic.model.SignInfo;

/**
 *
 * @author Michael Chow <mizhoux@gmail.com>
 */
@Service
public class SignInfoService {

    @Autowired
    private ProjectDao projectDao;

    @Autowired
    private MessageDao messageDao;

    @Autowired
    private SignInfoDao signInfoDao;

    public boolean addSignInfo(SignInfo si) {

        boolean success = signInfoDao.addSignInfo(si);
        if (success) {
            projectDao.getProject(si.getProId())
                    .map(pro -> Message.of(pro, Message.Type.JOIN))
                    .ifPresent(msg -> messageDao.addMessage(msg));
        }

        return success;
    }

    public Optional<SignInfo> getSignInfo(Integer id) {
        return signInfoDao.getSignInfo(id);
    }

    public List<SignInfo> getSignInfos(Integer proId) {
        return signInfoDao.getSignInfos(proId);
    }

}

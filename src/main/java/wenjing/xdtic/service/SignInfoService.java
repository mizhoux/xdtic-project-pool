package wenjing.xdtic.service;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import wenjing.xdtic.dao.MessageDao;
import wenjing.xdtic.dao.ProjectDao;
import wenjing.xdtic.dao.SignInfoDao;
import wenjing.xdtic.model.Message;
import wenjing.xdtic.model.Project;
import wenjing.xdtic.model.SignInfo;

/**
 *
 * @author Michael Chow <mizhoux@gmail.com>
 */
@Service
public class SignInfoService {

    @Autowired
    private SignInfoDao signInfoDao;

    @Autowired
    private ProjectDao projectDao;

    @Autowired
    private MessageDao messageDao;

    public boolean addSignInfo(SignInfo signInfo) {
        syncDataForBack(signInfo);
        boolean success = signInfoDao.addSignInfo(signInfo);
        if (success) {
            Project project = projectDao.getProject(signInfo.getProId());
            Message message = Message.of(project, Message.Type.JOIN);
            messageDao.addMessage(message);
        }

        return success;
    }

    public SignInfo getSignInfo(Integer id) {
        SignInfo signInfo = signInfoDao.getSignInfo(id);
        syncDataForFront(signInfo);

        return signInfo;
    }

    public List<SignInfo> getSignInfos(Integer proId) {
        List<SignInfo> signInfos = signInfoDao.getSignInfos(proId);
        signInfos.forEach(this::syncDataForFront);

        return signInfos;
    }

    public void syncDataForBack(SignInfo signInfo) {
        if (signInfo == null) {
            return;
        }
        signInfo.setId(signInfo.getSid());
        signInfo.setUserId(signInfo.getUid());
        signInfo.setSkill(signInfo.getProfile());
        signInfo.setExperience(signInfo.getPexperice());
    }

    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("yyyy.MM.dd");
    private static final DateTimeFormatter TIME_FORMATTER = DateTimeFormatter.ofPattern("HH:mm");

    public void syncDataForFront(SignInfo signInfo) {
        if (signInfo == null) {
            return;
        }
        signInfo.setSid(signInfo.getId());
        signInfo.setUid(signInfo.getUserId());
        signInfo.setProfile(signInfo.getSkill());
        signInfo.setPexperice(signInfo.getExperience());
        LocalDateTime dateTime = LocalDateTime.ofInstant(
                signInfo.getSignTime().toInstant(), ZoneId.systemDefault());
        signInfo.setDate(DATE_FORMATTER.format(dateTime));
        signInfo.setTime(TIME_FORMATTER.format(dateTime));
    }
}

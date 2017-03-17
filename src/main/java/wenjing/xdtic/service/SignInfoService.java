package wenjing.xdtic.service;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
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
        return signInfoDao.getSignInfo(id).map(this::syncDataForFront);
    }

    public List<SignInfo> getSignInfos(Integer proId) {
        List<SignInfo> signInfos = signInfoDao.getSignInfos(proId);
        signInfos.forEach(this::syncDataForFront);

        return signInfos;
    }

    public SignInfo syncDataForBack(SignInfo signInfo) {

        signInfo.setId(signInfo.getSid());
        signInfo.setUserId(signInfo.getUid());
        signInfo.setSkill(signInfo.getProfile());
        signInfo.setExperience(signInfo.getPexperice());

        return signInfo;
    }

    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("yyyy.MM.dd");
    private static final DateTimeFormatter TIME_FORMATTER = DateTimeFormatter.ofPattern("HH:mm");

    public SignInfo syncDataForFront(SignInfo signInfo) {

        signInfo.setSid(signInfo.getId());
        signInfo.setUid(signInfo.getUserId());
        signInfo.setProfile(signInfo.getSkill());
        signInfo.setPexperice(signInfo.getExperience());
        LocalDateTime dateTime = LocalDateTime.ofInstant(
                signInfo.getSignTime().toInstant(), ZoneId.systemDefault());
        signInfo.setDate(DATE_FORMATTER.format(dateTime));
        signInfo.setTime(TIME_FORMATTER.format(dateTime));

        return signInfo;
    }
}

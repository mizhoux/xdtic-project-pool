package xdtic.projpool.service;

import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import xdtic.projpool.dao.MessageMapper;
import xdtic.projpool.dao.ProjectDao;
import xdtic.projpool.dao.SignInfoMapper;
import xdtic.projpool.model.Message;
import xdtic.projpool.model.SignInfo;

/**
 * Sign Info Service
 *
 * @author Michael Chow <mizhoux@gmail.com>
 */
@Service
public class SignInfoService {

    @Autowired
    private ProjectDao projectDao;

    @Autowired
    private MessageMapper messageMapper;

    @Autowired
    private SignInfoMapper signInfoMapper;

    public boolean addSignInfo(SignInfo si) {

        int result = signInfoMapper.addSignInfo(si);
        if (result == 1) {
            projectDao.getProject(si.getProId())
                    .map(pro -> Message.of(pro, Message.Type.JOIN))
                    .ifPresent(msg -> messageMapper.addMessage(msg));
        }

        return result == 1;
    }

    public Optional<SignInfo> getSignInfo(Integer id) {
        SignInfo signInfo = signInfoMapper.getSignInfo(id);
        return Optional.ofNullable(signInfo);
    }

    public List<SignInfo> getSignInfos(Integer proId) {
        return signInfoMapper.getSignInfosByProId(proId);
    }

}

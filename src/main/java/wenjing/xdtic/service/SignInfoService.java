package wenjing.xdtic.service;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import wenjing.xdtic.dao.SignInfoDao;
import wenjing.xdtic.model.SignInfo;

/**
 *
 * @author Michael Chow <mizhoux@gmail.com>
 */
@Service
public class SignInfoService {

    @Autowired
    private SignInfoDao signInfoDao;

    public boolean addSignInfo(SignInfo signInfo) {
        return signInfoDao.addSignInfo(signInfo);
    }

    public SignInfo getSignInfo(Integer id) {
        SignInfo signInfo = signInfoDao.getSignInfo(id);
        SignInfo.syncDataForFront(signInfo);

        return signInfo;
    }

    public List<SignInfo> getSignInfos(Integer proId) {
        List<SignInfo> signInfos = signInfoDao.getSignInfos(proId);
        signInfos.forEach(SignInfo::syncDataForFront);

        return signInfos;
    }

}

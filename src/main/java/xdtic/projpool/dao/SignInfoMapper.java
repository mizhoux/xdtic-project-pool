package xdtic.projpool.dao;

import java.util.List;
import xdtic.projpool.model.SignInfo;

/**
 * Sign Info Mapper
 *
 * @author Michael Chow <mizhoux@gmail.com>
 */
public interface SignInfoMapper {

    int addSignInfo(SignInfo signInfo);

    SignInfo getSignInfo(Integer id);

    List<SignInfo> getSignInfoByProId(Integer proId);

    long containsSignInfo(Integer userId, Integer proId);
}

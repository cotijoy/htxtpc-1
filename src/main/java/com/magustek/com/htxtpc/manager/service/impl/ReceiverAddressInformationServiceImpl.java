package com.magustek.com.htxtpc.manager.service.impl;

import com.magustek.com.htxtpc.manager.bean.ReceiverAddressInformationVO;
import com.magustek.com.htxtpc.manager.dao.ReceiverAddressInformationDAO;
import com.magustek.com.htxtpc.manager.dao.UserDao;
import com.magustek.com.htxtpc.manager.service.ReceiverAddressInformationService;
import com.magustek.com.htxtpc.user.bean.ReceiverAddressInformation;
import com.magustek.com.htxtpc.user.bean.User;
import com.magustek.com.htxtpc.util.common.util.StringUtil;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service("ReceiverAddressInformationServiceImpl")
public class ReceiverAddressInformationServiceImpl implements ReceiverAddressInformationService {
    private ReceiverAddressInformationDAO receiverAddressInformationDAO;
    private UserDao userDao;

    public ReceiverAddressInformationServiceImpl(ReceiverAddressInformationDAO receiverAddressInformationDAO, UserDao userDao) {
        this.receiverAddressInformationDAO = receiverAddressInformationDAO;
        this.userDao = userDao;
    }

    /**
     * 修改、新增或设置默认收货地址
     */
    @Transactional
    @Override
    public ReceiverAddressInformation addOrUpdateReceiverAddressInformation(ReceiverAddressInformation receiverAddressInformation, String username) {
        String userCode = userDao.findUserByUsername(username).getUserCode();
        receiverAddressInformation.setUserCode(userCode);
        receiverAddressInformationDAO.cleanReceiverAddressInformationDefaultFlagBy2("",username);
        //receiverAddressInformationDAO.cleanReceiverAddressInformationDefaultFlagBy2("","Jamie");
        return receiverAddressInformationDAO.save(receiverAddressInformation);
    }

    /**
     * 删除指定的收货地址
     */
    @Override
    public void deleteReceiverAddressInformation(ReceiverAddressInformation receiverAddressInformation) {
        receiverAddressInformationDAO.delete(receiverAddressInformation);
    }

    /**
     * 查询收货地址
     */
    @Override
    public Page<ReceiverAddressInformation> searchReceiverAddressInformationBysearchingContent(ReceiverAddressInformationVO receiverAddressInformationVO, String username) {
        //默认搜索框为空
        if( StringUtil.isEmpty(receiverAddressInformationVO.getSearchStr()) ) {
            return receiverAddressInformationDAO.findAllByCreator(username, receiverAddressInformationVO.getPageRequest());
        } else {
            String searchingContent = receiverAddressInformationVO.getSearchStr();
            String receiverAddress = searchingContent;
            String receiver = searchingContent;
            return receiverAddressInformationDAO.selectReceiverAddressInformations
                    (receiverAddress, receiver, username, receiverAddressInformationVO.getPageRequest());
        }
    }


}

package com.magustek.com.htxtpc.manager.service;

import com.magustek.com.htxtpc.manager.bean.ReceiverAddressInformationVO;
import com.magustek.com.htxtpc.user.bean.ReceiverAddressInformation;
import org.springframework.data.domain.Page;

import java.util.List;

public interface ReceiverAddressInformationService {
    ReceiverAddressInformation addOrUpdateReceiverAddressInformation (ReceiverAddressInformation receiverAddressInformation, String username);
    void deleteReceiverAddressInformation (ReceiverAddressInformation receiverAddressInformation);
    Page<ReceiverAddressInformation> searchReceiverAddressInformationBysearchingContent (ReceiverAddressInformationVO receiverAddressInformationVO, String username);
}

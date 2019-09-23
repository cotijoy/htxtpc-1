package com.magustek.com.htxtpc.manager.dao;

import com.magustek.com.htxtpc.user.bean.ReceiverAddressInformation;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.Map;

public interface ReceiverAddressInformationDAO extends CrudRepository<ReceiverAddressInformation, Long> {

    /**
     ** 清空将当前用户的收件地址的默认标识
     */
    @Modifying
    @Query("update ReceiverAddressInformation r SET r.defaultFlag = ?1  where r.creator = ?2")
    int cleanReceiverAddressInformationDefaultFlagBy2(String defaultFlag, String username);

    /**
     ** 模糊查询收件地址信息
     */
    @Query(value = "select r.userCode, r.nationTest, r.nation, r.provinceText, r.province, " +
        "r.cityText, r.city, r.areaText, r.area, r.townText, r.town, r.receiverAddress, " +
        "r.receiver, r.cellphoneNum, r.defaultFlag from ReceiverAddressInformation as r " +
        "where ( r.receiverAddress like concat('%', :receiverAddress, '%') or r.receiver like concat('%', :receiver, '%') ) "  +
        "and r.creator = :username")
    Page<ReceiverAddressInformation> selectReceiverAddressInformations (@Param("receiverAddress")String receiverAddress, String receiver, String name, Pageable pageable);

    /**
     ** 查询当前用户的所有地址信息
     */
    Page<ReceiverAddressInformation> findAllByCreator(String username, Pageable pageable);


}

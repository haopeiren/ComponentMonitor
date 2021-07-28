package com.ffcs.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ffcs.constants.ComponentType;
import com.ffcs.mapper.ComponentDBInfoMapper;
import com.ffcs.mapper.MQMapper;
import com.ffcs.model.ComponentDBInfo;
import com.ffcs.model.MQInstance;
import com.ffcs.service.AbstractComponentSyncService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * MQService
 *
 * @author haopeiren
 * @since 2021/7/15
 */
@Service
@Slf4j
public class MQService extends AbstractComponentSyncService<MQMapper, MQInstance> {
    private static final String MQ_CLUSTER_TABLE = "mq_cluster";

    private static final String MQ_CLUSTER_COLUMNS = "id, tenant_id tenantId, user_id userId," +
            " prod_specid prodSpecId, out_userid outUserId, vpc_id vpcId, brokerClusterName, " +
            "namesrvClusterName, clusterInstanceName, ssh_info sshInfo, path_perfix pathPerfix, " +
            "version, zk_address zkAddress, zk_ssl zkSsl, order_id orderId, `state`, prop, " +
            "crt_time crtTime, mod_time modTime, bak_int_1 bakInt1, bak_int_2 bakInt2, " +
            "bak_varchar_1 bakVarchar1, bak_varchar_2 bakVarchar2, ipv6_flag ipv6Flag";

    @Autowired
    private MQMapper mqMapper;

    @Autowired
    private ComponentDBInfoMapper dbInfoMapper;

    public MQService() {
        super(MQ_CLUSTER_TABLE, MQ_CLUSTER_COLUMNS, MQInstance.class);
    }

    /**
     * 查询MQ实例列表
     *
     * @param page 查询条数
     * @param size    偏移量
     * @return  实例列表
     */
    public IPage<MQInstance> listMQInstance(Integer page, Integer size) {
        IPage<MQInstance> mqPage = new Page<>();
        mqPage.setCurrent(page);
        mqPage.setSize(size);
        mqPage = mqMapper.selectPage(mqPage, null);
        return mqPage;
    }

    /**
     * 同步MQ实例信息
     */
    public void syncMQInstance() {
        LambdaQueryWrapper<ComponentDBInfo> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(ComponentDBInfo::getComponentType, ComponentType.MQ);
        List<ComponentDBInfo> mqDBInfoList = dbInfoMapper.selectList(wrapper);
        syncComponentInstances(mqDBInfoList);
    }

    /**
     * 保存组件实例信息
     *
     * @param instanceList 实例列表
     */
    @Override
    public void saveInstance(List<MQInstance> instanceList) {
        saveBatch(instanceList);
//        mqMapper.saveMQInstance(instanceList);
    }

//    /**
//     * 同步MQ实例信息
//     */
//    @Transactional(rollbackFor = Exception.class)
//    @Override
//    public void syncComponentInstances(List<ComponentDBInfo> dbInfoList) {
//        initDataSource(dbInfoList);
//        List<MQInstance> instanceList = getMQInstanceList();
//        mqMapper.saveMQInstance(instanceList);
//    }

//    private void saveData1(List<MQInstance> instanceList) {
//        List<MQInstance> dbInstanceList = mqMapper.queryMQInstanceList(null, null);
//        List<MQInstance> remainInstanceList = dbInstanceList
//                .stream()
//                .filter(item -> item.getDelete().equals(0))
//                .collect(Collectors.toList());
//        // 新增的实例
//        List<MQInstance> newInstanceList = instanceList
//                .stream()
//                .filter(item -> !remainInstanceList.contains(item))
//                .collect(Collectors.toList());
//        if (!CollectionUtils.isEmpty(newInstanceList)) {
//            List<Integer> newInstanceIdList = newInstanceList
//                    .stream()
//                    .map(MQInstance::getId)
//                    .collect(Collectors.toList());
//            log.info("there are {} instance to be added, {}", newInstanceList.size(), newInstanceIdList);
//            mqMapper.saveMQInstance(newInstanceList);
//        }
//
//        // 删除的实例
//        List<MQInstance> deleteInstanceList = remainInstanceList
//                .stream()
//                .filter(item -> !instanceList.contains(item))
//                .collect(Collectors.toList());
//        if (!CollectionUtils.isEmpty(deleteInstanceList)) {
//            List<Integer> deleteInstanceIdList = deleteInstanceList
//                    .stream()
//                    .map(MQInstance::getId)
//                    .collect(Collectors.toList());
//            log.info("there are {} instance to be deleted, {}", deleteInstanceList.size(), deleteInstanceIdList);
//            mqMapper.deleteMQInstance(deleteInstanceIdList);
//        }
        // TODO 需要更新状态的实例
//        List<MQInstance> updateInstanceList = new ArrayList<>();
//        List<MQInstance> bothList = instanceList
//                .stream()
//                .filter(remainInstanceList::contains)
//                .collect(Collectors.toList());
//        Map<Integer, MQInstance> instanceMap = instanceList
//                .stream()
//                .collect(Collectors.toMap(MQInstance::getId, Function.identity()));
//        bothList.forEach(item -> {
//            Integer id = item.getId();
//            if (item.getState().equals(instanceMap.get(id).getState())) {
//                updateInstanceList.add(item);
//            }
//        });
//    }

}

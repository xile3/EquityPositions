package com.example.equity.positions.service.impl;

import com.alibaba.fastjson.JSON;
import com.example.equity.positions.common.CommonException;
import com.example.equity.positions.controller.dto.EquityPositionsDTO;
import com.example.equity.positions.domain.entity.SharesTradeRecords;
import com.example.equity.positions.domain.mapper.PositionsOperationMapper;
import com.example.equity.positions.domain.repository.PositionsOperationRepository;
import com.example.equity.positions.service.PositionsOperationService;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

/**
 * @author pyg
 */
@Service
@Slf4j
public class PositionsOperationServiceImpl implements PositionsOperationService {

  @Autowired
  private PositionsOperationRepository positionsOperationRepository;
  @Autowired
  private PositionsOperationMapper positionsOperationMapper;
  /**
   * 创建记录
   */
  @Override
  public EquityPositionsDTO insert(EquityPositionsDTO dto) {

    SharesTradeRecords entity = new SharesTradeRecords();
    createBean(dto,entity);
    SharesTradeRecords resultEntity = positionsOperationMapper.selectByCondition(dto.getSecurityCode());
    log.info("认购操作，查询现有记录版本,入参:{},{},返回结果:{}",dto.getSecurityCode(),dto.getOperationType(), JSON.toJSONString(resultEntity));

    String operationType = "Sell";
    //有记录
    if (resultEntity!=null) {

      if (operationType.equals(dto.getOperationType())) {
        if(resultEntity.getQuantity()>=dto.getQuantity()) {
          //get max tradeId of records,tradeId ++
          entity.setTradeId(resultEntity.getMaxTradeId()!=null?resultEntity.getMaxTradeId()+1:resultEntity.getTradeId()+1);
          entity.setVersion(resultEntity.getVersion());
          entity.setActionType("INSERT");
        }else {
          throw new CommonException("持有股数小于卖出的股数!");
        }

      }else {
        entity.setTradeId(resultEntity.getTradeId());
        entity.setActionType("UPDATE");
        //get max version of records,version ++
        entity.setVersion(resultEntity.getMaxVersion()!=null?resultEntity.getMaxVersion()+1:resultEntity.getVersion()+1);
      }
    }else {
      if (operationType.equals(dto.getOperationType())) {
        throw new CommonException("您还没有持有，请先做购买操作，不能做卖出操作!");
      }
      // 初始，无记录情况，直接插入记录
      entity.setTradeId(1L);
      entity.setVersion(1L);
      entity.setActionType("INSERT");
    }
    log.info("认购操作，插入记录，入参:{}",JSON.toJSONString(entity));
    SharesTradeRecords records  = positionsOperationRepository.save(entity);
    BeanUtils.copyProperties(records,dto);
    log.info("认购操作结束，返回结果:{}",JSON.toJSONString(dto));
    return dto;
  }

  /**
   * 更新记录 做插入记录操作
   */
  @Override
  public EquityPositionsDTO update(EquityPositionsDTO dto) {
    String actionType = "CANCEL";
    SharesTradeRecords entity;
    log.info("取消认购操作，入参:{}",JSON.toJSONString(dto));
    if (actionType.equals(dto.getActionType())) {
    Optional<SharesTradeRecords> opt =  positionsOperationRepository.findById(dto.getTransactionId());
      log.info("取消认购操作，查询对应认购记录，返回结果:{}",JSON.toJSONString(opt));
      entity =opt.get();
      if (!StringUtils.isEmpty(entity.getSecurityCode())) {
        entity.setActionType(actionType);
        log.info("取消认购操作，更新对应认购记录，入参:{}",JSON.toJSONString(entity));
        positionsOperationRepository.save(entity);
      }else {
        throw new CommonException("数据异常!");
      }
    }else {
      throw new CommonException("数据异常!");
    }
    BeanUtils.copyProperties(entity,dto);
    log.info("取消认购操作结束，返回值:{}",JSON.toJSONString(dto));
    return dto;
  }

  /**
   * 按securityCode分组统计
   */
  @Override
  public List<EquityPositionsDTO> getStatisticsByGroup() {
    List<EquityPositionsDTO> listDto = new ArrayList<>();
    List<SharesTradeRecords> list = positionsOperationMapper.getStatisticsByGroup();
    if (!CollectionUtils.isEmpty(list)) {
      EquityPositionsDTO dto;
      for (SharesTradeRecords records :list) {
        dto = new EquityPositionsDTO();
        dto.setSecurityCode(records.getSecurityCode());
        dto.setSumQuantity(records.getSumQuantity());
        listDto.add(dto);
      }
    }
    return listDto;
  }

  private void createBean(EquityPositionsDTO dto, SharesTradeRecords entity) {
    if (Objects.nonNull(dto)) {
      entity.setQuantity(dto.getQuantity());
      entity.setSecurityCode(dto.getSecurityCode());
      entity.setOperationType(dto.getOperationType());
      entity.setCreateTime(new Date());
    }
  }
}

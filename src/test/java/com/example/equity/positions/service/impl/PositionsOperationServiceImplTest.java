package com.example.equity.positions.service.impl;

import static org.junit.jupiter.api.Assertions.*;

import com.alibaba.fastjson.JSON;
import com.example.equity.positions.controller.dto.EquityPositionsDTO;
import com.example.equity.positions.service.PositionsOperationService;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.util.Assert;

@SpringBootTest
@Slf4j
class PositionsOperationServiceImplTest {

  @Autowired
  private PositionsOperationService positionsOperationService;
  @Test
  void insert() {
    EquityPositionsDTO equityPositionsDTO = new EquityPositionsDTO();
    equityPositionsDTO.setOperationType("Sell");
    equityPositionsDTO.setSecurityCode("ITC");
    equityPositionsDTO.setQuantity(40L);
    EquityPositionsDTO dto = positionsOperationService.insert(equityPositionsDTO);
    log.info("认购操作返回结果:{}", JSON.toJSONString(dto));

  }

  @Test
  void update() {
    EquityPositionsDTO equityPositionsDTO = new EquityPositionsDTO();
    equityPositionsDTO.setTransactionId(3L);
    equityPositionsDTO.setActionType("CANCEL");
    EquityPositionsDTO dto = positionsOperationService.update(equityPositionsDTO);
    log.info("取消认购操作返回结果:{}", JSON.toJSONString(dto));
  }

  @Test
  void getRecords() {
   List<EquityPositionsDTO> list = positionsOperationService.getStatisticsByGroup();
    log.info("查询认购统计，返回结果:{}", JSON.toJSONString(list));
  }
}
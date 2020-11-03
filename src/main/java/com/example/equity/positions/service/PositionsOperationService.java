package com.example.equity.positions.service;

import com.example.equity.positions.controller.dto.EquityPositionsDTO;
import java.util.List;

/**
 * @author pyg
 * 股权份额服务
 */
public interface PositionsOperationService {

  /**
   * 创建/更新记录
   * 做插入记录操作
   * @param dto
   * @return
   */
  EquityPositionsDTO insert(EquityPositionsDTO dto);

  /**
   * 更新记录
   * 做插入记录操作
   * @param dto
   * @return
   */
  EquityPositionsDTO update(EquityPositionsDTO dto);

  /**
   * 按securityCode分组统计
   * @return
   */
  List<EquityPositionsDTO> getStatisticsByGroup();

}

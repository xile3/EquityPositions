package com.example.equity.positions.controller.dto;

import lombok.Data;

/**
 * @author pyg
 */
@Data
public class EquityPositionsDTO {

  /**
   * Security Identifier
   */
  private String securityCode;
  /**
   * shares number
   */
  private Long quantity;
  /**
   * Buy/Sell
   */
  private String operationType;
  /**
   * 取消交易 CANCEL
   */
  private String actionType;
  /**
   * 取消操作必填
   */
  private Long transactionId;
  private Long sumQuantity;

}

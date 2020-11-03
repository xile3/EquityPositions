package com.example.equity.positions.domain.entity;

import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Index;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.persistence.UniqueConstraint;
import lombok.Data;


/**
 * @author pyg
 */
@Entity
@Table(name = "SHARES_TRADE_RECORDS")
@Data
public class SharesTradeRecords {

  /**
   * primary Key
   */
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long transactionId;
  @Column(length = 10)
  private Long tradeId;
  @Column(length = 10)
  private Long version;
  @Column(length = 60)
  private String securityCode;
  @Column(length = 10)
  private Long quantity;
  /**
   * Insert/Update/Cancel
   */
  @Column(length = 50)
  private String actionType;

  /**
   * Buy/Sell
   */
  @Column(length = 50)
  private String operationType;

  private Date createTime;
  private Date updateTime;
  @Column(length = 50)
  private String createBy;
  @Column(length = 50)
  private String updateBy;

  @Transient
  private Long maxTradeId;
  @Transient
  private Long maxVersion;

  @Transient
  private Long sumQuantity;
}

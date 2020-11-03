package com.example.equity.positions.domain.mapper;

import com.example.equity.positions.domain.entity.SharesTradeRecords;
import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

/**
 * @author pyg
 */
@Component
@Mapper
public interface PositionsOperationMapper {

  /**
   * 创建/更新/取消记录
   * 做插入记录操作,根据actionType做相应操作
   * @param equityPositions
   * @return
   */
  SharesTradeRecords insert(SharesTradeRecords equityPositions);

  /**
   * 判断该维度下是否有记录,返回结果>0则有记录`
   * @param securityCode
   * @return
   */
  SharesTradeRecords selectByCondition(@Param("securityCode") String securityCode);

  /**
   * 按securityCode分组统计
   * @return
   */
  List<SharesTradeRecords> getStatisticsByGroup();

}

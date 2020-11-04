package com.example.equity.positions.controller;

import com.alibaba.fastjson.JSON;
import com.example.equity.positions.controller.dto.EquityPositionsDTO;
import com.example.equity.positions.service.PositionsOperationService;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author pyg
 */
@RestController("positionsOperationController")
@RequestMapping("/equ/positions")
@Slf4j
public class PositionsOperationController {

  private final PositionsOperationService positionsOperationService;

  public PositionsOperationController(
      PositionsOperationService positionsOperationService) {
    this.positionsOperationService = positionsOperationService;
  }

  /**
   * 做认购操作INSERT/UPDATE
   * @param equityPositionsDTO
   * @return
   */
  @PostMapping("/save")
  public ResponseEntity<EquityPositionsDTO> save(@RequestBody EquityPositionsDTO equityPositionsDTO) {
    //TODO 用户鉴权
    log.info("equity positions insert data:{}", JSON.toJSONString(equityPositionsDTO));
    return ResponseEntity.ok(this.positionsOperationService.insert(equityPositionsDTO));
  }

  /**
   * 根据记录Id，做取消操作 CANCEL
   * @param equityPositionsDTO
   * @return
   */
  @PostMapping("/update")
  public ResponseEntity<EquityPositionsDTO> update(@RequestBody EquityPositionsDTO equityPositionsDTO) {
    //TODO 用户鉴权
    log.info("equity positions insert data:{}", JSON.toJSONString(equityPositionsDTO));
    return ResponseEntity.ok(this.positionsOperationService.update(equityPositionsDTO));
  }

  /**
   * 查询每个securityCode对应的交易总数，这里省去了分页。
   * @return
   */
  @PostMapping
  public ResponseEntity<List<EquityPositionsDTO>> list() {
    //TODO 用户鉴权
    log.info("equity positions get records");
    return ResponseEntity.ok(this.positionsOperationService.getStatisticsByGroup());
  }

}

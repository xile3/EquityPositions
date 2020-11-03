package com.example.equity.positions.controller;


import com.alibaba.fastjson.JSON;
import com.example.equity.EquityApplication;
import com.example.equity.positions.common.CommonException;
import com.example.equity.positions.controller.dto.EquityPositionsDTO;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;



@SpringBootTest(classes={EquityApplication.class})
@AutoConfigureMockMvc
@Slf4j
class PositionsOperationControllerTest {
  @Autowired
  private MockMvc mockMvc;
  private MockHttpSession session;


  @BeforeEach
  public void setupMockMvc() {
    session = new MockHttpSession();

  }

  @Test
  void save() throws Exception {

    EquityPositionsDTO equityPositionsDTO = new EquityPositionsDTO();
    equityPositionsDTO.setOperationType("Sell");
    equityPositionsDTO.setSecurityCode("INF");
    equityPositionsDTO.setQuantity(20L);

    MvcResult mvcResult  = mockMvc.perform(MockMvcRequestBuilders.post("/equ/positions/save")
            .accept(MediaType.APPLICATION_JSON)
            .contentType(MediaType.APPLICATION_JSON)
            .session(session)
            .content(JSON.toJSONString(equityPositionsDTO)))
        .andExpect(MockMvcResultMatchers.status().isOk())
        .andDo(MockMvcResultHandlers.print())
        .andReturn();

    //得到返回代码
    int status = mvcResult.getResponse().getStatus();
    //得到返回结果
    String content = mvcResult.getResponse().getContentAsString();
    log.info("股票购买操作,返回结果,状态码:{},返回内容:{}",status,content);

  }

  @Test
  void update() throws Exception {
    EquityPositionsDTO equityPositionsDTO = new EquityPositionsDTO();
    equityPositionsDTO.setTransactionId(6L);
    equityPositionsDTO.setActionType("CANCEL");
    MvcResult mvcResult  = mockMvc.perform(MockMvcRequestBuilders.post("/equ/positions/update")
        .accept(MediaType.APPLICATION_JSON)
        .contentType(MediaType.APPLICATION_JSON)
        .session(session)
        .content(JSON.toJSONString(equityPositionsDTO)))
        .andExpect(MockMvcResultMatchers.status().isOk())
        .andDo(MockMvcResultHandlers.print())
        .andReturn();

    //得到返回代码
    int status = mvcResult.getResponse().getStatus();
    //得到返回结果
    String content = mvcResult.getResponse().getContentAsString();
    log.info("股票购买取消操作,返回结果,状态码:{},返回内容:{}",status,content);
  }

  @Test
  void getRecords() throws Exception{
    MvcResult mvcResult  = mockMvc.perform(MockMvcRequestBuilders.post("/equ/positions")
        .accept(MediaType.APPLICATION_JSON)
        .contentType(MediaType.APPLICATION_JSON)
        .session(session)
        )
        .andExpect(MockMvcResultMatchers.status().isOk())
        .andDo(MockMvcResultHandlers.print())
        .andReturn();

    //得到返回代码
    int status = mvcResult.getResponse().getStatus();
    //得到返回结果
    String content = mvcResult.getResponse().getContentAsString();
    log.info("股票查询统计,返回结果,状态码:{},返回内容:{}",status,content);

  }
}
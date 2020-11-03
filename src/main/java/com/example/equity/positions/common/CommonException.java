package com.example.equity.positions.common;

/**
 * @author pyg
 */
public class CommonException extends RuntimeException {
  private static final long serialVersionUID = 1L;
  private final transient Object[] parameters;
  private String msg;
  private String code;

  public CommonException( String code, Object... parameters) {
    super(code);
    this.parameters = parameters;
    this.code = code;
  }

  public CommonException(String code, Throwable cause, Object... parameters) {
    super(code, cause);
    this.parameters = parameters;
    this.code = code;
  }

  public CommonException(String code, Throwable cause) {
    super(code, cause);
    this.code = code;
    this.parameters = new Object[0];
  }

  public Object[] getParameters() {
    return this.parameters;
  }

  public String getCode() {
    return this.code;
  }

}

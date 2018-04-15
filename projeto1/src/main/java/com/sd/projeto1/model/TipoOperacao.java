package com.sd.projeto1.model;


public enum TipoOperacao {
    
    CREATE(1),  
    UPDATE(2),
    DELETE(3),
    SEARCH(4);
    
  private final int value;    

  private TipoOperacao(int value) {
    this.value = value;
  }

  public int getValue() {
    return value;
  }
}

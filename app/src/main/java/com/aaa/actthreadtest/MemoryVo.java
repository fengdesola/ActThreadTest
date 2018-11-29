package com.aaa.actthreadtest;

public class MemoryVo {
    private byte[][] bytes = new byte[1024*10][1024*10];
    
    public MemoryVo(){
        for(int i =0; i<bytes.length; i++){
            for(int j = 0; j < bytes.length; j++)
                bytes[i][j] = 1;
        }
    }
}

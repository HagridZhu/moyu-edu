package com.moyulab.cn.exam.common;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashMap;
import java.util.Map;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Result<T> {

    private int code;
    private String message;
    private T data;

    public Result(int code, String message){
        this.code = code;
        this.message = message;
    }

    public static Result error(String message){
        return new Result(500, message);
    }

    public static <T> Result success(T data){
        return new Result<T>(200, "success", data);
    }

    public static Result<Map<String,Object>> success(String field, Object value){
        Map<String,Object> map = new HashMap<>();
        map.put(field, value);
        return Result.success(map);
    }

    public static Builder newBuilder(){
        return new Builder();
    }


    public static class Builder{

        private Map<String,Object> map ;

        public Builder(){
            this.map = new HashMap<>();
        }

        public Builder put(String field, Object value){
            this.map.put(field, value);
            return this;
        }

        public Result<Map<String,Object>> build(){
            return Result.success(this.map);
        }

    }

}

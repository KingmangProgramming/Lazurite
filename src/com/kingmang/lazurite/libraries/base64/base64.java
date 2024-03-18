package com.kingmang.lazurite.libraries.base64;

import com.kingmang.lazurite.core.Arguments;
import com.kingmang.lazurite.utils.ValueUtils;
import com.kingmang.lazurite.libraries.Library;
import com.kingmang.lazurite.runtime.Lzr.LzrArray;
import com.kingmang.lazurite.runtime.Lzr.LzrMap;
import com.kingmang.lazurite.runtime.Value;
import com.kingmang.lazurite.runtime.Variables;

import java.nio.charset.StandardCharsets;
import java.sql.Types;
import java.util.Base64;


public class base64 implements Library {
    private static final int TYPE = 8;
    @Override
    public void init() {
        LzrMap base = new LzrMap(2);
        base.set("encode",this::encode);
        base.set("decode", this::decode);
        Variables.define("base64", base);
    }
    private Value encode(Value... args){
        Arguments.checkOrOr(1,2,args.length);
        return LzrArray.of(enc(args).encode(input(args)));

    }

    private Base64.Encoder enc(Value[] args){
        if(args.length == 2 && args[1].asInt() == TYPE){
            return Base64.getUrlEncoder();
        }
        return Base64.getEncoder();
    }
    private byte[] input(Value[] args){
        byte[] input;
        if(args[0].type() == Types.ARRAY){
            input = ValueUtils.toByteArray((LzrArray) args[0]);
        }else{
            try{
                input = args[0].asString().getBytes(StandardCharsets.UTF_8);
            }catch(Exception e){
                input = args[0].asString().getBytes();
            }
        }
        return input;
    }

    private Value decode(Value[] args) {
        Arguments.checkOrOr(1, 2, args.length);
        final Base64.Decoder decoder = getDecoder(args);
        final byte[] result;
        if (args[0].type() == Types.ARRAY) {
            result = decoder.decode(ValueUtils.toByteArray((LzrArray) args[0]));
        } else {
            result = decoder.decode(args[0].asString());
        }
        return LzrArray.of(result);
    }



    private Base64.Decoder getDecoder(Value[] args) {
        if (args.length == 2 && args[1].asInt() == 8) {
            return Base64.getUrlDecoder();
        }
        return Base64.getDecoder();
    }
}

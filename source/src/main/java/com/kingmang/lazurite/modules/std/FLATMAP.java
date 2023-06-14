package com.kingmang.lazurite.modules.std;

import com.kingmang.lazurite.lib._TExeprion;
import com.kingmang.lazurite.lib.Arguments;
import com.kingmang.lazurite.runtime.ArrayValue;
import com.kingmang.lazurite.lib.Function;
import com.kingmang.lazurite.lib.Types;
import com.kingmang.lazurite.runtime.Value;
import com.kingmang.lazurite.lib.ValueUtils;
import java.util.ArrayList;
import java.util.List;

public final class FLATMAP implements Function {

    @Override
    public Value execute(Value... args) {
        Arguments.check(2, args.length);
        if (args[0].type() != Types.ARRAY) {
            throw new _TExeprion("Array expected in first argument");
        }
        final Function mapper = ValueUtils.consumeFunction(args[1], 1);
        return flatMapArray((ArrayValue) args[0], mapper);
    }
    
    private Value flatMapArray(ArrayValue array, Function mapper) {
        final List<Value> values = new ArrayList<>();
        final int size = array.size();
        for (int i = 0; i < size; i++) {
            final Value inner = mapper.execute(array.get(i));
            if (inner.type() != Types.ARRAY) {
                throw new _TExeprion("Array expected " + inner);
            }
            for (Value value : (ArrayValue) inner) {
                values.add(value);
            }
        }
        return new ArrayValue(values);
    }
}

package com.kingmang.lazurite.libraries.gforms;


import com.kingmang.lazurite.core.*;
import com.kingmang.lazurite.runtime.LZR.LZRFunction;
import com.kingmang.lazurite.runtime.LZR.LZRNumber;
import com.kingmang.lazurite.runtime.Value;

import javax.swing.JTextField;
import java.awt.*;

import static com.kingmang.lazurite.utils.ValueUtils.consumeFunction;

public class JTextFieldValue extends JTextComponentValue {

    private final JTextField textField;

    public JTextFieldValue(JTextField textField) {
        super(10, textField);
        this.textField = textField;
        init();
    }

    private void init() {
        set("onAction", new LZRFunction(this::addActionListener));
        set("setFont", new LZRFunction(this::setTextSize));
        set("addActionListener", new LZRFunction(this::addActionListener));
        set("getColumns", Converters.voidToInt(textField::getColumns));
        set("getHorizontalAlignment", Converters.voidToInt(textField::getHorizontalAlignment));
        set("getScrollOffset", Converters.voidToInt(textField::getScrollOffset));
        set("postActionEvent", Converters.voidToVoid(textField::postActionEvent));
        set("setActionCommand", Converters.stringToVoid(textField::setActionCommand));
        set("setColumns", Converters.intToVoid(textField::setColumns));
        set("setHorizontalAlignment", Converters.intToVoid(textField::setHorizontalAlignment));
        set("setScrollOffset", Converters.intToVoid(textField::setScrollOffset));
    }

    private Value addActionListener(Value... args) {
        Arguments.check(1, args.length);
        Function action = consumeFunction(args[0], 1);
        textField.addActionListener(e -> action.execute());
        return LZRNumber.ZERO;
    }
    private Value setTextSize(Value... args) {
        textField.setFont(new Font(args[0].asString(), args[1].asInt(), args[2].asInt()));
        return LZRNumber.ZERO;
    }

}
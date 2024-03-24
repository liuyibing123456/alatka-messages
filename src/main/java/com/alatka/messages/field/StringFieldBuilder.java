package com.alatka.messages.field;

import com.alatka.messages.context.FieldDefinition;
import com.alatka.messages.context.MessageDefinition;
import com.alatka.messages.util.BytesUtil;

/**
 * {@link String}类型报文域解析器
 *
 * @author ybliu
 * @see AbstractFieldBuilder
 * @see FieldBuilder
 */
public class StringFieldBuilder extends AbstractFieldBuilder<String> {

    @Override
    protected byte[] fromObjectToAscii(String value, FieldDefinition fieldDefinition) {
        return value.getBytes(messageDefinition.getCharset());
    }

    @Override
    protected byte[] fromObjectToBinary(String value, FieldDefinition fieldDefinition) {
        return BytesUtil.toBytes(value);
    }

    @Override
    protected String toObjectWithAscii(byte[] bytes, FieldDefinition fieldDefinition) {
        String result = new String(bytes, messageDefinition.getCharset()).trim();
        return result.isEmpty() ? null : result;
    }

    @Override
    protected String toObjectWithBinary(byte[] bytes, FieldDefinition fieldDefinition) {
        return BytesUtil.toString(bytes);
    }

    @Override
    public int getOrder() {
        return super.getOrder();
    }

    @Override
    public boolean matched(MessageDefinition messageDefinition, FieldDefinition definition) {
        return definition.getClazz() == String.class;
    }
}

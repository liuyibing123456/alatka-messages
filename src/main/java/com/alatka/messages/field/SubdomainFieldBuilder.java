package com.alatka.messages.field;

import com.alatka.messages.context.FieldDefinition;
import com.alatka.messages.context.IsoFieldDefinition;
import com.alatka.messages.context.MessageDefinition;

import java.util.Map;

/**
 * 子域类型报文域解析器
 *
 * @author ybliu
 * @see AbstractFieldBuilder
 * @see FieldBuilder
 */
public abstract class SubdomainFieldBuilder<T> extends AbstractFieldBuilder<T> {

    /**
     * 子域打包
     *
     * @param value
     * @param fieldDefinition {@link FieldDefinition}
     * @param usageMap        子域 usage Map
     * @return
     */
    protected abstract byte[] pack(T value, FieldDefinition fieldDefinition, Map<String, MessageDefinition> usageMap);

    /**
     * 子域解包
     *
     * @param bytes
     * @param fieldDefinition {@link FieldDefinition}
     * @param usageMap        子域 usage Map
     * @return
     */
    protected abstract T unpack(byte[] bytes, FieldDefinition fieldDefinition, Map<String, MessageDefinition> usageMap);

    @Override
    protected byte[] fromObjectToNone(T value, FieldDefinition fieldDefinition) {
        return this.pack(value, fieldDefinition, fieldDefinition.getMessageDefinitionMap());
    }

    @Override
    protected T toObjectWithNone(byte[] bytes, FieldDefinition fieldDefinition) {
        Map<String, MessageDefinition> map = fieldDefinition.getMessageDefinitionMap();
        if (map.isEmpty()) {
            throw new RuntimeException(fieldDefinition + "未配置子域");
        }
        return this.unpack(bytes, fieldDefinition, map);
    }

    @Override
    protected byte[] fromObjectToBcd(T value, FieldDefinition fieldDefinition) {
        return this.fromObjectToNone(value, fieldDefinition);
    }

    @Override
    protected T toObjectWithBcd(byte[] bytes, FieldDefinition fieldDefinition) {
        return this.toObjectWithNone(bytes, fieldDefinition);
    }

    @Override
    protected byte[] fromObjectToEbcdic(T value, FieldDefinition fieldDefinition) {
        return this.fromObjectToNone(value, fieldDefinition);
    }

    @Override
    protected T toObjectWithEbcdic(byte[] bytes, FieldDefinition fieldDefinition) {
        return this.toObjectWithNone(bytes, fieldDefinition);
    }

    @Override
    public int getOrder() {
        return super.getOrder() + 100;
    }

    @Override
    public boolean matched(MessageDefinition messageDefinition, FieldDefinition definition) {
        return definition.getExistSubdomain();
    }

    protected boolean validate(FieldDefinition fieldDefinition, String usageId) {
        Map<String, MessageDefinition> map = fieldDefinition.getMessageDefinitionMap();
        if (map.containsKey(usageId)) {
            return true;
        }
        if (((IsoFieldDefinition) fieldDefinition).getNonSubdomainException()) {
            throw new RuntimeException(fieldDefinition + "未配置子域usage: " + usageId);
        }
        return false;
    }
}

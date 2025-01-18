package com.alatka.messages.core.field;

import com.alatka.messages.core.context.FieldDefinition;
import com.alatka.messages.core.context.MessageDefinition;

import java.util.List;
import java.util.Map;

/**
 * 子域bitmap报文域解析器
 *
 * @author ybliu
 * @see AbstractFieldBuilder
 * @see FieldBuilder
 */
public class SubdomainBitmapFieldBuilder extends AbstractBitmapFieldBuilder {

    @Override
    public boolean matched(MessageDefinition messageDefinition, FieldDefinition definition) {
        return MessageDefinition.Type.iso == messageDefinition.getType()
                && MessageDefinition.Kind.subPayload == messageDefinition.getKind()
                && definition.getDomainNo() == 0;
    }

    @Override
    protected int calculateLength(Object instance, List<FieldDefinition> list, FieldDefinition fieldDefinition) {
        return fieldDefinition.getLength();
    }

}

package com.alatka.messages.message;

import com.alatka.messages.context.FieldDefinition;
import com.alatka.messages.context.MessageDefinition;
import com.alatka.messages.field.BitmapFieldBuilder;
import com.alatka.messages.holder.MessageHolderUtil;

import java.util.Map;

/**
 * 8583报文打包/解包器
 *
 * @author ybliu
 */
class IsoMessageBuilder extends MessageBuilder {

    private ThreadLocal<Map<Integer, Boolean>> bitmap = new ThreadLocal<>();

    protected IsoMessageBuilder() {
    }

    @Override
    protected boolean filter(FieldDefinition fieldDefinition) {
        return MessageDefinition.Kind.payload == definition.getKind() && fieldDefinition.getDomainNo() > 1 ?
                this.bitmap.get().get(fieldDefinition.getDomainNo()) : true;
    }

    @Override
    protected void postProcess(FieldDefinition fieldDefinition, Object instance, Object value) {
        if (MessageDefinition.Kind.payload == definition.getKind() && fieldDefinition.getDomainNo() == 1) {
            if (value instanceof Map) {
                this.bitmap.set(MessageHolderUtil.getByDomainNo(definition, instance, 1));
            } else if (value instanceof byte[]) {
                this.bitmap.set(BitmapFieldBuilder.generate((byte[]) value));
            } else {
                throw new IllegalArgumentException("error type: " + value.getClass());
            }
        }
    }

    @Override
    protected void postProcess() {
        if (MessageDefinition.Kind.payload == definition.getKind()) {
            this.bitmap.remove();
        }
    }
}
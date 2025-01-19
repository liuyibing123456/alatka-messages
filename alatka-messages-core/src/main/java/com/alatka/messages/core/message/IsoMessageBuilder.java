package com.alatka.messages.core.message;

import com.alatka.messages.core.context.FieldDefinition;
import com.alatka.messages.core.context.MessageDefinition;
import com.alatka.messages.core.holder.Bitmap;
import com.alatka.messages.core.holder.MessageHolderUtil;

/**
 * 8583 payload报文打包/解包器
 *
 * @author ybliu
 */
public class IsoMessageBuilder extends MessageBuilder {

    private final ThreadLocal<Bitmap> bitmap = new ThreadLocal<>();

    public IsoMessageBuilder(MessageDefinition definition) {
        super.definition = definition;
    }

    @Override
    protected boolean filter(FieldDefinition fieldDefinition) {
        return fieldDefinition.getDomainNo() <= 1 || this.bitmap.get().exist(fieldDefinition.getDomainNo());
    }

    @Override
    protected void postProcess(FieldDefinition fieldDefinition, Object instance, Object value, boolean packed) {
        if (fieldDefinition.getDomainNo() == 1) {
            if (packed) {
                this.bitmap.set(new Bitmap((byte[]) value));
            } else {
                this.bitmap.set(MessageHolderUtil.getByDomainNo(definition, instance, 1));
            }
        }
    }

    @Override
    protected void postProcess() {
        this.bitmap.remove();
    }
}

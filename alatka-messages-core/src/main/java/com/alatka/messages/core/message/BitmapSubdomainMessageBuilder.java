package com.alatka.messages.core.message;

import com.alatka.messages.core.context.FieldDefinition;
import com.alatka.messages.core.context.MessageDefinition;
import com.alatka.messages.core.holder.Bitmap;
import com.alatka.messages.core.holder.MessageHolderUtil;

/**
 * bitmap类型子域报文打包/解包器
 *
 * @author ybliu
 */
public class BitmapSubdomainMessageBuilder extends MessageBuilder {

    private final ThreadLocal<Bitmap> bitmap = new ThreadLocal<>();

    public BitmapSubdomainMessageBuilder(MessageDefinition definition) {
        super.definition = definition;
    }

    @Override
    protected boolean filter(FieldDefinition fieldDefinition) {
        return this.bitmap.get() == null || this.bitmap.get().exist(fieldDefinition.getDomainNo());
    }

    @Override
    protected void postProcess(FieldDefinition fieldDefinition, Object instance, Object value, boolean packed) {
        if (fieldDefinition.getClassType() == Bitmap.class) {
            if (packed) {
                this.bitmap.set(new Bitmap((byte[]) value));
            } else {
                this.bitmap.set(MessageHolderUtil.getByName(instance, fieldDefinition.getName()));
            }
        }
    }

    @Override
    protected void postProcess() {
        this.bitmap.remove();
    }
}

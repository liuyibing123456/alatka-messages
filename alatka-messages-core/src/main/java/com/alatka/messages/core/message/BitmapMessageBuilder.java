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
public class BitmapMessageBuilder extends MessageBuilder {

    private static final String BITMAP_NAME = "bitmap";

    private Bitmap bitmap;

    public BitmapMessageBuilder(MessageDefinition definition) {
        super.definition = definition;
    }

    @Override
    protected boolean filter(FieldDefinition fieldDefinition) {
        return fieldDefinition.getDomainNo() < 1 || this.bitmap.exist(fieldDefinition.getDomainNo());
    }

    @Override
    protected void postProcess(FieldDefinition fieldDefinition, Object instance, Object value, boolean packed) {
        if (fieldDefinition.getName().equals(BITMAP_NAME)) {
            if (packed) {
                this.bitmap = new Bitmap((byte[]) value, 0);
            } else {
                this.bitmap = MessageHolderUtil.getByName(instance, BITMAP_NAME);
            }
        }
    }

}

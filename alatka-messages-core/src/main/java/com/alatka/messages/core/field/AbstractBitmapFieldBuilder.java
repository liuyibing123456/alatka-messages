package com.alatka.messages.core.field;

import com.alatka.messages.core.context.FieldDefinition;
import com.alatka.messages.core.context.MessageDefinitionContext;
import com.alatka.messages.core.holder.Bitmap;
import com.alatka.messages.core.holder.MessageHolderAware;
import com.alatka.messages.core.holder.MessageHolderUtil;

import java.util.List;

/**
 * 抽象bitmap报文域解析器
 *
 * @author ybliu
 * @see AbstractFieldBuilder
 * @see FieldBuilder
 */
public abstract class AbstractBitmapFieldBuilder extends AbstractFieldBuilder<Bitmap> implements MessageHolderAware {

    private final ThreadLocal<Object> messageHolder = new ThreadLocal<>();

    @Override
    protected boolean returnIfNull() {
        return false;
    }

    @Override
    protected Bitmap toObjectWithBinary(byte[] bytes, FieldDefinition fieldDefinition) {
        String offset = fieldDefinition.getPattern();
        return new Bitmap(bytes, offset == null ? 0 : Integer.parseInt(offset));
    }

    @Override
    protected byte[] fromObjectToBinary(Bitmap value, FieldDefinition fieldDefinition) {
        if (value != null) {
            return value.getBytes();
        }

        try {
            List<FieldDefinition> list = MessageDefinitionContext.getInstance().fieldDefinitions(messageDefinition);
            Object instance = messageHolder.get();

            int length = this.calculateLength(instance, list, fieldDefinition);

            int offset = fieldDefinition.getPattern() == null ? 0 : Integer.parseInt(fieldDefinition.getPattern());

            byte[] bytes = new byte[length];
            list.stream()
                    .filter(definition -> definition.getDomainNo() - offset > 0)
                    .filter(definition -> definition.getDomainNo() - offset <= length * 8)
                    .filter(definition -> MessageHolderUtil.getByName(instance, definition.getName()) != null)
                    .map(FieldDefinition::getDomainNo)
                    .forEach(domainNo -> {
                        Integer index = domainNo - offset - 1;
                        bytes[index / 8] |= 0x80 >> (index % 8);
                    });

            this.postBitmap(bytes);

            return bytes;
        } finally {
            messageHolder.remove();
        }

    }

    @Override
    public int getOrder() {
        return super.getOrder() + 60;
    }

    @Override
    public void setMessageHolder(Object instance) {
        this.messageHolder.set(instance);
    }

    protected abstract int calculateLength(Object instance, List<FieldDefinition> list, FieldDefinition fieldDefinition);

    protected void postBitmap(byte[] bytes) {
    }

}

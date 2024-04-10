package com.alatka.messages.domain;

import com.alatka.messages.context.FieldDefinition;
import com.alatka.messages.context.MessageDefinition;
import com.alatka.messages.holder.MessageHolderAware;
import com.alatka.messages.holder.MessageHolderUtil;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 分页域解析器
 *
 * @author ybliu
 * @see AbstractDomainParsed
 * @see DomainParsed
 */
public class PageDomainParsed extends AbstractDomainParsed implements MessageHolderAware {

    private ThreadLocal<Object> messageHolder = new ThreadLocal<>();

    @Override
    public int getOrder() {
        return 10;
    }

    @Override
    public boolean matched(MessageDefinition messageDefinition, FieldDefinition fieldDefinition) {
        return !fieldDefinition.getFixed() && List.class.isAssignableFrom(fieldDefinition.getClazz());
    }

    @Override
    public byte[] pack(byte[] bytes, FieldDefinition fieldDefinition) {
        return bytes;
    }

    @Override
    public byte[] unpack(byte[] bytes, FieldDefinition fieldDefinition, AtomicInteger counter) {
        try {
            int elementLength = fieldDefinition.getLength();
            int pageSize = MessageHolderUtil.getByName(messageHolder.get(), fieldDefinition.getPageSizeName());
            return Arrays.copyOfRange(bytes, counter.get(), counter.addAndGet(elementLength * pageSize));
        } finally {
            messageHolder.remove();
        }
    }

    @Override
    public void setMessageHolder(Object instance) {
        this.messageHolder.set(instance);
    }
}

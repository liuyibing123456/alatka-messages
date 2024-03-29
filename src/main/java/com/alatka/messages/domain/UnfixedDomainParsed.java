package com.alatka.messages.domain;

import com.alatka.messages.context.FieldDefinition;
import com.alatka.messages.context.MessageDefinition;

import java.util.Arrays;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 不定长域解析器<br>
 * e.g.F122.2域
 *
 * @author ybliu
 * @see AbstractDomainParsed
 * @see DomainParsed
 */
public class UnfixedDomainParsed extends AbstractDomainParsed {

    @Override
    public int getOrder() {
        return 30;
    }

    @Override
    public boolean matched(MessageDefinition messageDefinition, FieldDefinition fieldDefinition) {
        return !fieldDefinition.getFixed()
                && MessageDefinition.Type.iso == messageDefinition.getType()
                && fieldDefinition.getLength() == -1;
    }

    @Override
    public byte[] pack(byte[] bytes, FieldDefinition fieldDefinition) {
        return bytes;
    }

    @Override
    public byte[] unpack(byte[] bytes, FieldDefinition fieldDefinition, AtomicInteger counter) {
        int length = bytes.length - counter.get();
        return Arrays.copyOfRange(bytes, counter.get(), counter.addAndGet(length));
    }
}
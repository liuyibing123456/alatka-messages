package com.alatka.messages.message;

import com.alatka.messages.context.FieldDefinition;
import com.alatka.messages.context.MessageDefinition;
import com.alatka.messages.domain.DomainParsed;
import com.alatka.messages.domain.DomainParsedFactory;
import com.alatka.messages.field.FieldBuilder;
import com.alatka.messages.field.FieldBuilderFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author ybliu
 */
public class IsoUnfixedSubDomainMessageBuilder extends MessageBuilder {

    public IsoUnfixedSubDomainMessageBuilder(MessageDefinition definition) {
        super.definition = definition;
    }

    private List<byte[]> list = new ArrayList<>();

    @Override
    protected <T> Wrapper doPack(T instance, MessageDefinition definition, FieldDefinition fieldDefinition) {
        try {
            // 对象解析为字节数组
            FieldBuilder fieldBuilder = FieldBuilderFactory.getInstance(instance, definition, fieldDefinition);
            byte[] bytes = fieldBuilder.serialize(instance, fieldDefinition);
            if (bytes.length == 0) {
                return new Wrapper(bytes, fieldDefinition);
            }

            DomainParsed domainParsed = DomainParsedFactory.getInstance(instance, definition, fieldDefinition);
            byte[] packed = domainParsed.pack(bytes, fieldDefinition);
            return new Wrapper(packed, fieldDefinition);
        } catch (Exception e) {
            throw new RuntimeException(definition + " -> " + fieldDefinition + "解析报错", e);
        }
    }

    @Override
    protected <T> MessageBuilder.Wrapper doUnpack(T instance, MessageDefinition definition,
                                                  FieldDefinition fieldDefinition, byte[] bytes, AtomicInteger counter) {
        return counter.get() >= bytes.length ? new Wrapper(null, fieldDefinition) :
                super.doUnpack(instance, definition, fieldDefinition, bytes, counter);
    }
}


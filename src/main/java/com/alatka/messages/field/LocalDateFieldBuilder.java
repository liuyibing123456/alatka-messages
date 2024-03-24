package com.alatka.messages.field;

import com.alatka.messages.context.FieldDefinition;
import com.alatka.messages.context.MessageDefinition;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

/**
 * {@link java.time.LocalDate}类型报文域解析器
 *
 * @author ybliu
 * @see AbstractFieldBuilder
 * @see TimeFieldBuilder
 * @see FieldBuilder
 */
public class LocalDateFieldBuilder extends TimeFieldBuilder<LocalDate> {

    @Override
    protected LocalDate parse(String datetime, DateTimeFormatter formatter) {
        return LocalDate.parse(datetime, formatter);
    }

    @Override
    public int getOrder() {
        return super.getOrder() + 80;
    }

    @Override
    public boolean matched(MessageDefinition messageDefinition, FieldDefinition definition) {
        return definition.getClazz() == LocalDate.class;
    }
}

package com.alatka.messages.core.domain;

import com.alatka.messages.core.context.FieldDefinition;
import com.alatka.messages.core.context.IsoFieldDefinition;
import com.alatka.messages.core.context.MessageDefinition;

/**
 * LV（length-value）域解析器<br>
 * L(ASCII)
 *
 * @author ybliu
 * @see AbstractDomainParsed
 * @see DomainParsed
 */
public class AsciiLVDomainParsed extends LVDomainParsed {

    @Override
    public int getOrder() {
        return 20;
    }

    @Override
    public boolean matched(MessageDefinition messageDefinition, FieldDefinition fieldDefinition) {
        return super.matched(messageDefinition, fieldDefinition)
                && super.buildLenParseType(fieldDefinition) == FieldDefinition.ParseType.ASCII ;
    }

    @Override
    protected byte[] intToBytes(int length, IsoFieldDefinition fieldDefinition) {
        return String.format("%0" + fieldDefinition.getLength() + "d", length).getBytes();
    }

    @Override
    protected int bytesToInt(byte[] lengthBytes, IsoFieldDefinition fieldDefinition) {
        return Integer.parseInt(new String(lengthBytes));
    }
}

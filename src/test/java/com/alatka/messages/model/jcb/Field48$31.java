package com.alatka.messages.model.jcb;

import com.alatka.messages.annotation.IsoFieldMeta;
import com.alatka.messages.annotation.MessageMeta;

import static com.alatka.messages.context.FieldDefinition.ParseType.BCD;
import static com.alatka.messages.context.MessageDefinition.Kind.subPayload;
import static com.alatka.messages.context.MessageDefinition.Type.iso;

@MessageMeta(type = iso, group = "jcb", code = "common", kind = subPayload, domain = "F48", usage = "31", remark = "jcb 48域usage=31")
public class Field48$31 implements Field48 {

    @IsoFieldMeta(domainNo = 1, fixed = true, length = 8, remark = "Transaction ID", parseType = BCD)
    private String f48f1;

    public String getF48f1() {
        return f48f1;
    }

    public void setF48f1(String f48f1) {
        this.f48f1 = f48f1;
    }
}

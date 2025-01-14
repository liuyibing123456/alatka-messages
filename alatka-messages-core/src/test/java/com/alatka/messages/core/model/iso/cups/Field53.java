package com.alatka.messages.core.model.iso.cups;

import com.alatka.messages.core.annotation.IsoFieldMeta;
import com.alatka.messages.core.annotation.MessageMeta;

import static com.alatka.messages.core.context.MessageDefinition.Kind.subPayload;
import static com.alatka.messages.core.context.MessageDefinition.Type.iso;

@MessageMeta(type = iso, group = "cups", code = "common", kind = subPayload, domain = "F53", remark = "银联8583 53域")
public class Field53 {

    @IsoFieldMeta(domainNo = 1, fixed = true, length = 1, remark = "重置密钥的类型/PIN格式")
    private String keyType;
    @IsoFieldMeta(domainNo = 2, fixed = true, length = 1, remark = "加密算法标志")
    private String encryptionMethodUsed;
    @IsoFieldMeta(domainNo = 3, fixed = true, length = 14, remark = "保留使用")
    private String reserved;

    public String getKeyType() {
        return keyType;
    }

    public void setKeyType(String keyType) {
        this.keyType = keyType;
    }

    public String getEncryptionMethodUsed() {
        return encryptionMethodUsed;
    }

    public void setEncryptionMethodUsed(String encryptionMethodUsed) {
        this.encryptionMethodUsed = encryptionMethodUsed;
    }

    public String getReserved() {
        return reserved;
    }

    public void setReserved(String reserved) {
        this.reserved = reserved;
    }
}

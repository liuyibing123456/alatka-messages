package com.alatka.messages.context;

import com.alatka.messages.domain.TLV2DomainParsed;
import com.alatka.messages.domain.TLVDomainParsed;
import com.alatka.messages.domain.TVDomainParsed;

/**
 * 8583报文域定义类
 *
 * @author ybliu
 */
public class IsoFieldDefinition extends FieldDefinition {

    /**
     * {@link TVDomainParsed}
     * {@link TLVDomainParsed}
     * {@link TLV2DomainParsed}
     */
    private String aliasName;
    /**
     * 最大长度<br>
     * {@link IsoFieldDefinition#getFixed()} = true：{@link IsoFieldDefinition#getLength()} = {@link IsoFieldDefinition#maxLength}<br>
     * {@link IsoFieldDefinition#getFixed()} = false：数据最大长度
     */
    private Integer maxLength;
    /**
     * 未配置子域异常
     */
    private Boolean nonSubdomainException = Boolean.TRUE;

    public String getAliasName() {
        return aliasName;
    }

    public void setAliasName(String aliasName) {
        this.aliasName = aliasName;
    }

    public Integer getMaxLength() {
        return maxLength;
    }

    public void setMaxLength(Integer maxLength) {
        this.maxLength = maxLength;
    }

    public Boolean getNonSubdomainException() {
        return nonSubdomainException;
    }

    public void setNonSubdomainException(Boolean nonSubdomainException) {
        this.nonSubdomainException = nonSubdomainException;
    }

    @Override
    public String toString() {
        return "{" + String.join(":",
                "F" + getDomainNo(),
                getName(),
                getFixed() ? getLength().toString() : getLength() + "~" + getMaxLength(),
                getRemark())
                + "}";
    }

}

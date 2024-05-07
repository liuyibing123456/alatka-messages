package com.alatka.messages.definition;

import com.alatka.messages.context.MessageDefinition;
import com.alatka.messages.holder.MessageHolder;
import com.alatka.messages.support.Constant;
import com.alatka.messages.util.ClassUtil;
import com.alatka.messages.util.XmlUtil;

import java.nio.file.Path;
import java.util.*;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * xml文件报文定义解析器
 *
 * @author ybliu
 * @see FileMessageDefinitionBuilder
 * @see AbstractMessageDefinitionBuilder
 */
public abstract class XmlMessageDefinitionBuilder extends FileMessageDefinitionBuilder {

    public XmlMessageDefinitionBuilder(String classpath) {
        super(classpath);
    }

    @Override
    protected List<Map<String, Object>> doBuildFieldDefinitions(MessageDefinition definition, Path source) {
        Map<String, Object> xml = XmlUtil.getMap(source.toFile(), Object.class);
        Map<String, Object> message = this.getValueWithMap(xml, "message");
        MessageDefinition.Kind kind = definition.getKind();

        Map<String, Object> result;
        if (kind == MessageDefinition.Kind.subPayload) {
            Object value = this.getValueWithMap(message, kind.name());
            result = value instanceof Map ? (Map<String, Object>) value :
                    ((List<Map<String, Object>>) value).stream()
                            .filter(map -> Objects.equals(map.get("domain"), definition.getDomain())
                                    && Objects.equals(Optional.ofNullable(map.get("usage")).orElse(""), definition.getUsage()))
                            .findFirst()
                            .orElseThrow(() -> new RuntimeException("未匹配子域"));
        } else {
            result = this.getValueWithMap(message, kind.name());
        }

        Object value = this.getValueWithMap(result, "field");
        return (List<Map<String, Object>>) (value instanceof Map ? Collections.singletonList(value) : value);
    }

    @Override
    protected List<MessageDefinition> buildMessageDefinitions(Path source) {
        Map<String, Object> xml = XmlUtil.getMap(source.toFile(), Object.class);
        Map<String, Object> message = this.getValueWithMap(xml, "message");

        AtomicReference<MessageDefinition> header = new AtomicReference<>();

        return Arrays.stream(MessageDefinition.Kind.values())
                .filter(kind -> message.containsKey(kind.name()))
                .flatMap(kind -> {
                    if (kind == MessageDefinition.Kind.subPayload) {
                        Object value = this.getValueWithMap(message, kind.name());
                        List<Map<String, Object>> list =
                                (List<Map<String, Object>>) (value instanceof Map ? Collections.singletonList(value) : value);
                        return list.stream().map(subPayload -> buildMessageDefinition(kind, subPayload, message));
                    }
                    return Stream.of(buildMessageDefinition(kind, null, message));
                }).peek(definition -> {
                    if (definition.getKind() == MessageDefinition.Kind.header) {
                        header.set(definition);
                    }
                    if (definition.getKind() == MessageDefinition.Kind.payload ||
                            definition.getKind() == MessageDefinition.Kind.request ||
                            definition.getKind() == MessageDefinition.Kind.response) {
                        definition.setHeader(header.get());
                    }
                }).collect(Collectors.toList());
    }

    private MessageDefinition buildMessageDefinition(MessageDefinition.Kind kind,
                                                     Map<String, Object> subPayload,
                                                     Map<String, Object> message) {
        MessageDefinition definition = new MessageDefinition();
        definition.setType(MessageDefinition.Type.valueOf(message.get("type").toString()));
        definition.setGroup(message.get("group").toString());
        definition.setCode(message.get("code").toString());
        definition.setKind(kind);
        definition.setCharset(message.get("charset") == null ? Constant.DEFAULT_CHARSET : message.get("charset").toString());
        definition.setRemark(message.get("remark").toString());
        definition.setHolder(message.get("clazz") == null ? MessageHolder.class : ClassUtil.forName(message.get("clazz").toString()));

        if (subPayload != null) {
            definition.setDomain(subPayload.get("domain").toString());
            definition.setUsage(subPayload.get("usage") == null ? "" : subPayload.get("usage").toString());
            definition.setDomainType(subPayload.get("domainType") == null ? MessageDefinition.DomainType.NONE :
                    MessageDefinition.DomainType.valueOf(subPayload.get("domainType").toString()));
        } else {
            definition.setDomain("");
            definition.setUsage("");
            definition.setDomainType(MessageDefinition.DomainType.NONE);
        }

        return definition;
    }

    @Override
    protected String fileSuffix() {
        return ".xml";
    }
}

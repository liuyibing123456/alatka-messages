package com.alatka.messages.definition;

import com.alatka.messages.context.FieldDefinition;
import com.alatka.messages.context.FixedFieldDefinition;
import com.alatka.messages.context.MessageDefinition;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class FixedYamlMessageDefinitionBuilderTest {

    @Test
    @DisplayName("type()")
    void test01() {
        FixedYamlMessageDefinitionBuilder builder = new FixedYamlMessageDefinitionBuilder();
        Assertions.assertEquals(MessageDefinition.Type.fixed, builder.type());
    }

    @Test
    @DisplayName("fieldDefinitionClass()")
    void test02() {
        FixedYamlMessageDefinitionBuilder builder = new FixedYamlMessageDefinitionBuilder();
        Assertions.assertEquals(FixedFieldDefinition.class, builder.fieldDefinitionClass());
    }

    @Test
    @DisplayName("postBuildFieldDefinition()")
    void test03() {
        FixedYamlMessageDefinitionBuilder builder = new FixedYamlMessageDefinitionBuilder();

        MessageDefinition messageDefinition = null;
        FieldDefinition fieldDefinition = null;
        builder.postBuildFieldDefinition(messageDefinition, fieldDefinition);

        Assertions.assertNull(messageDefinition);
        Assertions.assertNull(fieldDefinition);
    }
}

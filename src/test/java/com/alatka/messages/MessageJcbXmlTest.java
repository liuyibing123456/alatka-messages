package com.alatka.messages;

import com.alatka.messages.definition.IsoXmlMessageDefinitionBuilder;
import com.alatka.messages.holder.MessageHolder;
import com.alatka.messages.message.MessageBuilder;
import com.alatka.messages.util.BytesUtil;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.Arrays;

public class MessageJcbXmlTest {

    @BeforeAll
    public static void beforeAll() {
        new IsoXmlMessageDefinitionBuilder().build();
    }

    private void doTest(String hex) {
        String key = "iso:jcb:common:payload";
        MessageHolder holder = MessageBuilder.init(key).unpack(hex);
        System.out.println(holder);
        byte[] pack = MessageBuilder.init(key).pack(holder);
        Assertions.assertEquals(BytesUtil.bytesToHex(pack), hex.toUpperCase());

        MessageHolder holder1 = MessageBuilder.init(key).unpack(pack);
        Assertions.assertEquals(holder1, holder);
    }

    @Test
    public void test01() {
        String hex = "F0F1F0F0767D448188E1A008103568560090000176000000000000000176000000000176122913412700000001000172134127122925041229639981000008885280000888518000F0F0F0F1F7F2F1F2F1F3F2F7F3F9F4F5F3F34040F3F2F5F6F2F2F0F340404040404040D1C3C240E3C5E2E340D4C5D9C3C8C1D5E3404040404040E3C5E2E340C3C9E3E84040404040D1D7D54AF0F2F4F71122334455667788990011223344556677889900000201094725454745445395202545900000000006404040404040F0F3F1F4F9F6F5F8F7F4404040F1F4404040F0F4F0F1F0F8F4F0F8F4F006F2F2F2F3F9F2";
        this.doTest(hex);
    }

    @Test
    public void test02() {
        String hex = "F0F1F0F0723C4481A8E180081035601234567890120000000000000261190923072729468214151228092398125399022004088802000008880200002503560123456789012198121010000033300000F9F4F0F9F2F3F4F6F8F2F1F47B94F2F386F891F4F1F1F7F5F1F8404040404040404040C1C3D8E4C9D9C5D940D5C1D4C540404040404040404040C3C9E3E840D5C1D4C54040404040E4E2C107F0F1F0F3F1F2F3F3F4F406F2F2F2F3F4F4";
        this.doTest(hex);
    }

    @Test
    public void test03() {
        String hex = "F0F1F0F0723C468188E0A2081035601234567890120000000000000100000121134935000003134935012102125399051000000008887010000888704000F0F0F0F0F0F0F0F2F6F9F9F3F4F1F1F0F0F0F0F7F4F0F0F1F0F1F2F3F4F5F6F7F24040C1C3D8E4C9D9C5D940D5C1D4C540404040404040404040C3C9E3E840D5C1D4C54040404040E4E2C1F8F2F6F8F5F8006C9F260844E22F5DF43476F29F2701809F100807290104A00000009F3704093DD9059F3602000B950500000080009A030201219C01009F02060000000100005F2A02082682025C009F1A0208269F03060000000000009F090200F04F07A00000006510108407A000000065101006F2F2F2F8F2F6";
        this.doTest(hex);
    }

    @Test
    public void test04() {
        String hex = "F0F1F0F0723844C1A8A0980810356012345678901201000000000002022002250345250088811245300225601102100004088812300008884560002503560123456789012105011011123498700000F0F0F7F0F5F9F4F8F0F1F2F9C1C1F0F0F3C2F9F2C1C3D8E4C9D9C5D940D5C1D4C540404040404040404040C3C9E3E840D5C1D4C54040404040E4E2C1F8F4F01223334444555556990101000056780006F0F0F2F7F6F4";
        this.doTest(hex);
    }

    @Test
    public void test05() {
        String hex = "F0F1F1F0723C00018E60820010356012345678901200000000000001000001211349350000031349350121021208887010000888704000F0F0F0F0F0F0F0F2F6F9F9F3F5F0F2F9F3F2F0F0F4F0F0F1F0F1F2F3F4F5F6F7F24040C1C3D8E4C9D9C5D940D5C1D4C540404040404040404040C3C9E3E840D5C1D4C54040404040E4E2C1F8F2F6000C910A0CF41B31653661173030";
        this.doTest(hex);
    }

    @Test
    public void test06() {
        String hex = "F0F1F2F0723C4481AEE180181035409999999999990000000000000261190923072729468214151228092398125399022004088802000008880200002503560123456789012198121010000033300000F9F4F0F9F2F3F4F6F8F2F1F4F9F8F7F6F5F4F0F07B94F2F386F891F4F1F1F7F5F1F8404040404040404040C1C3D8E4C9D9C5D940D5C1D4C540404040404040404040C3C9E3E840D5C1D4C54040404040E4E2C107F0F1F0F3F1F2F3F3F4F40AF0F1F0F1F1F0F2F0F1F206F2F2F2F3F4F4";
        this.doTest(hex);
    }

    @Test
    public void test07() {
        String hex = "F0F3F0F2822000018080000200000000080001021215131501108971088801000008880100007B94F2F386F891F408E2C3D7D7D7E2C5E704F6F3F3F2001EF1F3F5F4F0F5F9F9F9F9F9F9F9F1F1F2F0F0F4F2F0F0F0F1F2F1F1F1F1F10006F3F5F4F0F0F0";
        this.doTest(hex);
    }

    @Test
    public void test08() {
        String hex = "F0F3F0F2822000018080000200000000080001021215131501108971088801000008880100007B94F2F386F891F408E2C3D7D7D7E2C5E704F6F3F3F2001EF2F3F5F4F0F5F9F9F9F9F9F9F9F1F1F2F0F4F3F1F9F9F9F1F2F1F1F1F1F10006F3F5F4F0F0F0";
        this.doTest(hex);
    }

    @Test
    public void test09() {
        String hex = "F0F3F0F28220000180000002000000000800010202120908070345670888801000088880200008E2C3D7D7D7E2C5E704F6F3F3F20011F5F3F5F4F0F1F2F3F4F5F6F7F8F9F0F1F20006F9F8F7F6F5F4";
        this.doTest(hex);
    }

    @Test
    public void test10() {
        String hex = "F0F3F1F282200001820000020000000008000100021209080703456708888010000888802000F0F008E2C3D7D7D7E2C5E704F6F3F3F2001EF5F3F5F4F0F1F2F3F4F5F6F7F8F9F0F1F2F4F3F2F0F0F5F0F4F0F0F0F0F0";
        this.doTest(hex);
    }

    @Test
    public void test11() {
        String hex = "F0F4F2F0F23804818E60800000000042000000001035405999999999990000000000000261190923084813640003163401092302200408880200000888020000F9F4F0F9F2F3F6F4F0F0F0F3F2F0F9F8F7F6F1F7F1F1F7F5F1F8F1F1F7C1C3D8E4C9D9C5D940D5C1D4C540404040404040404040C3C9E3E840D5C1D4C54040404040E4E2C1F5F1F8F1F1F7F3F4F4010064000309230847330008802000000088020000F0F0F0F0F0F0F0F0F0F0F0F0F0F0F0F0F0F0F0F0F0F0F0F0F0F0F0F0F0F0F0F0F0F0F0F0F0F0F0F0F0F0";
        this.doTest(hex);
    }

    @Test
    public void test12() {
        String hex = "F0F6F2F00220000000010000092311312021869628F0F8F3F6822000008000000004000000100000000905211558218372088802000099990888502000";
        this.doTest(hex);
    }

    @Test
    public void test13() {
        String hex = "F0F8F0F0822000008000000004000000100000000905211558218372088802000000010888502000";
        this.doTest(hex);
    }

    @Test
    public void test14() {
        String hex = "F0F8F0F0822000008000000004000000100000000905211558218372088802000000020888502000";
        this.doTest(hex);
    }

    @Test
    public void test15() {
        String hex = "F0F8F0F0822000008000000004000000100000000905211558218372088802000003010888502000";
        this.doTest(hex);
    }

}

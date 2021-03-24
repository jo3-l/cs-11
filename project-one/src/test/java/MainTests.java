import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

public class MainTests {
    @ParameterizedTest
    @MethodSource("provideStringsForTestProblemOne")
    public void testProblemOne(String input, int expected) {
        assertEquals(expected, Main.problemOne(input));
    }

    private static Stream<Arguments> provideStringsForTestProblemOne() {
        return Stream.of(
                Arguments.of("ysqundfruogaxcruaqhieie", 10),
                Arguments.of("wurexc", 2),
                Arguments.of("wiaytixcaigoiaernsi", 10),
                Arguments.of("wdcyxcczoizfaieuoihi", 9),
                Arguments.of("vsiwmbuuoeuizfd", 7),
                Arguments.of("uzdexwiwy", 3),
                Arguments.of("urjuuodceroyaeau", 10),
                Arguments.of("toalhzemaepvidbufioleny", 10),
                Arguments.of("oxfjxup", 2),
                Arguments.of("oitolqwxdmpevzjexfmbise", 7),
                Arguments.of("nlotjiodzrnzbr", 3),
                Arguments.of("njwaaowewlisqmducwwwy", 6),
                Arguments.of("lzxyryxmllshpkt", 0),
                Arguments.of("lluaegmldkhemurk", 5),
                Arguments.of("jifwagsigiokeool", 8),
                Arguments.of("iueuaondjmhoiuxj", 9),
                Arguments.of("ifbzmfmcnubeuidyjltyf", 5),
                Arguments.of("foipguewboiahsem", 8),
                Arguments.of("byxqkwjrqagtfdeqe", 3),
                Arguments.of("aakreisoph", 5)
        );
    }

    @ParameterizedTest
    @MethodSource("provideStringsForTestProblemTwo")
    public void testProblemTwo(String input, int expected) {
        assertEquals(expected, Main.problemTwo(input));
    }

    private static Stream<Arguments> provideStringsForTestProblemTwo() {
        return Stream.of(
                Arguments.of("ybovtobboboobcboobbobbbob", 3),
                Arguments.of("xoboobobbobmmbobbb", 3),
                Arguments.of("wpoabobbobobobobbbobbboobbkbobobbooboboobobsbobob", 12),
                Arguments.of("obooobobobobolsyog", 3),
                Arguments.of("obooobbobobtobobv", 3),
                Arguments.of("oboboboobobofbnfbobbobbboobpcbobbobob", 8),
                Arguments.of("geibobobbobb", 3),
                Arguments.of("ebobbyobbbkbo", 1),
                Arguments.of("cbobobobjlboobriobf", 3),
                Arguments.of("cbobbdboobobn", 2),
                Arguments.of("boobwbobobobbobbdbobpbvbobobobooboobbobb", 9),
                Arguments.of("boobubovocobbobbojwox", 1),
                Arguments.of("bobobobobobobobobobobobobobob", 14),
                Arguments.of("bobbzobobooboboboobobobbobboboou", 8),
                Arguments.of("bobbobobolbooboboboyzbk", 5),
                Arguments.of("bicyfybobbkobrbobbbobobkbobbbobbbobbp", 7),
                Arguments.of("bbobbooboojboobobbobobobbeto", 5),
                Arguments.of("bbaboobobobbcboob", 2),
                Arguments.of("aurjaoxjax", 0),
                Arguments.of("aoboboboboobobbboboo", 5)
        );
    }

    @ParameterizedTest
    @MethodSource("provideStringsForTestProblemThree")
    public void testProblemThree(String input, String expected) {
        assertEquals(expected, Main.problemThree(input));
    }

    private static Stream<Arguments> provideStringsForTestProblemThree() {
        return Stream.of(
                Arguments.of("ixysuoizvwwebyyhp", "ixy"),
                Arguments.of("zyxwvutsrqponmlkjihgfedcba", "z"),
                Arguments.of("ztapvefsnx", "apv"),
                Arguments.of("zhmqbbea", "hmq"),
                Arguments.of("yqpnlilestyiwmmqsqpv", "esty"),
                Arguments.of("usfuitmwigom", "fu"),
                Arguments.of("unazbnhjzmjdihl", "hjz"),
                Arguments.of("tunivklq", "klq"),
                Arguments.of("smpmqblmgaanxmlcvhw", "aanx"),
                Arguments.of("sgsndcjsptchiid", "chii"),
                Arguments.of("pflvvhfqcmgjhgtrwmitab", "flvv"),
                Arguments.of("pagqqbqnufckbjtauacldroe", "agqq"),
                Arguments.of("opgrtupprfwbxsmwonswv", "grtu"),
                Arguments.of("nkdaqlfvkprute", "kpru"),
                Arguments.of("nhnwhowgjhhkj", "hnw"),
                Arguments.of("ncafxxrullo", "afxx"),
                Arguments.of("matnjjjh", "jjj"),
                Arguments.of("hrgtrpbwtxefzur", "efz"),
                Arguments.of("dmegjmydadjwsdhckjjrydwj", "egjmy"),
                Arguments.of("abcdefghijklmnopqrstuvwxyz", "abcdefghijklmnopqrstuvwxyz")
        );
    }
}

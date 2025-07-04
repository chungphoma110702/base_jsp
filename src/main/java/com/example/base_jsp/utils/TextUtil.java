package com.example.base_jsp.utils;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.apache.logging.log4j.util.Strings;

import java.text.Normalizer;
import java.util.List;
import java.util.Objects;
import java.util.regex.Pattern;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class TextUtil {
    private static final String BOLD_TEXT_FORMAT = "<b>%s</b>";

    public static String toBold(String str){
        if(!Objects.isNull(str)){
            return String.format(BOLD_TEXT_FORMAT, str);
        }
        return "";
    }

	/**
	 * @param input chuỗi cần kiểm tra
	 * @return true nếu chuỗi rỗng, null hoặc chứa toàn khoảng trắng
	 */
    public static boolean isBlank(String input) {
	    if (Strings.isBlank(input)) {
	        return true;
		}

		return input.trim().isEmpty();
    }

	public static boolean isNotBlank(String input) {
		return !isBlank(input);
	}

	/**
	 * @param str chuỗi cần chuẩn hóa
	 * @return chuỗi đã chuẩn hóa, loại bỏ dấu tiếng Việt, khoảng trắng thừa
	 */
    public static String normalize(String str) {
        str = str.trim();
        str = str.replaceAll(" +", " ");
        str = str.replace("à|á|ạ|ả|ã|â|ầ|ấ|ậ|ẩ|ẫ|ă|ằ|ắ|ặ|ẳ|ẵ", "a");
        str = str.replace("À|Á|Ạ|Ả|Ã|Â|Ầ|Ấ|Ậ|Ẩ|Ẫ|Ă|Ằ|Ắ|Ặ|Ẳ|Ẵ", "A");
        str = str.replace("è|é|ẹ|ẻ|ẽ|ê|ề|ế|ệ|ể|ễ", "e");
        str = str.replace("È|É|Ẹ|Ẻ|Ẽ|Ê|Ề|Ế|Ệ|Ể|Ễ", "E");
        str = str.replace("ì|í|ị|ỉ|ĩ", "i");
        str = str.replace("Ì|Í|Ị|Ỉ|Ĩ", "I");
        str = str.replace("ò|ó|ọ|ỏ|õ|ô|ồ|ố|ộ|ổ|ỗ|ơ|ờ|ớ|ợ|ở|ỡ", "o");
        str = str.replace("Ò|Ó|Ọ|Ỏ|Õ|Ô|Ồ|Ố|Ộ|Ổ|Ỗ|Ơ|Ờ|Ớ|Ợ|Ở|Ỡ", "O");
        str = str.replace("ù|ú|ụ|ủ|ũ|ư|ừ|ứ|ự|ử|ữ", "u");
        str = str.replace("Ù|Ú|Ụ|Ủ|Ũ|Ư|Ừ|Ứ|Ự|Ử|Ữ", "U");
        str = str.replace("ỳ|ý|ỵ|ỷ|ỹ", "y");
        str = str.replace("Ỳ|Ý|Ỵ|Ỷ|Ỹ", "Y");
        str = str.replace("đ", "d");
        str = str.replace("Đ", "D");
        String nfdNormalizedString = Normalizer.normalize(str, Normalizer.Form.NFD);
        Pattern pattern = Pattern.compile("\\p{InCombiningDiacriticalMarks}+");
        str = pattern.matcher(nfdNormalizedString).replaceAll("");
        return str;
    }

	/**
	 * @param query chuỗi tìm kiếm
	 * @return danh sách từ khóa tìm kiếm
	 */
	public static List<String> handleSearchText(String query) {
		return List.of(normalize(query).toLowerCase().trim().split(" "));
	}

	/**
	 * @param input chuỗi cần chuyển đổi
	 * @return chuỗi đã chuyển đổi sang dạng snake_case
	 */
	public static String toSnakeCase(String input) {
		if (isBlank(input)) {
			return "";
		}

		return input.replaceAll("([a-z])([A-Z]+)", "$1_$2").toLowerCase();
	}
}

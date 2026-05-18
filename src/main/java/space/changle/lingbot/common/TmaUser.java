package space.changle.lingbot.common;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

/**
 * @author 长乐
 * @version 1.0.0
 * @date 2026/5/14
 * @time 08:38
 * @description telegram用户信息
 */
@Data
public class TmaUser {

    @JsonProperty("id")
    private Long userId;

    @JsonProperty("first_name")
    private String firstName;

    @JsonProperty("last_name")
    private String lastName;

    @JsonProperty("username")
    private String tgUserName;

    @JsonProperty("photo_url")
    private String photoUrl;

    @JsonProperty("language_code")
    private String languageCode;

}

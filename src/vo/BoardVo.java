package vo;

import lombok.Data;

@Data
public class BoardVo {

    private int board_no;
    private String title;
    private String content;
    private String name;
    private String wri_date;
    private String board_code;
}

package dao;

import util.JDBCUtil;
import vo.BoardVo;
import vo.MemberVo;

import java.util.List;

public class BoardDao {

    private static BoardDao instance;

    private BoardDao() {

    }

    public static BoardDao getInstance() {
        if (instance == null) {
            instance = new BoardDao();
        }
        return instance;
    }
    JDBCUtil jdbc = JDBCUtil.getInstance();

    public MemberVo login(List<Object> param) {
        String sql = "SELECT *\n" +
                "FROM JAVA_MEMBER\n" +
                "WHERE MEM_ID = ?\n" +
                "AND MEM_PASS = ?";

        return jdbc.selectOne(sql, param, MemberVo.class);
    }

    public void freeBoardInsert(List<Object> param) {
        String sql = "INSERT INTO JAVA_BOARD VALUES ((SELECT NVL(MAX(BOARD_NO),0)+1\n" +
                "                                    FROM JAVA_BOARD),\n" +
                "                                    ?, ?, ?, SYSDATE, 'A')";
        jdbc.update(sql, param);
    }

    public List<BoardVo> boardList() {
        String sql = "SELECT * FROM JAVA_BOARD" +
                "     WHERE BOARD_CODE = 'A'";
        return jdbc.selectList(sql, BoardVo.class);
    }

    public BoardVo boardList(List<Object> param) {
        String sql = "SELECT * FROM JAVA_BOARD" +
                "     WHERE BOARD_NO = ?";
        return jdbc.selectOne(sql, param, BoardVo.class);
    }

    public void boardUpdate(List<Object> param) {
        String sql = "UPDATE JAVA_BOARD\n" +
                     "SET TITLE = ?,\n" +
                     "    CONTENT = ?,\n" +
                     "    WRI_DATE = SYSDATE\n" +
                     "WHERE BOARD_NO = ?";

        jdbc.update(sql, param);
    }

    public void boardDelete(List<Object> param) {
        String sql = " DELETE JAVA_BOARD " +
                "      WHERE BOARD_NO = ?";

        jdbc.update(sql, param);
    }

    public void qnaBoardInsert(List<Object> param) {
        String sql = "INSERT INTO JAVA_BOARD VALUES ((SELECT NVL(MAX(BOARD_NO),0)+1\n" +
                "                                    FROM JAVA_BOARD),\n" +
                "                                    ?, ?, ?, SYSDATE, 'B')";
        jdbc.update(sql, param);
    }

    public List<BoardVo> qnaBoardList() {
        String sql = "SELECT * FROM JAVA_BOARD" +
                "     WHERE BOARD_CODE = 'B'";
        return jdbc.selectList(sql, BoardVo.class);
    }
}

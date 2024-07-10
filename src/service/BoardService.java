package service;

import controller.MainController;
import dao.BoardDao;
import vo.BoardVo;
import vo.MemberVo;

import java.util.List;

public class BoardService {

    private static BoardService instance;

    private BoardService() {

    }

    public static BoardService getInstance() {
        if (instance == null) {
            instance = new BoardService();
        }
        return instance;
    }
    BoardDao dao = BoardDao.getInstance();


    public boolean login(List<Object> param) {
        MemberVo member = dao.login(param);
        if (member ==null) return false;
        MainController.sessionStorage.put("member", member);
        return true;
    }

    public void freeInsert(List<Object> param) {

        dao.freeBoardInsert(param);
    }

    public List<BoardVo> boardList() {
        return dao.boardList();
    }

    public BoardVo boardDetail(List<Object> param) {
        return dao.boardList(param);

    }

    public void boardUpdate(List<Object> param) {
        dao.boardUpdate(param);
    }

    public void boardDelete(List<Object> param) {
        dao.boardDelete(param);
    }

    public void qnaInsert(List<Object> param) {
        dao.qnaBoardInsert(param);
    }

    public List<BoardVo> qnaBoardList() {
        return dao.qnaBoardList();
    }
}

package controller;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import service.BoardService;
import util.ScanUtil;
import view.View;
import vo.BoardVo;
import vo.MemberVo;

import static view.View.*;

public class MainController{
	static public Map<String, Object> sessionStorage = new HashMap<>();


	BoardService boardService = BoardService.getInstance();

	public static void main(String[] args) {
		new MainController().start();
	}
	
	private void start() {
		View view = View.MAIN;
		while (true) {
			switch (view) {
				case MAIN:
					view = main();
					break;
				case LOGIN:
					view = login();
					break;
				case ADMIN:
					view = admin();
					break;
				case FREE_BOARD:
					view = freeBoardMain();
					break;
				case QNA_BOARD:
					view = qNABoardMain();
					break;
				case BOARD_INSERT:
					view = freeBoardInsert();
					break;
				case BOARD_LIST:
					view = boardList();
					break;
				case BOARD_DETAIL:
					view = boardDetail();
					break;
					case BOARD_UPDATE:
					view = boardUpdate();
					break;
				case BOARD_DELETE:
					view = boardDelete();
					break;
				case QNA_BOARD_INSERT:
					view = qnaBoardInsert();
					break;
				case QNA_BOARD_LIST:
					view = qnaBoardList();
					break;



				default:
					break;

			}
		}
	}

	private View qnaBoardList() {
		List<BoardVo> boardList = boardService.qnaBoardList();
		for (BoardVo boardVo : boardList) {
			System.out.println(boardVo);
		}
		System.out.println("1. 상세 게시판");
		System.out.println("2. 메인");
		int sel = ScanUtil.nextInt("입력 : ");
		switch (sel) {
			case 1: int boardNo = ScanUtil.nextInt("게시판 번호 : ");
				sessionStorage.put("boardNo", boardNo);
				return View.BOARD_DETAIL;
			case 2: return ADMIN;
			default: return MAIN;
		}
	}

	private View qnaBoardInsert() {
		if (sessionStorage.get("member") == null) {
			System.out.println("비로그인으로인한 잘못된 접근입니다.");
			System.out.println("로그인 창으로 이동합니다");
			return View.LOGIN;
		}
		MemberVo member = (MemberVo) sessionStorage.get("member");

		List<Object> param = new ArrayList<>();
		String name = member.getMem_name();
		//BOARD_NO
		String title = ScanUtil.nextLine("질문 제목 : ");
		String content = ScanUtil.nextLine("내용 : ");
		//NAME
		//WRI_DATE
		//BOARD_CODE

		param.add(title);
		param.add(content);
		param.add(name);
		boardService.qnaInsert(param);

		return View.QNA_BOARD_LIST;
	}

	private View boardDelete() {
		MemberVo member = (MemberVo) sessionStorage.get("member");
		String memName = member.getMem_name();
		System.out.println(memName);
		String name = (String) sessionStorage.get("name");
		System.out.println(name);
		if (sessionStorage.get("member") == null) {
			System.out.println("비로그인으로인한 잘못된 접근입니다.");
			System.out.println("로그인 창으로 이동합니다");
			return View.LOGIN;
		}
		if (!name.equals(memName)) {
			System.out.println("작성한 게시자가 아닙니다 상세 게시판으로 돌아갑니다.");
			return BOARD_DETAIL;
		}

		int no = (int) sessionStorage.get("boardNo");
		List<Object> param = new ArrayList<>();
		param.add(no);
		boardService.boardDelete(param);
		return BOARD_LIST;
	}

	private View boardUpdate() {
		MemberVo member = (MemberVo) sessionStorage.get("member");
		String memName = member.getMem_name();
		System.out.println(memName);
		String name = (String) sessionStorage.get("name");
		System.out.println(name);
		if (sessionStorage.get("member") == null) {
			System.out.println("비로그인으로인한 잘못된 접근입니다.");
			System.out.println("로그인 창으로 이동합니다");
			sessionStorage.put("View", BOARD_UPDATE);
			return View.LOGIN;
		}
		System.out.println("1. 전체 수정");
		System.out.println("2. 게시판 리스트");
		int sel = ScanUtil.menu();
		if (sel == 1) {
			if (!name.equals(memName)) {
				System.out.println("작성한 게시자가 아닙니다 상세 게시판으로 돌아갑니다.");
				return BOARD_DETAIL;
			}

			int no = (int) sessionStorage.get("boardNo");
			List<Object> param = new ArrayList<>();
			String title = ScanUtil.nextLine("제목 : ");
			String content = ScanUtil.nextLine("내용 : ");

			param.add(title);
			param.add(content);
			param.add(no);
			boardService.boardUpdate(param);
		}
		return BOARD_DETAIL;
	}

	private View boardDetail() {
		int boardNo = (int) sessionStorage.get("boardNo");
		List<Object> param = new ArrayList<>();
		param.add(boardNo);

		BoardVo bookVo = boardService.boardDetail(param);
		String name = bookVo.getName();
		sessionStorage.put("name", name);

		System.out.println(bookVo);
		System.out.println();
		System.out.println("1. 게시판 수정");
		System.out.println("2. 게시판 삭제");
		System.out.println("3. 자유 게시판 리스트");
		System.out.println("4. 질문 게시판 리스트");
		int sel = ScanUtil.menu();
		switch (sel) {
			case 1:
				return View.BOARD_UPDATE;
			case 2:
				return View.BOARD_DELETE;
			case 3:
				return View.FREE_BOARD;
			case 4:
				return View.QNA_BOARD;
			default:
				return BOARD_DETAIL;
		}
	}

	private View boardList() {
		List<BoardVo> boardList = boardService.boardList();
		for (BoardVo boardVo : boardList) {
			System.out.println(boardVo);
		}
		System.out.println("1. 상세 게시판");
		System.out.println("2. 메인");
		int sel = ScanUtil.nextInt("입력 : ");
		switch (sel) {
			case 1: int boardNo = ScanUtil.nextInt("게시판 번호 : ");
				sessionStorage.put("boardNo", boardNo);
				return View.BOARD_DETAIL;
			case 2: return ADMIN;
			default: return MAIN;
		}
	}

	private View freeBoardInsert() {

		if (sessionStorage.get("member") == null) {
			System.out.println("비로그인으로인한 잘못된 접근입니다.");
			System.out.println("로그인 창으로 이동합니다");
			return View.LOGIN;
		}
		MemberVo member = (MemberVo) sessionStorage.get("member");

		List<Object> param = new ArrayList<>();
		String name = member.getMem_name();
		//BOARD_NO
		String title = ScanUtil.nextLine("제목 : ");
		String content = ScanUtil.nextLine("내용 : ");
		//NAME
		//WRI_DATE
		//BOARD_CODE

		param.add(title);
		param.add(content);
		param.add(name);
		boardService.freeInsert(param);

		return View.BOARD_LIST;
	}

	private View qNABoardMain() {
		System.out.println();
		System.out.println("질문 게시판입니다");
		System.out.println("1. 질문 게시판 등록");
		System.out.println("2. 질문 게시판 리스트 출력");

		int input = ScanUtil.nextInt("숫자 입력 : ");
		switch (input) {
			case 1: return View.QNA_BOARD_INSERT;
			case 2: return View.QNA_BOARD_LIST;
			default: break;

		}
		return View.QNA_BOARD_LIST;
	}

	private View freeBoardMain() {
		System.out.println();
		System.out.println("자유 게시판입니다");
		System.out.println("1. 게시판 등록");
		System.out.println("2. 게시판 리스트 출력");

		int input = ScanUtil.nextInt("숫자 입력 : ");
		switch (input) {
			case 1: return View.BOARD_INSERT;
			case 2: return View.BOARD_LIST;
		}
		return View.FREE_BOARD;
	}


	private View admin() {
		System.out.println("1. 자유 게시판");
		System.out.println("2. Q&A 게시판");
		int sel = ScanUtil.menu();
		switch (sel) {
			case 1:
				return View.FREE_BOARD;
			case 2:
				return QNA_BOARD;
			default:
				return View.ADMIN;
		}
	}

	private View login() {
		String id = ScanUtil.nextLine("ID : ");
		String pw = ScanUtil.nextLine("PW : ");
		List<Object> param = new ArrayList<>();
		param.add(id);
		sessionStorage.put("id", id);
		param.add(pw);
		boolean login = boardService.login(param);
		if (!login) {
			System.out.println("1. 재로그인");
			System.out.println("2. 회원가입");
			System.out.println("3. 홈");
			return View.LOGIN;
		}
		return View.ADMIN;
	}

	public View main() {
		System.out.println("1. 자유 게시판");
		System.out.println("2. Q&A 게시판");
		System.out.println("3. 로그인");

		int sel = ScanUtil.nextInt("메뉴 선택: ");
		switch (sel) {
			case 1:
				return FREE_BOARD;
			case 2:
				return QNA_BOARD;
			case 3:
				return LOGIN;
			default:
				return View.MAIN;

//				View view =null;
////			if (sessionStorage.containsKey("view")) {
//				view = (View) sessionStorage.get("view");
//				return view;
			}
		}
	}

package com.konkuk.controller;

import com.konkuk.UI;
import com.konkuk.dto.Member;
import com.konkuk.service.MemberService;

public class MemberController extends Controller {
    public enum Menu {FIND, ADD}
    protected Menu currentMenu;

    public MemberController(Menu menu) {
        this.currentMenu = menu;
    }

    public Controller start() {
        if (currentMenu == Menu.FIND) {
            find();
        } else if (currentMenu == Menu.ADD) {
            add();
        }
        // 수정 필요
        return null;
    }

    private void find() {
    }

    private void add() {
        // Member 생성
        Member member = new Member();
        // member.id ....

        // 모든 service, repository 는 임시로 new 만들어쓰기
        boolean result = new MemberService().add(member);
        // 성공과 실패의 처리
    }
}


//관리자페이지 로그인
function login() {
	if (document.adminLogin.userId.value == "") {
		alert("아이디를 입력해주세요");
		document.adminLogin.userId.focus();
	} else if (document.adminLogin.userPw.value == "") {
		alert("비밀번호를 입력해주세요");
		document.adminLogin.userPw.focus();
	} else {
		document.adminLogin.submit();
	}
}
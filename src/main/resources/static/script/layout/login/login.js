document.addEventListener('DOMContentLoaded', (event) => {
    // 로그인 실패 시 alert 메시지 표시
    if (loginError) {
        showAlert('아이디 또는 비밀번호가 올바르지 않습니다.');
    }

    // 글 작성은 로그인 후 가능합니다 alert 메시지 표시
    if (writeSession) {
        showAlert('글 작성은 로그인 후 가능합니다.');
    }

    // 좋아요 버튼은 로그인 후 가능합니다 alert 메시지 표시
    if (likeSession) {
        showAlert('좋아요 버튼은 로그인 후 이용 가능합니다.');
    }
});

function showAlert(message) {
    alert(message);
}

function checkPasswordMatch() {
    const password = document.getElementById('userPw').value;
    const confirmPassword = document.getElementById('userPwConfirm').value;
    const message = document.getElementById('passwordMatchMessage');

    if (password !== confirmPassword) {
        message.textContent = '비밀번호가 일치하지 않습니다.';
        return false;
    } else {
        message.textContent = '';
        return true;
    }
}

document.addEventListener('DOMContentLoaded', (event) => {
    const nicknameInput = document.getElementById('userNickname');
    if (nicknameInput) {
        nicknameInput.addEventListener('blur', function() {
            const nickname = this.value;
            fetch(`/mypage/check-nickname?nickname=${nickname}`)
                .then(response => response.text())
                .then(data => {
                    const message = document.getElementById('nicknameMessage');
                    if (data === 'true') {
                        message.textContent = '이미 사용중인 닉네임입니다.';
                    } else {
                        message.textContent = '';
                    }
                });
        });
    }
});
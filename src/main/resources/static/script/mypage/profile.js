document.addEventListener('DOMContentLoaded', (event) => {
    const passwordInput = document.getElementById('userPw');
    const confirmPasswordInput = document.getElementById('userPwConfirm');
    const passwordMatchMessage = document.getElementById('passwordMatchMessage');
    const nicknameInput = document.getElementById('userNickname');
    const phoneInput = document.getElementById('userPhonenumber');
    const phoneMessage = document.getElementById('phoneMessage');

    if (passwordInput && confirmPasswordInput) {
        confirmPasswordInput.addEventListener('input', () => {
            if (passwordInput.value !== confirmPasswordInput.value) {
                passwordMatchMessage.textContent = '비밀번호가 일치하지 않습니다.';
                passwordMatchMessage.style.color = 'red';
            } else {
                passwordMatchMessage.textContent = '비밀번호가 일치합니다.';
                passwordMatchMessage.style.color = 'green';
            }
        });
    }

    if (nicknameInput) {
        nicknameInput.addEventListener('blur', function() {
            const nickname = this.value;
            fetch(`/mypage/check-nickname?nickname=${nickname}`)
                .then(response => response.json())
                .then(data => {
                    const nicknameMessage = document.getElementById('nicknameMessage');
                    if (!data) {
                        nicknameMessage.textContent = '이미 사용중인 닉네임입니다.';
                        nicknameMessage.style.color = 'red';
                        isNicknameValid = false;
                    } else {
                        nicknameMessage.textContent = '사용 가능한 닉네임입니다.';
                        nicknameMessage.style.color = 'green';
                        isNicknameValid = true;
                    }
                });
        });
    }
    if (phoneInput) {
        phoneInput.addEventListener('input', () => {
            const phonePattern = /^\d{11}$/;
            if (!phonePattern.test(phoneInput.value)) {
                phoneMessage.textContent = '전화번호는 11자리 숫자여야 합니다.';
                phoneMessage.style.color = 'red';
            } else {
                phoneMessage.textContent = '';
            }
        });
    }
});

function validateForm() {
    const passwordInput = document.getElementById('userPw');
    const confirmPasswordInput = document.getElementById('userPwConfirm');
    const nicknameInput = document.getElementById('userNickname');
    const phoneInput = document.getElementById('userPhonenumber');

    if (passwordInput.value !== confirmPasswordInput.value) {
        alert('비밀번호가 일치하지 않습니다.');
        return false;
    }

    if (!/^\d{11}$/.test(phoneInput.value)) {
        alert('전화번호는 11자리 숫자여야 합니다.');
        return false;
    }

    // 닉네임 중복 확인을 위한 추가 검증 로직을 넣을 수 있습니다.

    return true;
}

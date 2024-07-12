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

function execDaumPostcode() {
    new daum.Postcode({
        oncomplete: function(data) {
            // 팝업에서 검색결과 항목을 클릭했을때 실행할 코드를 작성하는 부분.
            var fullRoadAddr = data.roadAddress; // 도로명 주소 변수
            var extraRoadAddr = ''; // 도로명 조합형 주소 변수

            // 법정동명이 있을 경우 추가한다. (법정리는 제외)
            // 법정동의 경우 마지막 문자가 "동/로/가"로 끝난다.
            if(data.bname !== '' && /[동|로|가]$/g.test(data.bname)){
                extraRoadAddr += data.bname;
            }
            // 건물명이 있고, 공동주택일 경우 추가한다.
            if(data.buildingName !== '' && data.apartment === 'Y'){
                extraRoadAddr += (extraRoadAddr !== '' ? ', ' + data.buildingName : data.buildingName);
            }
            // 도로명, 지번 조합형 주소가 있을 경우, 괄호까지 추가한 최종 문자열을 만든다.
            if(extraRoadAddr !== ''){
                extraRoadAddr = ' (' + extraRoadAddr + ')';
            }
            // 도로명, 지번 주소의 유무에 따라 해당 조합형 주소를 추가한다.
            if(fullRoadAddr !== ''){
                fullRoadAddr += extraRoadAddr;
            }

            // 우편번호와 주소 정보를 해당 필드에 넣는다.
            document.getElementById('userAddress').value = fullRoadAddr;
        }
    }).open();
}


































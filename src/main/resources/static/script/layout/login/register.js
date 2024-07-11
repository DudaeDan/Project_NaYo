document.addEventListener('DOMContentLoaded', (event) => {
    // 비밀번호 일치 확인
    document.getElementById('user_rePw').addEventListener('input', checkPasswordMatch);

    // 닉네임 중복 체크
    document.getElementById('user_Nickname').addEventListener('blur', checkNickname);
});

function checkPasswordMatch() {
    const pw = document.getElementById('user_Pw').value;
    const rePw = document.getElementById('user_rePw').value;
    const resultMessage = document.getElementById('pw-match-result');

    if (pw === rePw) {
        resultMessage.innerText = '비밀번호가 일치합니다.';
        resultMessage.style.color = 'green';
    } else {
        resultMessage.innerText = '비밀번호가 일치하지 않습니다.';
        resultMessage.style.color = 'red';
    }
}

let isIdChecked = false;
let isNicknameChecked = false;

function checkId() {
    const userIdInput = document.getElementById('user_Id');
    const userId = userIdInput.value;
    const resultMessage = document.createElement('div');
    const formField = userIdInput.parentNode;

    // Remove existing result message if any
    const existingMessage = document.getElementById('check-result');
    if (existingMessage) {
        existingMessage.remove();
    }

    fetch(`/login/check-id?userId=${userId}`)
        .then(response => response.text())
        .then(data => {
            if (data === '아이디가 이미 존재합니다.') {
                resultMessage.innerText = '아이디가 이미 존재합니다.';
                resultMessage.style.color = 'red';
                isIdChecked = false;
            } else {
                resultMessage.innerText = '사용 가능한 아이디입니다.';
                resultMessage.style.color = 'green';
                isIdChecked = true;
            }
            resultMessage.id = 'check-result';
            formField.appendChild(resultMessage);
        })
        .catch(error => {
            resultMessage.innerText = '아이디 중복 확인 중 오류가 발생했습니다.';
            resultMessage.style.color = 'red';
            resultMessage.id = 'check-result';
            formField.appendChild(resultMessage);
            isIdChecked = false;
        });
}

function checkNickname() {
    const nicknameInput = document.getElementById('user_Nickname');
    const nickname = nicknameInput.value;
    const resultMessage = document.createElement('div');
    const formField = nicknameInput.parentNode;

    // Remove existing result message if any
    const existingMessage = document.getElementById('nickname-check-result');
    if (existingMessage) {
        existingMessage.remove();
    }

    fetch(`/login/check-nickname?nickname=${nickname}`)
        .then(response => response.text())
        .then(data => {
            if (data === '닉네임이 이미 존재합니다.') {
                resultMessage.innerText = '닉네임이 이미 존재합니다.';
                resultMessage.style.color = 'red';
                isNicknameChecked = false;
            } else {
                resultMessage.innerText = '사용 가능한 닉네임입니다.';
                resultMessage.style.color = 'green';
                isNicknameChecked = true;
            }
            resultMessage.id = 'nickname-check-result';
            formField.appendChild(resultMessage);
        })
        .catch(error => {
            resultMessage.innerText = '닉네임 중복 확인 중 오류가 발생했습니다.';
            resultMessage.style.color = 'red';
            resultMessage.id = 'nickname-check-result';
            formField.appendChild(resultMessage);
            isNicknameChecked = false;
        });
}

function validateForm() {
    const pwMatchResult = document.getElementById('pw-match-result').innerText;
    if (pwMatchResult !== '비밀번호가 일치합니다.') {
        alert('비밀번호가 일치하지 않습니다.');
        return false;
    }
    if (!isIdChecked) {
        alert('아이디 중복 확인을 해주세요.');
        return false;
    }
    if (!isNicknameChecked) {
        alert('닉네임 중복 확인을 해주세요.');
        return false;
    }
    return true;
}

//주소찾기

function execDaumPostcode() {
    new daum.Postcode({
        oncomplete: function(data) {
            // 팝업에서 검색결과 항목을 클릭했을때 실행할 코드를 작성하는 부분.

            // 도로명 주소의 노출 규칙에 따라 주소를 조합한다.
            var fullRoadAddr = data.roadAddress; // 도로명 주소 변수
            var extraRoadAddr = ''; // 도로명 조합형 주소 변수

            // 법정동명이 있을 경우 추가한다. (법정리는 제외)
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

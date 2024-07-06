function confirmDelete(boardId) {
    const password = prompt("비밀번호를 입력해주세요:");
    if (password != null) {
        deleteBoard(boardId, password);
    }
}

function deleteBoard(boardId, password) {
    fetch(`/board/delete/${boardId}`, {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify({ password: password })
    })
    .then(response => response.text())
    .then(result => {
        if (result === 'success') {
            alert("게시글이 삭제되었습니다.");
            window.location.href = '/board/list';
        } else if (result === 'invalidPassword') {
            alert("비밀번호가 일치하지 않습니다.");
        } else {
            alert("오류가 발생했습니다. 다시 시도해주세요.");
        }
    })
    .catch(error => {
        console.error('Error:', error);
    });
}
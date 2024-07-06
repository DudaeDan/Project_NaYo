function confirmDelete(boardNumber) {
    const userPw = prompt("비밀번호를 입력해주세요:");
    if (userPw != null && userPw !== "") {
        const xhr = new XMLHttpRequest();
        xhr.open("POST", `/board/delete/${boardNumber}`, true);
        xhr.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");
        xhr.onreadystatechange = function () {
            if (xhr.readyState === XMLHttpRequest.DONE) {
                if (xhr.status === 200) {
                    if (xhr.responseText === "deleted") {
                        alert("게시물이 삭제되었습니다.");
                        window.location.href = "/board/list";
                    } else if (xhr.responseText === "incorrectPassword") {
                        alert("비밀번호가 일치하지 않습니다.");
                    } else {
                        alert("삭제 중 오류가 발생했습니다.");
                    }
                }
            }
        };
        xhr.send(`userPw=${encodeURIComponent(userPw)}`);
    }
}
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

function addComment(boardId) {
    const content = document.getElementById('commentContent').value;
    if (!content.trim()) {
        alert('댓글 내용을 입력해주세요');
        return;
    }

    const xhr = new XMLHttpRequest();
    xhr.open("POST", "/comments/add", true);
    xhr.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");
    xhr.onreadystatechange = function () {
        if (xhr.readyState === XMLHttpRequest.DONE) {
            if (xhr.status === 200) {
                if (xhr.responseText === "success") {
                    alert("댓글이 추가되었습니다");
                    location.reload();
                } else if (xhr.responseText === "notLoggedIn") {
                    alert("로그인 후 이용할 수 있습니다");
                } else {
                    alert("댓글 추가 중 오류가 발생했습니다");
                }
            }
        }
    };
    xhr.send(`boardId=${boardId}&content=${encodeURIComponent(content)}`);
}

function addReply(commentId) {
    const content = document.getElementById(`replyContent-${commentId}`).value;
    if (!content.trim()) {
        alert('답글 내용을 입력해주세요');
        return;
    }

    const xhr = new XMLHttpRequest();
    xhr.open("POST", "/replies/add", true);
    xhr.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");
    xhr.onreadystatechange = function () {
        if (xhr.readyState === XMLHttpRequest.DONE) {
            if (xhr.status === 200) {
                if (xhr.responseText === "success") {
                    alert("답글이 추가되었습니다");
                    location.reload();
                } else if (xhr.responseText === "notLoggedIn") {
                    alert("로그인 후 이용할 수 있습니다");
                } else {
                    alert("답글 추가 중 오류가 발생했습니다");
                }
            }
        }
    };
    xhr.send(`commentId=${commentId}&content=${encodeURIComponent(content)}`);
}

function showReplyForm(commentId) {
    console.log(`Trying to find reply form with ID: replyForm-${commentId}`);
    const replyForm = document.getElementById(`replyForm-${commentId}`);
    if (replyForm) {
        if (replyForm.style.display === 'none' || replyForm.style.display === '') {
            replyForm.style.display = 'block';
        } else {
            replyForm.style.display = 'none';
        }
    } else {
        console.error(`Reply form with ID replyForm-${commentId} not found`);
    }
}

function scrollToComment(commentId) {
    const commentElement = document.getElementById(`comment-${commentId}`);
    if (commentElement) {
        commentElement.scrollIntoView({ behavior: 'smooth' });
    } else {
        console.error(`Comment with ID comment-${commentId} not found`);
    }
}

function deleteComment(commentId) {
    if (confirm("정말로 댓글을 삭제하시겠습니까?")) {
        const xhr = new XMLHttpRequest();
        xhr.open("POST", `/comments/delete/${commentId}`, true);
        xhr.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");
        xhr.onreadystatechange = function () {
            if (xhr.readyState === XMLHttpRequest.DONE) {
                if (xhr.status === 200) {
                    if (xhr.responseText === "deleted") {
                        alert("댓글이 삭제되었습니다.");
                        location.reload();
                    } else {
                        alert("댓글 삭제 중 오류가 발생했습니다.");
                    }
                }
            }
        };
        xhr.send();
    }
}

function deleteReply(replyId) {
    if (confirm("정말로 답글을 삭제하시겠습니까?")) {
        const xhr = new XMLHttpRequest();
        xhr.open("POST", `/replies/delete/${replyId}`, true);
        xhr.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");
        xhr.onreadystatechange = function () {
            if (xhr.readyState === XMLHttpRequest.DONE) {
                if (xhr.status === 200) {
                    if (xhr.responseText === "deleted") {
                        alert("답글이 삭제되었습니다.");
                        location.reload();
                    } else {
                        alert("답글 삭제 중 오류가 발생했습니다.");
                    }
                }
            }
        };
        xhr.send();
    }
}
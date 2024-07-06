function toggleLike(boardId) {
    const likeIcon = document.getElementById('like-icon');
    const likeCount = document.getElementById('like-count');
    fetch(`/board/like/${boardId}`, {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json'
        }
    })
    .then(response => response.text())
    .then(status => {
        if (status === 'liked') {
            likeIcon.src = '/Images/border/like.png';
            likeCount.textContent = parseInt(likeCount.textContent) + 1;
        } else if (status === 'unliked') {
            likeIcon.src = '/Images/border/empty_like.png';
            likeCount.textContent = parseInt(likeCount.textContent) - 1;
        } else if (status === 'notLoggedIn') {
            alert("로그인 후 이용 가능합니다.");
        }
    })
    .catch(error => console.error('Error:', error));
}

function alertLogin() {
    alert("로그인 후 이용 가능합니다.");
}
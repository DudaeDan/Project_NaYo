function toggleCommentLike(commentId) {
    const commentLikeIcon = document.querySelector(`#comment-${commentId} .like-icon`);
    const commentLikeCount = document.querySelector(`#comment-${commentId} .like-count`);
    fetch(`/comments/like/${commentId}`, {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json'
        }
    })
    .then(response => response.text())
    .then(status => {
        if (status === 'liked') {
            commentLikeIcon.src = '/Images/border/like.png';
            commentLikeCount.textContent = parseInt(commentLikeCount.textContent) + 1;
        } else if (status === 'unliked') {
            commentLikeIcon.src = '/Images/border/empty_like.png';
            commentLikeCount.textContent = parseInt(commentLikeCount.textContent) - 1;
        } else if (status === 'notLoggedIn') {
            alert("로그인 후 이용 가능합니다.");
        }
    })
    .catch(error => console.error('Error:', error));
}

function toggleReplyLike(replyId) {
    const replyLikeIcon = document.querySelector(`#reply-${replyId} .like-icon`);
    const replyLikeCount = document.querySelector(`#reply-${replyId} .like-count`);
    fetch(`/replies/like/${replyId}`, {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json'
        }
    })
    .then(response => response.text())
    .then(status => {
        if (status === 'liked') {
            replyLikeIcon.src = '/Images/border/like.png';
            replyLikeCount.textContent = parseInt(replyLikeCount.textContent) + 1;
        } else if (status === 'unliked') {
            replyLikeIcon.src = '/Images/border/empty_like.png';
            replyLikeCount.textContent = parseInt(replyLikeCount.textContent) - 1;
        } else if (status === 'notLoggedIn') {
            alert("로그인 후 이용 가능합니다.");
        }
    })
    .catch(error => console.error('Error:', error));
}

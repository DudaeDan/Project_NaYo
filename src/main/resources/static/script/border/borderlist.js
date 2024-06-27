function changePage(page) {
    let currentPage = /*[[${currentPage}]]*/ 0;
    let totalPages = /*[[${totalPages}]]*/ 1;

    if (page === 'prev') {
        if (currentPage > 1) {
            currentPage--;
        }
    } else if (page === 'next') {
        if (currentPage < totalPages) {
            currentPage++;
        }
    } else {
        currentPage = page;
    }

    window.location.href = `?page=${currentPage}`;
}

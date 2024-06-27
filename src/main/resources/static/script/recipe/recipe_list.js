        function changePage(page) {
            const size = /*[@requestParam('size') or default value]*/ 30; // 페이지 당 항목 수를 설정합니다.
            window.location.href = '/recipes?page=' + page + '&size=' + size;
        }
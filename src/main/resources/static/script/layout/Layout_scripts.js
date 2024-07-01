/*function toggleSidebar() {
    var sidebar = document.querySelector('.sidebar');
    var menuButton = document.querySelector('.menu-button');
    if (sidebar.classList.contains('show')) {
        sidebar.classList.remove('show');
        menuButton.style.display = 'block';
    } else {
        sidebar.classList.add('show');
        menuButton.style.display = 'none';
    }
}*/


/*function toggleSidebar() {
    var sidebar = document.querySelector('.sidebar');
    var mainContent = document.querySelector('.main-content');
    sidebar.classList.toggle('show');

    if (sidebar.classList.contains('show')) {
        mainContent.style.marginLeft = '220px'; // 사이드바 폭 + 여백만큼
    } else {
        mainContent.style.marginLeft = '0';
    }
}
*/

// Layout_scripts.js 파일
document.addEventListener("DOMContentLoaded", function () {
    const sidebar = document.querySelector(".sidebar");
    const mainContent = document.querySelector(".main-content");
    const menuButton = document.querySelector(".menu-button");
    const closeButton = document.querySelector(".close-button");

    function toggleSidebar() {
        sidebar.classList.toggle("show");
        if (sidebar.classList.contains("show")) {
            mainContent.style.marginLeft = "250px";
        } else {
            mainContent.style.marginLeft = "0";
        }
    }

    menuButton.addEventListener("click", toggleSidebar);
    closeButton.addEventListener("click", toggleSidebar);
});

function toggleSidebar() {
    var sidebar = document.querySelector('.sidebar');
    var menuButton = document.querySelector('.menu-button');
    if (sidebar.classList.contains('show')) {
        sidebar.classList.remove('show');
        menuButton.style.display = 'block';
    } else {
        sidebar.classList.add('show');
        menuButton.style.display = 'none';
    }
}

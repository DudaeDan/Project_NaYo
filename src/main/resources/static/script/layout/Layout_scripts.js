function toggleSidebar() {
    var sidebar = document.querySelector('.sidebar');
    if (sidebar.classList.contains('show')) {
        sidebar.classList.remove('show');
    } else {
        sidebar.classList.add('show');
    }
}

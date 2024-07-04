
// 리스트 페이징
function navigateToPage(page) {
	const url = new URL(window.location.href);
	url.searchParams.set('page', page);
	window.location.href = url.toString();
}
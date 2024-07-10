// =========================
// refrigerator_main에서 사용
// =========================

//페이지 로드시 유통기한 지난 식재료 css 변경
window.onload = function() {
	const rows = document.querySelectorAll('.refriListRow');
	const today = new Date();
	rows.forEach(row => {
		const expDateCell = row.querySelector('.list_exp_date');
		const expDate = new Date(expDateCell.textContent.trim());
		if (expDate < today) {
			row.classList.add('expired');
		}
	});
};

//페이징
function navigateToPage(page) {
	const url = new URL(window.location.href);
	url.searchParams.set('page', page);
	window.location.href = url.toString();
}


//최상단 체크박스 클릭시 모든 체크박스 온 오프
function allCheck(target) {
	const allCheckbox = document.getElementById('allCheckbox');
	const is_checked = allCheckbox.checked;
	if (is_checked) {
		checkAllCheckbox();
	} else {
		uncheckAllCheckbox();
	}
}

//개별 체크박스 클릭시
function boxCheck() {
	//전체 체크박스 수
	const allBoxCount = document.querySelectorAll('.listCheckbox').length;

	const checked = 'input[name="listCheckbox"]:checked';
	const selectedElements = document.querySelectorAll(checked);
	const selectedElementsCount = selectedElements.length;

	if (allBoxCount == selectedElementsCount) {
		document.getElementById('allCheckbox').checked = true;
	} else {
		document.getElementById('allCheckbox').checked = false;
	}
}

//체크박스 전체 체크
function checkAllCheckbox() {
	document.querySelectorAll(".listCheckbox").forEach(function(c, i) {
		c.checked = true;
	});
}
//체크박스 전체 해체
function uncheckAllCheckbox() {
	document.querySelectorAll(".listCheckbox").forEach(function(c, i) {
		c.checked = false;
	});
}

//삭제할 정보 전송
function refriDeleteConfirm() {
	const selectedItems = [];
	document.querySelectorAll('.listCheckbox:checked').forEach(function(checkbox) {
		const refrigeratorNumber = checkbox.closest('.refriListRow').querySelector('.refrigeratorNumber').value;
		selectedItems.push(parseInt(refrigeratorNumber, 10));
	});

	if (selectedItems.length > 0) {
		$.ajax({
			type: "POST",
			url: "/refrigerator/ingredientDelete",
			contentType: "application/json",
			data: JSON.stringify(selectedItems),
			success: function() {
				console.log("성공");
				window.location.href = "/refrigerator";
			},
			error: function() {
				console.error("실패");
				alert("오류가 발생했습니다");
				window.location.href = "/refrigerator";
			}
		});
	} else {
		alert('삭제할 항목을 선택해주세요');
	}
}


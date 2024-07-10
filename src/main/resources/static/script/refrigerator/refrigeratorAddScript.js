// =========================
// refrigerator_add에서 사용
// =========================

//재료 리스트에서 버튼 클릭시 선택 리스트 테이블에 행 추가
function addIngredient(button) {
	var ingredientName = button.querySelector('.btnName').textContent.trim();
	var category = button.querySelector('.btnCategory').value.trim();
	var IngreNum = button.querySelector('.btnIngreNum').value.trim();
	var rowId = category + ingredientName;

	var tableBody = document.getElementById("selectedTableBody");
	var rowCheck = document.querySelector(`#${rowId}`);

	//행이 있으면 삭제 없으면 추가
	if (rowCheck) { 
		rowCheck.remove();
		button.classList.remove('btn-selected');
	} else {
		// 새로운 행의 HTML 생성
		var newRowHtml = `
	            <tr id='${rowId}'>
	                <td>${category}</td>
	                <td>${ingredientName}</td>
	                <td><input type='date' name='expDate' class='ingredientExp' /></td>
					<input type='hidden' name='refIngredientNumber' class='refIngredientNumber' value='${IngreNum}'/>
	            </tr>
	        `;
		button.classList.add('btn-selected');
		// 새로운 행을 테이블 바디에 추가
		tableBody.insertAdjacentHTML('beforeend', newRowHtml);
		//유통기한 기본값 설정
		var newRow = tableBody.lastElementChild;
		var expDate = newRow.querySelector('.ingredientExp');
		setExpDate(expDate);
	}
}

//유통기한 기본값 설정
function setExpDate(element) {
	let now = new Date();
	//기본 7일 뒤
	element.value = new Date(now.setDate(now.getDate() + 7)).toISOString().substring(0, 10);
}

//이벤트 리스너
document.addEventListener('DOMContentLoaded', function() {
	var expElements = document.getElementsByClassName('ingredientExp');
	for (var i = 0; i < expElements.length; i++) {
		setExpDate(expElements[i]);
	}
});

//카테고리 변환
function changeCategory(category) {
	var categories = document.querySelectorAll('.category');
	categories.forEach(function(cat) {
		cat.style.display = 'none';
	});
	document.getElementById(category).style.display = 'block';
}

//컨트롤러로 정보 전달
function ingredientAddConfirm() {
	var selectedRows = $("#selectedTableBody tr");
	var selectedData = [];
	var userNumber = $(".userNumber").val().trim();

	// 각 행에서 필요한 데이터를 수집하여 selectedData 배열에 저장합니다.
	selectedRows.each(function() {
		var expDate = $(this).find('.ingredientExp').val().trim();
		var refIngredientNumber = $(this).find('.refIngredientNumber').val().trim();
		selectedData.push({
			userNumber: userNumber,
			expDate: expDate,
			refIngredientNumber: refIngredientNumber
		});
	});

	// AJAX 요청을 설정합니다.
	$.ajax({
		type: "POST",
		url: "/refrigerator/ingredientAddConfirm",
		contentType: "application/json",
		data: JSON.stringify(selectedData),
		success: function() {
			console.log("성공");
			window.location.href = "/refrigerator";
		},
		error: function() {
			console.error("실패");
			alert("오류가 발생했습니다");
			window.location.href = "/refrigerator/addIngredient";
		}
	});
}



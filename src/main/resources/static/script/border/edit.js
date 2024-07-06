function previewMainImage(event) {
    const reader = new FileReader();
    reader.onload = function() {
        const output = document.getElementById('mainImgPreview');
        output.src = reader.result;
        output.style.display = 'block';
    };
    reader.readAsDataURL(event.target.files[0]);
}

function previewStepImage(event, input) {
    const reader = new FileReader();
    reader.onload = function() {
        const output = input.parentElement.querySelector('img');
        if (output) {
            output.src = reader.result;
            output.style.display = 'block';
        } else {
            const img = document.createElement('img');
            img.src = reader.result;
            img.className = 'step-img';
            input.parentElement.appendChild(img);
        }
    };
    reader.readAsDataURL(event.target.files[0]);
}

function addIngredient() {
    const ingredientList = document.getElementById('ingredientList');
    const newIngredient = document.createElement('div');
    newIngredient.classList.add('ingredient-item');
    newIngredient.innerHTML = `
        <input type="text" name="ingredientNames" placeholder="예) 소고기" required style="resize: none;">
        <input type="text" name="ingredientAmounts" placeholder="예) 500g" required style="resize: none;">
        <button type="button" class="btn btn-danger" onclick="removeIngredient(this)">삭제</button>
    `;
    ingredientList.appendChild(newIngredient);
}

function removeIngredient(button) {
    const ingredient = button.parentElement;
    ingredient.remove();
}

function addStep() {
    const stepList = document.getElementById('stepList');
    const newStep = document.createElement('div');
    newStep.classList.add('step-item');
    newStep.innerHTML = `
        <img src="#" alt="step_img" class="step-img" style="display: none;">
        <textarea name="stepDescriptions" rows="5" placeholder="예) 물 1L기준 설탕 1숟가락을 넣고 고기를 담가 30분간 핏물을 제거해주세요" required style="resize: none;"></textarea>
        <input type="file" name="stepImages" accept="image/*" onchange="previewStepImage(event, this)" required>
        <button type="button" class="btn btn-danger" onclick="removeStep(this)">삭제</button>
    `;
    stepList.appendChild(newStep);
}

function removeStep(button) {
    const step = button.parentElement;
    step.remove();
}

function validateForm() {
    const stepList = document.getElementById('stepList');
    const ingredientList = document.getElementById('ingredientList');
    const mainImgFile = document.getElementById('mainImgFile');

    if (stepList.children.length === 0) {
        alert("요리 과정을 입력해주세요");
        return false;
    }

    if (ingredientList.children.length === 0) {
        alert("요리 재료를 입력해주세요");
        return false;
    }

    if (!mainImgFile.value) {
        alert("대표 이미지를 선택해주세요");
        return false;
    }

    const stepImages = document.querySelectorAll('input[name="stepImages"]');
    for (let stepImage of stepImages) {
        if (!stepImage.value) {
            alert("과정 이미지를 추가해주세요");
            return false;
        }
    }

    return true;
}
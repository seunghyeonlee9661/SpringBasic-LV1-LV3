// Validation 확인하는 함수
function checkValidity(form) {
  if (form.checkValidity() === false) {
    event.preventDefault();
    event.stopPropagation();
    form.classList.add('was-validated');
    return false;
  }
  form.classList.add('was-validated');
  return true;
}

// Ajax Request 함수
function Request(url, type, data, successCallback, errorCallback) {
  $.ajax({
    url: url,
    type: type,
    contentType: "application/json",
    data: JSON.stringify(data),
    success: function(response) {
      successCallback(response);
    },
    error: function(xhr) {
      errorCallback(xhr.responseText);
    }
  });
}

// Ajax Request 함수
function Request_GET(url, type, data, successCallback, errorCallback) {
  $.ajax({
    url: url,
    type: type,
    dataType: 'json',
    data: data,
    success: function(response) {
      successCallback(response);
    },
    error: function(xhr) {
      errorCallback(xhr.responseText);
    }
  });
}

// 회원가입 요청
function signupRequest(data,successCallback,errorCallback) {
  if (data.department == "marketing" && data.role == "MANAGER") {
    alert("마케팅 부서는 관리자 권한을 받을 수 없습니다.");
  } else {
    Request('/backoffice/api/signup', 'POST', data, successCallback,errorCallback);
  }
}

// 로그인 요청
function loginRequest(data, successCallback,errorCallback) {
  Request('/backoffice/api/login', 'POST',data, successCallback,errorCallback)
}

function logoutRequest() {
        if(confirm("로그아웃 하시겠습니까?")){
            Cookies.remove('Authorization', { path: '/' });
            location.href = location.href;
        }
    }

// 모든 강사 요청
function teachersRequest(data, successCallback,errorCallback) {
  Request('/backoffice/api/teachers', 'GET', data,successCallback,errorCallback);
}

// 강의 요청
function lecturesRequest(data, successCallback,errorCallback) {
  Request_GET('/backoffice/api/lectures', 'GET', data,successCallback,errorCallback);
}

function setPagination(paging) {
  var paginationHtml = '';
  paginationHtml += '<li class="page-item ' + (!paging.hasPrevious ? 'disabled' : '') + '">';
  paginationHtml += '<a class="page-link" href="javascript:void(0)" data-page="' + (paging.number - 1) + '">Previous</a>';
  paginationHtml += '</li>';

  for (var i = 0; i < paging.totalPages; i++) {
      if (i >= paging.number - 5 && i <= paging.number + 5) {
          paginationHtml += '<li class="page-item ' + (i === paging.number ? 'active' : '') + '">';
          paginationHtml += '<a class="page-link" href="javascript:void(0)" data-page="' + i + '">' + i + '</a>';
          paginationHtml += '</li>';
      }
  }

  paginationHtml += '<li class="page-item ' + (!paging.hasNext ? 'disabled' : '') + '">';
  paginationHtml += '<a class="page-link" href="javascript:void(0)" data-page="' + (paging.number + 1) + '">Next</a>';
  paginationHtml += '</li>';

  document.getElementById('pagination').innerHTML = paginationHtml;

  // 페이지네이션 버튼 기능
  const page_elements = document.getElementsByClassName("page-link");
      Array.from(page_elements).forEach(function(element) {
      element.addEventListener('click', function() {
          document.getElementById('page').value = this.dataset.page;
          console.log(this.dataset.page);
          document.getElementById('searchForm').submit();
      });
  });
}